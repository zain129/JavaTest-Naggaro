package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.MedicalService;
import com.zainimtiaz.nagarro.wrapper.MedicalServiceWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author    : Qari Muhammad Jamal
 * @Date      : 15-May-2018
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
 * @FileName  : MedicalServicesRepository
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Repository
public interface MedicalServiceRepository extends JpaRepository<MedicalService, Long> {

/*    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms.id, ms.title, ms.fee, ms.cost, ms.status, b.id, b.name, cd.id, cd.name, t.id, t.rate, ms.description, ms.duration) " +
            "FROM MedicalService ms JOIN ms.getDepartments cdms JOIN cdms.clinicalDpt cd JOIN ms.branches bms JOIN bms.branch b JOIN ms.tax t ")
    List<MedicalServiceWrapper> findAllPaginated(Pageable pageable);*/

    //    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms")
    List<MedicalService> findAllByCreatedOnNotNull(Pageable pageable);

    MedicalService findByName(String name);

    MedicalService findByIdNotAndName(long id, String title);


    @Query("SELECT DISTINCT  new com.sd.his.wrapper.MedicalServiceWrapper(ms) " +
            "FROM MedicalService ms JOIN ms.departmentMedicalServices cdms JOIN cdms.department d JOIN ms.branchMedicalServices bms join bms.branch b JOIN ms.tax t " +
            "WHERE ms.name LIKE  CONCAT('%',:serviceName,'%') or " +
            "ms.code LIKE  CONCAT('%',:searchCode,'%') OR " +
            "b.id = :branchId OR " +
            "d.id = :departmentId OR " +
            "ms.fee = :serviceFee")
    List<MedicalServiceWrapper> findAllByParam(@Param("serviceName") String serviceName,
                                               @Param("searchCode") String searchCode,
                                               @Param("branchId") Long branchId,
                                               @Param("departmentId") Long departmentId,
                                               @Param("serviceFee") Double serviceFee,
                                               Pageable pageable);

    @Query("SELECT DISTINCT  new com.sd.his.wrapper.MedicalServiceWrapper(ms) " +
            "FROM MedicalService ms JOIN ms.departmentMedicalServices cdms JOIN cdms.department d JOIN ms.branchMedicalServices bms join bms.branch b JOIN ms.tax t " +
            "WHERE ms.name LIKE  CONCAT('%',:serviceName,'%') or " +
            "ms.code LIKE  CONCAT('%',:searchCode,'%') OR " +
            "b.id = :branchId OR " +
            "d.id = :departmentId OR " +
            "ms.fee = :serviceFee")
    List<MedicalServiceWrapper> countAllByParam(@Param("serviceName") String serviceName,
                                                @Param("searchCode") String searchCode,
                                                @Param("branchId") Long branchId,
                                                @Param("departmentId") Long departmentId,
                                                @Param("serviceFee") Double serviceFee);


    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms WHERE ms.status=:status")
    List<MedicalServiceWrapper> findAllMedicalServiceWrappers(@Param("status") Boolean status);

    List<MedicalService> findAllByIdIn(List<Long> ids);

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms where ms.status=true")
    List<MedicalServiceWrapper> findAllMedicalServiceWrappers();

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms ")
    List<MedicalServiceWrapper> findAllMedicalServiceWrappersForDataTable();

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms.id,ms.name,ms.description) FROM MedicalService ms where ms.status=TRUE ")
    List<MedicalServiceWrapper> findAllMedicalServicesForAppointment();

    /*@Query("SELECT NEW com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms INNER JOIN ms.departmentMedicalServices dms where ms.")
    List<MedicalServiceWrapper> findAllByDepartmentId(@Param("deptId") Long deptId);*/

    @Query("SELECT new com.sd.his.wrapper.MedicalServiceWrapper(ms) FROM MedicalService ms inner join ms.departmentMedicalServices dms where dms.department.id=:deptId")
    List<MedicalServiceWrapper> findMedicalServicesByDepartmentId(@Param("deptId") Long deptId);
}
