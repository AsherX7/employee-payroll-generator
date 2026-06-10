package ui;

import dao.DBConnection;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class EmployeeDashboard extends JPanel {
    private static final long serialVersionUID = 1L;

    private final String currentEmpId; 
    private JLabel lblName, lblDept, lblType, lblBasic, lblGross, lblDeductions, lblNet;
    private DefaultTableModel allowanceModel, deductionModel;

    public EmployeeDashboard(String loggedInEmpId) {
        this.currentEmpId = loggedInEmpId;
        
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 242, 245));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(makeTopBar(),      BorderLayout.NORTH);
        add(makeCenterPanel(), BorderLayout.CENTER);
        add(makeBottomBar(),   BorderLayout.SOUTH);

        loadEmployeeData();
    }

    private JPanel makeTopBar() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(33, 97, 140));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel title = new JLabel("My Payroll Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JPanel makeCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        // Info Strip
        JPanel infoPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        infoPanel.add(lblName  = makeInfoLabel("Name: —"));
        infoPanel.add(lblDept  = makeInfoLabel("Department: —"));
        infoPanel.add(lblType  = makeInfoLabel("Type: —"));
        infoPanel.add(lblBasic = makeInfoLabel("Basic Salary: —"));

        // Summary Cards
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 12, 0));
        cardsPanel.setOpaque(false);
        cardsPanel.add(makeCard("Gross Salary",     lblGross = new JLabel("Rs. 0.00"),      new Color(39, 174, 96)));
        cardsPanel.add(makeCard("Total Deductions", lblDeductions = new JLabel("Rs. 0.00"), new Color(192, 57, 43)));
        cardsPanel.add(makeCard("Net Take-Home",    lblNet = new JLabel("Rs. 0.00"),        new Color(41, 128, 185)));

        // Tables Split Panel
        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        tablesPanel.setOpaque(false);
        
        allowanceModel = new DefaultTableModel(new String[]{"Code", "Allowance", "Rate", "Amount"}, 0);
        deductionModel = new DefaultTableModel(new String[]{"Code", "Deduction", "Basis", "Amount"}, 0);
        
        tablesPanel.add(createTableScroll(allowanceModel, "Earnings & Allowances", new Color(39, 174, 96)));
        tablesPanel.add(createTableScroll(deductionModel, "Deductions & Taxes", new Color(192, 57, 43)));

        panel.add(infoPanel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(cardsPanel);
        panel.add(Box.createVerticalStrut(12));
        panel.add(tablesPanel);

        return panel;
    }

    private JLabel makeInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        return lbl;
    }

    private JPanel makeCard(String title, JLabel valLabel, Color bg) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bg);
        card.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel titleLbl = new JLabel(title, SwingConstants.CENTER);
        titleLbl.setFont(new Font("Arial", Font.BOLD, 12));
        titleLbl.setForeground(new Color(230, 230, 230));

        valLabel.setFont(new Font("Arial", Font.BOLD, 20));
        valLabel.setForeground(Color.WHITE);
        valLabel.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valLabel, BorderLayout.CENTER);
        return card;
    }

    private JScrollPane createTableScroll(DefaultTableModel model, String title, Color color) {
        JTable table = new JTable(model) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(24);
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(color, 1), title,
            TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 13), color
        ));
        return sp;
    }

    private JPanel makeBottomBar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setOpaque(false);

        JButton btnPayslip = new JButton("Generate Payslip");
        btnPayslip.setFont(new Font("Arial", Font.BOLD, 13));
        btnPayslip.setBackground(new Color(33, 97, 140));
        btnPayslip.setForeground(Color.WHITE);
        btnPayslip.addActionListener(e -> generatePayslip());

        panel.add(btnPayslip);
        return panel;
    }

    private void loadEmployeeData() {
        allowanceModel.setRowCount(0);
        deductionModel.setRowCount(0);

        // Try-with-resources handles closing statements and connection safe and clean
        try (Connection con = DBConnection.getConnection()) {
            
            // 1. Employee Core Details
            double basicSalary;
            try (PreparedStatement pst = con.prepareStatement("SELECT * FROM employee WHERE employeeid = ?")) {
                pst.setString(1, currentEmpId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "Employee details not found.");
                        return;
                    }
                    basicSalary = rs.getDouble("salary");
                    lblName.setText("Name: " + rs.getString("name"));
                    lblDept.setText("Dept: " + rs.getString("department"));
                    lblType.setText("Type: " + rs.getString("type"));
                    lblBasic.setText("Basic: Rs. " + String.format("%,.2f", basicSalary));
                }
            }

            // 2. Allowances Calculations
            double totalAllowances = 0;
            allowanceModel.addRow(new Object[]{"BASIC", "Basic Salary", "—", String.format("%,.2f", basicSalary)});
            
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM allowances")) {
                while (rs.next()) {
                    double pct = rs.getDouble("percentage");
                    double amt = Math.round((basicSalary * pct / 100.0) * 100.0) / 100.0;
                    totalAllowances += amt;
                    allowanceModel.addRow(new Object[]{rs.getString("code"), rs.getString("name"), pct + "%", String.format("%,.2f", amt)});
                }
            }
            double grossSalary = basicSalary + totalAllowances;

            // 3. Deductions Calculations
            double totalDeductions = 0;
            try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM tax_deductions")) {
                while (rs.next()) {
                    String basis = rs.getString("basis_type");
                    double pct = rs.getDouble("percentage");
                    double amt = basis.equals("BASIC") ? (basicSalary * pct / 100.0) :
                                 basis.equals("GROSS") ? (grossSalary * pct / 100.0) : rs.getDouble("fixed_amount");
                    
                    amt = Math.round(amt * 100.0) / 100.0;
                    totalDeductions += amt;
                    
                    String basisLbl = basis.equals("FIXED") ? "Fixed Amount" : pct + "% of " + basis;
                    deductionModel.addRow(new Object[]{rs.getString("code"), rs.getString("name"), basisLbl, String.format("%,.2f", amt)});
                }
            }

            // Update Cards Metrics
            lblGross.setText("Rs. " + String.format("%,.2f", grossSalary));
            lblDeductions.setText("Rs. " + String.format("%,.2f", totalDeductions));
            lblNet.setText("Rs. " + String.format("%,.2f", (grossSalary - totalDeductions)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generatePayslip() {
        if (lblName.getText().contains("—")) return;

        StringBuilder sb = new StringBuilder("========================================\n\t  SALARY PAYSLIP\n========================================\n");
        sb.append(lblName.getText()).append("\n").append(lblDept.getText()).append("\n").append(lblBasic.getText()).append("\n");
        sb.append("----------------------------------------\nEARNINGS\n----------------------------------------\n");
        
        for (int i = 0; i < allowanceModel.getRowCount(); i++) {
            sb.append(String.format(" %-25s Rs. %s%n", allowanceModel.getValueAt(i, 1), allowanceModel.getValueAt(i, 3)));
        }
        sb.append("----------------------------------------\nDEDUCTIONS\n----------------------------------------\n");
        for (int i = 0; i < deductionModel.getRowCount(); i++) {
            sb.append(String.format(" %-25s Rs. %s%n", deductionModel.getValueAt(i, 1), deductionModel.getValueAt(i, 3)));
        }
        sb.append("========================================\n");
        sb.append(String.format(" Gross Salary:             %s%n", lblGross.getText()));
        sb.append(String.format(" Total Deductions:         %s%n", lblDeductions.getText()));
        sb.append(String.format(" NET TAKE-HOME:            %s%n", lblNet.getText()));
        sb.append("========================================\n");

        JTextArea txt = new JTextArea(sb.toString());
        txt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(txt), "My Payslip Document", JOptionPane.PLAIN_MESSAGE);
    }
}