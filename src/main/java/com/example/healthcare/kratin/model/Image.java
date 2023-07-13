package com.example.healthcare.kratin.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Lob
	@Column(name = "imagedata")
	private byte[] imageData;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Image() {
		
	}

	public Image(Long id, byte[] imageData, Person person) {
		this.id = id;
		this.imageData = imageData;
		this.person = person;
	}
	
	
}
