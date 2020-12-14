package com.zainimtiaz.nagarro.repository;

        import com.zainimtiaz.nagarro.model.Insurance;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {


        @Query("SELECT CASE WHEN COUNT (insurance) > 0 THEN true ELSE false END " +
                "FROM com.sd.his.model.Insurance insurance " +
                "WHERE insurance.planN.id =:id")
        boolean isCodeAssociated(@Param("id") Long codeId);


        @Query("SELECT CASE WHEN COUNT (insurance) > 0 THEN true ELSE false END " +
                "FROM com.sd.his.model.Insurance insurance " +
                "WHERE insurance.company.id =:id")
        boolean isCodeAssociatedProfile(@Param("id") Long codeId);

}
