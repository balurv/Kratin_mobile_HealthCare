package com.example.healthcare.kratin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.kratin.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
