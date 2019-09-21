package com.jazz321254.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazz321254.mongo.model.Subject;
import com.jazz321254.mongo.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public List<Subject> listAll() {
		List<Subject> products = new ArrayList<>();
		subjectRepository.findAll().forEach(products::add);
		return products;
	}

	@Override
	public Subject getById(String id) {
		return subjectRepository.findById(id).orElse(null);
	}

	@Override
	public Subject saveOrUpdate(Subject subject) {
		subjectRepository.save(subject);
		return subject;
	}

	@Override
	public void delete(String id) {
		subjectRepository.deleteById(id);
	}

}
