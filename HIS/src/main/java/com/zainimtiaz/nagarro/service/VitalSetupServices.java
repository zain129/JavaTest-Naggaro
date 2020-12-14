package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.VitalSetup;
import com.zainimtiaz.nagarro.repository.VitalSetupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VitalSetupServices {

    @Autowired
    VitalSetupRepository vitalSetupRepository;

    public List<VitalSetup> getAll(){
        return vitalSetupRepository.getAll();
    }

    public void saveConfiguration(VitalSetup vitalSetup){
        vitalSetupRepository.save(vitalSetup);
    }

    public void deleteConfiguration(Long id){
        vitalSetupRepository.delete(id);
    }
}
