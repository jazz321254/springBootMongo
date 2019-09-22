package com.jazz321254.mongo.dto;

import java.util.List;

public class UnitQuestions {
	
	private String difficult;
	private List<String> items;

	public String getDifficult() {
		return difficult;
	}

	public void setDifficult(String difficult) {
		this.difficult = difficult;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

}
