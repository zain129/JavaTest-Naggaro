/**
 * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.zain.imtiaz.java.model.User;

@Component
public class UserValidation implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(UserValidation.class);

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object object, Errors errors) {
		User user = (User) object;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.empty");

		if (!(user.getUsername().equals("testadmin") || user.getUsername().equals("testUser"))) {
			logger.debug("Username not found");
			errors.rejectValue("username", "userName.notFound");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty");
	}

}
