package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.ReceiptPaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptPaymentTypeRepository extends JpaRepository<ReceiptPaymentType, Long> {


}
