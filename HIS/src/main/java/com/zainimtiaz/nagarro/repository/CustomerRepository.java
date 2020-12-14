package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository  extends  JpaRepository<CustomerEntity,Long>{

    @Query("SELECT ce FROM CustomerEntity ce")
    List<CustomerEntity> getAllCustomer();

    List<CustomerEntity> findAll();


    //@Query("SELECT te FROM CustomerEntity te where te.firstName = ?1")
    CustomerEntity  findAllByFirstNameAndLastName(String fname,String lName);

}
