package ui;

//import
import javax.swing.*;
import dao.*;
import model.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    private JPanel overlayPanel;
    private boolean companyMode = false;
    private JLabel boyLabel;
    private JLayeredPane layeredPane;
    private CardLayout companyCardLayout;
    private JPanel companyCardPanel;

    public LoginFrame() {

        setTitle("PayTrack Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);

        JPanel employeePanel = createEmployeePanel();
        employeePanel.setBounds(0, 0, 450, 550);

        JPanel companyPanel = createCompanyContainer();
        companyPanel.setBounds(450, 0, 450, 550);

        overlayPanel = createOverlayPanel();
        overlayPanel.setBounds(0, 0, 450, 550);

        layeredPane.add(employeePanel, Integer.valueOf(1));
        layeredPane.add(companyPanel, Integer.valueOf(1));
        layeredPane.add(overlayPanel, Integer.valueOf(2));
        ImageIcon boyIcon = new ImageIcon("boy.png");

        boyLabel = new JLabel(boyIcon);

        boyLabel.setBounds(650, 220, 180, 220);
        layeredPane.add(boyLabel, Integer.valueOf(3));
        add(layeredPane);
    }

    //--------------- EMPLOYEE SIDE PANEL---------------------------------
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // Employee Icon
        ImageIcon empIcon = new ImageIcon("images/employeeicon.png");
        Image empImg = empIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        JLabel empLabel = new JLabel(new ImageIcon(empImg));
        empLabel.setBounds(195, 40, 60, 60);

        // Title
        JLabel title = new JLabel("EMPLOYEE LOGIN");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(25, 42, 86));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(75, 115, 300, 40);

        // Username Label
        JLabel lblUser = new JLabel("EmployeeID");
        lblUser.setBounds(100, 170, 100, 20);

        // Username Field
        JTextField userField = new JTextField();
        userField.setBounds(140, 195, 210, 40);
        userField.setMargin(new Insets(0,10,0,10));

        userField.setText("Enter EmployeeID");
        userField.setForeground(Color.GRAY);
      //username logo      
        ImageIcon userIcon = new ImageIcon("images/employeeicon3.png");
        Image userImg = userIcon.getImage()
                .getScaledInstance(30, 25, Image.SCALE_SMOOTH);

        JLabel userIconLbl = new JLabel(new ImageIcon(userImg));
        userIconLbl.setBounds(110, 205, 20, 20);

        panel.add(userIconLbl);
       
        userField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Enter EmployeeID")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().trim().isEmpty()) {
                    userField.setText("Enter EmployeeID");
                    userField.setForeground(Color.GRAY);
                }
            }
        });
        
        // Password Label
        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(100, 255, 100, 20);

        // Password Field
        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 280, 210, 40);
        passField.setMargin(new Insets(0,10,0,10));
        passField.setEchoChar((char)0);
        passField.setText("Enter password");
        passField.setForeground(Color.GRAY);
        //pasword logo
        ImageIcon lockIcon = new ImageIcon("images/lock.jpeg");
        Image lockImg = lockIcon.getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JLabel lockIconLbl = new JLabel(new ImageIcon(lockImg));
        lockIconLbl.setBounds(110, 290, 20, 20);

        panel.add(lockIconLbl);

        passField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                if (String.valueOf(passField.getPassword())
                        .equals("Enter password")) {

                    passField.setText("");
                    passField.setForeground(Color.BLACK);
                    passField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (passField.getPassword().length == 0) {

                    passField.setText("Enter password");
                    passField.setForeground(Color.GRAY);
                    passField.setEchoChar((char)0);
                }
            }
        });
        //company mail id
        JTextField mailField = new JTextField();
        mailField.setBounds(140, 365, 210, 40);
        mailField.setMargin(new Insets(0,10,0,10));

        mailField.setText("Enter Company mail id");
        mailField.setForeground(Color.GRAY);
        mailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userField.getText().equals("Enter Company mail id")) {
                    userField.setText("");
                    userField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userField.getText().trim().isEmpty()) {
                    userField.setText("Enter Company mail id");
                    userField.setForeground(Color.GRAY);
                }
            }
        });
        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(125, 350, 200, 45);

        loginBtn.setBackground(new Color(15, 35, 70));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);

        loginBtn.addActionListener(e -> {

            String username = userField.getText();
            String password = String.valueOf(passField.getPassword());

            EmployeeDAO dao = new EmployeeDAO();

            if (dao.validateLogin(username, password)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful");
                JOptionPane.showMessageDialog(
                        this,
                        "Employee Login: " + userField.getText());
                dispose();
                new EmployeeDashboard().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password");
            }
        });
        panel.add(empLabel);
        panel.add(title);
        panel.add(lblUser);
        panel.add(userField);
        panel.add(lblPass);
        panel.add(passField);
        panel.add(loginBtn);

        return panel;
    }
    // -----------------COMPANY SIDE PANEL--------------------------------
    private JPanel createCompanyContainer() {

        companyCardLayout = new CardLayout();
        companyCardPanel = new JPanel(companyCardLayout);

        companyCardPanel.add(createCompanyLoginPanel(), "LOGIN");
        companyCardPanel.add(createCompanySignupPanel(), "SIGNUP");

        return companyCardPanel;
    }
