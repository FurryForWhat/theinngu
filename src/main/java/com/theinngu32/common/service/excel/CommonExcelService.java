package com.theinngu32.common.service.excel;

import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.exception.ResourceNotFoundException;
import com.theinngu32.common.service.storage.StorageService;
import com.theinngu32.common.utils.CommonExcelUtils;
import com.theinngu32.common.utils.constants.FileTypes;
import com.theinngu32.common.utils.constants.YogiImportFileColumns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
@Scope("prototype")
public abstract class CommonExcelService {

    private final StorageService storageService;
    public abstract Map<Integer, Consumer<String>> getFieldSetters(TempYogi tempYogi);
    public abstract void toTempYogiRow(TempYogi source, Row destination);
    public abstract Sheet getCurrentSheet(Workbook workbook);
    private Sheet sheet;
    private List<Integer> columnsIndex;

    public  void updateExcelWorkBookByYogiObj(TempYogi tempYogi, Row targetRow){
        toTempYogiRow(tempYogi, targetRow);
    }

    public void deleteExcelWorkBookByYogiObj(Row targetRow) {
        int lastRowNum = targetRow.getSheet().getLastRowNum();
        int rowIndex = targetRow.getRowNum();

        if (rowIndex >= 0 && rowIndex < lastRowNum) {
            targetRow.getSheet().shiftRows(rowIndex + 1, lastRowNum, -1);
        }

        targetRow.getSheet().removeRow(targetRow);
    }

