package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jamal on 10/8/2018.
 */
@Service
public class InsuranceService {

    @Autowired
    private InsuranceRepository insuranceRepository;

    public InsuranceRepository getInsuranceRepository() {
        return insuranceRepository;
    }
    public void setInsuranceRepository(InsuranceRepository insuranceRepository) {
        this.insuranceRepository = insuranceRepository;
    }
}
