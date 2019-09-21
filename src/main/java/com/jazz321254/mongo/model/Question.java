package com.jazz321254.mongo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "question")
public class Question {

	@Id
	private String _id;
	private String subject;
	private String difficult;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDifficult() {
		return difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public List<String> getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(List<String> knowledge) {
		this.knowledge = knowledge;
	}

	private List<String> knowledge;
}
