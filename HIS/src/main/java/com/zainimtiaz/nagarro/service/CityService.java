package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.City;
import com.zainimtiaz.nagarro.repository.CityRepository;
import com.zainimtiaz.nagarro.wrapper.CityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class CityService  {

    @Autowired
    private CityRepository cityRepository;


    public List<CityWrapper> getCitiesByStateId(long stateId){
        return this.cityRepository.getAllCitiesByStateId(stateId);
    }

    public City getCityById(long cityId){
        return this.cityRepository.getOne(cityId);
    }

    public List<City> getCities(){
        return this.cityRepository.findAll();
    }


    public List<CityWrapper> getCitiesByCountryId(long stateId){
        return this.cityRepository.getAllCitiesByCountryId(stateId);
    }

}
