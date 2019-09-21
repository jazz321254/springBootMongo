package com.jazz321254.mongo.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.jazz321254.mongo.dto.Result;
import com.jazz321254.mongo.model.Question;

@Repository
public class QuestionDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Result> findQuestionUnitBySubjectId(String subjectId) {
		Aggregation agg = newAggregation(
				match(new Criteria("subject").is(subjectId)),
				lookup("unit", "knowledge", "knowledge", "units"),
				unwind("units"),
				sort(Sort.Direction.ASC, "_id").and(Sort.Direction.DESC, "units.order"),
				group("_id").first("difficult").as("difficult").first("units._id").as("unit"),
				group("unit").count().as("total")
				);
		AggregationResults<Result> groupResults = mongoTemplate.aggregate(agg, Question.class, Result.class);
		List<Result> result = groupResults.getMappedResults();
		return result;
	}
}
