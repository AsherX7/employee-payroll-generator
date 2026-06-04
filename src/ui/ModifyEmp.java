package ui;

import javax.swing.*;
import java.awt.event.*;

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
        setSize(600, 500);
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

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            

                try {
                    Employee emp = new Employee();

                    emp.setEmpId(txtId.getText()); 

                    emp.setName(txtName.getText());
                    emp.setDep(txtDepartment.getText()); 
                    emp.setEmpType(txtType.getText());

                    emp.setYearsExp(Integer.parseInt(txtExp.getText()));
                    emp.setHrate(Integer.parseInt(txtRate.getText()));
                    emp.setHwork(Integer.parseInt(txtHours.getText()));
                    emp.setSalary(Integer.parseInt(txtSalary.getText()));

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

        setVisible(true);
    }

    public static void main(String[] args) {
        new ModifyEmp();
    }
}