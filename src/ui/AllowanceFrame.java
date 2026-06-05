package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.AllowanceDAO;

public class AllowanceFrame extends JFrame {
    
    public AllowanceFrame(String empId) {
        setTitle("Allowance Management");
        setSize(750, 400);
        setLayout(null);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 

     // 1. Employee ID Display (Created as a button at the top)
        JButton btnEmpIdDisplay = new JButton("Employee ID: " + empId);
        btnEmpIdDisplay.setBounds(30, 15, 180, 30);
        btnEmpIdDisplay.setEnabled(false); // Makes it look like a neat label-button, non-clickable
        add(btnEmpIdDisplay);

        // 2. Table Setup (Only 2 columns now: Name and Amount)
        String[] columns = {"Allowance Name", "Amount"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Moved Y down to 55 to leave room for the ID button at the top
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 55, 500, 240);
        add(scrollPane);

        // 3. Add Row Button
        JButton btnAddRow = new JButton("Add Row");
        btnAddRow.setBounds(560, 55, 140, 30);
        add(btnAddRow);

        btnAddRow.addActionListener(e -> {
            model.addRow(new Object[]{"", ""});
        });

        // 4. Save Button
        JButton btnSave = new JButton("Save Allowance");
        btnSave.setBounds(560, 100, 140, 30);
        add(btnSave);

        btnSave.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                try {
                    String name = model.getValueAt(row, 0).toString();
                    double amount = Double.parseDouble(model.getValueAt(row, 1).toString());
                    
                    // Uses the background empId from the constructor
                    AllowanceDAO dao = new AllowanceDAO();
                    dao.addAllowance(empId, name, amount);
                    
                    JOptionPane.showMessageDialog(this, "Saved Successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row first!");
            }
        });

        // 5. Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(560, 145, 140, 30);
        add(btnBack);

        btnBack.addActionListener(e -> {
            dispose(); 
        });

        setVisible(true);}}