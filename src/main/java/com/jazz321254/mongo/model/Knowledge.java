package com.jazz321254.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "knowledge")
public class Knowledge {

	@Id
    private String _id;
	private String subject;
	private String name;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
}
