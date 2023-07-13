package com.example.healthcare.kratin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.healthcare.kratin.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

	List<Image> findByPersonId(Long personId);
}
