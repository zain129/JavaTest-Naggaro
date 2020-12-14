/**
 * @author Zain I.
 **/

package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "accounts", collectionResourceRel = "accounts", itemResourceRel = "account")
public interface AccountRepository extends JpaRepository<Account, Long> {
}
