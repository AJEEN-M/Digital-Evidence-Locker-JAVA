package com.del.model;

public abstract class DigitalItem {
    private String id;
    private String title;
    private String type;

    public DigitalItem(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getType() { return type; }

    public abstract String getDetails();
}
