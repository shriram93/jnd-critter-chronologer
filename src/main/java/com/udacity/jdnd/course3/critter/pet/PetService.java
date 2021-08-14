package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.data.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet getPet(Long id) {
        Optional<Pet> optionalPet = petRepository.findById(id);
        Pet pet = optionalPet.orElseThrow(PetNotFoundException::new);
        return pet;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(User user) {
        return petRepository.findAllByUser(user);
    }
}
