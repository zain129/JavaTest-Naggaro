package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.SMSTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsTemplateRepository extends JpaRepository<SMSTemplate, Long> {

  //  SMSTemplate findByServerType(String serverType);
}
