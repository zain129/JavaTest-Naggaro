package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.repository.DrugManufacturerRepository;
import com.zainimtiaz.nagarro.wrapper.DrugManufacturerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugManufacturerService {

    @Autowired
    DrugManufacturerRepository drugManufacturerRepository;

    public List<DrugManufacturerWrapper> getAllActiveDrugManufacturer() {
        return getAllDrugManufacturer().stream().filter(DrugManufacturerWrapper::getStatus).collect(Collectors.toList());
    }

    public List<DrugManufacturerWrapper> getAllDrugManufacturer() {
        return drugManufacturerRepository.getAllDrugManufacturers();
    }

    public DrugManufacturerWrapper getDrugManufacturerById(Long id) {
        return drugManufacturerRepository.getDrugManufacturerById(id);
    }
}
