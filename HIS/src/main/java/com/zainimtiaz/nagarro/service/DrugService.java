package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.model.Drug;
import com.zainimtiaz.nagarro.model.DrugManufacturer;
import com.zainimtiaz.nagarro.model.Prefix;
import com.zainimtiaz.nagarro.repository.CountryRepository;
import com.zainimtiaz.nagarro.repository.DrugManufacturerRepository;
import com.zainimtiaz.nagarro.repository.DrugRepository;
import com.zainimtiaz.nagarro.repository.PrefixRepository;
import com.zainimtiaz.nagarro.wrapper.DrugManufacturerWrapper;
import com.zainimtiaz.nagarro.wrapper.DrugWrapper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 10/22/2018.
 */
@Service
public class DrugService {

    @Autowired
    DrugRepository drugRepository;
    @Autowired
    private PrefixRepository prefixRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DrugManufacturerRepository drugManufacturerRepository;

    public boolean isNameDrugDuplicateByName(String name) {
        return this.drugRepository.getDrugByDrugName(name);
    }

    public boolean isNameDrugDuplicateByNameAndNotEqualId(long id, String name) {
        return this.drugRepository.getDrugByDrugNameAndNotEqualId(id, name);
    }

    @Transactional
    public void saveDrug(DrugWrapper drugWrapper) {
        Drug drug = new Drug(drugWrapper);
        drug.setStrengths(drugWrapper.getStrengths());
        DrugManufacturer drugManufacturer = null;

        if (drugWrapper.getDrugMaker() != null && !drugWrapper.getDrugMaker().trim().equals("")
                && !drugWrapper.getDrugMaker().trim().equals("Select Manufacturer")) {
            boolean chkStatusDrugMaker = containsDigit(drugWrapper.getDrugMaker());
            Long numMaker;

            if (chkStatusDrugMaker) {
                numMaker = Long.parseLong(drugWrapper.getDrugMaker());
                drugManufacturer = drugManufacturerRepository.findOne(numMaker);
            } else {
                DrugManufacturerWrapper drugManufacturerWrapper = drugManufacturerRepository.getDrugManufacturerByCompanyName(drugWrapper.getDrugMaker());
                numMaker = drugManufacturerWrapper.getId();
                drugManufacturer = drugManufacturerRepository.findOne(numMaker);
            }
        }

        drug.setDrugManufacturer(drugManufacturer);
        Prefix pr = this.prefixRepository.findByModule(ModuleEnum.DRUG.name());
        pr.setCurrentValue(pr.getCurrentValue() + 1L);
        this.prefixRepository.save(pr);
        this.drugRepository.save(drug);
    }

    public List<DrugWrapper> getPaginatedAllDrugs(Pageable pageable) {
        return this.drugRepository.findAllByCreatedOn(pageable);
    }

    public int countPaginatedAllDrugs() {
        return this.drugRepository.findAll().size();
    }

    @Transactional
    public boolean deleteDrug(long id) {
        Drug drug = this.drugRepository.findOne(id);
        if (drug != null) {
            this.drugRepository.delete(drug);
            return true;
        }
        return false;
    }

    public DrugWrapper getDrugWrapper(long id) {
        return this.drugRepository.getDrugById(id);
    }

    @Transactional
    public void updateDrug(DrugWrapper drugWrapper) {
        Drug drug = this.drugRepository.findOne(drugWrapper.getId());
        DrugManufacturer drugManufacturer = null;

        if (drugWrapper.getDrugMaker() != null && !drugWrapper.getDrugMaker().trim().equals("")
                && !drugWrapper.getDrugMaker().trim().equals("Select Manufacturer")) {
            boolean chkStatusDrugMaker = containsDigit(drugWrapper.getDrugMaker());
            String numCountry;

            if (chkStatusDrugMaker) {
                drugManufacturer = drugManufacturerRepository.findOne(Long.valueOf(drugWrapper.getDrugMaker()));
                numCountry = drugManufacturer.getName();
            } else {
                numCountry = drugWrapper.getDrugMaker();
            }

            drugWrapper.setDrugMaker(numCountry);
        }

        new Drug(drug, drugWrapper);
        Drug saveDrug = this.drugRepository.save(drug);
        if (drugManufacturer != null) saveDrug.setDrugManufacturer(drugManufacturer);
        this.drugRepository.save(saveDrug);
    }

    public List<DrugWrapper> searchDrugByParams(Pageable pageable, DrugWrapper drugWrapper) {
        return /*this.drugRepository.searchDrugByParams(pageable, drugWrapper.getDrugName())*/ null;
    }

    public String getDrugNaturalId() {
        Prefix prefix = prefixRepository.findByModule(ModuleEnum.DRUG.name());
        return (prefix.getName() + "-" + prefix.getCurrentValue());
    }

    public List<String> searchByDrugNameAutoComplete(String text) {
        return this.drugRepository.searchDrugByParams(text);
    }

    public List<DrugWrapper> getAllDrugWrappers() {
        return this.drugRepository.getAllDrugWrappers();
    }

    public Drug getDrugById(long id) {
        return this.drugRepository.findOne(id);
    }


    private boolean containsDigit(String s) {
        boolean containsDigit = false;
        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }
        return containsDigit;
    }

    public String searchByDrugNameAutoCompleteDetail(String text) {
        return this.drugRepository.searchDrugByDifferentParams(text);
    }

    public Drug searchByDrugNameAutoCompleteStrengths(String text) {
        return this.drugRepository.searchDrugStrengthsByDifferentParams(text);
    }

}
