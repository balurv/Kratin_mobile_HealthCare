package com.example.healthcare.kratin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.healthcare.kratin.model.Person;
import com.example.healthcare.kratin.requestModel.Membership;
import com.example.healthcare.kratin.requestModel.UpdatePerson;
import com.example.healthcare.kratin.service.PersonService;
import com.razorpay.RazorpayException;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService personService;

	@PostMapping
	public Person createPerson(@RequestBody Person person) {
		return personService.createPerson(person);
	}
	
	@PostMapping("/{id}/uploadImage")
	public void uploadImage(@PathVariable Long id, @RequestParam("personImage")MultipartFile file) throws IOException {
		personService.uploadImage(id, file);
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<byte[]> downloadImage(@PathVariable Long personId) {
		byte[] image = personService.downloadImage(personId);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(image);
	}
	
	@PostMapping("/membership")
	public void createPersonMembership(@RequestBody Membership membership ) throws RazorpayException {
		personService.createMembership(membership);
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

	@GetMapping("/bmi/{personId}")
	public Double getPersonBMI(@PathVariable("personId") Long personId) {
		return personService.calculateBMI(personId);
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
