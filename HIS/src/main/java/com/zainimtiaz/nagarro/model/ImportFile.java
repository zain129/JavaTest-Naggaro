package com.zainimtiaz.nagarro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IMPORT_FILE")
public class ImportFile extends BaseEntity {
    @Column(name = "PATH")
    private String path;

    @Column(name = "DUPLICATE_RECORD_OPERATION")
    private String duplicateRecordOperation;

    @Column(name = "CHARACTER_ENCODING")
    private String characterEncoding;

    @Column(name = "STATUS")
    private boolean active;

    @Column(name = "FIELD_MAPPING")
    private String fieldMapping;

    public ImportFile() {
    }

    public ImportFile(String path, String duplicateRecordOperation, String characterEncoding) {
        this.path = path;
        this.duplicateRecordOperation = duplicateRecordOperation;
        this.characterEncoding = characterEncoding;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuplicateRecordOperation() {
        return duplicateRecordOperation;
    }

    public void setDuplicateRecordOperation(String duplicateRecordOperation) {
        this.duplicateRecordOperation = duplicateRecordOperation;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public String getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(String fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

