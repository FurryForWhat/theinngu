package com.theinngu32.common.utils.validation;

import com.theinngu32.common.entity.City;
import com.theinngu32.common.entity.NrcCode;
import com.theinngu32.common.entity.constants.Gender;
import com.theinngu32.common.entity.constants.NrcType;
import com.theinngu32.common.repository.CityRepository;
import com.theinngu32.common.repository.NrcCodeRepository;
import com.theinngu32.common.repository.OldYogiRepository;
import com.theinngu32.common.repository.YogiRepository;
import com.theinngu32.common.utils.NrcUtil;
import com.theinngu32.common.utils.constants.RegexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Slf4j
@RequiredArgsConstructor
public class IntegrityCheckerUtils {
    private static final Map<String, List<String>> locationCache = new HashMap<>();
    private static final Map<String, Map<String, List<String>>> nrcCache = new HashMap<>();

    private final CityRepository cityRepository;
    private final NrcCodeRepository nrcRepository;
    private final YogiRepository yogiRepository;
    private final OldYogiRepository oldYogiRepository;

    @PostConstruct
    public void init() {
        refreshLocationCache();
        refreshNrcCache();
    }

    /** Refresh Region → City Cache (Myanmar and English names) */
    private void refreshLocationCache() {
        synchronized (locationCache) {
            locationCache.clear();
            List<City> cities = cityRepository.findAll();

            for (City city : cities) {
                String regionNameEn = city.getRegion().getNameEn();
                String cityNameEn = city.getNameEn();
                String cityNameMm = city.getNameMm();

                if (cityNameEn != null) {
                    locationCache.computeIfAbsent(regionNameEn, k -> new ArrayList<>()).add(cityNameEn);
                }
                if (cityNameMm != null) {
                    locationCache.computeIfAbsent(regionNameEn, k -> new ArrayList<>()).add(cityNameMm);
                }
            }
        }
    }

    /** Refresh NRC Format Cache (District Code → Region → Citizenship Type) */
    private void refreshNrcCache() {
        synchronized (nrcCache) {
            nrcCache.clear();
            List<NrcCode> nrcList = nrcRepository.findAll();

            for (NrcCode nrc : nrcList) {
                String districtCode = String.valueOf(nrc.getPrefixCode());
                String regionNameEn = nrc.getNameEn();
                String regionNameMm = nrc.getNameMm();

                nrcCache.computeIfAbsent(districtCode, k -> new HashMap<>())
                        .computeIfAbsent(regionNameEn, k -> new ArrayList<>()).add(regionNameMm);
                nrcCache.computeIfAbsent(districtCode, k -> new HashMap<>())
                        .computeIfAbsent(regionNameMm, k -> new ArrayList<>()).add(regionNameEn);
            }
        }
    }

    /** Validate Region → City (Both English and Myanmar names are valid) */
    public static boolean isValidLocation(String region, String city) {
        return locationCache.containsKey(region) && locationCache.get(region).contains(city);
    }

    /** Validate NRC Format (District Code → Region) */
    public static boolean isValidNrc(String nrc) {
        return Optional.ofNullable(nrc)
                .flatMap(NrcUtil::parseNRC)
                .map(nrcData -> isValidNrc(nrcData.prefixCode(), nrcData.regionName(), nrcData.type().getMmName(), nrcData.serialNumber()))
                .orElse(false);
    }

    /** Validate NRC Format (District Code → Region) */
    public static boolean isValidNrc(String districtCode, String regionName, String citizenshipType, String serialNumber) {
        return nrcCache.containsKey(districtCode) &&
                nrcCache.get(districtCode).containsKey(regionName) &&
                nrcCache.get(districtCode).get(regionName).contains(citizenshipType) &&
                serialNumber.matches("\\d{6}");
    }

    public static boolean isValidDate(String dayStr, String monthStr, String yearStr) {
        try {
            int day = Integer.parseInt(dayStr);
            int month = Integer.parseInt(monthStr);
            int year = Integer.parseInt(yearStr);
            LocalDate.of(year, month, day); // Validate date
            return true;
        } catch (NumberFormatException | DateTimeException e) {
            return false; // Return false if parsing or date creation fails
        }
    }

    public static boolean isValidGender(String genderType) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equalsIgnoreCase(genderType)) {
                return true;
            }
        }
        return false;
    }

}