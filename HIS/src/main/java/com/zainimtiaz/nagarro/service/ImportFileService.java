package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.ImportFile;
import com.zainimtiaz.nagarro.repository.ImportFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImportFileService {

    @Autowired
    private ImportFileRepository importFileRepository;

    public void deleteImportFileData(Long id){
        importFileRepository.delete(id);
    }

    public List<ImportFile> getAll(){
        return importFileRepository.findAll();
    }

    public ImportFile saveImportFileData(ImportFile importFile){
        return importFileRepository.save(importFile);
    }

    public ImportFile saveImportFileData(String fileName, String dupRecOp, String charEncoding){
        return importFileRepository.save(new ImportFile(fileName, dupRecOp, charEncoding));
    }

    public ImportFile saveImportFile(ImportFile importFile){
        return this.importFileRepository.save(importFile);
    }

    public ImportFile getImportFileDataById(Long importFileId) {
        return importFileRepository.getOne(importFileId);
    }

}
