package ui;
//IMPORTS

import javax.swing.*;
import java.awt.*;
import dao.EmployeeDAO;
import model.Employee;
import service.PayrollService;
import utils.Validation;

public class Payrollgen extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField idField;
    private JTextArea outputArea;
    private JButton generateButton;
    private JButton backButton;

    public Payrollgen() {

        setTitle("Payslip Generator");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Components
        idField = new JTextField(15);
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        generateButton = new JButton("Generate Payslip");
        backButton = new JButton("Back");

        // Top Panel
        JPanel formPanel = new JPanel();

        formPanel.add(new JLabel("Employee ID"));
        formPanel.add(idField);

        // Button Panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(generateButton);
        buttonPanel.add(backButton);

        // Container
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Generate Payslip
        generateButton.addActionListener(e -> generatePayslip());

        // Back Button
        backButton.addActionListener(e -> {

            new HomeFrame(); // Replace with your Dashboard class
            dispose();

        });

        setVisible(true);
    }

    private void generatePayslip() {

        try {

            String empId = idField.getText().trim();

            if(empId.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter Employee ID"
                );
                return;
            }
            
            
            if(!Validation.isValidEmployeeId(empId)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Invalid Employee ID format.\nMust start with 'E' followed by digits (e.g. E1001)",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            
            EmployeeDAO dao = new EmployeeDAO();

            // Create this method in EmployeeDAO
            Employee emp = dao.getEmployeeById(empId);

            if(emp == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Employee Not Found"
                );
                return;
            }
            
            
            PayrollService payrollService = new PayrollService();

            double netSalary =
                    payrollService.calculateNetSalary(emp);

            String payslip =
                    "=====================================\n" +
                    "          EMPLOYEE PAYSLIP\n" +
                    "=====================================\n" +
                    "Employee ID   : " + emp.getEmpId() + "\n" +
                    "Employee Name : " + emp.getName() + "\n" +
                    "Employee Type : " + emp.getEmpType() + "\n" +
                    "-------------------------------------\n" +
                    "Basic Salary  : ₹" +
                    String.format("%.2f", emp.getSalary()) + "\n" +
                    "-------------------------------------\n" +
                    "Net Salary    : ₹" +
                    String.format("%.2f", netSalary) + "\n" +
                    "=====================================";
            
            outputArea.setText(payslip);
    } 
        catch(Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
}
    public static void main(String[] args) {

        new Payrollgen();
    }
}


