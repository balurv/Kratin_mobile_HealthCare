package com.example.healthcare.kratin.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

import com.example.healthcare.kratin.model.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "VARCHAR(255)")
	private com.example.healthcare.kratin.model.enums.Gender gender;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "person_fitness", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "fitness_id"))
//	@JsonIgnore
	private Set<Fitness> fitness;

	public Person() {
		
	}

	public Person(String name, String email, LocalDate dateOfBirth, String phoneNumber, Gender gender) {
		this.name = name;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public com.example.healthcare.kratin.model.enums.Gender getGender() {
		return gender;
	}

	public void setGender(com.example.healthcare.kratin.model.enums.Gender gender) {
		this.gender = gender;
	}

	public Set<Fitness> getFitness() {
		return fitness;
	}

	public void setFitness(Set<Fitness> fitness) {
		this.fitness = fitness;
	}
	
	public void addFitness(Fitness fitness) {
		this.fitness.add(fitness);
		fitness.getPersons().add(this);
	}
	
	public void removeFitness(long fitnessId) {
		Fitness fitness = this.fitness.stream().filter(t -> t.getId() == fitnessId).findFirst().orElse(null);
		if(fitness != null) {
			this.fitness.remove(fitness);
			fitness.getPersons().remove(this);
		}
	}

	public int getPersonAge(LocalDate dateOfBirth) {
		Period period = Period.between(dateOfBirth, LocalDate.now());
		return period.getYears();
	}
	
	public void clearFitness() {
		// TODO Auto-generated method stub
		
	}	
}