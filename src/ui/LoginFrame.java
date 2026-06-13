package ui;

//import
import javax.swing.*;
import dao.*;
import model.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    // ── Palette ────────────────────────────────────
    private static final Color NAVY        = new Color(15,  35,  70);
    private static final Color NAVY_LIGHT  = new Color(35,  70, 140);
    private static final Color ACCENT_GOLD = new Color(255, 193,   7);
    private static final Color WHITE       = Color.WHITE;
    private static final Color BG_FORM     = new Color(245, 247, 252);
    private static final Color BORDER_CLR  = new Color(200, 210, 230);
    private static final Color TEXT_HINT   = new Color(160, 175, 200);
    private static final Color TEXT_DARK   = new Color(25,  40,  75);
    private static final Color ERROR_RED   = new Color(185, 50,  40);

    // ── Fonts ──────────────────────────────────────────────────────────────────
    private static final Font FONT_FIELD  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_LINK   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 10);

    // ── State ──────────────────────────────────────────────────────────
    private JPanel overlayPanel;
    private boolean companyMode = false;
    private JLabel boyLabel;
    private JLayeredPane layeredPane;
    private CardLayout companyCardLayout;
    private JPanel companyCardPanel;
 
//===============================================
    public LoginFrame() {

        setTitle("PayTrack Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBackground(BG_FORM);

        // Left form — Employee
        JPanel employeePanel = createEmployeePanel();
        employeePanel.setBounds(0, 0, 450, 550);
        // Right form — Company (login + signup via CardLayout)
        JPanel companyPanel = createCompanyContainer();
        companyPanel.setBounds(450, 0, 450, 550);
        // Overlay — navy sliding panel
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
    //==============================================
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);
     // gold stripe 
        JPanel goldLine = new JPanel();
        goldLine.setBackground(ACCENT_GOLD);
        goldLine.setBounds(0, 0, 450, 3);
       panel.add(goldLine);

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
        //subtitle
        JLabel subtitle = new JLabel("Login to view your payslip");
        subtitle.setFont(FONT_SMALL);
        subtitle.setForeground(new Color(130, 150, 190));
        subtitle.setBounds(170, 135, 300, 40);
        panel.add(subtitle);
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
        lblPass.setBounds(100, 250, 100, 20);

        // Password Field
        JPasswordField passField = new JPasswordField();
        passField.setBounds(140, 270, 210, 40);
        passField.setMargin(new Insets(0,10,0,10));
        passField.setEchoChar((char)0);
        passField.setText("Enter password");
        passField.setForeground(Color.GRAY);
        //pasword logo
        ImageIcon lockIcon = new ImageIcon("images/lock.jpeg");
        Image lockImg = lockIcon.getImage()
                .getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JLabel lockIconLbl = new JLabel(new ImageIcon(lockImg));
        lockIconLbl.setBounds(110, 280, 20, 20);

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

        // ── Show / Hide password toggle ───────────────────────────────────────
        JCheckBox showPass = new JCheckBox("Show password");
        showPass.setFont(FONT_SMALL);
        showPass.setForeground(new Color(100, 120, 165));
        showPass.setBackground(WHITE);
        showPass.setFocusPainted(false);
        showPass.setBounds(150, 310, 120, 20);
        showPass.addActionListener(e ->
            passField.setEchoChar(showPass.isSelected() ? (char) 0 : '•'));
        panel.add(showPass);


        // company id label
        JLabel lblid = new JLabel("CompanyID");
        lblid.setBounds(100, 330, 100, 20);
     // Company_id Field
        JTextField mailField = new JTextField();
        mailField.setBounds(140, 355, 210, 40);
        mailField.setMargin(new Insets(0,10,0,10));

        mailField.setText("Enter Company ID");
        mailField.setForeground(Color.GRAY);
        mailField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (mailField.getText().equals("Enter Company ID")) {
                    mailField.setText("");
                   mailField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (mailField.getText().trim().isEmpty()) {
                    mailField.setText("Enter Company ID");
                    mailField.setForeground(Color.GRAY);
                }
            }
        });   
        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(165, 420, 140, 35);
        loginBtn.setBackground(new Color(15, 35, 70));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setFocusPainted(false);
     // ── Error label ───────────────────────────────────────────────────────
        JLabel errLabel = new JLabel(" ");
        errLabel.setFont(FONT_SMALL);
        errLabel.setForeground(ERROR_RED);
        errLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errLabel.setBounds(165, 435, 140, 16);
        panel.add(errLabel);
        loginBtn.addActionListener(e -> {
            String empId    = userField.getText().trim();
            String password = String.valueOf(passField.getPassword());
            String cmpId    = mailField.getText().trim();

            if (empId.isEmpty() || empId.equals("Enter your Employee ID")) {
                flashError(errLabel, "Please enter your Employee ID."); return;
            }
            if (password.isEmpty() || password.equals("Enter your password")) {
                flashError(errLabel, "Please enter your password."); return;
            }
            if (cmpId.isEmpty() || cmpId.equals("Enter your Company ID")) {
                flashError(errLabel, "Please enter your Company ID."); return;
            }

            EmployeeDAO dao = new EmployeeDAO();
            if (dao.validateLogin(empId, password)) {
                errLabel.setText(" ");
                new EmployeeDashboard(empId);
                dispose();
            } else {
                flashError(errLabel, "Invalid Employee ID or password.");
            }
        });
     
        panel.add(empLabel);
        panel.add(title);
        panel.add(lblUser);
        panel.add(lblid);
        panel.add(userField);
        panel.add(lblPass);
        panel.add(passField);
        panel.add(mailField);
        panel.add(loginBtn);

        return panel;
    }
    // -----------------COMPANY SIDE PANEL--------------------------------
    //====================================================
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
        panel.setBackground(BG_FORM);
