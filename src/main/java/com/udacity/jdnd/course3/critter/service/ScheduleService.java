package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.Exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.Exception.ScheduleNotFoundException;
import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.Schedule;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final PetService petService;

    public ScheduleService(ScheduleRepository scheduleRepository, PetService petService) {
        this.scheduleRepository = scheduleRepository;
        this.petService = petService;
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
}
