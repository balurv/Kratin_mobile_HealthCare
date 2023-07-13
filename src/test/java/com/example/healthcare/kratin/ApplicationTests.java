package com.example.healthcare.kratin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.healthcare.kratin.model.Person;
import com.example.healthcare.kratin.model.enums.AgeGroup;
import com.example.healthcare.kratin.model.enums.Gender;

@SpringBootTest
class ApplicationTests {

	private Person balu = new Person("balu", "balurv1997@gmail.com", LocalDate.of(1997, 10, 01), "9035540770", Gender.MALE );

	@Test
	void personAge() {
		assertEquals(balu.getPersonAge(balu.getDateOfBirth()), 25);
	}

	@Test
	void personAgeGroup() {
		assertEquals(AgeGroup.getAgeGroup(balu.getPersonAge(balu.getDateOfBirth())), AgeGroup.FROM_18_TO_50);
	}
}
