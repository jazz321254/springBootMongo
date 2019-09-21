package com.jazz321254.mongo.dto;

public class QuestionResult {

	private String id;

	private long hard;
	private long mid;
	private long easy;

	public long getHard() {
		return hard;
	}

	public void setHard(long hard) {
		this.hard = hard;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getEasy() {
		return easy;
	}

	public void setEasy(long easy) {
		this.easy = easy;
	}

}