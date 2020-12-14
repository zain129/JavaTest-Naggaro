package com.zainimtiaz.nagarro.model;

import javax.persistence.*;
import java.io.Serializable;

/*
 * @author    : Irfan Nasim
 * @Date      : 02-May-18
 * @version   : ver. 1.0.0
 *
 * ________________________________________________________________________________________________
 *
 *  Developer				Date		     Version		Operation		Description
 * ________________________________________________________________________________________________
 *
 *
 * ________________________________________________________________________________________________
 *
 * @Project   : HIS
 * @Package   : com.sd.his.model
 * @FileName  : ICDCodeVersion
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */
@Entity
@Table(name = "ICD_CODE_VERSION")
public class ICDCodeVersion extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICD_ID", nullable = false)
    private ICDCode icd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICD_VERSION_ID", nullable = false)
    private ICDVersion version;

    @Column(name = "description",length = 400)
    private String description;

    public ICDCodeVersion() {
    }

    public ICDCode getIcd() {
        return icd;
    }

    public void setIcd(ICDCode icd) {
        this.icd = icd;
    }

    public ICDVersion getVersion() {
        return version;
    }

    public void setVersion(ICDVersion version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