//gold line
        JPanel goldLine = new JPanel();
        goldLine.setBackground(ACCENT_GOLD);
        goldLine.setBounds(0, 0, 450, 3);
        panel.add(goldLine);
   //title     
        JLabel title = new JLabel("Company Login");//TITLE
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(120, 80, 250, 35);
//subtitle
        JLabel subtitle = new JLabel("Access your company payroll portal");
        subtitle.setFont(FONT_SMALL);
        subtitle.setForeground(new Color(130, 150, 190));
        subtitle.setBounds(135, 115, 300, 16);
        panel.add(subtitle);
       
        JLabel lblUser = styledLabel("User ID");
        lblUser.setBounds(100, 155, 100, 20);
        panel.add(lblUser);

        JTextField userField = styledField("Enter Company User ID");
        userField.setBounds(100, 180, 250, 35);
        panel.add(userField);

        JLabel lblPass = styledLabel("Password");
        lblPass.setBounds(100, 218, 100, 20);
        panel.add(lblPass);

        JPasswordField passField = styledPassField("Enter your password");
        passField.setBounds(100, 240, 250, 35);
        panel.add(passField);

        JButton loginBtn = primaryButton("Login", NAVY, ACCENT_GOLD);
        loginBtn.setBounds(165, 310, 140, 35);
        loginBtn.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
        panel.add(loginBtn);

        JLabel errLabel = new JLabel(" ");
        errLabel.setFont(FONT_SMALL);
        errLabel.setForeground(ERROR_RED);
        errLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errLabel.setBounds(165, 335,140, 16);
        panel.add(errLabel);

        // Divider + signup link
        JSeparator div = new JSeparator();
        div.setForeground(BORDER_CLR);
        div.setBounds(88, 360,250, 1);
        panel.add(div);

        JLabel noAcc = new JLabel("Don't have an account?");
        noAcc.setFont(FONT_SMALL);
        noAcc.setForeground(new Color(130, 150, 190));
        noAcc.setBounds(90, 372, 180, 18);
        panel.add(noAcc);

        JButton signupBtn = linkButton("Create one →");
        signupBtn.setBounds(230, 370, 120, 22);
        signupBtn.addActionListener(e -> companyCardLayout.show(companyCardPanel, "SIGNUP"));
        panel.add(signupBtn);
      //company login credentials
        loginBtn.addActionListener(e -> {
            String userId   = userField.getText().trim();
            String password = String.valueOf(passField.getPassword());

            if (userId.isEmpty() || userId.equals("Enter Company User ID")) {
                flashError(errLabel, "Please enter your User ID."); return;
            }
            if (password.isEmpty() || password.equals("Enter your password")) {
                flashError(errLabel, "Please enter your password."); return;
            }

            CompanyDAO dao = new CompanyDAO();
            if (dao.validateLogin(userId, password)) {
                errLabel.setText(" ");
                dispose();
                new HomeFrame(Integer.parseInt(userId)).setVisible(true);
            } else {
                flashError(errLabel, "Invalid User ID or password.");
            }
        });

        panel.add(loginBtn);
        panel.add(signupBtn);
        panel.add(title);
        return panel;
    }
    //----------------------COMPANY SIGNUP---------------------------------------

    private JPanel createCompanySignupPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(BG_FORM);
//title
        JLabel title = new JLabel("Create Company Account");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBounds(70, 40, 300, 35);
