package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Country;
import com.zainimtiaz.nagarro.wrapper.CountryWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query("SELECT new com.sd.his.wrapper.CountryWrapper(c) FROM com.sd.his.model.Country c ")
    List<CountryWrapper> getAllCountries();


    @Query("SELECT c FROM Country c")
    List<Country> getAllBy();


    @Query("SELECT t FROM Country t where t.id = :id")
    Country findTitleById(@Param("id") Long id);

    @Query("SELECT t FROM Country t where UPPER(t.name) like UPPER(:name) ")
    Country findByName(@Param("name") String name);

    @Query("SELECT new com.sd.his.wrapper.CountryWrapper(c) FROM Country c where c.id = :id")
    CountryWrapper getByCountryId(@Param("id") Long id);
}
