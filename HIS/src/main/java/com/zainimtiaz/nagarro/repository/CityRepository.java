package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.City;
import com.zainimtiaz.nagarro.wrapper.CityWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT new com.sd.his.wrapper.CityWrapper(c) " +
            "FROM City c " +
            "WHERE c.state.id=:id")
    List<CityWrapper> getAllCitiesByStateId(@Param("id") Long stateId);



    @Query("SELECT c FROM City c WHERE c.state.id=:id")
    List<City> getAllCitiesById(@Param("id") Long stateId);

    @Query("SELECT c FROM City c")
    List<City> findAllBy();


    @Query("SELECT t FROM City t where t.name = :name")
    City findTitleById(@Param("name") String paraName);


    @Query("SELECT new com.sd.his.wrapper.CityWrapper(c) " +
            "FROM City c " +
            "WHERE c.country.id=:id")
    List<CityWrapper> getAllCitiesByCountryId(@Param("id") Long countryId);


}
