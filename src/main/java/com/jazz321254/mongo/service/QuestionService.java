package com.jazz321254.mongo.service;

import java.util.List;

import com.jazz321254.mongo.dto.Result;

public interface QuestionService {
	
	List<Result> findUnitQuestionBySubjectId(String subjectId);
}