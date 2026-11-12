package com.del.model;

public class Evidence {
	public String evidenceId;
	public String title;
	public String type;
	public double sizeMB;
	
	public Evidence() {
		
	}
	
	public Evidence(String evidenceId,String title,String type,double sizeMB) {
		this.evidenceId=evidenceId;
		this.title=title;
		this.type=type;
		this.sizeMB=sizeMB;
	}
	
	public String getEvidenceId() {
		return evidenceId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getType() {
		return type;
	}
	
	public double getSizeMB() {
		return sizeMB;
	}
	
}







