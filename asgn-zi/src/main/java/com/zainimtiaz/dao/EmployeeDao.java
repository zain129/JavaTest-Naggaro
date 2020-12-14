package com.zainimtiaz.dao;

import java.util.List;

import com.zainimtiaz.model.Employee;

public interface EmployeeDao {
	int insertEmployee(Employee employee);
	void insertEmployees(List<Employee> employees);
	List<Employee> getAllEmployees();
	Employee getEmployeeById(String empId);
	int save(Employee employee);
	int delete(String employeeId);
}