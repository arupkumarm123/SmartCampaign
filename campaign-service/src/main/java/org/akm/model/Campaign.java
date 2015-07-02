package org.akm.model;

import org.springframework.data.annotation.Id;



public class Campaign {

	@Id
	private String Id;  // cust_id + seq_num
	
	private String name ;
	
	private String description;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
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
	
	
}
