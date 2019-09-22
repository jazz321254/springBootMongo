package com.jazz321254.mongo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jazz321254.mongo.dao.QuestionDAO;
import com.jazz321254.mongo.dto.QuestionResult;
import com.jazz321254.mongo.dto.Result;
import com.jazz321254.mongo.dto.UnitQuestions;

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

	@Override
	public List<String> genQuestion(String unitId, Integer nums) {
		if (nums <= 0) {
			LOGGER.warn("The numbers is invalid !");
			return null;
		}
		List<UnitQuestions> uqs = questionDAO.findQuestionByUnitId(unitId);
		Integer totalNums = uqs.stream()
				.map(UnitQuestions::getItems)
				.filter(rs -> rs != null).mapToInt(List::size)
				.sum();
		if (nums > totalNums) {
			LOGGER.warn("Questions is not enough !");
			return null;
		}
		List<String> allQus = new ArrayList<>();
		Set<String> resultSet = new HashSet<>();
		Random rand = new Random();
		for (UnitQuestions uq : uqs) {
			LOGGER.debug(uq.getItems().toString());
			allQus.addAll(uq.getItems());
			Integer temp = (int) (nums * ((double) uq.getItems().size() / (double) totalNums));
			for (int i = 0; i < temp; i++) {
				String s = uq.getItems().get(rand.nextInt(uq.getItems().size()));
				while (resultSet.contains(s)) {
					s = uq.getItems().get(rand.nextInt(uq.getItems().size()));
				}
				resultSet.add(s);
			}
		}
		// if not full
		while (resultSet.size() < nums) {
			String s = allQus.get(rand.nextInt(allQus.size()));
			while (resultSet.contains(s)) {
				s = allQus.get(rand.nextInt(allQus.size()));
			}
			resultSet.add(s);
		}
		List<String> resultList = new ArrayList<>(resultSet);
		Collections.shuffle(resultList);
		return resultList;
	}

}
