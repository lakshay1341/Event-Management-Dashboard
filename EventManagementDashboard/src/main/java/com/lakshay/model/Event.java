package com.lakshay.model;


public class Event {
	
	private int id;
    private String name;
    private String description;
    private String location;
    private String date;
	

	public Event(int id, String name, String description, String location, String date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.date = date;
	}
	
	public Event() {
		
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
