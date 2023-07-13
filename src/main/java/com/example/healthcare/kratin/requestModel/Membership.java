package com.example.healthcare.kratin.requestModel;

public class Membership {
	private int personId;
	private Double amount;
	
	Membership(){
		
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Membership(int personId, Double amount) {
		this.personId = personId;
		this.amount = amount;
	}
	
	
}
