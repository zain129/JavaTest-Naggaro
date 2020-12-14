package com.zainimtiaz.nagarro.repository;

import com.zainimtiaz.nagarro.model.Document;
import com.zainimtiaz.nagarro.wrapper.DocumentWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jamal on 9/3/2018.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {


    @Query("SELECT new com.sd.his.wrapper.DocumentWrapper(doc.id,doc.createdOn,doc.updatedOn,doc.name,doc.type,doc.description,doc.url,doc.patient.id) " +
            "FROM com.sd.his.model.Document doc where doc.id=:id")
    DocumentWrapper findDocumentById(@Param("id") long documentId);

    @Query("SELECT new com.sd.his.wrapper.DocumentWrapper(doc.id,doc.createdOn,doc.updatedOn,doc.name,doc.type,doc.description,doc.url,doc.patient.id) " +
            "FROM com.sd.his.model.Document doc " +
            "WHERE doc.patient.id=:patientId")
    List<DocumentWrapper> getPaginatedDocuments(Pageable pageable, @Param("patientId") Long patientId);


    @Query("SELECT CASE WHEN COUNT (doc) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Document doc " +
            "WHERE doc.name=:name AND doc.patient.id=:patientId")
    boolean isNameExists(@Param("name") String nameDocument, @Param("patientId") Long patientId);

    @Query("SELECT CASE WHEN COUNT (doc) > 0 THEN true ELSE false END " +
            "FROM com.sd.his.model.Document doc " +
            "WHERE doc.name=:name AND doc.id <>:id AND doc.patient.id=:patientId")
    boolean isNameExistsAgainstId(@Param("name") String nameDocument, @Param("id") Long id, @Param("patientId") Long patientId);
}
