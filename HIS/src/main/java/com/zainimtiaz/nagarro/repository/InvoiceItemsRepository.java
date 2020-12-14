package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.InvoiceItems;
import com.zainimtiaz.nagarro.wrapper.response.InvoiceItemsResponseWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemsRepository  extends JpaRepository<InvoiceItems, Long> {


    @Query("SELECT NEW com.sd.his.wrapper.response.InvoiceItemsResponseWrapper(invcItems) " +
            "FROM InvoiceItems invcItems WHERE invcItems.invoice.id =?1")
    List<InvoiceItemsResponseWrapper> findAllByInvoiceId(Long id);



    @Modifying
    @Query("delete from InvoiceItems u where u.invoice.id=?2 AND u.id not in ?1")
    void deleteRemoveInviceItems(List<Long> ids, Long incId);
}
