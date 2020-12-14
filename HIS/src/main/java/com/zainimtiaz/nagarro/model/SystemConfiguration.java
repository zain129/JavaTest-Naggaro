package com.zainimtiaz.nagarro.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYSTEM_CONFIGURATION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SystemConfiguration extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "CLIENT_ID")
    private String clientId;

    @Column(name = "CLIENT_SECRET")
    private String clientSecret;

    @Column(name = "AUTH_SERVER_SCHEME")
    private String authServerScheme;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAuthServerScheme() {
        return authServerScheme;
    }

    public void setAuthServerScheme(String authServerScheme) {
        this.authServerScheme = authServerScheme;
    }
}
