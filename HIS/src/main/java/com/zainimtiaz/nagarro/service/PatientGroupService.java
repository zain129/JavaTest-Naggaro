package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.PatientGroup;
import com.zainimtiaz.nagarro.repository.PatientGroupRepository;
import com.zainimtiaz.nagarro.wrapper.PatientGroupWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 10/23/2018.
 */
@Service
public class PatientGroupService {

    @Autowired
    private PatientGroupRepository patientGroupRepository;

    public boolean isPatientGroupByNameExist(String name) {
        return this.patientGroupRepository.getPatientGroupByName(name);
    }

    @Transactional
    public String savePatientGroup(PatientGroupWrapper patientGroupWrapper) {
        PatientGroup patientGroup = new PatientGroup(patientGroupWrapper);
        this.patientGroupRepository.save(patientGroup);
        return "";
    }

    public List<PatientGroupWrapper> getAllPatientGroups() {
        return this.patientGroupRepository.findAllGroups();
    }

    public List<PatientGroupWrapper> getAllPaginatedPatientGroup(Pageable pageable) {
        return this.patientGroupRepository.findAllByCreatedOn(pageable);
    }

    public int countAllPaginatedPatientGroup() {
        return this.patientGroupRepository.findAll().size();
    }

    public PatientGroupWrapper getPatientGroupById(long id) {
        return this.patientGroupRepository.getPatientGroupById(id);
    }

    public boolean isPatientGroupByNameExistAndNotEqualId(long id, String name) {
        return this.patientGroupRepository.getPatientGroupByNameAndIdNotEqual(id, name);
    }


    @Transactional
    public String updatePatientGroup(PatientGroupWrapper patientGroupWrapper) {
        PatientGroup patientGroup = this.patientGroupRepository.findOne(patientGroupWrapper.getId());
        new PatientGroup(patientGroup, patientGroupWrapper);
        this.patientGroupRepository.save(patientGroup);
        return "";
    }

    @Transactional
    public boolean deletePatientGroup(long id) {
        PatientGroup patientGroup = this.patientGroupRepository.findOne(id);
        if (patientGroup != null) {
            this.patientGroupRepository.delete(patientGroup);
            return true;
        }
        return false;
    }


    public boolean hasChild(long patientGroupId) {
        // to do
        return false;
    }
}
