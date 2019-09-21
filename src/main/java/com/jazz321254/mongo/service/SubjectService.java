package com.jazz321254.mongo.service;

import java.util.List;

import com.jazz321254.mongo.model.Subject;

public interface SubjectService {

	List<Subject> listAll();

	Subject getById(String id);

	Subject saveOrUpdate(Subject product);

	void delete(String id);

}