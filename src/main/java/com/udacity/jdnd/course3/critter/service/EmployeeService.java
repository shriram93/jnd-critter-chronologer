package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Exception.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);
        return employee;
    }

    public Employee setEmployeeAvailability(Long id, Set<DayOfWeek> daysAvailable) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee employee = optionalEmployee.orElseThrow(EmployeeNotFoundException::new);
        employee.setDaysAvailable(daysAvailable);
        Employee updatedEmployee = employeeRepository.save(employee);
        return updatedEmployee;
    }
}
