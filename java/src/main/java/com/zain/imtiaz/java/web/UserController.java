/**
 * @author Zain Imtiaz
 **/

package com.zain.imtiaz.java.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zain.imtiaz.java.model.User;
import com.zain.imtiaz.java.service.SecurityService;
import com.zain.imtiaz.java.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidation userValidation;

	@GetMapping("/registration")
	public String registration(Model model) {
		model.addAttribute("userForm", new User());

		return "registration";
	}

	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidation.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPassword());

		return "redirect:/welcome";
	}

	@RequestMapping({ "/login" })
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

//        return "redirect:/welcome";
		return "login";
	}

	@GetMapping({ "/", "/welcome" })
	public String welcome(Model model) {
		model.addAttribute("userRole", securityService.findLoggedInUserRole());
		return "welcome";
	}

	@GetMapping({ "error" })
	public String error(Model model) {
		model.addAttribute("userRole", securityService.findLoggedInUserRole());
		return "error";
	}
}
