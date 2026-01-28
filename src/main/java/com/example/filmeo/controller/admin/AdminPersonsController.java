package com.example.filmeo.controller.admin;

import com.example.filmeo.entity.Country;
import com.example.filmeo.entity.Person;
import com.example.filmeo.repository.CountryRepository;
import com.example.filmeo.repository.PersonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/persons")
public class AdminPersonsController {

    private final PersonRepository personRepository;
    private final CountryRepository countryRepository;

    public AdminPersonsController(PersonRepository personRepository,
                                  CountryRepository countryRepository) {
        this.personRepository = personRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "admin/persons/index";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("person", new Person());
        model.addAttribute("countries", countryRepository.findAll());
        return "admin/persons/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Person person,
                         @RequestParam(required = false) Long countryId) {

        if (countryId != null) {
            Country country = countryRepository.findById(countryId).orElse(null);
            person.setCountry(country);
        }

        personRepository.save(person);
        return "redirect:/admin/persons";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Person person = personRepository.findById(id).orElseThrow();
        model.addAttribute("person", person);
        model.addAttribute("countries", countryRepository.findAll());
        return "admin/persons/form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       @ModelAttribute Person form,
                       @RequestParam(required = false) Long countryId) {

        Person person = personRepository.findById(id).orElseThrow();

        person.setFirstName(form.getFirstName());
        person.setLastName(form.getLastName());
        person.setBirthDate(form.getBirthDate());
        person.setDeathDate(form.getDeathDate());
        person.setGender(form.getGender());
        person.setPhotoUrl(form.getPhotoUrl());

        if (countryId != null) {
            person.setCountry(countryRepository.findById(countryId).orElse(null));
        } else {
            person.setCountry(null);
        }

        personRepository.save(person);
        return "redirect:/admin/persons";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        personRepository.deleteById(id);
        return "redirect:/admin/persons";
    }
}
