

package ui;



import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.BorderLayout;

import model.Employee;
import service.PayrollService;

public class Payrollgen extends JFrame{
    
	 
	private static final long serialVersionUID = -8527493745696243885L;
	
	private JTextField idField;
    private JTextField nameField;
    private JTextField salaryField;
    private JTextField typeField;

    private JTextArea outputArea;

    private JButton generateButton;

    public Payrollgen() {

        setTitle("Payslip Generator");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        idField = new JTextField(20);
        nameField = new JTextField(20);
        salaryField = new JTextField(20);
        typeField = new JTextField(20);

        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);

        generateButton = new JButton("Generate Payslip");

        JPanel formPanel = new JPanel(new GridLayout(1, 2, 5, 10));
        formPanel.add(new JLabel("Employee ID"));
        formPanel.add(idField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(generateButton);
        
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topContainer.add(formPanel);
        topContainer.add(buttonPanel);
        
        add(topContainer, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        

        generateButton.addActionListener(e ->{try{

                Employee emp = new Employee();

                emp.setEmpId(Integer.parseInt(idField.getText()));
                emp.setName(nameField.getText());
                emp.setSalary(Double.parseDouble(salaryField.getText()));
                emp.setEmpType(typeField.getText());

                PayrollService PayrollService = new PayrollService();
                double netSalary = PayrollService.calculateNetSalary(emp);

                String payslip =
                		"                                       " +
                        "=====================================\n" +
                        "          EMPLOYEE PAYSLIP\n" +
                        "=====================================\n" +
                        "Employee ID      : " + emp.getEmpId() + "\n" +
                        "Employee Name    : " + emp.getName() + "\n" +
                        "Employee Type    : " + emp.getEmpType() + "\n" +
                        "-------------------------------------\n" +
                        "Basic Salary     : ₹" + String.format("%.2f", emp.getSalary()) + "\n" +
                        "Increments       : Calculated by Payroll Service\n" +
                        "Deductions       : Calculated by Payroll Service\n" +
                        "-------------------------------------\n" +
                        "Net Salary       : ₹" + String.format("%.2f", netSalary) + "\n" +
                        "=====================================";

                outputArea.setText(payslip);

            } 
        
        catch (Exception ex){

                JOptionPane.showMessageDialog(
                        null,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setVisible(true);
        
    }

    public static void main(String[] args){
        new Payrollgen();
        
    }
    
}
