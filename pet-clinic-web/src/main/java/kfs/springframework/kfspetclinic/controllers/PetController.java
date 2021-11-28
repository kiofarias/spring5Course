package kfs.springframework.kfspetclinic.controllers;

import kfs.springframework.kfspetclinic.model.Owner;
import kfs.springframework.kfspetclinic.model.Pet;
import kfs.springframework.kfspetclinic.model.PetType;
import kfs.springframework.kfspetclinic.services.OwnerService;
import kfs.springframework.kfspetclinic.services.PetService;
import kfs.springframework.kfspetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_FORM="pets/createOrUpdatePetForm";
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes(){
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId){
        return ownerService.findById(ownerId);
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model){
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet",pet);
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/new")
    public String processCreationForm(Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model){
        if(StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(),true)!= null){
            bindingResult.rejectValue("name","duplicate", "already exists");
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        pet.setOwner(owner);
        owner.getPets().add(pet);
        petService.save(pet);
        return "redirect:/owners/"+owner.getId();
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model){
        model.addAttribute("pet",petService.findById(petId));
        return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@PathVariable Long petId, Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            pet.setOwner(owner);
            model.addAttribute("pet",pet);
            return VIEWS_PETS_CREATE_OR_UPDATE_FORM;
        }
        Pet petUpdated = petService.findById(petId);
        petUpdated.setName(pet.getName());
        petUpdated.setBirthDate(pet.getBirthDate());
        petUpdated.setPetType(pet.getPetType());
        petService.save(petUpdated);
        return "redirect:/owners/"+owner.getId();
    }
}
