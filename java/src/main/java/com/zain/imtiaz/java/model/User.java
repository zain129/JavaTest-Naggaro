/**
  * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(doNotUseGetters = true)
@Entity
@Table(name = "users") // , schema = "nagarro")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "username", nullable = false, length = -1)
	private String username;

	@Basic
	@Column(name = "password", nullable = false, length = -1)
	private String password;

	@Basic
	@Column(name = "role", nullable = false)
	private Long role; // 1 = Admin User, 2 = Test User

}
