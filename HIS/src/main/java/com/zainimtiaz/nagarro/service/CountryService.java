package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Country;
import com.zainimtiaz.nagarro.repository.CountryRepository;
import com.zainimtiaz.nagarro.wrapper.CountryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryWrapper> getAllCountries() {
        return this.countryRepository.getAllCountries();
    }

    public Country getCountryById(Long id) {
        return this.countryRepository.findOne(id);
    }

    public CountryWrapper getCountryWrapperById(Long id) {
        return this.countryRepository.getByCountryId(id);
    }

    public Country getCountryByName(String name) {
        return this.countryRepository.findByName("%"+name+"%");
    }


    public List<Country> getAllCountriesBy() {
        return this.countryRepository.getAllBy();
    }
}
