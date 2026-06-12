package ui;
//IMPORTS

import javax.swing.*;
import java.awt.*;
import dao.EmployeeDAO;
import model.Employee;
import utils.Validation;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Payrollgen extends JPanel {
	
	private static final Color NAVY = new Color(15, 35, 70);
	private static final Color GOLD = new Color(255, 193, 7);
	private static final Color BG = new Color(245, 247, 252);

    private static final long serialVersionUID = 1L;

    private JTextField idField;
    private JTextPane outputArea;
    private JButton generateButton;
    private JButton backButton;

    public Payrollgen() {

        setLayout(new BorderLayout(10, 10));
        setBackground(BG);

        // Components
        idField = new JTextField(15);
        outputArea = new JTextPane();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(NAVY);
        generateButton = new JButton("Generate Payslip");
        generateButton.setBackground(NAVY);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        backButton = new JButton("Back");
        backButton.setBackground(GOLD);
        backButton.setForeground(NAVY);
        backButton.setFocusPainted(false);

        // Top Panel
        JPanel formPanel = new JPanel();

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setForeground(NAVY);
        idLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        formPanel.add(idLabel);
        formPanel.add(idField);

        // Button Panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(generateButton);
        buttonPanel.add(backButton);

        // Container
        JPanel topPanel = new JPanel();
        topPanel.setBackground(BG);
        formPanel.setBackground(BG);
        buttonPanel.setBackground(BG);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);
        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // Generate Payslip
        generateButton.addActionListener(e -> generatePayslip());

        // Back Button
        backButton.addActionListener(e -> {

        	JOptionPane.showMessageDialog(this, "Use the side menu to navigate.");
            

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
            
            

            double basicSalary = emp.getSalary();

         // Allowances
         double hra = basicSalary * 0.40;
         double ta  = basicSalary * 0.10;
         double ma  = basicSalary * 0.05;

         double totalAllowances = hra + ta + ma;

         double grossSalary = basicSalary + totalAllowances;

         // Deductions
         double pf  = basicSalary * 0.12;
         double esi = grossSalary * 0.0075;
         double tds = basicSalary * 0.08;
         double pt  = 200;

         double totalDeductions =
                 pf + esi + tds + pt;

         double netSalary =
                 grossSalary - totalDeductions;

         StyledDocument doc = outputArea.getStyledDocument();

         Style normal = outputArea.addStyle("normal", null);

         Style green = outputArea.addStyle("green", null);
         StyleConstants.setForeground(green, Color.GREEN);

         Style red = outputArea.addStyle("red", null);
         StyleConstants.setForeground(red, Color.RED);

         Style blue = outputArea.addStyle("blue", null);
         StyleConstants.setForeground(blue, Color.BLUE);

         doc.remove(0, doc.getLength());

         doc.insertString(doc.getLength(),
                 "=====================================\n" +
                 "         EMPLOYEE PAYSLIP\n" +
                 "=====================================\n" +
                 "Employee ID   : " + emp.getEmpId() + "\n" +
                 "Employee Name : " + emp.getName() + "\n" +
                 "Employee Type : " + emp.getEmpType() + "\n" +
                 "=====================================\n\n",
                 normal);

         doc.insertString(doc.getLength(), "EARNINGS\n", green);

         doc.insertString(doc.getLength(),
                 "Basic Salary        : ₹" + String.format("%.2f", basicSalary) + "\n" +
                 "HRA (40%)           : ₹" + String.format("%.2f", hra) + "\n" +
                 "Transport (10%)     : ₹" + String.format("%.2f", ta) + "\n" +
                 "Medical (5%)        : ₹" + String.format("%.2f", ma) + "\n\n",
                 normal);

         doc.insertString(doc.getLength(), "DEDUCTIONS\n", red);

         doc.insertString(doc.getLength(),
                 "PF (12%)            : ₹" + String.format("%.2f", pf) + "\n" +
                 "ESI (0.75%)         : ₹" + String.format("%.2f", esi) + "\n" +
                 "TDS (8%)            : ₹" + String.format("%.2f", tds) + "\n" +
                 "Professional Tax    : ₹" + String.format("%.2f", pt) + "\n\n",
                 normal);

         doc.insertString(doc.getLength(),
                 "NET SALARY : ₹" + String.format("%.2f", netSalary),
                 blue);
            
            
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
    
}


