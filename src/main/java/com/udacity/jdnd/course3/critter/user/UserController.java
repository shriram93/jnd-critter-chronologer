package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.User;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.UserService;
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
    private final PetService petService;

    public UserController(UserService userService, EmployeeService employeeService, PetService petService) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
       User createdUser = userService.createUser(convertCustomerDTOToUser(customerDTO));
       return convertUserToCustomerDTO(createdUser);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<User> users = userService.getAllUsers();
        List<CustomerDTO> customerDTOS = new LinkedList<>();
        users.forEach(user -> customerDTOS.add(convertUserToCustomerDTO(user)));
        return customerDTOS;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        User user = userService.getOwnerByPet(petId);
        return convertUserToCustomerDTO(user);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee createdEmployee = employeeService.createEmployee(convertEmployeeDTOToEmployee(employeeDTO));
        return convertEmployeeToEmployeeDTO(createdEmployee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return convertEmployeeToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public EmployeeDTO setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee updatedEmployee = employeeService.setEmployeeAvailability(employeeId, daysAvailable);
        return convertEmployeeToEmployeeDTO(updatedEmployee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    private User convertCustomerDTOToUser(CustomerDTO customerDTO) {
        User user = new User();
        user.setName(customerDTO.getName());
        user.setPhoneNumber(customerDTO.getPhoneNumber());
        List<Pet> pets = new LinkedList<>();
        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            petIds.forEach(id -> petService.getPet(id));
        }
        user.setPets(pets);
        return user;
    }

    private CustomerDTO convertUserToCustomerDTO(User user) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(user.getId());
        customerDTO.setName(user.getName());
        customerDTO.setPhoneNumber(user.getPhoneNumber());
        List<Long> petIds = new LinkedList<>();
        List<Pet> pets = user.getPets();
        if (pets != null) {
            pets.forEach(pet -> petIds.add(pet.getId()));
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        return employee;
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        employeeDTO.setSkills(employee.getSkills());
        return employeeDTO;
    }
}
