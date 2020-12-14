package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.GeneralLedger;
import com.zainimtiaz.nagarro.wrapper.GeneralLedgerWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralLedgerRepository  extends JpaRepository<GeneralLedger, Long> {

    @Query("SELECT new com.sd.his.wrapper.GeneralLedgerWrapper(gl) " +
            " FROM GeneralLedger gl")
    List<GeneralLedgerWrapper> getAll();
}
