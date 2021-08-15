package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.data.Pet;
import com.udacity.jdnd.course3.critter.data.User;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
        return pets.stream().map(pet -> convertPetToPetDto(pet)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        User user = userService.getUser(ownerId);
        List<Pet> pets = petService.getPetsByOwner(user);
        return pets.stream().map(pet -> convertPetToPetDto(pet)).collect(Collectors.toList());
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
