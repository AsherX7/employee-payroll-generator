package ui;

import javax.swing.*;
import dao.AllowanceDAO;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import dao.EmployeeDAO;
import model.Employee;

public class ModifyEmp extends JFrame {
	private static final long serialVersionUID = 1L;
    JTextField txtId;
    JTextField txtName;
    JTextField txtDepartment;
    JTextField txtType;
    JTextField txtExp;
    JTextField txtRate;
    JTextField txtHours;
    JTextField txtSalary;
    private JButton backButton;
    public ModifyEmp() {

        setTitle("Modify Employee");
        setSize(850, 750);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblId = new JLabel("Employee ID");
        lblId.setBounds(50, 50, 100, 30);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(170, 50, 150, 30);
        add(txtId);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(350, 50, 100, 30);
        add(btnSearch);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String empId = txtId.getText();

                EmployeeDAO dao = new EmployeeDAO();
                Employee emp = dao.searchEmployee(empId);

                if (emp != null) {

                    txtName.setText(emp.getName());
                    txtDepartment.setText(emp.getDep());
                    txtType.setText(emp.getEmpType());
                    txtExp.setText(String.valueOf(emp.getYearsExp()));
                    txtRate.setText(String.valueOf(emp.getHrate()));
                    txtHours.setText(String.valueOf(emp.getHwork()));
                    txtSalary.setText(String.valueOf(emp.getSalary()));

                } else {
                    JOptionPane.showMessageDialog(null, "Employee Not Found");
                }
            }
        });
        backButton = new JButton("Back");
        backButton.setBounds(20, 10, 80, 30);
        add(backButton);
        backButton.addActionListener(e -> {

            new HomeFrame(); // Replace with your Dashboard class
            dispose();
            
        });

        JLabel lblName = new JLabel("Name");
        lblName.setBounds(50, 100, 100, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(170, 100, 150, 30);
        add(txtName);

        JLabel lblDepartment = new JLabel("Department");
        lblDepartment.setBounds(50, 150, 100, 30);
        add(lblDepartment);

        txtDepartment = new JTextField();
        txtDepartment.setBounds(170, 150, 150, 30);
        add(txtDepartment);

        JLabel lblType = new JLabel("Type");
        lblType.setBounds(50, 200, 100, 30);
        add(lblType);

        txtType = new JTextField();
        txtType.setBounds(170, 200, 150, 30);
        add(txtType);

        JLabel lblExp = new JLabel("Years Exp");
        lblExp.setBounds(50, 250, 100, 30);
        add(lblExp);

        txtExp = new JTextField();
        txtExp.setBounds(170, 250, 150, 30);
        add(txtExp);

        JLabel lblRate = new JLabel("Hourly Rate");
        lblRate.setBounds(50, 300, 100, 30);
        add(lblRate);

        txtRate = new JTextField();
        txtRate.setBounds(170, 300, 150, 30);
        add(txtRate);

        JLabel lblHours = new JLabel("Hours Worked");
        lblHours.setBounds(50, 350, 100, 30);
        add(lblHours);

        txtHours = new JTextField();
        txtHours.setBounds(170, 350, 150, 30);
        add(txtHours);

        JLabel lblSalary = new JLabel("Salary");
        lblSalary.setBounds(50, 400, 100, 30);
        add(lblSalary);

        txtSalary = new JTextField();
        txtSalary.setBounds(170, 400, 150, 30);
        add(txtSalary);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(350, 400, 100, 30);
        add(btnUpdate);
        //update

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {        

                try {
                    Employee emp = new Employee();

                    emp.setEmpId(txtId.getText()); 

                    emp.setName(txtName.getText());
                    emp.setDep(txtDepartment.getText()); 
                    emp.setEmpType(txtType.getText());

                    emp.setYearsExp(Integer.parseInt(txtExp.getText()));
                    emp.setHrate(Double.parseDouble(txtRate.getText()));
                    emp.setHwork(Integer.parseInt(txtHours.getText()));
                    emp.setSalary(Double.parseDouble(txtSalary.getText()));

                    EmployeeDAO dao = new EmployeeDAO();

                    boolean status = dao.updateEmployee(emp);

                    if (status) {
                        JOptionPane.showMessageDialog(null, "Employee Updated Successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "Update Failed");
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers for Exp, Rate, Hours, and Salary.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "An error occurred.");
                    ex.printStackTrace();
                }
            }
        });
        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(350, 300, 100, 30);
        add(btnAdd);

        btnAdd.addActionListener(e -> {

            try {

                EmployeeDAO dao = new EmployeeDAO();

                dao.addEmployee(
                    txtId.getText(),
                    txtName.getText(),
                    txtDepartment.getText(),
                    txtType.getText(),
                    Integer.parseInt(txtExp.getText()),
                    Double.parseDouble(txtRate.getText()),
                    Integer.parseInt(txtHours.getText()),
                    Double.parseDouble(txtSalary.getText())
                );

                JOptionPane.showMessageDialog(null,
                        "Employee Added Successfully");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null,
                        "Error while adding employee");

                ex.printStackTrace();
            }
        });
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(350, 350, 100, 30);
        add(btnDelete);

        btnDelete.addActionListener(e -> {

            try {

                EmployeeDAO dao = new EmployeeDAO();

                dao.deleteEmployee(txtId.getText());

                JOptionPane.showMessageDialog(null,
                        "Employee Deleted Successfully");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null,
                        "Error while deleting employee");

                ex.printStackTrace();
            }
        });
        
    
        JButton btnAllowance = new JButton("Allowance Table");
        btnAllowance.setBounds(350, 250, 150, 30); 
        add(btnAllowance);

        btnAllowance.addActionListener(e -> {
            new AllowanceFrame(txtId.getText()); 
        });

        revalidate();
        repaint();
        setVisible(true);
        }

        public static void main(String[] args) {
            new ModifyEmp();
        }
        public class AllowanceFrame extends JFrame {
        	private static final long serialVersionUID = 1L;

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
        }