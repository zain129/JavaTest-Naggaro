package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Tax;
import com.zainimtiaz.nagarro.wrapper.TaxWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 7/31/2018.
 */
@Repository
public interface TaxRepository extends JpaRepository<Tax, Long> {

    @Query("SELECT new com.sd.his.wrapper.TaxWrapper(tax) FROM com.sd.his.model.Tax tax ")
    List<TaxWrapper> findAllByCreatedOnNotNull(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.TaxWrapper(tax) FROM com.sd.his.model.Tax tax where tax.active =:status ")//status true , false
    List<TaxWrapper> findAllByActiveTrue(@Param("status") boolean status);

    @Query("SELECT new com.sd.his.wrapper.TaxWrapper(tax) FROM com.sd.his.model.Tax tax ")//status true , false
    List<TaxWrapper> findAllTaxesForDataTable();

    List<Tax> findAllByNameAndIdNot(String name, long id);

    Tax findByName(String name);

    @Query("SELECT new com.sd.his.wrapper.TaxWrapper(tax) FROM com.sd.his.model.Tax tax where tax.name LIKE CONCAT('%',:name,'%') ")
    List<TaxWrapper> findAllByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.TaxWrapper(tax) FROM com.sd.his.model.Tax tax where tax.name LIKE CONCAT('%',:name,'%') ")
    List<TaxWrapper> findAllByNameContaining(@Param("name") String name);

    @Query("SELECT tax.name FROM com.sd.his.model.Tax tax where tax.name LIKE CONCAT('%',:name,'%') ")
    int countTaxByName(@Param("name") String name);

}