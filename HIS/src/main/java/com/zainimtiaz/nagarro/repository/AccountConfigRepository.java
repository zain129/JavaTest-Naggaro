package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.AccountConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountConfigRepository  extends JpaRepository<AccountConfig, Long> {
}
