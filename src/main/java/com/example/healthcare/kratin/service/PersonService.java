package com.example.healthcare.kratin.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.healthcare.kratin.model.Fitness;
import com.example.healthcare.kratin.model.Person;
import com.example.healthcare.kratin.model.enums.AgeGroup;
import com.example.healthcare.kratin.model.enums.FitnessMode;
import com.example.healthcare.kratin.repository.FitnessRepository;
import com.example.healthcare.kratin.repository.PersonRepository;
import com.example.healthcare.kratin.requestModel.UpdatePerson;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private FitnessRepository fitnessRepository;

	public Person createPerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> getAllPerson() {
		List<Person> person = new ArrayList<>();
		personRepository.findAll().forEach(person::add);
		Collections.sort(person, new Comparator<Person>() {
			@Override
			public int compare(Person p1, Person p2) {
				// TODO Auto-generated method stub
				return p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
			}
			
		});
		return person;
	}

	public Optional<Person> findById(Long id) {
		Optional<Person> result = personRepository.findById(id);
		System.out.println(result);
		return result;
			
	}

	public Person updatePerson( UpdatePerson updatedPerson) {
		Person person = personRepository.findById(updatedPerson.getPersonId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + updatedPerson.getPersonId()));

//		person.setName(updatedPerson.getName());
//		person.setEmail(updatedPerson.getEmail());
//		person.setDateOfBirth(updatedPerson.getDateOfBirth());
		person.getFitness().clear();
//		person.removeFitness(person.getFitness().);
		Set<Fitness> fitnessSet = new HashSet<>();
		for (long fitnessId : updatedPerson.getFitnesdId()) {
			Fitness fitness = fitnessRepository.findById(fitnessId).get();
			fitnessSet.add(fitness);
		}
		person.setFitness(fitnessSet);
//		person.setGender(updatedPerson.getGender());

		return personRepository.save(person);
	}

	public void deletePerson(Long id) throws Exception {
		Person person = findById(id).get();
		if (person != null) {
			personRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Person automatePersonFitness(Long personId) {
		Person person = personRepository.findById(personId).orElseThrow(() -> new IllegalArgumentException("Person Id not found:"+personId));
		Period period = Period.between(person.getDateOfBirth(), LocalDate.now());
		int personAge = period.getYears();
		AgeGroup ageGroup = AgeGroup.getAgeGroup(personAge);
		
		List<Fitness> allFitness = fitnessRepository.findAll();
		if(person.getFitness().size() > 0 || allFitness.size() < 1) {
			return person;
		}
		Set<Fitness>fitnessSet = new HashSet<>();

		switch(ageGroup) {
		case UNDER_18:
			for(Fitness fitness : allFitness) {
				if(fitnessSet.size() >= 2) {
					break;
				}
				if(fitness.getFitnessMode().equals(FitnessMode.EASY)) {
					fitnessSet.add(fitness);
				}
				if(fitness.getFitnessMode().equals(FitnessMode.MEDIUM)) {
					fitnessSet.add(fitness);
				}
			}
			break;
		case FROM_18_TO_50:
			for(Fitness fitness : allFitness) {
				if(fitnessSet.size() >= 3) {
					break;
				}
				fitnessSet.add(fitness);
			}
			break;
		case OVER_50:
			for(Fitness fitness : allFitness) {
				if(fitnessSet.size() >= 1) {
					break;
				}
				if(fitness.getFitnessMode().equals(FitnessMode.EASY)) {
					fitnessSet.add(fitness);
				}
			}
			break;
		default:
			return null;
		}
		person.setFitness(fitnessSet);
		personRepository.save(person);
		return person;
	}
}
