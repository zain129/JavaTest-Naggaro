package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PaymentType;
import com.zainimtiaz.nagarro.wrapper.response.PaymentTypeWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentTypeRepository  extends JpaRepository<PaymentType,Long> {

    @Query("SELECT pt FROM PaymentType pt")
    List<PaymentType> getAllBy();

    List<PaymentType> findAll();


    @Query("SELECT new com.sd.his.wrapper.response.PaymentTypeWrapper(pt) FROM PaymentType pt")
    List<PaymentTypeWrapper> getAllWithWrapper();
}
