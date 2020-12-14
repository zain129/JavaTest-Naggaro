package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.model.Status;
import com.zainimtiaz.nagarro.repository.StatusRepository;
import com.zainimtiaz.nagarro.wrapper.StatusWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
 * @Package   : com.sd.his.service
 * @FileName  : TaxService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;

    public StatusWrapper saveStatus(StatusWrapper statusWrapper) {
        Status status1 = new Status();
        status1.setName(statusWrapper.getName());
        status1.setAbbreviation(statusWrapper.getAbbreviation());
        status1.setHashColor(statusWrapper.getColorHash());
        status1.setStatus(statusWrapper.isActive());
        status1.setSystemStatus(statusWrapper.isSystemStatus());
        statusRepository.save(status1);
        return statusWrapper;
    }

     public List<StatusWrapper> getAllStatusesWithPagination(int offset, int limit) {
            Pageable pageable = new PageRequest(offset, limit);
            List<Status> list = statusRepository.findBy(pageable);
            return list.stream().map(
                    x ->
                    new StatusWrapper(x.getId(),x.getName(),x.getAbbreviation(),x.isStatus(),x.getHashColor(),x.isSystemStatus()))
                    .collect(Collectors.toList());
     }
    public List<StatusWrapper> getAllStatuses() {
        List<Status> list = statusRepository.findAll();
        return list.stream().map(
                x ->
                        new StatusWrapper(x.getId(),x.getName(),x.getAbbreviation(),x.isStatus(),x.getHashColor(),x.isSystemStatus()))
                .collect(Collectors.toList());
    }

     public int statusesCount(){
         return statusRepository.countByStatusTrue();
    }
     public Status isAlreadyExist(String name){
        return statusRepository.findByName(name);
     }
     public Status getById(long id){
         return statusRepository.findOne(id);
     }
     public Status deleteStatus(Status status){
         statusRepository.delete(status);
         return status;
     }
     public StatusWrapper  updateStatus(StatusWrapper statusWrapper,Status status1){
        status1.setName(statusWrapper.getName());
        status1.setAbbreviation(statusWrapper.getAbbreviation());
        status1.setHashColor(statusWrapper.getColorHash());
        status1.setStatus(statusWrapper.isActive());
        status1.setSystemStatus(statusWrapper.isSystemStatus());
        statusRepository.save(status1);
        return statusWrapper;

    }
     public boolean isStatusNameOrIdExistsAlready(String name, long brId) {
        return statusRepository.findByNameAndIdNot(name,brId) == null ? false : true;
    }
    public List<StatusWrapper> searchByStatusName(String name, int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<StatusWrapper> branches = statusRepository.findByNameAndStatusTrue(name,pageable);

        return branches;
    }
}
