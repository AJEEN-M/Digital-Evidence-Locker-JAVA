package com.del.main;

import com.del.model.*;
import com.del.exceptions.*;
import javax.swing.*;
import java.awt.*;

public class EvidenceLockerApp {
    private EvidenceLockerSystem system = new EvidenceLockerSystem();

    public void launch() {
        JFrame frame = new JFrame("Digital Evidence Locker");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton addImageBtn = new JButton("Add Image Evidence");
        JButton addVideoBtn = new JButton("Add Video Evidence");
        JButton addDocBtn = new JButton("Add Document Evidence");
        JButton displayBtn = new JButton("Display Catalog");
        JButton searchBtn = new JButton("Search Evidence");

        // Add Image Evidence
        addImageBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter ID:");
                String title = JOptionPane.showInputDialog("Enter Title:");
                double res = Double.parseDouble(JOptionPane.showInputDialog("Enter Resolution (MP):"));

                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    String storedPath = system.uploadFile(path);
                    system.addItem(new ImageEvidence(id, title, res, storedPath));
                    JOptionPane.showMessageDialog(frame, "Image Evidence added!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // Add Video Evidence
        addVideoBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter ID:");
                String title = JOptionPane.showInputDialog("Enter Title:");
                double dur = Double.parseDouble(JOptionPane.showInputDialog("Enter Duration (minutes):"));

                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    String storedPath = system.uploadFile(path);
                    system.addItem(new VideoEvidence(id, title, dur, storedPath));
                    JOptionPane.showMessageDialog(frame, "Video Evidence added!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // Add Document Evidence
        addDocBtn.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("Enter ID:");
                String title = JOptionPane.showInputDialog("Enter Title:");
                int pages = Integer.parseInt(JOptionPane.showInputDialog("Enter Number of Pages:"));

                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    String storedPath = system.uploadFile(path);
                    system.addItem(new DocumentEvidence(id, title, pages, storedPath));
                    JOptionPane.showMessageDialog(frame, "Document Evidence added!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        // Display Catalog
        displayBtn.addActionListener(e -> {
            String catalogText = system.displayCatalog();
            JOptionPane.showMessageDialog(frame, catalogText);
        });

        // Search Evidence
        searchBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog("Enter keyword:");
            String results = system.searchCatalog(keyword);
            JOptionPane.showMessageDialog(frame, results);
        });

        frame.add(addImageBtn);
        frame.add(addVideoBtn);
        frame.add(addDocBtn);
        frame.add(displayBtn);
        frame.add(searchBtn);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvidenceLockerApp().launch());
    }
}
