package com.example.healthcare.kratin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.healthcare.kratin.model.Fitness;
import com.example.healthcare.kratin.model.Image;
import com.example.healthcare.kratin.model.Person;
import com.example.healthcare.kratin.model.enums.AgeGroup;
import com.example.healthcare.kratin.model.enums.FitnessMode;
import com.example.healthcare.kratin.repository.FitnessRepository;
import com.example.healthcare.kratin.repository.ImageRepository;
import com.example.healthcare.kratin.repository.PersonRepository;
import com.example.healthcare.kratin.requestModel.Membership;
import com.example.healthcare.kratin.requestModel.UpdatePerson;
import com.example.healthcare.kratin.util.ImageUtil;
import com.razorpay.*;

@Service
public class PersonService {
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private FitnessRepository fitnessRepository;

	@Autowired
	private ImageRepository imageRepository;
	
	public Person createPerson(Person person) {
		return personRepository.save(person);
	}

	public List<Person> getAllPerson() {
		System.out.println("hello balu");
		List<Person> person = new ArrayList<>();
		personRepository.findAll().forEach(person::add);
		Collections.sort(person, new Comparator<Person>() {
			@Override
			public int compare(Person p1, Person p2) {
				// TODO Auto-generated method stub
				return p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
			}

		});
		return person;
	}

	public Optional<Person> findById(Long id) {
		Optional<Person> result = personRepository.findById(id);
		System.out.println(result);
		return result;

	}

	public Person updatePerson(UpdatePerson updatedPerson) {
		Person person = personRepository.findById(updatedPerson.getPersonId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + updatedPerson.getPersonId()));

//		person.setName(updatedPerson.getName());
//		person.setEmail(updatedPerson.getEmail());
//		person.setDateOfBirth(updatedPerson.getDateOfBirth());
		person.getFitness().clear();
//		person.removeFitness(person.getFitness().);
		Set<Fitness> fitnessSet = new HashSet<>();
		for (long fitnessId : updatedPerson.getFitnesdId()) {
			Fitness fitness = fitnessRepository.findById(fitnessId).get();
			fitnessSet.add(fitness);
		}
		person.setFitness(fitnessSet);
//		person.setGender(updatedPerson.getGender());

		return personRepository.save(person);
	}

	public void deletePerson(Long id) throws Exception {
		Person person = findById(id).get();
		if (person != null) {
			personRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Person automatePersonFitness(Long personId) {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new IllegalArgumentException("Person Id not found:" + personId));
		int personAge = person.getPersonAge(person.getDateOfBirth());
		AgeGroup ageGroup = AgeGroup.getAgeGroup(personAge);

		List<Fitness> allFitness = fitnessRepository.findAll();
		if (person.getFitness().size() > 0 || allFitness.size() < 1) {
			return person;
		}
		Set<Fitness> fitnessSet = new HashSet<>();

		switch (ageGroup) {
		case UNDER_18:
			for (Fitness fitness : allFitness) {
				if (fitnessSet.size() >= 2) {
					break;
				}
				if (fitness.getFitnessMode().equals(FitnessMode.EASY)) {
					fitnessSet.add(fitness);
				}
				if (fitness.getFitnessMode().equals(FitnessMode.MEDIUM)) {
					fitnessSet.add(fitness);
				}
			}
			break;
		case FROM_18_TO_50:
			for (Fitness fitness : allFitness) {
				if (fitnessSet.size() >= 3) {
					break;
				}
				fitnessSet.add(fitness);
			}
			break;
		case OVER_50:
			for (Fitness fitness : allFitness) {
				if (fitnessSet.size() >= 1) {
					break;
				}
				if (fitness.getFitnessMode().equals(FitnessMode.EASY)) {
					fitnessSet.add(fitness);
				}
			}
			break;
		default:
			return null;
		}
		person.setFitness(fitnessSet);
		personRepository.save(person);
		return person;
	}

	public Double calculateBMI(Long personId) {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new IllegalArgumentException("Person Id not found:" + personId));
		double weight = person.getKg();
		double height = (double) person.getHeight() / 100;
		Double bmi = weight / (height * height);
		return bmi;
	}

	public String createMembership(Membership membership) throws RazorpayException {
		
		var razorpayClient = new RazorpayClient("rzp_test_CeY2Pl2te31vRX", "saoiyO1PHzh7S91S0iixVxOu");

		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", membership.getAmount() * 100); // amount in the smallest currency unit
		orderRequest.put("currency", "INR");
		orderRequest.put("receipt", "order_rcptid_11");
		Order order = razorpayClient.orders.create(orderRequest);
//		System.out.println(order);
		return order.toString();
	}

	public void uploadImage(Long personId, MultipartFile file) throws IOException {
		Person person = personRepository.findById(personId)
				.orElseThrow(() -> new IllegalArgumentException("Person Id not found:" + personId));
		Image image = new Image();
		image.setImageData(ImageUtil.compressImage(file.getBytes()));
		image.setPerson(person);
		imageRepository.save(image);
	}

	public byte[] downloadImage(Long personId) {
		
//		Optional<Person> person = personRepository.findById(personId);
		List<Image> image = imageRepository.findByPersonId(personId);
		if(image.size() > 0) {
			return ImageUtil.decompressImage(image.get(0).getImageData());
		}
		return null;
	}
}
