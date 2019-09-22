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
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.jazz321254.mongo.dto.QuestionResult;
import com.jazz321254.mongo.dto.Result;
import com.jazz321254.mongo.dto.UnitQuestions;
import com.jazz321254.mongo.model.Question;
import com.jazz321254.mongo.tools.CustomProjectAggregationOperation;

@Repository
public class QuestionDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Result> findQuestionUnitBySubjectId(String subjectId) {
		Aggregation agg = newAggregation(match(new Criteria("subject").is(subjectId)));
		AggregationResults<Result> groupResults = mongoTemplate.aggregate(agg, "subjectQuestion", Result.class);
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

	public List<UnitQuestions> findQuestionByUnitId(String unitId){
		Aggregation agg = newAggregation(match(new Criteria("unit").is(unitId)));
		AggregationResults<UnitQuestions> groupResults = mongoTemplate.aggregate(agg, "unitQuestion", UnitQuestions.class);
		List<UnitQuestions> result = groupResults.getMappedResults();
		return result;
	}
	
	@Async
	@Scheduled(fixedRate = 60000 * 30)
	public void genSubjectQuestionCollection() {
		String merge = "{$merge: { into: \"subjectQuestion\", whenMatched: \"replace\", whenNotMatched: \"insert\" }}";
		Aggregation agg = newAggregation(
				lookup("unit", "knowledge", "knowledge", "units"),
				unwind("units"),
				sort(Sort.Direction.ASC, "_id").and(Sort.Direction.DESC, "units.order"),
				group("_id").first("units.subject").as("subject").first("difficult").as("difficult").first("units._id").as("unit"),
				group("unit").first("subject").as("subject").count().as("total"),
				new CustomProjectAggregationOperation(merge)
				);
		mongoTemplate.aggregate(agg, Question.class, Result.class);
	}
	
	@Async
	@Scheduled(fixedRate = 60000 * 30)
	public void genUnitQuestionCollection(){
		String group = "{$group: { _id: { unit: \"$unit\", difficult: \"$difficult\"}, items: {$addToSet: \"$_id\"}}}";
		String project = "{$project: { \"_id\": 0, unit: \"$_id.unit\", difficult: \"$_id.difficult\", items: \"$items\"}}";
		String merge = "{$merge: { into: \"unitQuestion\", whenMatched: \"replace\", whenNotMatched: \"insert\" }}";
		Aggregation agg = newAggregation(
				lookup("unit", "knowledge", "knowledge", "units"),
				unwind("units"),
				sort(Sort.Direction.ASC, "_id").and(Sort.Direction.DESC, "units.order"),
				group("_id").first("difficult").as("difficult").first("units._id").as("unit"),
				new CustomProjectAggregationOperation(group),
				new CustomProjectAggregationOperation(project),
				new CustomProjectAggregationOperation(merge)
				);
		mongoTemplate.aggregate(agg, Question.class, UnitQuestions.class);
	}
}
