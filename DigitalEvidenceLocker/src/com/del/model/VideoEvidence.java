package com.del.model;

import com.del.interfaces.Searchable;

public class VideoEvidence extends DigitalItem implements Searchable {
    private double duration;
    private String filePath;

    public VideoEvidence(String id, String title, double duration, String filePath) {
        super(id, title, "Video");
        this.duration = duration;
        this.filePath = filePath;
    }

    public double getDuration() { return duration; }
    public String getFilePath() { return filePath; }

    @Override
    public String getDetails() {
        return getId() + " | " + getTitle() + " | " + getType() +
               " | " + duration + " mins | File: " + filePath;
    }

    @Override
    public boolean matches(String keyword) {
        return getId().contains(keyword) ||
               getTitle().contains(keyword) ||
               filePath.contains(keyword);
    }
}
