package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.Exception.UserNotFoundException;
import com.udacity.jdnd.course3.critter.data.User;
import com.udacity.jdnd.course3.critter.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PetService petService;

    public UserService(UserRepository userRepository, PetService petService) {
        this.userRepository = userRepository;
        this.petService = petService;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(UserNotFoundException::new);
        return user;
    }

    public User getOwnerByPet(Long petId) {
        Pet pet = petService.getPet(petId);
        Optional<User> optionalUser = userRepository.findByPet(pet);
        User user = optionalUser.orElseThrow(UserNotFoundException::new);
        return user;
    }
}
