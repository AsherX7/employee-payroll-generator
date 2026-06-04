package ui;

//import
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//class

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

    
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

        ImageIcon icon = new ImageIcon(
                getClass().getResource("logo.png")
        );

        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(200,200, Image.SCALE_SMOOTH);

        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setBounds(250, 50, 100, 100);

        panel.add(logoLabel);
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        logoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        logoLabel.setBounds(225, 90, 150, 80);

       

        
        loginButton = new JButton("Login");
        loginButton.setBounds(250, 310, 100, 35);
        
        loginButton.addActionListener(e -> {
        	 new HomeFrame();   // Open next page
             dispose();         // Close login page
        });
        panel.add(titleLabel);
        panel.add(logoLabel);
       
        panel.add(loginButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}