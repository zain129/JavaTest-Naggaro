package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.S3Bucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * @author    : Tahir Mehmood
 * @Date      : 26-Jul-2018
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
public interface S3BucketRepository extends JpaRepository<S3Bucket, Long> {

    Branch findByName(String name);

    S3Bucket findByActiveTrue();
}

