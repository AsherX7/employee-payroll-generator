package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	    public HomeFrame() {
//heading
        setTitle("Employee Payroll System");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1,20,20));
//buttons
        JButton modifyBtn = new JButton("Modify Employee");
        JButton payslipBtn = new JButton("Payslip Calculation");
        JButton payrollBtn = new JButton("Payroll Generator");

        panel.add(modifyBtn);
        panel.add(payslipBtn);
        panel.add(payrollBtn);

        add(panel);
        
        //actions
        modifyBtn.addActionListener(e -> {
            new ModifyEmp();
        });
               
        payslipBtn.addActionListener(e -> {
            new Payrollcalc();
        });
        payrollBtn.addActionListener(e -> {
            new Payrollgen();
        });

        setVisible(true);
    }
}