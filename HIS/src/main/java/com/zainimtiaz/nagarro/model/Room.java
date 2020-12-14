package com.zainimtiaz.nagarro.model;/*
 * @author    : waqas kamran
 * @Date      : 17-Apr-18
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
 * @Package   : com.sd.his.*
 * @FileName  : UserAuthAPI
 *
 * Copyright ©
 * SolutionDots,
 * All rights reserved.
 *
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ROOM")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Room extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "ROOM_NAME")
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID", nullable = false)
    private Branch branch;

    @Column(name = "IS_ACTIVE", columnDefinition = "boolean default true", nullable = false)
    private Boolean active;

    @Column(name = "ALLOW_ONLINE_SCHEDULING")
    private Boolean allowOnlineScheduling;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Appointment> appointments;

    @Transient
    public String label;
    @Transient
    public Long value;

    public Room() {
    }

    public Room(Long id,String roomName, Boolean allowOnlineScheduling) {
        this.roomName = roomName;
        this.label = roomName;
        this.value =id;
        this.setId(id);
        this.allowOnlineScheduling = allowOnlineScheduling;
    }

    public Room(String roomName, Branch branch, Boolean active, Boolean allowOnlineScheduling) {
        this.roomName = roomName;
        this.branch = branch;
        this.active = active;
        this.allowOnlineScheduling = allowOnlineScheduling;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getAllowOnlineScheduling() {
        return allowOnlineScheduling;
    }

    public void setAllowOnlineScheduling(Boolean allowOnlineScheduling) {
        this.allowOnlineScheduling = allowOnlineScheduling;
    }
}