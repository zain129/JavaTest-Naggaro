package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.State;
import com.zainimtiaz.nagarro.repository.StateRepository;
import com.zainimtiaz.nagarro.wrapper.StateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jamal on 11/8/2018.
 */
@Service
@Transactional
public class StateService {
    @Autowired
    private StateRepository stateRepository;


    public List<StateWrapper> getAllStates(){
        return this.stateRepository.getAllStates();
    }

    public List<StateWrapper> getAllStatesByCountryId(long countryId){
        return this.stateRepository.getAllStatesByCountry(countryId);
    }

    public State getStateById(long stateId){
        return this.stateRepository.getOne(stateId);
    }

    public StateWrapper getStateWrapperById(Long stateId){
        return this.stateRepository.getByIdOne(stateId);
    }

}
