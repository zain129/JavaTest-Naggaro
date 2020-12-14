package com.zainimtiaz.nagarro.repository;


import com.zainimtiaz.nagarro.model.TimeZone;
import com.zainimtiaz.nagarro.wrapper.TimezoneWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
 * @author    : Irfan Nasim
 * @Date      : 14-May-18
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
 * @FileName  : TaxRepository
 *
 * Copyright © 
 * SolutionDots, 
 * All rights reserved.
 * 
 */
@Repository
public interface TimezoneRepository extends JpaRepository<TimeZone, Long> {


    @Query("SELECT new com.sd.his.wrapper.TimezoneWrapper(t.id,t.countryCode,t.name) FROM TimeZone t")
     List<TimezoneWrapper> findAllByCountryCode();

}