//subtitle
        JLabel subtitle = new JLabel("Register your organisation on PayTrack");
        subtitle.setFont(FONT_SMALL);
        subtitle.setForeground(new Color(130, 150, 190));
        subtitle.setBounds(150, 60, 300, 35);
             
         JLabel lblName = styledLabel("Company Name");
        lblName.setBounds(100, 100, 150, 20);
        panel.add(lblName);

        JTextField companyField = styledField("Your organisation name");
        companyField.setBounds(100, 125, 250, 35);
        panel.add(companyField);

        JLabel lblEmail = styledLabel("Email Address");
        lblEmail.setBounds(100, 170, 150, 20);
        panel.add(lblEmail);

        JTextField emailField = styledField("admin@company.com");
        emailField.setBounds(100, 195, 250, 35);
        panel.add(emailField);

        JLabel lblPass = styledLabel("Password");
        lblPass.setBounds(100, 240, 150, 20);
        panel.add(lblPass);

        JPasswordField passField = styledPassField("Create a strong password");
        passField.setBounds(100, 265, 250, 35);
        panel.add(passField);

        JLabel lblConfirm = styledLabel("Confirm Password");
        lblConfirm.setBounds(100, 310, 200, 18);
        panel.add(lblConfirm);

        JPasswordField confirmField = styledPassField("Re-enter your password");
        confirmField.setBounds(100, 330, 250, 35);
        panel.add(confirmField);
        
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
        backBtn.addActionListener(e -> companyCardLayout.show(companyCardPanel, "LOGIN"));

        JLabel errLabel = new JLabel(" ");
        errLabel.setFont(FONT_SMALL);
        errLabel.setForeground(ERROR_RED);
        errLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errLabel.setBounds(100, 370, 250, 16);
        panel.add(errLabel);

        registerBtn.addActionListener(e -> {
            String name     = companyField.getText().trim();
            String email    = emailField.getText().trim();
            String password = String.valueOf(passField.getPassword());
            String confirm  = String.valueOf(confirmField.getPassword());

            if (name.isEmpty() || name.equals("Your organisation name")) {
                flashError(errLabel, "Company name is required."); return;
            }
            if (email.isEmpty() || !email.contains("@")) {
                flashError(errLabel, "Enter a valid email address."); return;
            }
            if (password.length() < 6) {
                flashError(errLabel, "Password must be at least 6 characters."); return;
            }
            if (!password.equals(confirm)) {
                flashError(errLabel, "Passwords do not match."); return;
            }

            CompanyDAO dao = new CompanyDAO();
            if (dao.emailExists(email)) {
                flashError(errLabel, "That email is already registered."); return;
            }

            Company company = new Company();
            company.setCompanyName(name);
            company.setEmail(email);
            company.setPassword(password);

            if (dao.addCompany(company)) {
                JOptionPane.showMessageDialog(this,
                    "Account created! You can now log in.",
                    "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                companyField.setText("");
                emailField.setText("");
                passField.setText("");
                confirmField.setText("");
                companyCardLayout.show(companyCardPanel, "LOGIN");
            } else {
                flashError(errLabel, "Registration failed. Please try again.");
            }
        });

        panel.add(title);
        panel.add(companyField);
        panel.add(emailField);
        panel.add(passField);
        panel.add(registerBtn);
        panel.add(backBtn);
        panel.add(subtitle);

        return panel;
    }

    //-----------------------NAVY BLUE OVERLAY-----------------------------------
    //================================================================
    private JPanel createOverlayPanel() {

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(15, 35, 70));

        ImageIcon logoIcon = new ImageIcon("images/logo 2 copy.png");

        Image logoImg = logoIcon.getImage()
                .getScaledInstance(220, 84, Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(logoImg));
        logoLabel.setBounds(115, 35, 220, 84);
    

        JLabel title = new JLabel("Welcome");
        title.setForeground(new Color(255, 193, 7)); // Gold
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBounds(120, 120, 220, 40);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitle = new JLabel("Company Portal");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Arial", Font.BOLD, 20));
        subtitle.setBounds(120, 165, 220, 40);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel info = new JLabel("<html><center style='color:#A8BEDE;line-height:160%'>"
                + "Your payroll, always<br>accurate and on time.</center></html>");
        info.setForeground(Color.WHITE);
        info.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        info.setBounds(100, 210, 250, 40);
        info.setHorizontalAlignment(SwingConstants.CENTER);
     // Decorative dot row
        JLabel dots = new JLabel("· · · · ·");
        dots.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        dots.setForeground(ACCENT_GOLD);
        dots.setHorizontalAlignment(SwingConstants.CENTER);
        dots.setBounds(100, 250,250, 24);
        
        JButton switchBtn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover()
                    ? ACCENT_GOLD : new Color(25,42,86));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        switchBtn.setText("Switch to Employee Login");
        switchBtn.setBounds(80, 300, 300, 50);
        switchBtn.setFont(new Font("Arial", Font.BOLD, 14));
        switchBtn.setForeground(WHITE);
        switchBtn.setFocusPainted(false);
        switchBtn.setContentAreaFilled(false);
        switchBtn.setOpaque(false);
        switchBtn.setBorderPainted(false);
        switchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Load and resize icon
        ImageIcon icon = new ImageIcon("images/employeeicon2.png");
        Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        switchBtn.setIcon(new ImageIcon(img));

        // Icon left, text right
        switchBtn.setHorizontalAlignment(SwingConstants.CENTER);
        switchBtn.setHorizontalTextPosition(SwingConstants.RIGHT);
        switchBtn.setIconTextGap(10);
        // Footer note
        JLabel footer = new JLabel(
            "<html><center style='color:#607090'>© PayTrack Payroll System</center></html>");
        footer.setFont(FONT_SMALL);
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        footer.setBounds(0,480,360,20);

        // ── Switch action ──────────────────────────────────────────────────────

        switchBtn.addActionListener(e -> {
        	// Show company forms
            if (!companyMode) {
                animateOverlay(450);
                switchBtn.setText("Switch to Company LOGIN");
                switchBtn.setIcon(null);  // Remove icon
                switchBtn.setHorizontalAlignment(SwingConstants.CENTER);
                subtitle.setText(" ");
                companyMode = true;
            } else {
                animateOverlay(0);
                switchBtn.setText("Switch to Employee Login");
                switchBtn.setIcon(new ImageIcon(img)); // Show icon again
                title.setText("Welcome");
                subtitle.setText("Company Portal");
                companyMode = false;
            }
        });
        panel.add(logoLabel);
        panel.add(title);
        panel.add(info);
        panel.add(switchBtn);
        panel.add(footer);
        panel.add(dots);
        panel.add(subtitle);

        return panel;
    }
