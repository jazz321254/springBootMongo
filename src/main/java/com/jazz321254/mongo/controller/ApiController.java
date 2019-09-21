package com.jazz321254.mongo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jazz321254.mongo.dto.Result;
import com.jazz321254.mongo.model.Subject;
import com.jazz321254.mongo.service.QuestionService;
import com.jazz321254.mongo.service.SubjectService;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private QuestionService questionService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	protected static final String LOGGER_HEADER = "========== {} ==========";

	@GetMapping("/subjects")
	public List<Subject> getAllSubject() {
		LOGGER.info(LOGGER_HEADER, "Fetch to all subjects");
		return subjectService.listAll();
	}

	@GetMapping("/unit_question_amont/{subjectId}")
	public List<Result> getUnitCount(@PathVariable(value = "subjectId") String subjectId) {
		LOGGER.info(LOGGER_HEADER, "Fetch the number of unit question by subject id, subjectId: " + subjectId);
		return questionService.findUnitQuestionBySubjectId(subjectId);
	}
}
