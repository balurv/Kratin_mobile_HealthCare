package com.example.healthcare.kratin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.healthcare.kratin.model.Fitness;
import com.example.healthcare.kratin.repository.FitnessRepository;
import com.example.healthcare.kratin.repository.PersonRepository;

@Service
public class FitnessService {

	@Autowired
	private FitnessRepository fitnessRepository;
	
	@Autowired
	private PersonRepository personRepository;

	public Fitness createFitness(Fitness fitnessRequest) throws Exception {	
		
		return fitnessRepository.save(fitnessRequest);
//		Fitness fitness = personRepository.findById(personId).map(person -> {
//			Long fitnessId = fitnessRequest.getId();
//			
//			if(fitnessId != null && fitnessId != 0L) {
//				Fitness _newFitness = fitnessRepository.findById(fitnessId)
//						.orElseThrow(() -> new IllegalArgumentException("fitness id not found"+ fitnessId));
//				person.addFitness(_newFitness);
//				personRepository.save(person);
//				return _newFitness;
//			}
//			Fitness savedFitness = fitnessRepository.save(fitnessRequest);
//			person.addFitness(savedFitness);
//			personRepository.save(person);
//			return savedFitness;
//		}).orElseThrow(() -> new IllegalArgumentException("Person id not found:"+ personId));
//		
//		return fitness;
	}

	public List<Fitness> getAllFitness() {
		return fitnessRepository.findAll();
	}

	public Optional<Fitness> findFitnessById(Long id) {
		return fitnessRepository.findById(id);
	}

	public Fitness updateFitness(Long id, Fitness updateFitness) {
		Fitness fitness = fitnessRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Fitness id:" + id));
		fitness.setFitnessMode(updateFitness.getFitnessMode());
		fitness.setPersons(updateFitness.getPersons());
		fitness.setProgramName(updateFitness.getProgramName());
		return fitnessRepository.save(fitness);
	}

	public String createOrUpdateFitness(Long id, Fitness updateFitness) {
		Fitness fitness = fitnessRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Fitness id:"+id));
		fitness.setFitnessMode(updateFitness.getFitnessMode());
//		fitness.setPerson();
		return null;
	}

	public void deleteFitness(Long id) throws Exception {
		Fitness fitness = findFitnessById(id).get();
		if (fitness != null) {
			fitnessRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException();
		}
	}

}
