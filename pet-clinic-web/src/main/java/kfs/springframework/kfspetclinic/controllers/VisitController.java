package kfs.springframework.kfspetclinic.controllers;

import kfs.springframework.kfspetclinic.model.Pet;
import kfs.springframework.kfspetclinic.model.Visit;
import kfs.springframework.kfspetclinic.services.PetService;
import kfs.springframework.kfspetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class VisitController {


    private static final String VIEWS_VISITS_CREATE_OR_UPDATE_FORM="pets/createOrUpdateVisitForm";

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model){
        Pet pet = petService.findById(petId);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        model.addAttribute("pet", pet);
        return visit;
    }

    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable Long petId, Model model){

        return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String processNewVisitForm(@PathVariable Long ownerId,@Valid Visit visit, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return VIEWS_VISITS_CREATE_OR_UPDATE_FORM;
        }
        visitService.save(visit);
        return "redirect:/owners/"+ownerId;
    }

}
