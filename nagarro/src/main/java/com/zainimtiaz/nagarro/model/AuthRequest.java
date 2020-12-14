/**
 * @author Zain I.
 * created on 13/12/2020
 **/

package com.zainimtiaz.nagarro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest implements Serializable {
    private static final long serialVersionUID = -1L;
    private String username;
    private String password;
}

