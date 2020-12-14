package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.model.LabTestSpeciman;
import com.zainimtiaz.nagarro.repository.LabTestSpecimanRepository;
import com.zainimtiaz.nagarro.repository.PrefixRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LabTestSpecimanService {

    @Autowired
    LabTestSpecimanRepository labTestSpecimanRepository;
    @Autowired
    private PrefixRepository prefixRepository;
    @Autowired
    private HISUtilService hisUtilService;

    public List<LabTestSpeciman> getAll(){
        return labTestSpecimanRepository.getAll();
    }

    public void saveConfiguration(LabTestSpeciman labTestSpeciman){
      //  Prefix pr = this.prefixRepository.findByModule(ModuleEnum.LABTEST.name());
        labTestSpeciman.setSpecimanId(hisUtilService.getPrefixId(ModuleEnum.LAB_TEST));

        labTestSpecimanRepository.save(labTestSpeciman);
    }


    @Transactional
    public boolean delete(long id) {
      //  LabTestSpeciman labTestSpeciman = this.labTestSpecimanRepository.findOne(id);
        if (id > 0) {
            this.labTestSpecimanRepository.delete(id);
            return true;
        }
        return false;
    }

    public boolean isNameDuplicateByNameAndNotEqualId(long id, String name) {
        return this.labTestSpecimanRepository.getNameAndEqualId(id, name);
    }


    @Transactional
    public String update(LabTestSpeciman labTestSpeciman) {
        LabTestSpeciman labTestSpecimanObj = this.labTestSpecimanRepository.findOne(labTestSpeciman.getId());
        labTestSpecimanObj.setMaxNormalRange(labTestSpeciman.getMaxNormalRange());
        labTestSpecimanObj.setMinNormalRange(labTestSpeciman.getMinNormalRange());
        labTestSpecimanObj.setTestCode(labTestSpeciman.getTestCode());
        labTestSpecimanObj.setTestName(labTestSpeciman.getTestName());
        labTestSpecimanObj.setDescription(labTestSpeciman.getDescription());
        labTestSpecimanObj.setUnit(labTestSpeciman.getUnit());
       // new LabTestSpeciman(labTestSpecimanObj);
        this.labTestSpecimanRepository.save(labTestSpecimanObj);
        return "";
    }
}
