package com.example.healthcare.kratin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.kratin.model.Person;
import com.example.healthcare.kratin.requestModel.UpdatePerson;
import com.example.healthcare.kratin.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping
	public Person createPerson(@RequestBody Person person) {
		return personService.createPerson(person);
	}

	@GetMapping
	public List<Person> getAllPersons() {
		return personService.getAllPerson();
	}

	@GetMapping("/{id}")
	public Optional<Person> getPersonById(@PathVariable Long id) {
		return personService.findById(id);
	
	}
	@GetMapping("/personFitness/{personId}")
	public Person automatePersonFitness(@PathVariable("personId")Long personId){
		return personService.automatePersonFitness(personId);
	}

	@PutMapping("/{id}")
	public Person updatePerson( @RequestBody UpdatePerson updatedPerson) {
		return personService.updatePerson(updatedPerson);
	}

	@DeleteMapping("/{id}")
	public String deletePerson(@PathVariable Long id) {
		try {
			personService.deletePerson(id);
		} catch (Exception e) {
			return e.toString();
		}
		return "Successfully deleted!";
	}
}
