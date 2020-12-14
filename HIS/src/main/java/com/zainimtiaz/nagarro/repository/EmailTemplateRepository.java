package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.EmailTemplate;
import com.zainimtiaz.nagarro.wrapper.request.EmailTemplateWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Muhammad Jamal
 * @Date      : 21-May-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.repositories
 * @FileName  : EmailTemplateRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    @Query("SELECT new com.sd.his.wrapper.request.EmailTemplateWrapper(email.id,email.createdOn,email.updatedOn,email.title,email.subject,email.type,email.emailTemplate,email.active) " +
            "FROM com.sd.his.model.EmailTemplate email")
    List<EmailTemplateWrapper> findAllByCreatedOnNotNull(Pageable pageable);

    List<EmailTemplate> findAllByTitle(String title);

    EmailTemplate findByTitleAndIdNot(String title, long id);

    @Query("SELECT new com.sd.his.wrapper.request.EmailTemplateWrapper(email.id,email.createdOn,email.updatedOn,email.title,email.subject,email.type,email.emailTemplate,email.active) " +
            "FROM com.sd.his.model.EmailTemplate email WHERE email.title LIKE CONCAT('%',:title,'%')  ")
    List<EmailTemplateWrapper> findAllByTitleContaining(@Param("title") String title, Pageable pageable);

    List<EmailTemplate> findAllByTitleContaining(String title);


    @Query("SELECT new com.sd.his.wrapper.request.EmailTemplateWrapper(email.id,email.createdOn,email.updatedOn,email.title,email.subject,email.type,email.emailTemplate,email.active) " +
            "FROM com.sd.his.model.EmailTemplate email WHERE email.id=:id")
    EmailTemplateWrapper getEmailTemplateById(@Param("id") long id);
}