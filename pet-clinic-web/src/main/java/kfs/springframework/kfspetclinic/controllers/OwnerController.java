package kfs.springframework.kfspetclinic.controllers;

import kfs.springframework.kfspetclinic.model.Owner;
import kfs.springframework.kfspetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM="owners/createOrUpdateOwnerForm";
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("/find")
    public String findOwners(Model model){
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult result ,Model model){
        if(owner.getLastName()==null){
            owner.setLastName("");
        }
        List<Owner> results = ownerService.findAllByLastNameLike("%"+owner.getLastName()+"%");
        if(results.isEmpty()){
            result.rejectValue("lastName","notFound","not found");
            return "owners/findOwners";
        }
        if(results.size()==1){
            owner=results.iterator().next();
            return "redirect:/owners/"+owner.getId();
        }
        model.addAttribute("listOwners", results);
        return "owners/ownersList";
    }

    @GetMapping("/{ownerId}")
    public ModelAndView listOwnerDetails(@PathVariable("ownerId") Long ownerId){
        ModelAndView mav= new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;
    }

    @GetMapping("/new")
    public String initCreationForm(Model model){
        model.addAttribute("owner",Owner.builder().build());
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, BindingResult result){
        if(result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        Owner ownerSaved = ownerService.save(owner);
        return  "redirect:/owners/"+ownerSaved.getId();
    }

    @GetMapping("/{ownerId}/edit")
    public String initUpdateForm(@PathVariable("ownerId") Long ownerId, Model model){
        model.addAttribute("owner",ownerService.findById(ownerId));
        return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{ownerId}/edit")
    public String processUpdateForm(@Valid Owner owner, @PathVariable("ownerId") Long ownerId, BindingResult result){
        if(result.hasErrors()){
            return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
        }
        owner.setId(ownerId);
        Owner ownerSaved = ownerService.save(owner);
        return  "redirect:/owners/"+ownerSaved.getId();
    }
}

