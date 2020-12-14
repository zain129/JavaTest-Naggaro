package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.State;
import com.zainimtiaz.nagarro.wrapper.StateWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query("SELECT new com.sd.his.wrapper.StateWrapper(s) FROM com.sd.his.model.State s ")
    List<StateWrapper> getAllStates();

    @Query("SELECT new com.sd.his.wrapper.StateWrapper(s) FROM com.sd.his.model.State s WHERE s.country.id =:id ")
    List<StateWrapper> getAllStatesByCountry(@Param("id") Long countryId);


    @Query("SELECT s FROM State s WHERE s.country.id =:id ")
    List<State> getAllStatesByCountryById(@Param("id") Long countryId);


    @Query("SELECT t FROM State t where t.name = :name")
    State findTitleById(@Param("name") String paraName);

    @Query("SELECT new com.sd.his.wrapper.StateWrapper(s) FROM com.sd.his.model.State s WHERE s.id =:id ")
    StateWrapper getByIdOne(@Param("id") Long stateId);
}
