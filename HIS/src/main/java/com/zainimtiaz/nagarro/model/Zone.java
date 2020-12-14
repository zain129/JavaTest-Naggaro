package com.zainimtiaz.nagarro.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ZONE")
public class Zone implements Serializable {
    @Id
    @Column(name = "ZONE_ID")
    private long zoneId;

    @Column(name = "ZONE_NAME")
    private String name;

    @Column(name="ZONE_TIME")
    private String zoneTime;

    public Zone() {
    }

    public Zone(String name, String zoneTime) {
        this.name = name;
        this.zoneTime = zoneTime;
    }

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneTime() {
        return zoneTime;
    }

    public void setZoneTime(String zoneTime) {
        this.zoneTime = zoneTime;
    }
}
