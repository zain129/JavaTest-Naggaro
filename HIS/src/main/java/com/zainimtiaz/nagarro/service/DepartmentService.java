package com.zainimtiaz.nagarro.service;

import com.zainimtiaz.nagarro.enums.ModuleEnum;
import com.zainimtiaz.nagarro.model.Branch;
import com.zainimtiaz.nagarro.model.BranchDepartment;
import com.zainimtiaz.nagarro.model.Department;
import com.zainimtiaz.nagarro.repository.BranchDepartmentRepository;
import com.zainimtiaz.nagarro.repository.BranchRepository;
import com.zainimtiaz.nagarro.repository.DepartmentRepository;
import com.zainimtiaz.nagarro.utill.HISCoreUtil;
import com.zainimtiaz.nagarro.wrapper.BranchesListWrapper;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @author    : Arif Heer
 * @Date      : 04/5/2018
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer    Date       Version  Operation  Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.service
 * @FileName  : DepartmentService
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private BranchDepartmentRepository branchDepartmentRepository;
    @Autowired
    HISUtilService hisUtilService;


    private final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    public List<DepartmentWrapper> getDepartmentsActive() {
        List<Department> dpts = departmentRepository.findAll();
        List<DepartmentWrapper> dptsWrappers = new ArrayList<>();
        for (Department cd : dpts) {
            if (cd.getStatus()) {
                DepartmentWrapper dpt = new DepartmentWrapper(cd);
              //  dpt.setListOfBranches(cd.getBranchDepartments());
                List<BranchesListWrapper> branhes= new ArrayList<>();
                for(BranchDepartment cdd :cd.getBranchDepartments()){
                    if(cdd.getId() != 0 ){
                    /*    dpt.setBranchDepartmentId(cdd.getId());
                        dpt.setBranchId(cdd.getBranch().getId());
                        dpt.setBranch(cdd.getBranch().getName());*/
                        BranchesListWrapper branch =new BranchesListWrapper();
                        branch.setId(cdd.getBranch().getId());
                        branch.setName(cdd.getBranch().getName());
                        branch.setLabel(cdd.getBranch().getName());
                        branch.setValue(cdd.getBranch().getId());
                        branhes.add(branch);
                    }
                   dpt.setListOfBranches(branhes);
                }
                dptsWrappers.add(dpt);
            }
        }
        return dptsWrappers;
    }

    public List<DepartmentWrapper> getDepartmentsByBranchIds(List<Long> branchIds) {
        List<DepartmentWrapper> departmentList = new ArrayList<>();
        for (Long i : branchIds) {
            List<BranchDepartment> branchDepartment = branchDepartmentRepository.getAllByBranch_id(i);
            for (BranchDepartment bd : branchDepartment) {
                boolean idFound = false;

                for (DepartmentWrapper d : departmentList) {
                    if (d.getId() == bd.getDepartment().getId()) {
                        idFound = true;
                        break;
                    }
                }
                if (!idFound) {
                    departmentList.add(new DepartmentWrapper(bd.getDepartment()));
                }
            }
        }

        return departmentList;
    }

    public List<DepartmentWrapper> getPaginatedAllDepartments(int offset, int limit) {
        Pageable pageable = new PageRequest(offset, limit);
        List<Department> dpts = departmentRepository.findAllByOrderByNameAsc(pageable);
        List<DepartmentWrapper> dptsWrappers = new ArrayList<>();

        for (Department cd : dpts) {
            DepartmentWrapper dpt = new DepartmentWrapper(cd);
            for(BranchDepartment cdd :cd.getBranchDepartments()){
                dpt.setBranchDepartmentId(cdd.getId());
                dpt.setBranchId(cdd.getBranch().getId());
            }
            dptsWrappers.add(dpt);
        }

        return dptsWrappers;
    }

    public int countPaginatedAllDepartments() {
        return departmentRepository.findAll().size();
    }

    public Department findDepartmentById(long dptId) {
        return departmentRepository.findOne(dptId);
    }

    public boolean isDepartmentByNameAndNotIdExist(String name, long dptId) {
        return departmentRepository.findByNameAndIdNot(name, dptId) == null ? true : false;
    }

    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteDepartment(long id) {
        departmentRepository.delete(id);
        return true;
    }

    public int countSearchedClinicalDepartments(String name) {
        return departmentRepository.findByName(name).size();
    }

    public List<DepartmentWrapper> getPageableSearchedDepartment(int offset, int limit, String name) {
        Pageable pageable = new PageRequest(offset, limit);
        return departmentRepository.findByName(pageable, name);
    }

    @Transactional(rollbackOn = Throwable.class)
    public Department saveDepartment(DepartmentWrapper createRequest) {
        Department dpt = new Department(createRequest);
        dpt.setDeptId(hisUtilService.generatePrefix(ModuleEnum.DEPARTMENT));
        List<Long> listOFBranch=  createRequest.getSelectedBranches().stream().collect(Collectors.toList());
        List<Branch> branch = branchRepository.findAllByIdIn(listOFBranch);
        BranchDepartment branchDepartment = new BranchDepartment();
        Department department = departmentRepository.save(dpt);
        List<BranchDepartment> branchDepartmentList = new ArrayList<>();
        if(branch !=null){
        for(Branch br :branch){
         BranchDepartment brDpt = new BranchDepartment();
         brDpt.setBranch(br);
         brDpt.setDepartment(department);
         branchDepartmentList.add(brDpt);
        }
         branchDepartmentRepository.save(branchDepartmentList);
         hisUtilService.updatePrefix(ModuleEnum.DEPARTMENT);
        }
        return dpt;
    }

    @Transactional(rollbackOn = Throwable.class)
    public Department updateDepartment(DepartmentWrapper updateRequest) {
        Department dpt = departmentRepository.findOne(updateRequest.getId());
        dpt.setName(updateRequest.getName());
        dpt.setDescription(updateRequest.getDescription());
        dpt.setStatus(updateRequest.isActive());
        /*long branchId = updateRequest.getBranchId();
        Branch branch = branchRepository.findOne(branchId);
        if(updateRequest.getBranchDepartmentId() != 0)
        branchDepartment = branchDepartmentRepository.findByDepartment(dpt);
        Department department = departmentRepository.save(dpt);
        branchDepartment.setBranch(branch);
      //  branchDepartment.setDepartment(dpt);
        branchDepartmentRepository.save(branchDepartment);*/
   //     dpt.setDeptId(hisUtilService.generatePrefix(ModuleEnum.DEPARTMENT));
        List<Long> listOFBranch=  updateRequest.getSelectedBranches().stream().collect(Collectors.toList());
        List<Branch> branch = branchRepository.findAllByIdIn(listOFBranch);

        List<BranchDepartment> branchDepartment = branchDepartmentRepository.findAllByDepartment(dpt);
        if(!HISCoreUtil.isListEmpty(branchDepartment)){
          branchDepartmentRepository.delete(branchDepartment);
        }
        List<BranchDepartment> branchDepartmentList = new ArrayList<>();
        Department department = departmentRepository.save(dpt);
        if(branch != null){
            for(Branch br :branch){
                BranchDepartment brDpt = new BranchDepartment();
                brDpt.setBranch(br);
                brDpt.setDepartment(department);
                branchDepartmentList.add(brDpt);
            }
            branchDepartmentRepository.save(branchDepartmentList);
        //    hisUtilService.updatePrefix(ModuleEnum.DEPARTMENT);
        }
        return department;
    }

    public DepartmentWrapper findDepartmentByName(String name) {
        return departmentRepository.findByNameAndActiveNotNull(name);
    }


    public boolean hasChild(long dptId) {
        Department department = this.departmentRepository.findOne(dptId);
        if (department != null && department.getDepartmentMedicalServices() != null && department.getDepartmentMedicalServices().size() > 0) {
            return true;
        }
        if (department != null && department.getBranchDepartments() != null && department.getBranchDepartments().size() > 0) {
            return true;
        }
        return false;
    }
}