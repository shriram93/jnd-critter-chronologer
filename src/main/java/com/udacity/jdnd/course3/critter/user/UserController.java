package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.user.data.Employee;
import com.udacity.jdnd.course3.critter.user.data.User;
import com.udacity.jdnd.course3.critter.user.service.EmployeeService;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmployeeService employeeService;

    public UserController(UserService userService, EmployeeService employeeService) {
        this.userService = userService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       User user = new User();
       BeanUtils.copyProperties(customerDTO, user);
       User createdUser = userService.createUser(user);
       BeanUtils.copyProperties(createdUser, customerDTO);
       return customerDTO;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<User> users = userService.getAllUsers();
        List<CustomerDTO> customerDTOS = new LinkedList<>();
        for(User user: users) {
            CustomerDTO customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(user, customerDTO);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        Employee createdEmployee = employeeService.createEmployee(employee);
        BeanUtils.copyProperties(createdEmployee, employeeDTO);
        return employeeDTO;
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return  employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

}
