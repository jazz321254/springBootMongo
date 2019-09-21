package com.jazz321254.mongo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazz321254.mongo.dao.QuestionDAO;
import com.jazz321254.mongo.dto.QuestionResult;
import com.jazz321254.mongo.dto.Result;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuestionServiceImpl.class);

	@Autowired
	private QuestionDAO questionDAO;
	
	@Override
	public List<Result> findUnitQuestionBySubjectId(String subjectId) {
		return questionDAO.findQuestionUnitBySubjectId(subjectId);
	}

	@Override
	public List<QuestionResult> findQuestionDifficultBySubjectId(String subjectId) {
		return questionDAO.findQuestionDifficultBySubjectId(subjectId);
	}
}
