package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.S3Bucket;
import com.zainimtiaz.nagarro.repository.S3BucketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/*
 * @author    : Irfan Nasim
 * @Date      : 26-Jun-18
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
 * @Package   : com.sd.his.service
 * @FileName  : S3BucketService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class S3BucketService {

    @Autowired
    S3BucketRepository s3BucketRepository;

    public S3Bucket findActiveBucket() {
        return s3BucketRepository.findByActiveTrue();
    }
}