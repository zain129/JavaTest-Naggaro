/**
 * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java.service;

import com.zain.imtiaz.java.model.User;

public interface UserService {
	void save(User user);

	User findByUsername(String username);
}
