package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame  extends JFrame{
	private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Employee Payroll Generator");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("EMPLOYEE PAYROLL SYSTEM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(120, 40, 400, 40);

        JLabel logoLabel = new JLabel("COMPANY LOGO");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoLabel.setBounds(225, 90, 150, 80);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(140, 210, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(240, 210, 180, 25);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(140, 250, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(240, 250, 180, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(250, 310, 100, 35);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if(username.equals("admin") && password.equals("admin123")) {

                    new HomeFrame();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid Username or Password!"
                    );
                }
            }

			private void dispose() {
				// TODO Auto-generated method stub
				
			}
        });

        panel.add(titleLabel);
        panel.add(logoLabel);
        panel.add(userLabel);
        panel.add(usernameField);
        panel.add(passLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    private void setDefaultCloseOperation(int exitOnClose) {
		// TODO Auto-generated method stub
		
	}

	private void setLocationRelativeTo(Object object) {
		// TODO Auto-generated method stub
		
	}

	private void setSize(int i, int j) {
		// TODO Auto-generated method stub
		
	}

	private void setTitle(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        new LoginFrame();
    }
}
