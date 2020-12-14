package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.DrugManufacturer;
import com.zainimtiaz.nagarro.wrapper.DrugManufacturerWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface DrugManufacturerRepository extends JpaRepository<DrugManufacturer, Long> {

    @Query("SELECT new com.sd.his.wrapper.DrugManufacturerWrapper(dm) FROM DrugManufacturer dm ")
    List<DrugManufacturerWrapper> getAllDrugManufacturers();

    @Query("SELECT new com.sd.his.wrapper.DrugManufacturerWrapper(dm) FROM DrugManufacturer dm WHERE dm.id = :id ")
    DrugManufacturerWrapper getDrugManufacturerById(@Param("id") Long id);

    @Query("SELECT new com.sd.his.wrapper.DrugManufacturerWrapper(dm) FROM DrugManufacturer dm WHERE dm.name LIKE :drugMaker ")
    DrugManufacturerWrapper getDrugManufacturerByCompanyName(@Param("drugMaker") String drugMaker);
}
