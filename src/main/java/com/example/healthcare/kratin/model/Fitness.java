package com.example.healthcare.kratin.model;

import java.util.HashSet;
import java.util.Set;

import com.example.healthcare.kratin.model.enums.FitnessMode;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Fitness {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	private String programName;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(255)")
	private com.example.healthcare.kratin.model.enums.FitnessMode fitnessMode;

	@ManyToMany(mappedBy = "fitness", fetch = FetchType.LAZY, 
			cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JsonIgnore
//	@JoinTable(name = "person_fitness", joinColumns = @JoinColumn(name = "fitness_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))

	private Set<Person> persons = new HashSet<>();

	public Fitness() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public FitnessMode getFitnessMode() {
		return fitnessMode;
	}

	public void setFitnessMode(FitnessMode fitnessMode) {
		this.fitnessMode = fitnessMode;
	}

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	

//	public void addFitness(Fitness fitness) {
//		this.fi
//	}
}
