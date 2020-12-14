package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Prefix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Tahir Mehmood
 * @Date      : 30-Jul-2018
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface PrefixRepository extends JpaRepository<Prefix, Long> {

    Prefix findByModule(String moduleName);

    Prefix findByName(String moduleName);


    @Query("SELECT new com.sd.his.model.Prefix(prefix) FROM Prefix prefix")
    List<Prefix> getAll();
}

