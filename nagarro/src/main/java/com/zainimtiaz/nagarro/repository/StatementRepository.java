/**
 * @author Zain I.
 * created on 14/12/2020
 **/

package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.entity.Account;
import com.zainimtiaz.nagarro.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "statements", collectionResourceRel = "statements", itemResourceRel = "statement")
public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findByAccount(Account account);
}
