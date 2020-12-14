package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Currency;
import com.zainimtiaz.nagarro.wrapper.CurrencyWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 10/29/2018.
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    @Query("SELECT new com.sd.his.wrapper.CurrencyWrapper(c.id,c.createdOn,c.updatedOn," +
            "c.ios,c.symbol,c.description,c.systemDefault,c.status) " +
            "FROM Currency c")
    List<CurrencyWrapper> getPaginatedCurrencies(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.CurrencyWrapper(c.id,c.createdOn,c.updatedOn," +
            "c.ios,c.symbol,c.description,c.systemDefault,c.status) " +
            "FROM Currency c WHERE c.id=:id")
    CurrencyWrapper getCurrencyById(@Param("id") Long id);

    @Query("SELECT CASE WHEN count (c) >0 THEN true ELSE false END " +
            "FROM Currency c WHERE c.ios=:ios")
    boolean getCurrencyByIos(@Param("ios") String ios);

    @Query("SELECT CASE WHEN count (c) >0 THEN true ELSE false END " +
            "FROM Currency c WHERE c.ios=:ios AND c.id<>:id")
    boolean getCurrencyByIosAndIdNot(@Param("id") Long id, @Param("ios") String ios);


    @Query("SELECT new com.sd.his.wrapper.CurrencyWrapper(c.id,c.createdOn,c.updatedOn," +
            "c.ios,c.symbol,c.description,c.systemDefault,c.status) " +
            "FROM Currency c WHERE c.status=:status")
    List<CurrencyWrapper> getAllCurrenciesByStatus(@Param("status") boolean status);

    @Query("SELECT new com.sd.his.wrapper.CurrencyWrapper(c.id,c.createdOn,c.updatedOn," +
            "c.ios,c.symbol,c.description,c.systemDefault,c.status) " +
            "FROM Currency c WHERE c.systemDefault=true")
    CurrencyWrapper getCurrencyBySystemDefault();


    List<Currency> getCurrencyBySystemDefaultTrue();
}
