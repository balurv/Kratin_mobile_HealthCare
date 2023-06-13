package com.example.healthcare.kratin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.healthcare.kratin.model.Fitness;
import com.example.healthcare.kratin.service.FitnessService;

@RestController
//@RequestMapping("/fitness")
public class FitnessController {

	@Autowired
	private FitnessService fitnessService;
	
//	@PostMapping("/person/{personId}/fitness")
	@PostMapping("/fitness")
	public Fitness createFitness(@RequestBody Fitness fitness) throws Exception {
		return fitnessService.createFitness(fitness);
	}
	
	@GetMapping("/fitness")
	public List<Fitness> getAllFitness(){
		return fitnessService.getAllFitness();
	}
	
	@GetMapping("/fitness/{id}")
	public Optional<Fitness> getFitnessById(@PathVariable Long id) {
		return fitnessService.findFitnessById(id);
	}
	
//	@PutMapping("/{id}")
//	public Fitness updateFitness(@PathVariable Long id, @RequestBody Fitness updateFitness) {
//		return fitnessService.updateFitness(id, updateFitness);
//	}
	
//	@PatchMapping("/{id}")
//	public String createOrUpdate(@PathVariable Long id, @RequestBody Fitness updateFitness) {
//		return fitnessService.createOrUpdateFitness(id, updateFitness);
//	}
	
	@DeleteMapping("/fitness/{id}")
	public String deleteFitness(@PathVariable Long id) {
		try {
			fitnessService.deleteFitness(id);
		}
		catch(Exception e) {
			return e.toString();
		}
		return "Successfully deleted!";
	}
	
}
