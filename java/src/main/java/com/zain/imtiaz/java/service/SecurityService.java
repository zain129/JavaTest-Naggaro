/**
 * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java.service;

public interface SecurityService {
	String findLoggedInUsername();
	String findLoggedInUserRole();
	void autoLogin(String username, String password);
}
