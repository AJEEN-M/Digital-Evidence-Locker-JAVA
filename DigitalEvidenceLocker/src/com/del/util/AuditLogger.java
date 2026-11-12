package com.del.util;

import java.io.*;
import java.time.LocalDateTime;

public class AuditLogger {
    private static final String LOG_FILE = "audit_log.txt";

    public static void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(LocalDateTime.now() + " - " + message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log: " + e.getMessage());
        }
    }
}
