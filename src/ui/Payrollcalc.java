package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import dao.AllowanceDAO;

public class Payrollcalc extends JFrame {
	private static final long serialVersionUID = 1L;
    private JTextField txtEmpType;
    private JTextField txtTaxName;
    private JTextField txtTaxPer;
    private JTextField txtType;

    private JTable table;
    private DefaultTableModel model;

    private JButton btnSave, btnUpdate, btnDelete;

    public Payrollcalc() {

        setTitle("Payroll Calculation");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        getContentPane().setBackground(new Color(245, 248, 255));

        // ================= NAVY TITLE BAR =================
        JPanel titleBar = new JPanel();
        titleBar.setBounds(0, 0, 1000, 60);
        titleBar.setBackground(new Color(4, 24, 82));
        titleBar.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel title = new JLabel("PAYROLL CALCULATION");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        titleBar.add(title);
        add(titleBar);

        // ================= FORM =================
        JLabel l1 = new JLabel("Employee Type");
        l1.setBounds(50, 90, 150, 25);

        txtEmpType = new JTextField();
        txtEmpType.setBounds(200, 90, 200, 28);

        JLabel l2 = new JLabel("Tax Name");
        l2.setBounds(50, 130, 150, 25);

        txtTaxName = new JTextField();
        txtTaxName.setBounds(200, 130, 200, 28);

        JLabel l3 = new JLabel("Percentage");
        l3.setBounds(50, 170, 150, 25);

        txtTaxPer = new JTextField();
        txtTaxPer.setBounds(200, 170, 200, 28);

        JLabel l4 = new JLabel("Type");
        l4.setBounds(50, 210, 150, 25);

        txtType = new JTextField();
        txtType.setBounds(200, 210, 200, 28);

        add(l1);
        add(txtEmpType);
        add(l2);
        add(txtTaxName);
        add(l3);
        add(txtTaxPer);
        add(l4);
        add(txtType);

        // ================= BUTTONS =================
        btnSave = new JButton("SAVE");
        btnSave.setBounds(450, 100, 120, 35);
        btnSave.setBackground(new Color(4, 24, 82));
        btnSave.setForeground(Color.WHITE);

        btnUpdate = new JButton("UPDATE");
        btnUpdate.setBounds(450, 150, 120, 35);
        btnUpdate.setBackground(new Color(0, 153, 76));
        btnUpdate.setForeground(Color.WHITE);

        btnDelete = new JButton("DELETE");
        btnDelete.setBounds(450, 200, 120, 35);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);

        add(btnSave);
        add(btnUpdate);
        add(btnDelete);

        // ================= TABLE =================
        String[] cols = {"Employee Type", "Tax Name", "Percentage", "Type"};
        model = new DefaultTableModel(cols, 0);

        table = new JTable(model);

        // ⭐ NAVY HEADER STYLE
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(4, 24, 82));
        table.getTableHeader().setForeground(Color.WHITE);

        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 280, 880, 250);

        add(sp);

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

        // ================= ACTIONS =================
        btnSave.addActionListener(e -> saveTax());
        btnUpdate.addActionListener(e -> updateTax());
        btnDelete.addActionListener(e -> deleteTax());

        setVisible(true);
    }

    // ================= LOAD =================
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error in input");
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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Update Error");
        }
    }

    // ================= DELETE =================
    private void deleteTax() {

        AllowanceDAO dao = new AllowanceDAO();
        dao.deleteTax(txtTaxName.getText());

        loadTable();
    }

    public static void main(String[] args) {
        new Payrollcalc();
    }
}
