package ui;

import javax.swing.*;



import java.awt.*;

public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
    private static final Font FONT_BRAND  = new Font("Segoe UI", Font.BOLD,  28);

	 private JPanel contentPanel;
	 private int companyId;   

	    public HomeFrame(int companyId) {   
	        this.companyId = companyId;    

	    	setTitle("PAYTRACK");   	
           setSize(1550, 850);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    setLayout(new BorderLayout());

    add(createTopPanel(), BorderLayout.NORTH);
    add(createSidePanel(), BorderLayout.WEST);

    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setBackground(new Color(245,245,245));

    add(contentPanel, BorderLayout.CENTER);

    showDashboard();
    setVisible(true);
}  
	    //top panel

public JPanel createTopPanel() {

    JPanel topPanel = new JPanel(new BorderLayout());

    topPanel.setBackground(new Color(4, 24, 82));
    topPanel.setPreferredSize(new Dimension(0, 90));
 // Gold strip just below top panel
    JPanel goldLine = new JPanel();
    goldLine.setBackground(new Color(255, 193, 7));
    goldLine.setBounds(280, 90, 1290, 4); // x, y, width, height
  add(goldLine);

    // LEFT
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
    leftPanel.setOpaque(false);

           JLabel brandLbl = new JLabel(
        "<html><span style='color:#FFFFFF'>PAY</span>"
      + "<span style='color:#FFC107'>TRACK</span></html>");
    brandLbl.setFont(FONT_BRAND);
    brandLbl.setBounds(24, 14, 200, 36);

    JLabel company = new JLabel("EMPLOYEE PAYROLL MANAGEMENT SYSTEM");
    company.setForeground(Color.WHITE);
    company.setFont(new Font("Segoe UI", Font.BOLD, 24));
    
    leftPanel.add(brandLbl);
    leftPanel.add(new JSeparator(SwingConstants.VERTICAL));
    leftPanel.add(company);

    // RIGHT
    JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,20,20));
    rightPanel.setOpaque(false);

    JButton adminBtn = new JButton("Admin");
    adminBtn.setBackground(new Color(15, 35, 70));//navy blue
    adminBtn.setForeground(new Color(240, 240, 240));
    adminBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        
    JButton logoutBtn = new JButton("x Logout");
    logoutBtn.setBackground(new Color(240, 240, 240));
    logoutBtn.setForeground(new Color(15, 35, 70));//navy blue
    logoutBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
    logoutBtn.addActionListener(e -> {
        dispose();
        new LoginFrame().setVisible(true);
    });    

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

 
    JButton generatorBtn =
            createMenuButton("Payroll Generator");

   
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

     sidePanel.add(generatorBtn);

    sidePanel.add(Box.createVerticalGlue());

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
                contentPanel.add(new Dashboard(companyId));
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            public void showPayrollgen() {
                contentPanel.removeAll();
                contentPanel.add(new Payrollgen(companyId));
                contentPanel.revalidate();
                contentPanel.repaint();
            }

            public void showPayrollcalc() {
                contentPanel.removeAll();
                contentPanel.add(new Payrollcalc(companyId));
                contentPanel.revalidate();
                contentPanel.repaint();
            }
            public void showModifyEmp() {
                contentPanel.removeAll();
                contentPanel.add(new ModifyEmp( companyId));
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        
public static void main(String[] args) {

    new HomeFrame(2);


}}

