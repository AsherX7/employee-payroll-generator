package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	 private JPanel contentPanel;
	    public HomeFrame() {

    setTitle("PAYTRACK");
    setSize(1550, 850);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    setLayout(new BorderLayout());

    add(createTopPanel(), BorderLayout.NORTH);
    add(createSidePanel(), BorderLayout.WEST);

    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(new Color(245,245,245));

    add(contentPanel, BorderLayout.CENTER);

    showDashboard();

    setVisible(true);
}  

public JPanel createTopPanel() {

    JPanel topPanel = new JPanel(new BorderLayout());

    topPanel.setBackground(new Color(4, 24, 82));
    topPanel.setPreferredSize(new Dimension(0, 90));

    // LEFT
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
    leftPanel.setOpaque(false);

    JLabel logo = new JLabel("PAYTRACK");
    logo.setForeground(Color.WHITE);
    logo.setFont(new Font("Segoe UI", Font.BOLD, 32));

    JLabel company = new JLabel("ABC Technologies Pvt. Ltd.");
    company.setForeground(Color.WHITE);
    company.setFont(new Font("Segoe UI", Font.BOLD, 24));

    leftPanel.add(logo);
    leftPanel.add(new JSeparator(SwingConstants.VERTICAL));
    leftPanel.add(company);

    // RIGHT
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,20));
    rightPanel.setOpaque(false);

    JButton adminBtn = new JButton("Admin");
    JButton logoutBtn = new JButton("Logout");

    adminBtn.setFocusPainted(false);
    logoutBtn.setFocusPainted(false);

    rightPanel.add(adminBtn);
    rightPanel.add(logoutBtn);

    topPanel.add(leftPanel, BorderLayout.WEST);
    topPanel.add(rightPanel, BorderLayout.EAST);

    return topPanel;
}
public JPanel createSidePanel() {

    JPanel sidePanel = new JPanel();

    sidePanel.setBackground(new Color(4,24,82));
    sidePanel.setPreferredSize(new Dimension(280,0));

    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

    sidePanel.add(Box.createVerticalStrut(30));

    JButton dashboardBtn =
            createMenuButton("Dashboard");

    JButton empBtn =
            createMenuButton("Modify Employee");

    JButton payrollBtn =
            createMenuButton("Payroll Calculation");

    JButton reportBtn =
            createMenuButton("Report");

    JButton generatorBtn =
            createMenuButton("Payroll Generator");

    JButton logoutBtn =
            createMenuButton("Logout");

    dashboardBtn.addActionListener(e -> showDashboard());
    empBtn.addActionListener(e -> showModifyEmp());
    payrollBtn.addActionListener(e -> showPayrollcalc());
    generatorBtn.addActionListener(e -> showPayrollgen());

    sidePanel.add(dashboardBtn);
    sidePanel.add(Box.createVerticalStrut(15));

    sidePanel.add(empBtn);
    sidePanel.add(Box.createVerticalStrut(15));

    sidePanel.add(payrollBtn);
    sidePanel.add(Box.createVerticalStrut(15));

    sidePanel.add(reportBtn);
    sidePanel.add(Box.createVerticalStrut(15));

    sidePanel.add(generatorBtn);

    sidePanel.add(Box.createVerticalGlue());

    sidePanel.add(logoutBtn);

    return sidePanel;
}
private JButton createMenuButton(String text) {

    JButton btn = new JButton(text);

    btn.setMaximumSize(new Dimension(250,55));

    btn.setBackground(new Color(22, 63, 164));
    btn.setForeground(Color.WHITE);

    btn.setFocusPainted(false);

    btn.setFont(new Font(
            "Segoe UI",
            Font.BOLD,
            18));

    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    return btn;
}                      

            public void showDashboard() {
                contentPanel.removeAll();
                contentPanel.add(new Dashboard());
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            public void showPayrollgen() {
                contentPanel.removeAll();
                contentPanel.add(new Payrollgen());
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            public void showPayrollcalc() {
                contentPanel.removeAll();
                contentPanel.add(new Payrollcalc());
                contentPanel.revalidate();
                contentPanel.repaint();
            }
            public void showModifyEmp() {
                contentPanel.removeAll();
                contentPanel.add(new ModifyEmp());
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        
public static void main(String[] args) {

    new HomeFrame();

}}