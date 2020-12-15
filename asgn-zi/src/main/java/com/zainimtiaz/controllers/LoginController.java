package com.zainimtiaz.controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zainimtiaz.model.Employee;
import com.zainimtiaz.service.EmployeeService;

@RestController
public class LoginController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/getEmp")
	public List<Employee> all() {
		return employeeService.getAllEmployees();
	}

	@GetMapping("/getEmp/{id}")
	public Employee one(@PathVariable Long id) {
		return employeeService.getEmployeeById(id + "");
	}

	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		int result = employeeService.delete(id + "");
		if (result > 0)
			return "Employee with ID=" + id + " deleted";
		return "Failed to delete Employee";
	}

	@PostMapping("/saveEmp")
	public Employee save(@RequestBody Employee newEmployee) {
		if (employeeService.save(newEmployee) > 0)
			return newEmployee;
		return null;
	}

	@GetMapping("/")
	public String userAuth() {
		// get security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// check whether the auth token is known or not
		if (authentication instanceof AnonymousAuthenticationToken) {
			return "Invalid session";
		}

		String userType = "";
		Collection<? extends GrantedAuthority> listAuth = authentication.getAuthorities();
		for (GrantedAuthority authority : listAuth) {
			if (authority.getAuthority().contains("ROLE_ADMIN")) {
				userType = "Admin User";
				break;
			}

			if (authority.getAuthority().contains("ROLE_USER")) {
				userType = "Test User";
				break;
			}
		}
		return "Welcome to the test app. Logged-in as " + userType;
	}

	@GetMapping("/403")
	public String forbidden() {
		return "You are not authorized.";
	}
	
	@GetMapping(value = "/login/{param}")
	public String login(@PathVariable String param) {
		if (param != null) {
			if(param.trim().equals("loginFailed")) {
				return "Failed to login. Please try again";
			}
			
			if(param.trim().equals("loginExpired")) {
				return "Session timed out.";
			}
			
		}
		return "Logged in successfuly";
	}

//	@RequestMapping({ "/", "/welcome" })
//	public ModelAndView firstPage() {
//
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.setViewName("welcome");
//
//		// get security context
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		// check whether the auth token is known or not
//		if (authentication instanceof AnonymousAuthenticationToken) {
//			// sending back to login page for authentication
//			modelAndView.setViewName("login");
//			return modelAndView;
//		}
//
//		String userType = "";
//
//		Collection<? extends GrantedAuthority> listAuth = authentication.getAuthorities();
//		for (GrantedAuthority authority : listAuth) {
//			if (authority.getAuthority().contains("ROLE_ADMIN")) {
//				userType = "Admin User";
//				break;
//			}
//
//			if (authority.getAuthority().contains("ROLE_USER")) {
//				userType = "Test User";
//				break;
//			}
//		}
//
//		modelAndView.addObject("userType", userType);
//		return modelAndView;
//	}

//	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.GET)
//	public ModelAndView show() {
//		return new ModelAndView("addEmployee", "emp", new Employee());
//	}

//	@RequestMapping(value = "/addNewEmployee", method = RequestMethod.POST)
//	public ModelAndView processRequest(@ModelAttribute("emp") Employee emp) {
//
//		employeeService.insertEmployee(emp);
//		List<Employee> employees = employeeService.getAllEmployees();
//		ModelAndView model = new ModelAndView("getEmployees");
//		model.addObject("employees", employees);
//		return model;
//	}

//	@RequestMapping("/getEmployees")
//	public ModelAndView getEmployees() {
//		List<Employee> employees = employeeService.getAllEmployees();
//		ModelAndView model = new ModelAndView("getEmployees");
//		model.addObject("employees", employees);
//		return model;
//	}

//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login(Model model, String error, String logout, String expired) {
//		if (error != null) {
//			model.addAttribute("errorMsg", "Your username and password are invalid.");
//		}
//		if (logout != null) {
//			model.addAttribute("msg", "You have been logged out successfully.");
//		}
//		if (expired != null) {
//			model.addAttribute("errorMsg", "Your session has been expired.");
//		}
//		return "login";
//	}

}
