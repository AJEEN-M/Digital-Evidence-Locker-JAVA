package com.del.model;

import com.del.interfaces.Searchable;

public class DocumentEvidence extends DigitalItem implements Searchable {
    private int pages;
    private String filePath;

    public DocumentEvidence(String id, String title, int pages, String filePath) {
        super(id, title, "Document");
        this.pages = pages;
        this.filePath = filePath;
    }

    public int getPages() { return pages; }
    public String getFilePath() { return filePath; }

    @Override
    public String getDetails() {
        return getId() + " | " + getTitle() + " | " + getType() +
               " | " + pages + " pages | File: " + filePath;
    }

    @Override
    public boolean matches(String keyword) {
        return getId().contains(keyword) ||
               getTitle().contains(keyword) ||
               filePath.contains(keyword);
    }
}
