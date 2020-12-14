package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigurationRepository extends JpaRepository<SystemConfiguration,Long> {

    SystemConfiguration findById(Long id);

}
