package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Branch;

import com.zainimtiaz.nagarro.wrapper.BranchWrapperPart;
import com.zainimtiaz.nagarro.wrapper.CityWrapper;
import com.zainimtiaz.nagarro.wrapper.CountryWrapper;
import com.zainimtiaz.nagarro.wrapper.StateWrapper;
import com.zainimtiaz.nagarro.wrapper.response.BranchResponseWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
public interface BranchRepository extends JpaRepository<Branch, Long> {


    Branch findByName(String name);

    Branch findByNameAndIdNot(String name,Long id);

    @Query("SELECT  new com.sd.his.wrapper.response.BranchResponseWrapper(b.id,b.name,bb.firstName) FROM Branch b LEFT OUTER JOIN b.branchDoctors bu LEFT OUTER JOIN bu.doctor bb WHERE b.status=TRUE ")
    List<BranchResponseWrapper> findAllByActive(Pageable pageable);


/*    @Query("select br.id,br.name,br.country,br.city,br.noOfRooms,group_concat(d.firstName) from Branch  br left join br.branchDoctors bd left join bd.doctor d where br.active=true group by br.id,br.name,br.country,br.city,br.noOfRooms order by br.name",nativeQuery=true)*/
    /* @Query(value = "select dr.first_name,dr.id,b.`name`,b.no_of_rooms,b.city,b.country,GROUP_CONCAT(dr.first_name) from branch b LEFT OUTER JOIN branch_doctor bd ON b.id=bd.branch_id LEFT OUTER JOIN doctor dr ON bd.doctor_id=dr.id group by b.`name`,b.no_of_rooms,b.city,b.country ",nativeQuery = true)
     List<BranchResponseWrapper> findAllActiveBranches();*/

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b) FROM Branch b WHERE b.id =:id ")
    BranchResponseWrapper findAllById(@Param("id") Long id);

    List<Branch> findAllByIdIn(List<Long> ids);
    List<Branch> deleteAllByIdIn(List<Long> ids);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b) FROM Branch b WHERE b.status = TRUE")
    List<BranchResponseWrapper> findAllByActiveTrue();

    @Query("SELECT new com.sd.his.wrapper.BranchWrapperPart(b) FROM Branch b WHERE b.status = TRUE")
    List<BranchWrapperPart> findAllByActive();
    Branch findBySystemBranchTrue();
    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(b.id,b.name,bb.firstName) FROM Branch b LEFT OUTER JOIN b.branchDoctors bu LEFT OUTER JOIN bu.doctor bb WHERE b.status = TRUE and b.name LIKE CONCAT('%',:name,'%')")
    List<BranchResponseWrapper> findByNameAndBranchDepartments(@Param("name")String name ,Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.response.BranchResponseWrapper(br,bdd.id,bdd.firstName,bdd.lastName) from Branch br INNER JOIN br.branchDoctors bd inner join bd.doctor bdd")
    List<BranchResponseWrapper> findByBranchAndBranchDoctors();

    //  List<Branch> findByNameIgnoreCaseContainingAndActiveTrueOrBranchDepartments_department_nameIgnoreCaseContaining(String name, String department, Pageable pageable);


    @Query("SELECT new com.sd.his.wrapper.CityWrapper(cty) FROM Branch b JOIN b.city cty WHERE b.id = :branchId")
    CityWrapper findCityByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT new com.sd.his.wrapper.StateWrapper(st) FROM Branch b JOIN b.state st WHERE b.id = :branchId")
    StateWrapper findStateByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT new com.sd.his.wrapper.CountryWrapper(cntry) FROM Branch b JOIN b.country cntry WHERE b.id = :branchId")
    CountryWrapper findCountryByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT br from Branch br WHERE br.branchId=:branchId ")
    Branch getByBranchId(@Param("branchId") String stringCellValue);


 /*   Branch findByIdAndDeletedFalse(long id);

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms,pu.username) FROM Branch b INNER JOIN b.users bu JOIN bu.user pu WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findAllByName(Pageable pageable);

    List<Branch> findByNameIgnoreCaseContainingAndActiveTrueAndDeletedFalseOrClinicalDepartments_clinicalDpt_nameIgnoreCaseContaining(String name, String department, Pageable pageable);

    List<Branch> findAllByActiveTrueAndDeletedFalseOrderByNameAsc(Pageable pageable);

    List<Branch> findAllByActiveTrueAndDeletedFalse();

    @Query("SELECT new com.sd.his.response.BranchResponseWrapper(b.id,b.name, b.country,b.city,b.noOfRooms) FROM Branch b  WHERE b.active = TRUE AND b.deleted = FALSE")
    List<BranchResponseWrapper> findBranchWrapperAllByActiveTrueAndDeletedFalse();

  ;
*/
}

