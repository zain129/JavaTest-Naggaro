package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Patient_Order;
import com.zainimtiaz.nagarro.wrapper.Patient_OrderWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PatientOrderRepository extends JpaRepository<Patient_Order, Long> {


    @Query("SELECT new com.sd.his.wrapper.Patient_OrderWrapper(po) " +
            "FROM com.sd.his.model.Patient_Order po where po.id=:id")
    Patient_OrderWrapper findOrderById(@Param("id") long poId);

   @Query("SELECT new com.sd.his.wrapper.Patient_OrderWrapper(po) " +
            "FROM com.sd.his.model.Patient_Order po " +
            "WHERE po.patient.id=:patientId")
    List<Patient_OrderWrapper> getPaginatedOrder(Pageable pageable, @Param("patientId") Long patientId);


    @Query("SELECT CASE WHEN COUNT (po) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Patient_Order po " +
            "WHERE po.order=:name AND po.patient.id=:patientId")
    boolean isNameExists(@Param("name") String orderName, @Param("patientId") Long patientId);

    @Query("SELECT CASE WHEN COUNT (po) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Patient_Order po " +
            "WHERE po.order=:name AND po.id <>:id AND po.patient.id=:patientId")
    boolean isNameExistsAgainstId(@Param("name") String orderName, @Param("id") Long id, @Param("patientId") Long patientId);


    @Query("SELECT new com.sd.his.wrapper.Patient_OrderWrapper(po) " +
            "FROM com.sd.his.model.Patient_Order po where po.id=:id")
    Patient_OrderWrapper findOrderImageById(@Param("id") long poId);
}
