package com.jazz321254.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subject")
public class Subject {

	@Id
    private String _id;
	private String name;
	
	public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
