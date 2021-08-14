package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.data.User;
import com.udacity.jdnd.course3.critter.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    private final PetService petService;
    private final UserService userService;

    public PetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
       Pet createdPet = petService.createPet(convertPetDTOToPet(petDTO));
       return convertPetToPetDto(createdPet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId);
        return convertPetToPetDto(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOS = new LinkedList<>();
        pets.forEach(pet -> petDTOS.add(convertPetToPetDto(pet)));
        return petDTOS;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        User user = userService.getUser(ownerId);
        List<Pet> pets = petService.getPetsByOwner(user);
        List<PetDTO> petDTOS = new LinkedList<>();
        pets.forEach(pet -> petDTOS.add(convertPetToPetDto(pet)));
        return petDTOS;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        User user = userService.getUser(petDTO.getOwnerId());
        List<Pet> pets = user.getPets();
        pets.add(pet);
        user.setPets(pets);
        pet.setUser(user);
        return pet;
    }

    private PetDTO convertPetToPetDto(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getUser().getId());
        return petDTO;
    }
}
