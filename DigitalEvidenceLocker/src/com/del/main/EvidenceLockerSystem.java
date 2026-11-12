package com.del.main;

import com.del.model.*;
import com.del.exceptions.*;
import com.del.interfaces.Searchable;
import com.del.util.AuditLogger;
import com.del.util.FileEncryptor;

import java.util.*;
import java.io.*;
import java.nio.file.*;

public class EvidenceLockerSystem {
    private List<DigitalItem> catalog = new ArrayList<>();
    private static final String LOCKER_FOLDER = "locker_storage/";

    private static User admin = new User("admin", "1234"); // single login

    // Add item safely
    public void addItem(DigitalItem item) throws DuplicateEvidenceException {
        for (DigitalItem existing : catalog) {
            if (existing.getId().equals(item.getId())) {
                throw new DuplicateEvidenceException("Evidence with ID " + item.getId() + " already exists!");
            }
        }
        catalog.add(item);
        AuditLogger.log("Evidence added: " + item.getDetails());
    }

    // Getter for catalog
    public List<DigitalItem> getCatalog() {
        return catalog;
    }

    // Upload file (public for GUI)
    public String uploadFile(String originalPath) throws IOException {
        Path source = Paths.get(originalPath);
        Path target = Paths.get(LOCKER_FOLDER + source.getFileName());
        Files.createDirectories(Paths.get(LOCKER_FOLDER));
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        try {
            FileEncryptor.encryptFile(target.toString());
            AuditLogger.log("File uploaded and encrypted: " + target.getFileName());
        } catch (Exception e) {
            AuditLogger.log("Encryption failed for file: " + target.getFileName());
        }

        return target.toString();
    }

    // Display catalog (returns string for GUI)
    public String displayCatalog() {
        if (catalog.isEmpty()) {
            return "No evidence in catalog.";
        }
        StringBuilder sb = new StringBuilder();
        for (DigitalItem item : catalog) {
            sb.append(item.getDetails()).append("\n");
        }
        AuditLogger.log("Catalog displayed.");
        return sb.toString();
    }

    // Save catalog
    public void saveCatalog(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (DigitalItem item : catalog) {
                writer.write(item.getType() + "," + item.getId() + "," + item.getTitle() + "," + item.getDetails());
                writer.newLine();
            }
            AuditLogger.log("Catalog saved to file: " + filename);
        } catch (IOException e) {
            AuditLogger.log("Error saving catalog: " + e.getMessage());
        }
    }

    // Load catalog
    public void loadCatalog(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            catalog.clear();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String type = parts[0];
                    String id = parts[1];
                    String title = parts[2];
                    String attr = parts[3];
                    String filePath = parts[4];

                    switch (type) {
                        case "Image":
                            double res = Double.parseDouble(attr);
                            catalog.add(new ImageEvidence(id, title, res, filePath));
                            break;
                        case "Video":
                            double dur = Double.parseDouble(attr);
                            catalog.add(new VideoEvidence(id, title, dur, filePath));
                            break;
                        case "Document":
                            int pages = Integer.parseInt(attr);
                            catalog.add(new DocumentEvidence(id, title, pages, filePath));
                            break;
                    }
                }
            }
            AuditLogger.log("Catalog loaded from file: " + filename);
        } catch (IOException e) {
            AuditLogger.log("Error loading catalog: " + e.getMessage());
        }
    }

    // Search catalog (returns string for GUI)
    public String searchCatalog(String keyword) {
        StringBuilder sb = new StringBuilder();
        for (DigitalItem item : catalog) {
            if (item instanceof Searchable && ((Searchable) item).matches(keyword)) {
                sb.append(item.getDetails()).append("\n");
            }
        }
        AuditLogger.log("Search performed with keyword: " + keyword);
        return sb.length() == 0 ? "No evidence found for keyword: " + keyword : sb.toString();
    }

    // Authentication for GUI login
    public boolean authenticate(String username, String password) {
        return admin.authenticate(username, password);
    }
}