//--------------------animation------------------------------------
//====================================================================    
    private void animateOverlay(int targetX) {
        Timer timer = new Timer(6, null);
        timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int x = overlayPanel.getX();
                int step = Math.max(1, Math.abs(targetX - x) / 8); // ease-out
                if (x < targetX) {
                    overlayPanel.setLocation(Math.min(x + step, targetX), 0);
                } else if (x > targetX) {
                    overlayPanel.setLocation(Math.max(x - step, targetX), 0);
                } else {
                    timer.stop();
                }
                layeredPane.repaint();
            }
        });
        timer.start();
    }
    // ══════════════════════════════════════════════════════════════════════════
    //  UI COMPONENT HELPERS
    // ══════════════════════════════════════════════════════════════════════════

    /** Standard uppercase caption label */
    private JLabel styledLabel(String text) {
        JLabel lbl = new JLabel(text.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(100, 120, 165));
        return lbl;
    }

    /** Rounded text field with placeholder behaviour */
    private JTextField styledField(String placeholder) {
        JTextField field = new JTextField(placeholder) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(isFocusOwner() ? NAVY_LIGHT : BORDER_CLR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(FONT_FIELD);
        field.setForeground(TEXT_HINT);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        field.setOpaque(false);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                }
                field.repaint();
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_HINT);
                }
                field.repaint();
            }
        });
        return field;
    }

    /** Rounded password field with placeholder behaviour */
    private JPasswordField styledPassField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(isFocusOwner() ? NAVY_LIGHT : BORDER_CLR);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(FONT_FIELD);
        field.setForeground(TEXT_HINT);
        field.setBackground(WHITE);
        field.setEchoChar((char) 0);   // show placeholder as plain text
        field.setText(placeholder);
        field.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        field.setOpaque(false);

        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                    field.setEchoChar('•');
                }
                field.repaint();
            }
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_HINT);
                    field.setEchoChar((char) 0);
                }
                field.repaint();
            }
        });
        return field;
    }

    /** Solid rounded primary button */
    private JButton primaryButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed()  ? bg.darker()
                        : getModel().isRollover() ? NAVY_LIGHT
                        : bg;
                g2.setColor(c);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /** Underline-style text link button */
    private JButton linkButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_LINK);
        btn.setForeground(NAVY_LIGHT);
        btn.setBackground(null);
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, NAVY_LIGHT));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(NAVY); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(NAVY_LIGHT); }
        });
        return btn;
    }

    /** Flash an inline error message, auto-clear after 3 s */
    private void flashError(JLabel label, String msg) {
        label.setText(msg);
        Timer t = new Timer(3000, e -> label.setText(" "));
        t.setRepeats(false);
        t.start();
    }

 //--------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

}