//--------------------COMPANY LOGIN-----------------------------------------
    private JPanel createCompanyLoginPanel() {

        JPanel panel = new JPanel(null);//PANEL
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Company Login");//TITLE
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(120, 80, 250, 35);

        JTextField userField = new JTextField();
        userField.setBounds(100, 180, 250, 35);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 240, 250, 35);

        JButton loginBtn = new JButton("Login");//LOGINBUTTON
        loginBtn.setBounds(165, 310, 140, 35);
        loginBtn.setBackground(new Color(240, 240, 240));
        loginBtn.setForeground(new Color(15, 35, 70));//navy blue
        loginBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
            
        //company login credentials
        loginBtn.addActionListener(e -> {

            String company_id = userField.getText();
            String password = String.valueOf(passField.getPassword());

            CompanyDAO dao = new CompanyDAO();

            if (dao.validateLogin(company_id, password)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful");
                JOptionPane.showMessageDialog(this,
                        "Company Login: " + userField.getText());

                dispose();
                new HomeFrame().setVisible(true);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid UserID or Password");
            }
        });


        JButton signupBtn = new JButton("Create Account");//CREATE ACC BUTTON
        signupBtn.setBounds(165, 360, 140, 38);
        signupBtn.setBackground(new Color(15, 35, 70));//navy blue
        signupBtn.setForeground(Color.WHITE);
        signupBtn.addActionListener(e ->
                companyCardLayout.show(companyCardPanel, "SIGNUP"));

        panel.add(title);

        panel.add(new JLabel("UserID")).setBounds(100, 155, 100, 20);
        panel.add(userField);

        panel.add(new JLabel("Password")).setBounds(100, 215, 100, 20);
        panel.add(passField);

        panel.add(loginBtn);
        panel.add(signupBtn);

        return panel;
    }
    //----------------------COMPANY SIGNUP---------------------------------------

    private JPanel createCompanySignupPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(245, 245, 245));

        JLabel title = new JLabel("Company Sign Up");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(100, 40, 300, 35);

        JTextField companyField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();

        panel.add(new JLabel("Company Name")).setBounds(100, 100, 150, 20);
        companyField.setBounds(100, 125, 250, 35);

        panel.add(new JLabel("Email")).setBounds(100, 170, 150, 20);
        emailField.setBounds(100, 195, 250, 35);

        panel.add(new JLabel("Password")).setBounds(100, 240, 150, 20);
        passField.setBounds(100, 265, 250, 35);

        JButton registerBtn = new JButton("Register");//register
        registerBtn.setBounds(100, 390, 250, 40);
        registerBtn.setBackground(Color.WHITE);
        registerBtn.setForeground(new Color(15, 35, 70));//navy blue
        registerBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));

        JButton backBtn = new JButton("Back to Login");//back to login
        backBtn.setBounds(100, 440, 250, 40);
        backBtn.setForeground(new Color(240, 240, 240));
        backBtn.setBackground(new Color(15, 35, 70));//navy blue
        backBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));

        backBtn.addActionListener(e ->
                companyCardLayout.show(companyCardPanel, "LOGIN"));
              
        registerBtn.addActionListener(e -> {

            CompanyDAO dao = new CompanyDAO();

            String companyName = companyField.getText();
            String email = emailField.getText();
            String password =String.valueOf(passField.getPassword());
            if (dao.emailExists(email)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Email already exists");

                return;
            }

            Company company = new Company();

            company.setCompanyName(companyName);
            company.setEmail(email);
            company.setPassword(password);

            if (dao.addCompany(company)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Registration Successful");

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Registration Failed");
            }
        });

        panel.add(title);
        panel.add(companyField);
        panel.add(emailField);
        panel.add(passField);
        panel.add(registerBtn);
        panel.add(backBtn);

        return panel;
    }

    //-----------------------NAVY BLUE OVERLAY-----------------------------------
    private JPanel createOverlayPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(15, 35, 70));

        ImageIcon logoIcon = new ImageIcon("images/logo 2 copy.png");

        Image logoImg = logoIcon.getImage()
                .getScaledInstance(220, 84, Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setBounds(115, 35, 220, 84);

        panel.add(logoLabel);
      

        JLabel title = new JLabel("Welcome");
        title.setForeground(new Color(255, 193, 7)); // Gold
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(120, 150, 220, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel info = new JLabel("<html><center>please login to continue</center></html>");
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Arial", Font.PLAIN, 14));
        info.setBounds(100, 200, 250, 40);
        info.setHorizontalAlignment(SwingConstants.CENTER);

        JButton switchBtn = new JButton("Employee LOGIN");
        switchBtn.setBounds(100, 280, 250, 50);
        switchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        switchBtn.setForeground(Color.WHITE);
        switchBtn.setBackground(new Color(25,42,86));
        switchBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        switchBtn.setFocusPainted(false);
        switchBtn.setContentAreaFilled(false);
        switchBtn.setOpaque(false);

        // Load and resize icon
        ImageIcon icon = new ImageIcon("images/employeeicon2.png");
        Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        switchBtn.setIcon(new ImageIcon(img));

        // Icon left, text right
        switchBtn.setHorizontalAlignment(SwingConstants.CENTER);
        switchBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        switchBtn.setIconTextGap(10);

        switchBtn.addActionListener(e -> {

            if (!companyMode) {
                animateOverlay(450);
                switchBtn.setText("Company LOGIN");
                switchBtn.setIcon(null);  // Remove icon
                switchBtn.setHorizontalAlignment(SwingConstants.CENTER);
                title.setText("Company");
                companyMode = true;
            } else {
                animateOverlay(0);
                switchBtn.setText("Employee LOGIN");
                switchBtn.setIcon(new ImageIcon(img)); // Show icon again
                title.setText("Welcome");
                companyMode = false;
            }
        });
        panel.add(logoLabel);
        panel.add(title);
        panel.add(info);
        panel.add(switchBtn);

        return panel;
    }
//--------------------animation------------------------------------
    private void animateOverlay(int targetX) {

        Timer timer = new Timer(5, null);

        timer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int x = overlayPanel.getX();

                if (x < targetX) {

                    overlayPanel.setLocation(
                            Math.min(x + 5, targetX), 0);

                    boyLabel.setLocation(
                            boyLabel.getX() + 1,
                            boyLabel.getY());

                }
                else if (x > targetX) {

                    overlayPanel.setLocation(
                            Math.max(x - 5, targetX), 0);

                    boyLabel.setLocation(
                            boyLabel.getX() - 1,
                            boyLabel.getY());

                }
                else {

                    timer.stop();
                }
            }
        });

        timer.start();
    }   
 //--------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