    public void updateExcelWorkBookByYogis(List<TempYogi> fixedYogiList) {
        if (fixedYogiList == null || fixedYogiList.isEmpty()) {
            log.info("fixedYogiList is null or empty.");
            return;
        }

        var firstYogi = fixedYogiList.getFirst();
        // Get workbook from S3
        Resource resource = getExcelFileResource(firstYogi.getRecordType(), firstYogi.getRecordId(), firstYogi.getAttachmentName());

        try (FileInputStream fis = new FileInputStream(resource.getFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get sheet and update rows
            var sheet = getCurrentSheet(workbook);
            for (var yogi : fixedYogiList) {
                if (yogi.isNeededModification()) {
                    toTempYogiRow(yogi, sheet.getRow(yogi.getRowIndex()));
                }
            }

            // Save workbook to a temporary file
            File tempFile = File.createTempFile("updated_excel_", ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                workbook.write(fos);
            }

            // Upload updated file back to S3
            boolean success = storageService.uploadFile(
                    tempFile,
                    storageService.getStoragePrefix(firstYogi.getRecordType()),
                    firstYogi.getAttachmentName());
            if (!success) {
                throw new RuntimeException("File upload failed.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error processing Excel file.", e);
        }
    }


    public void createExcelWorkBook(String recordType, Long recordId, String name, List<TempYogi> tempYogis) {
        var key = storageService.getKey(recordType, recordId, name);

        // Get the base workbook template
        Workbook workbook = CommonExcelUtils.getYogiTemplateWorkbook().orElseThrow(
                () -> new RuntimeException("Could not find excel template.")
        );
        Sheet sheet = getCurrentSheet(workbook);

        int rowNum = 2; // Assuming row 0 and 1 are the header
        for (TempYogi yogi : tempYogis) {
            Row row = sheet.getRow(rowNum++);
            toTempYogiRow(yogi, row);
        }

        // Save workbook to a temporary file
        File tempFile;
        try {
            tempFile = File.createTempFile("excel_", ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                workbook.write(fos);
            }
            workbook.close();

            // Upload the file using the defined method
            boolean success = storageService.uploadFile(tempFile, storageService.getStoragePrefix(recordType), key);
            if (!success) {
                throw new RuntimeException("File upload failed.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create or upload Excel workbook", e);
        }
    }

    private int[] getMergedColumnRange(String headerName) {
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            Cell cell = sheet.getRow(mergedRegion.getFirstRow()).getCell(mergedRegion.getFirstColumn());

            if (cell.getStringCellValue().trim().equalsIgnoreCase(headerName) && cell.getRowIndex() == FileTypes.HEADER_INDEX) {
                return new int[]{mergedRegion.getFirstColumn(), mergedRegion.getLastColumn()};
            }
        }
        return new int[]{-1, -1}; // Not found
    }

    public List<Integer> getDobColumnsIndex(){
        return getDateColumnIndexes(YogiImportFileColumns.DOB.getValue());
    }

    public List<Integer> getMonkDateColumnsIndex(){
        return getDateColumnIndexes(YogiImportFileColumns.MONK_DATE.getValue());
    }

    private List<Integer> getDateColumnIndexes(String value) {
        int[] nameCols = getMergedColumnRange(value);
        return getMergedColumnIndexes(nameCols);
    }

    public List<Integer> getNrcColumnIndexes() {
        int[] nameCols = getMergedColumnRange(YogiImportFileColumns.NRC.getValue());
        return getMergedColumnIndexes(nameCols);
    }

    private List<Integer> getMergedColumnIndexes(int[] nameCols) {
        int nameStartCol = nameCols[0];
        int nameEndCol = nameCols[1];
        List<Integer> columnsIndexs = new ArrayList<>();

        if (nameStartCol <= -1) {
            System.out.println("Error: Could not find merged column.");
            return columnsIndexs;
        }

        while (nameStartCol <= nameEndCol) {
            columnsIndexs.add(nameStartCol++);
        }

        return columnsIndexs;
    }

    public List<TempYogi> extractExcelToTempObject(String recordType, Long recordId, String name) {
        Resource resource = getExcelFileResource(recordType, recordId, name);
        List<TempYogi> tempYogis = new ArrayList<>();

        try (InputStream fis = resource.getInputStream(); Workbook workbook = new XSSFWorkbook(fis)) {
            this.sheet = this.getCurrentSheet(workbook);
            Map<String, Integer> headerColumnData = getHeaderColumnsData();
            this.columnsIndex = getColumnsIndex(headerColumnData);

            int totalRows = sheet.getPhysicalNumberOfRows();
            int processedRows = 0;

            for (int i = 2; i < totalRows; i++) {
                Row row = sheet.getRow(i);
                if (row == null || isRowEmpty(row)) continue;

                var tempYogi = toTempYogiObject(row);
                tempYogi.setAttachmentName(name);
                tempYogi.setRecordId(recordId);
                tempYogi.setRecordType(recordType);
                tempYogi.setSheetName(sheet.getSheetName());
                tempYogi.setRowIndex(row.getRowNum());

                tempYogis.add(tempYogi);
                processedRows++;
            }

            // Validate row count consistency
            if (processedRows != tempYogis.size()) {
                throw new RuntimeException("Data Inconsistency Error: Processed row count does not match TempYogi objects.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing Excel file.", e);
        }

        return tempYogis;
    }

    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    private Map<String, Integer> getHeaderColumnsData() {
        Map<String, Integer> columnIndexes = new HashMap<>();
        Row headerRow = sheet.getRow(FileTypes.HEADER_INDEX);
        for (Cell cell : headerRow) {
            columnIndexes.put(cell.getStringCellValue().trim(), cell.getColumnIndex());
        }
        return columnIndexes;
    }

    public Resource getExcelFileResource(String recordType, Long recordId, String name) {
        var key = storageService.getKey(recordType, recordId, name);
        return storageService.retrieveFile(key).orElseThrow(
                () -> new ResourceNotFoundException("Excel file with %s not found.".formatted(key))
        );
    }

    public List<Integer> getColumnsIndex(Map<String, Integer> headerColumnData) {
        List<Integer> columnIndexes = new ArrayList<>();
        headerColumnData.forEach((key, value) -> {
            try {
                YogiImportFileColumns fileColumn = YogiImportFileColumns.fromValue(key);
                if (fileColumn.getValue() != null) {
                    if (fileColumn.getValue().equals(YogiImportFileColumns.NRC.getValue())) {
                        columnIndexes.addAll(getNrcColumnIndexes());
                    } else if(fileColumn.getValue().equals(YogiImportFileColumns.DOB.getValue())) {
                        columnIndexes.addAll(getDobColumnsIndex());
                    } else if(fileColumn.getValue().equals(YogiImportFileColumns.MONK_DATE.getValue())) {
                        columnIndexes.addAll(getMonkDateColumnsIndex());
                    }
                    else {
                        columnIndexes.add(value);
                    }
                }
            }catch (IllegalArgumentException e){
                log.info("Not found in defined Column : {}", key);
            }
        });
        Collections.sort(columnIndexes);
        return columnIndexes;
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) return "N/A";

        return switch (cell.getCellType()) {
            case STRING -> convertMyanmarToEnglishNumbers(cell.getStringCellValue());
            case NUMERIC -> {
                double numericValue = cell.getNumericCellValue();
                String value = (numericValue == (long) numericValue)
                        ? String.valueOf((long) numericValue)  // Integer case
                        : String.valueOf(numericValue);       // Decimal case
                yield convertMyanmarToEnglishNumbers(value);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "N/A";
        };
    }

    // Converts Myanmar numbers to English numbers
    private static String convertMyanmarToEnglishNumbers(String input) {
        return trimSpaces(input)
                .replaceAll("၀", "0")
                .replaceAll("၁", "1")
                .replaceAll("၂", "2")
                .replaceAll("၃", "3")
                .replaceAll("၄", "4")
                .replaceAll("၅", "5")
                .replaceAll("၆", "6")
                .replaceAll("၇", "7")
                .replaceAll("၈", "8")
                .replaceAll("၉", "9");
    }

    // trim space
    private static String trimSpaces(String input) {
        if (input == null) return "";
        return input.trim();
    }

    public TempYogi toTempYogiObject(Row row) {
        TempYogi tempYogi = new TempYogi();
        Map<Integer, Consumer<String>> fieldSetters = getFieldSetters(tempYogi);
        fieldSetters.forEach((index, setter) -> {
            if(this.columnsIndex.contains(index)){
                String value = getCellValue(row.getCell(index));
                setter.accept(value);
            } else {
                setter.accept("N/A");
            }
        });
        return tempYogi;
    }

}
