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

import com.jazz321254.mongo.dto.QuestionResult;
import com.jazz321254.mongo.dto.Result;
import com.jazz321254.mongo.model.Question;
import com.jazz321254.mongo.tools.CustomProjectAggregationOperation;

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

	public List<QuestionResult> findQuestionDifficultBySubjectId(String subjectId) {
		String project = "{ $project: { \"_id\": \"$_id.knowledge\", \"result\":{$cond:[  {$eq:[\"$_id.difficult\",\"hard\"]},  {\"hard\":\"$count\"},  {$cond:[{$eq:[\"$_id.difficult\",\"mid\"]},  {\"mid\":\"$count\"}, {\"easy\":\"$count\"}]}]}}}";
		String group = "{ $group:{\"_id\":\"$_id\",  \"hard\":{$sum:\"$result.hard\"},  \"mid\":{$sum:\"$result.mid\"},  \"easy\":{$sum:\"$result.easy\"}}}";
		Aggregation agg = newAggregation(
				match(new Criteria("subject").is(subjectId)),
				unwind("knowledge"),
				group("knowledge", "difficult").count().as("count"), 
				new CustomProjectAggregationOperation(project),
				new CustomProjectAggregationOperation(group));
		AggregationResults<QuestionResult> groupResults = mongoTemplate.aggregate(agg, Question.class, QuestionResult.class);
		List<QuestionResult> result = groupResults.getMappedResults();
		return result;
	}
}
