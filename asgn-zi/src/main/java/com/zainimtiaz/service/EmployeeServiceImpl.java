package com.zainimtiaz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zainimtiaz.dao.EmployeeDao;
import com.zainimtiaz.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDao;

	@Override
	public int insertEmployee(Employee employee) {
		return employeeDao.insertEmployee(employee);
	}

	@Override
	public void insertEmployees(List<Employee> employees) {
		employeeDao.insertEmployees(employees);
	}

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	@Override
	public Employee getEmployeeById(String employeeId) {
		if (!employeeId.trim().isEmpty())
			return employeeDao.getEmployeeById(employeeId);
		return null;
	}

	@Override
	public int save(Employee employee) {
		return employeeDao.save(employee);
	}

	@Override
	public int delete(String employeeId) {
		if (!employeeId.trim().isEmpty())
			return employeeDao.delete(employeeId);
		return -1;
	}

}