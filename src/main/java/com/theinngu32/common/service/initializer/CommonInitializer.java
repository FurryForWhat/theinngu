package com.theinngu32.common.service.initializer;

import com.theinngu32.common.entity.*;
import com.theinngu32.common.entity.constants.Status;
import com.theinngu32.common.repository.*;
import com.theinngu32.common.utils.FileUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
@Component
@Slf4j
public class CommonInitializer {

    private final JdbcTemplate jdbcTemplate;
    private final RegionRepository regionRepository;
    private final MissionaryGroupRepository missionaryGroupRepository;
    private final TransactionTemplate transactionTemplate;

    public CommonInitializer(JdbcTemplate jdbcTemplate, PlatformTransactionManager transactionManager,
                             RegionRepository regionRepository, MissionaryGroupRepository missionaryGroupRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.regionRepository = regionRepository;
        this.missionaryGroupRepository = missionaryGroupRepository;
        this.transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @PostConstruct
    public void initializeData() {
        initializeEntity("regions", this::parseRegion);
        initializeEntity("cities", this::parseCity);
        initializeEntity("nrc-codes", this::parseNrcCode);
        initializeEntity("levels", this::parseLevel);
        initializeEntity("missionary-group", this::parseMissionaryGroup);
        initializeEntity("sub-missionary-group", this::parseSubMissionaryGroup);
    }

    protected <T> void initializeEntity(String entityName, Function<String, T> parseFunction) {
        transactionTemplate.execute(status -> {
            List<T> entities = readCsvFile(entityName + ".csv", parseFunction);
            if (entities.isEmpty()) {
                log.warn("No entities found to initialize from {}.", entityName);
                return null;
            }

            String tableName = switch (entities.getFirst().getClass().getSimpleName()) {
                case "Region" -> "regions";
                case "City" -> "cities";
                case "NrcCode" -> "nrc_codes";
                case "Level" -> "levels";
                case "MissionaryGroup" -> "missionary_groups";
                case "SubMissionaryGroup" -> "sub_missionary_groups";
                default -> throw new IllegalArgumentException("Unknown entity class: " + entities.getFirst().getClass().getSimpleName());
            };

            // Retrieve existing IDs
            List<Long> existingIds = jdbcTemplate.queryForList("SELECT id FROM %s".formatted(tableName), Long.class);

            // Filter new entities
            entities = entities.stream()
                    .filter(e -> {
                        if (e instanceof MasterData data) {
                            return !existingIds.contains(data.getId());
                        }
                        return false;
                    }).toList();

            if (entities.isEmpty()) {
                log.info("No new entities to insert for {}", entityName);
                return null;
            }

            int count = 0;
            List<Object[]> batchArgs = new ArrayList<>();
            String sql = "";

            //  Separate MissionaryGroup and SubMissionaryGroup logic
            for (T entity : entities) {
                try {
                    if (entity instanceof Region region) {
                        sql = "INSERT INTO regions (id, name_en, name_mm, code, created_at) VALUES (?, ?, ?, ?, ?)";
                        batchArgs.add(new Object[]{
                                region.getId(), region.getNameEn(), region.getNameMm(), region.getCode(), LocalDateTime.now()
                        });
                    } else if (entity instanceof NrcCode nrcCode) {
                        sql = "INSERT INTO nrc_codes (id, details, name_mm, name_en, prefix_code, created_at) VALUES (?, ?, ?, ?, ?, ?)";
                        batchArgs.add(new Object[]{
                                nrcCode.getId(), nrcCode.getDetails(), nrcCode.getNameMm(), nrcCode.getNameEn(),
                                nrcCode.getPrefixCode(), LocalDateTime.now()
                        });
                    } else if (entity instanceof Level level) {
                        sql = "INSERT INTO levels (id, name, created_at) VALUES (?, ?, ?)";
                        batchArgs.add(new Object[]{
                                level.getId(), level.getName(), LocalDateTime.now()
                        });
                    } else if (entity instanceof MissionaryGroup group) {
                        sql = "INSERT INTO missionary_groups (id, name, status, under_control, created_at) VALUES (?, ?, ?, ?, ?)";
                        batchArgs.add(new Object[]{
                                group.getId(), group.getName(), 1,
                                group.isUnderControl(), LocalDateTime.now()
                        });
                    } else if (entity instanceof City city) {
                        sql = "INSERT INTO cities (id, name_mm, region_id, created_at) VALUES (?, ?, ?, ?)";
                        batchArgs.add(new Object[]{
                                city.getId(), city.getNameMm(), city.getRegion().getId(), LocalDateTime.now()
                        });
                    } else if (entity instanceof SubMissionaryGroup subGroup) {
                        sql = "INSERT INTO sub_missionary_groups (id, name, missionary_group_id, code, created_at) VALUES (?, ?, ?, ?, ?)";
                        batchArgs.add(new Object[]{
                                subGroup.getId(), subGroup.getName(), subGroup.getMissionaryGroup().getId(),
                                subGroup.getCode(), LocalDateTime.now()
                        });
                    }
                } catch (Exception e) {
                    log.error("Error preparing entity for batch insert: {}", e.getMessage(), e);
                }
            }

            //  Execute batch insert
            if (!batchArgs.isEmpty()) {
                try {
                    int[] results = jdbcTemplate.batchUpdate(sql, batchArgs);
                    for (int result : results) {
                        if (result > 0) {
                            count++;
                        }
                    }
                } catch (DataAccessException e) {
                    status.setRollbackOnly(); //  Rollback transaction on failure
                }
            }
            log.info("Successfully initialized {} {} entities.", count, entityName);
            return null;
        });
    }

    private <T> List<T> readCsvFile(String fileName, Function<String, T> parseFunction) {
        InputStream inputStream = FileUtils.getResourceAsStream("initial-data/" + fileName);

        if (inputStream == null) {
            log.error("File not found: initial-data/{}", fileName);
            return Collections.emptyList();
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return bufferedReader.lines()
                    .skip(1) // Skip header
                    .map(parseFunction)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Failed to read CSV file: initial-data/{}", fileName, e);
            return Collections.emptyList();
        }
    }

    private Level parseLevel(String line) {
        String[] data = line.split(",");
        Level level = new Level();
        level.setId(Long.parseLong(data[0].trim()));
        level.setName(data[1]);
        return level;
    }

    private MissionaryGroup parseMissionaryGroup(String line){
        String[] data = line.split(",",-1);
        MissionaryGroup missionaryGroup = new MissionaryGroup();
        missionaryGroup.setId(Long.parseLong(data[0]));
        missionaryGroup.setName(data[1]);
        missionaryGroup.setStatus(Status.ACTIVE);
        missionaryGroup.setUnderControl(data[4] == null || !data[4].equalsIgnoreCase("normal_class"));
        return missionaryGroup;
    }

    private SubMissionaryGroup parseSubMissionaryGroup(String line) {
        String[] data = line.split(",");
        MissionaryGroup missionaryGroup = this.missionaryGroupRepository.findById(Long.parseLong(data[2])).orElseThrow();
        SubMissionaryGroup subMissionaryGroup = new SubMissionaryGroup();
        subMissionaryGroup.setId(Long.parseLong(data[0]));
        subMissionaryGroup.setName(data[1]);
        subMissionaryGroup.setMissionaryGroup(missionaryGroup);
        subMissionaryGroup.setCode(data[3]);
        return subMissionaryGroup;
    }


    private NrcCode parseNrcCode(String line) {
        String[] data = line.split(",");
        NrcCode nrcCode = new NrcCode();
        nrcCode.setId(Long.parseLong(data[0].trim()));
        nrcCode.setDetails(data[1].trim());
        nrcCode.setNameMm(data[2].trim());
        nrcCode.setNameEn(data[3].trim());
        nrcCode.setPrefixCode(Integer.parseInt(data[4]));
        return nrcCode;
    }

    private City parseCity(String line) {
        String[] data = line.split(",");
        City city = new City();
        Region region = this.regionRepository.findById(Long.parseLong(data[3])).orElseThrow();
        city.setId(Long.parseLong(data[0].trim()));
        city.setNameMm(data[1]);
        city.setRegion(region);
        return city;
    }

    private Region parseRegion(String line) {
        String[] data = line.split(",");
        Region region = new Region();
        region.setId(Long.parseLong(data[0].trim()));
        region.setNameEn(data[1].trim());
        region.setNameMm(data[2].trim());
        region.setCode(data[3].trim());
        return region;
    }
}