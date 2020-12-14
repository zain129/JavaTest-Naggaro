package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.ICDCodeVersion;
import com.zainimtiaz.nagarro.wrapper.ICDCodeVersionWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDCodeWrapper;
import com.zainimtiaz.nagarro.wrapper.ICDVersionWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICDCodeVersionRepository extends JpaRepository<ICDCodeVersion, Long> {


    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion ")
    List<ICDCodeVersionWrapper> findAllByOrderByVersion_name(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDCodeVersionWrapper(codeVersion,codeVersion.icd,codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion")
    List<ICDCodeVersionWrapper> findAllByOrderByVersion_name();

    @Query("SELECT new com.sd.his.wrapper.ICDCodeWrapper(codeVersion,codeVersion.icd) FROM com.sd.his.model.ICDCodeVersion codeVersion where codeVersion.version.id=:id")
    List<ICDCodeWrapper> findAllByVersion_id(@Param("id") long iCDCVsById);

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(codeVersion.version) FROM com.sd.his.model.ICDCodeVersion codeVersion where codeVersion.icd.id=:id")
    List<ICDVersionWrapper> findAllVersionsByCode_id(@Param("id") Long iCDVsByCodeId);

    List<Long> deleteAllByVersion_id(long id);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.version.name LIKE concat('%',:n,'%') AND cv.icd.code LIKE concat('%',:c,'%') ")
    List<ICDCodeVersionWrapper> findAllCodeVersionByVersion_NameAndIcd_Code(@Param("n") String versionName, @Param("c") String code, Pageable pageable);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.version.name LIKE concat('%',:n,'%') AND cv.icd.code LIKE concat('%',:c,'%') ")
    List<ICDCodeVersionWrapper> countFindAllCodeVersionByVersion_NameAndIcd_Code(@Param("n") String versionName, @Param("c") String code);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.version.name LIKE concat('%',:n,'%') ")
    List<ICDCodeVersionWrapper> findAllCodeVersionByVersion_Name(@Param("n") String versionName, Pageable pageable);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.version.name LIKE concat('%',:n,'%') ")
    List<ICDCodeVersionWrapper> countFindAllCodeVersionByVersion_Name(@Param("n") String versionName);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.icd.code LIKE concat('%',:c,'%') ")
    List<ICDCodeVersionWrapper> findAllCodeVersionByIcd_Code(@Param("c") String code, Pageable pageable);

    @Query("SELECT DISTINCT new com.sd.his.wrapper.ICDCodeVersionWrapper(cv,cv.icd,cv.version) FROM com.sd.his.model.ICDCodeVersion cv " +
            "where cv.icd.code LIKE concat('%',:c,'%') ")
    List<ICDCodeVersionWrapper> countFindAllCodeVersionByIcd_Code(@Param("c") String code);

    ICDCodeVersion findByIcd_id(long codeId);

    @Query("SELECT CASE WHEN COUNT (codeVersion) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.ICDCodeVersion codeVersion " +
            "WHERE codeVersion.icd.id =:id")
    boolean isCodeAssociated(@Param("id") Long codeId);

    @Query("SELECT CASE WHEN COUNT (codeVersion) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.ICDCodeVersion codeVersion " +
            "WHERE codeVersion.version.id=:versionId")
    boolean isVersionAssociated(@Param("versionId") Long versionId);

    void deleteAllByIcd_id(Long id);

}
