package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.ICDCode;
import com.zainimtiaz.nagarro.wrapper.ICDCodeWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICDCodeRepository extends JpaRepository<ICDCode, Long> {


    //    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) from com.sd.his.model.ICDCode code")
    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) " +
            "from ICDCode code where code.status=true ")
    List<ICDCodeWrapper> findAllByCreatedOnNotNull(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) from com.sd.his.model.ICDCode code " +
            "where code.status=true ")
    List<ICDCodeWrapper> findAllByCreatedOnNotNull();

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) from com.sd.his.model.ICDCode code ")
    List<ICDCodeWrapper> findAllCodesForDataTable();

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) from com.sd.his.model.ICDCode code where code.code LIKE CONCAT('%',:code,'%')")
    List<ICDCodeWrapper> findAllByCodeContaining(@Param("code") String code, Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(code) from com.sd.his.model.ICDCode code where code.code LIKE CONCAT('%',:code,'%')")
    List<ICDCodeWrapper> findAllByCodeContaining(@Param("code") String code);

    ICDCode findByCode(String iCDCode);

    ICDCode findByCodeAndIdNot(String iCDCode, long iCDCodeId);

}

