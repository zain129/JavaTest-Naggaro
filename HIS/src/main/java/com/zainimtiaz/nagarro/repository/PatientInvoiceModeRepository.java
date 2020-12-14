package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PatientInvoiceModePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInvoiceModeRepository extends JpaRepository<PatientInvoiceModePayment, Long> {


}
