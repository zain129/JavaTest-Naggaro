package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.PatientInvoicePayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInvoicePaymentRepository  extends JpaRepository<PatientInvoicePayment, Long> {
}
