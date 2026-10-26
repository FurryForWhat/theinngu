package com.theinngu32.common.service.excel;

import com.theinngu32.common.service.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import com.theinngu32.common.entity.TempYogi;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
public class LocalYogiExcelService extends CommonExcelService {

    public LocalYogiExcelService(StorageService storageService) {
        super(storageService);
    }

    @Override
    public Map<Integer, Consumer<String>> getFieldSetters(TempYogi tempYogi) {
        Map<Integer, Consumer<String>> fieldSetters = new HashMap<>();

        fieldSetters.put(1, tempYogi::setName);
        fieldSetters.put(2, tempYogi::setFatherName);
        fieldSetters.put(3, tempYogi::setYogiId);
        fieldSetters.put(4, tempYogi::setLevel);
        fieldSetters.put(5, tempYogi::setNrcPrefixCode);
        fieldSetters.put(6, tempYogi::setNrcDivision);
        fieldSetters.put(7, tempYogi::setNrcType);
        fieldSetters.put(8, tempYogi::setNrcPostFixDigit);
        fieldSetters.put(9, tempYogi::setPhone);
        fieldSetters.put(10, tempYogi::setGender);
        fieldSetters.put(11, tempYogi::setDobDay);
        fieldSetters.put(12, tempYogi::setDobMonth);
        fieldSetters.put(13, tempYogi::setDobYear);
        fieldSetters.put(14, tempYogi::setMonkDay);
        fieldSetters.put(15, tempYogi::setMonkMonth);
        fieldSetters.put(16, tempYogi::setMonkYear);
        fieldSetters.put(17, tempYogi::setEducation);
        fieldSetters.put(18, tempYogi::setRegion);
        fieldSetters.put(19, tempYogi::setCity);
        fieldSetters.put(20, tempYogi::setAddress);
        fieldSetters.put(21, tempYogi::setOtherMedicationCenter);
        fieldSetters.put(22, tempYogi::setHealthStatus);
        fieldSetters.put(23, tempYogi::setFoodNotEaten);
        fieldSetters.put(24, tempYogi::setEmergencyContact);
        fieldSetters.put(25, tempYogi::setYogiStatus);
        fieldSetters.put(26, tempYogi::setRemark);

        return fieldSetters;
    }

    @Override
    public void toTempYogiRow(TempYogi tempYogi, Row row) {

    }

    @Override
    public Sheet getCurrentSheet(Workbook workbook) {
        return workbook.getSheetAt(0);
    }
}
