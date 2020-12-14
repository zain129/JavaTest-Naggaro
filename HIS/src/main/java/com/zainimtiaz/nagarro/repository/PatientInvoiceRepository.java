package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Invoice;
import com.zainimtiaz.nagarro.wrapper.response.InvoiceResponseWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientInvoiceRepository  extends JpaRepository<Invoice, Long> {

    Invoice findByAppointmentId(Long id);

    @Query(" SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper(invc) FROM Invoice invc ")
    List<InvoiceResponseWrapper> findAllInvoices();

    @Query("SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper(invc) FROM Invoice invc WHERE invc.status=:status")
    List<InvoiceResponseWrapper> getInvoiceListByStatus(@Param("status") String status);

    @Query("SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper( ( SUM(invc.invoiceAmount)-SUM(invc.paidAmount) ), SUM(invc.invoiceAmount), SUM(invc.paidAmount), SUM(p.advanceBalance) ) "+
            "FROM Invoice invc inner join invc.patient p WHERE p.id=:patientId ")
    InvoiceResponseWrapper getPatientInvoicesBalance(@Param("patientId") Long patientId);



    @Query("SELECT NEW com.sd.his.wrapper.response.InvoiceResponseWrapper( invc ) "+
            "FROM Invoice invc WHERE invc.patient.id=:id AND invc.status='Pending'")
    List<InvoiceResponseWrapper> findAllByPatientId(@Param("id") long id);

    Invoice findByInvoiceId(String invoiceId);
}
