package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Department;
import com.zainimtiaz.nagarro.wrapper.DepartmentWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Arif Heer
 * @Date      : 4/10/2018
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
 * @Package   : com.sd.his.repositories
 * @FileName  : ClinicalDepartmentRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByOrderByNameAsc(Pageable pageable);

    Department findByNameAndIdNot(String name, long id);

    @Query("SELECT new com.sd.his.wrapper.DepartmentWrapper(cd)" +
            " FROM Department cd where cd.name LIKE  CONCAT('%',:name,'%') ")
    List<DepartmentWrapper> findByName(Pageable pageable, @Param("name") String name);

    @Query("SELECT new com.sd.his.wrapper.DepartmentWrapper(cd)" +
            " FROM Department cd where cd.name LIKE  CONCAT('%',:name,'%') ")
    List<DepartmentWrapper> findByName(@Param("name") String name);

    @Query("SELECT new com.sd.his.wrapper.DepartmentWrapper(cd)" +
            " FROM Department cd where cd.name =:name ")
    DepartmentWrapper findByNameAndActiveNotNull(@Param("name") String name);

    List<Department> findAllByIdIn(List<Long> ids);




}
