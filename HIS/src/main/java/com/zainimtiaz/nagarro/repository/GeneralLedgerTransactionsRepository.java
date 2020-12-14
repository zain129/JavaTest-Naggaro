package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.GeneralLedgerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralLedgerTransactionsRepository extends JpaRepository<GeneralLedgerTransaction, Long> {


}
