package com.jazz321254.mongo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "unit")
public class Unit {

	@Id
    private String _id;
	private String subject;
	private List<String> knowledge;
	private Integer order;
	
	public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

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

	public List<String> getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(List<String> knowledge) {
		this.knowledge = knowledge;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
    
}
