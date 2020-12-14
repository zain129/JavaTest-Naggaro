package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.ICDVersion;
import com.zainimtiaz.nagarro.wrapper.ICDVersionWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ICDVersionRepository extends JpaRepository<ICDVersion, Long> {

    ICDVersion findByName(String iCDVersionName);

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(version) FROM com.sd.his.model.ICDVersion version ")
    List<ICDVersionWrapper> findAllByCreatedOnNotNull(Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(version) " +
            "FROM com.sd.his.model.ICDVersion version " +
            "WHERE version.status=true")
    List<ICDVersionWrapper> findAllByCreatedOnNotNull();

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(version) " +
            "FROM com.sd.his.model.ICDVersion version ")
    List<ICDVersionWrapper> findAllVersionsForDataTable();

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(version) FROM com.sd.his.model.ICDVersion  version where version.name LIKE CONCAT('%',:name,'%') ")
    List<ICDVersionWrapper> findAllByNameContaining(@Param("name") String name, Pageable pageable);

    @Query("SELECT new com.sd.his.wrapper.ICDVersionWrapper(version) FROM com.sd.his.model.ICDVersion  version where version.name LIKE CONCAT('%',:name,'%') ")
    List<ICDVersionWrapper> findAllByNameContaining(@Param("name") String name);

    ICDVersion findByNameAndIdNot(String name, long id);

}
