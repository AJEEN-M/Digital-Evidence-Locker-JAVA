package com.del.model;

import com.del.interfaces.Searchable;

public class ImageEvidence extends DigitalItem implements Searchable {
    private double resolution;
    private String filePath;

    public ImageEvidence(String id, String title, double resolution, String filePath) {
        super(id, title, "Image");
        this.resolution = resolution;
        this.filePath = filePath;
    }

    public double getResolution() { return resolution; }
    public String getFilePath() { return filePath; }

    @Override
    public String getDetails() {
        return getId() + " | " + getTitle() + " | " + getType() +
               " | " + resolution + " MP | File: " + filePath;
    }

    @Override
    public boolean matches(String keyword) {
        return getId().contains(keyword) ||
               getTitle().contains(keyword) ||
               filePath.contains(keyword);
    }
}
