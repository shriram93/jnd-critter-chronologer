package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Employee;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetService petService;
    private final EmployeeService employeeService;

    public ScheduleService(ScheduleRepository scheduleRepository, PetService petService, EmployeeService employeeService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(Long petId) {
        Pet pet = petService.getPet(petId);
        return scheduleRepository.findAllByPet(pet);
    }

    public List<Schedule> getScheduleForEmployee(Long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return scheduleRepository.findAllByEmployee(employee);
    }

    public List<Schedule> getScheduleForCustomer(Long customerId) {
        List<Schedule> schedules = scheduleRepository.findAllByUserId(customerId);
        return schedules;
    }
}
