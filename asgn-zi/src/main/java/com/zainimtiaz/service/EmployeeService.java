package com.zainimtiaz.service;

import java.util.List;

import com.zainimtiaz.model.Employee;

public interface EmployeeService {
	int insertEmployee(Employee emp);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(String empid);
	int save(Employee employee);
	int delete(String employeeId);
}