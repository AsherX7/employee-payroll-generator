package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import dao.AllowanceDAO;

public class Payrollcalc extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField txtEmpType;
    private JTextField txtTaxName;
    private JTextField txtTaxPer;
    private JTextField txtType;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnSave, btnUpdate, btnDelete;

    public Payrollcalc() {

        setLayout(null);
        setBackground(new Color(245, 248, 255));
        setPreferredSize(new Dimension(1000, 600));
       //content panel
        JPanel content = new JPanel();
        content.setLayout(null);
        content.setBackground(new Color(245,248,255));
        content.setBounds(20, 140, 1280, 700);
        add(content);
        // ================= TITLE BAR =================
        JPanel titleBar = new JPanel();
        titleBar.setBounds(0, 0, 1550, 130);
        titleBar.setBackground(new Color(4, 24, 82));
        titleBar.setLayout(null);

        JLabel title = new JLabel("PAYROLL CALCULATION");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(520, 40, 400, 40);
        titleBar.add(title);
        add(titleBar);

        // ================= FORM =================
        JLabel l1 = new JLabel("Employee Type");
        l1.setBounds(50, 50, 150, 25);

        txtEmpType = new JTextField();
        txtEmpType.setBounds(230, 50, 240, 32);

        JLabel l2 = new JLabel("Tax Name");
        l2.setBounds(50, 70, 150, 25);

        txtTaxName = new JTextField();
        txtTaxName.setBounds(230, 70, 240, 32);

        JLabel l3 = new JLabel("Percentage");
        l3.setBounds(50, 90, 150, 25);

        txtTaxPer = new JTextField();
        txtTaxPer.setBounds(230, 90, 240, 32);

        JLabel l4 = new JLabel("Type");
        l4.setBounds(60, 220, 150, 25);

        txtType = new JTextField();
        txtType.setBounds(230, 220, 240, 32);

        content.add(l1);
        content.add(txtEmpType);
        content.add(l2);
        content.add(txtTaxName);
        content.add(l3);
        content.add(txtTaxPer);
        content.add(l4);
        content.add(txtType);

        // ================= BUTTONS =================
        btnSave = new JButton("SAVE");
        btnSave.setBounds(560, 70, 150, 40);
        btnSave.setBackground(new Color(4, 24, 82));
        btnSave.setForeground(Color.WHITE);

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(560, 130, 150, 40);
        btnUpdate.setBackground(new Color(0, 153, 76));
        btnUpdate.setForeground(Color.WHITE);

        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(560, 190, 150, 40);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        content.add(btnSave);
        content.add(btnUpdate);
        content.add(btnDelete);

        // ================= TABLE =================
        String[] cols = {"Employee Type", "Tax Name", "Percentage", "Type"};

        model = new DefaultTableModel(cols, 0);

        table = new JTable(model);

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(4, 24, 82));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(60, 250, 1250, 300);

        content.add(sp);

        // ================= LOAD DATA =================
        loadTable();

        // ================= TABLE CLICK =================
        table.getSelectionModel().addListSelectionListener(e -> {

            int row = table.getSelectedRow();

            if (row >= 0) {

                txtEmpType.setText(model.getValueAt(row, 0).toString());
                txtTaxName.setText(model.getValueAt(row, 1).toString());
                txtTaxPer.setText(model.getValueAt(row, 2).toString());
                txtType.setText(model.getValueAt(row, 3).toString());
            }
        });

        // ================= BUTTON ACTIONS =================
        btnSave.addActionListener(e -> saveTax());
        btnUpdate.addActionListener(e -> updateTax());
        btnDelete.addActionListener(e -> deleteTax());
    }

    // ================= LOAD TABLE =================
    private void loadTable() {

        model.setRowCount(0);

        try {

            AllowanceDAO dao = new AllowanceDAO();
            ResultSet rs = dao.getAllTaxes();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString("emp_type"),
                        rs.getString("tax_name"),
                        rs.getDouble("tax_per"),
                        rs.getString("tax_type")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= SAVE =================
    private void saveTax() {

        try {

            AllowanceDAO dao = new AllowanceDAO();

            dao.addTax(
                    txtEmpType.getText(),
                    txtTaxName.getText(),
                    Double.parseDouble(txtTaxPer.getText()),
                    txtType.getText()
            );

            loadTable();

            JOptionPane.showMessageDialog(this, "Tax Saved Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Error in Input");
        }
    }

    // ================= UPDATE =================
    private void updateTax() {

        try {

            AllowanceDAO dao = new AllowanceDAO();

            dao.updateTaxPer(
                    txtTaxName.getText(),
                    Double.parseDouble(txtTaxPer.getText())
            );

            loadTable();

            JOptionPane.showMessageDialog(this, "Tax Updated Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Update Error");
        }
    }

    // ================= DELETE =================
    private void deleteTax() {

        try {

            AllowanceDAO dao = new AllowanceDAO();

            dao.deleteTax(txtTaxName.getText());

            loadTable();

            JOptionPane.showMessageDialog(this, "Tax Deleted Successfully");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(this, "Delete Error");
        }
    }
    }

