package com.example.healthcare.kratin.requestModel;

import java.util.List;

public class UpdatePerson {
	private Long personId;
	private List<Long> fitnesdId;
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public List<Long> getFitnesdId() {
		return fitnesdId;
	}
	public void setFitnesdId(List<Long> fitnesdId) {
		this.fitnesdId = fitnesdId;
	}
	
	
}
