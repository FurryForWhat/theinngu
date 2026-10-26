package com.theinngu32.common.service.excel;

import com.theinngu32.common.dto.FileUploadRequestDto;
import com.theinngu32.common.entity.TempYogi;
import com.theinngu32.common.entity.importfile.MasterImportFile;
import com.theinngu32.common.exception.FileUploadFailedException;
import com.theinngu32.common.exception.UnsupportedFileException;
import com.theinngu32.common.repository.TempYogiRepository;
import com.theinngu32.common.service.storage.StorageService;
import com.theinngu32.common.utils.constants.FileTypes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.theinngu32.common.utils.FileUtils.getFileExtension;
import static com.theinngu32.common.utils.constants.FileExtensions.XLS;
import static com.theinngu32.common.utils.constants.FileExtensions.XLSX;

@Service
@Scope("prototype")
@RequiredArgsConstructor
@Slf4j
public class ProcessExcelService {
    private final ForeignYogiExcelService foreignYogiExcelService;
    private final LocalYogiExcelService localYogiExcelService;
    private final StorageService storageService;
    private final TempYogiRepository tempYogiRepository;

    private String className;
    private String fileType;
    private Long id;
    private MasterImportFile masterImportFile;

    @Transactional
    public void runFile(MasterImportFile masterImportFile) {
        this.masterImportFile = masterImportFile;
        prepareFileParams();
        List<TempYogi> tempLocalYogis = localYogiExcelService.extractExcelToTempObject(className, id, fileType);
        List<TempYogi> tempForeignYogis = foreignYogiExcelService.extractExcelToTempObject(className, id, fileType);

        this.tempYogiRepository.saveAll(tempLocalYogis);
        this.tempYogiRepository.saveAll(tempForeignYogis);
    }

    public String getFileDownloadUrl(MasterImportFile masterImportFile) {
        this.masterImportFile = masterImportFile;
        prepareFileParams();
        return this.storageService.getFileUrl(this.className, this.id, this.fileType);
    }

    public void uploadAndSaveFile(MasterImportFile masterImportFile){
        this.masterImportFile = masterImportFile;
        prepareFileParams();
        MultipartFile file = masterImportFile.getImportFile();

        if (!validExcelFile(file)) {
            throw new UnsupportedFileException("Unsupported file type : " + file.getOriginalFilename());
        }

        try {
            FileUploadRequestDto dto = new FileUploadRequestDto(
                    file.getBytes(),
                    file.getOriginalFilename(),
                    file.getSize(),
                    file.getContentType(),
                    getFileExtension(file.getOriginalFilename()));
            storageService.uploadAndSave(dto, fileType, className, id);
        } catch (IOException e) {
            throw new FileUploadFailedException("Failed to get bytes from file : " + file.getOriginalFilename() ,e);
        }
    }

    private void prepareFileParams() {
        this.className = masterImportFile.getClass().getSimpleName();
        this.id = masterImportFile.getId();
        this.fileType = FileTypes.YogiImportFile;
    }

    private boolean validExcelFile(MultipartFile file) {
        return List.of(XLSX, XLS).contains(getFileExtension(file.getOriginalFilename()));
    }
}
