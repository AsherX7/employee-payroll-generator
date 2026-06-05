 package ui;
 //imports
 import javax.swing.*;
import dao.AllowanceDAO;
//class
 public class Payrollcalc extends JFrame {
	 private static final long serialVersionUID = 1L;
     private JTextField txtTaxName;
     private JTextField txtTaxPer;
     private JButton btnSave;
     private JButton backButton;
     
     public Payrollcalc() {

         setTitle("Tax Management");
         setSize(400,300);
         setLocationRelativeTo(null);
         setLayout(null);

         JLabel lblTaxName = new JLabel("Tax Name:");
         lblTaxName.setBounds(50,50,100,25);

         txtTaxName = new JTextField();
         txtTaxName.setBounds(150,50,150,25);

         JLabel lblTaxPer = new JLabel("Percentage:");
         lblTaxPer.setBounds(50,100,100,25);

         txtTaxPer = new JTextField();
         txtTaxPer.setBounds(150,100,150,25);

         btnSave = new JButton("Save");
         btnSave.setBounds(150,170,100,30);
         add(btnSave);
         //backbutton
         backButton = new JButton("Back");
         backButton.setBounds(40,170,100,30);
         add(backButton);
                 
         backButton.addActionListener(e -> {

             new HomeFrame(); // Replace with your Dashboard class
             dispose();
             
         });
         
         setVisible(true);
         
         add(lblTaxName);
         add(txtTaxName);
         add(lblTaxPer);
         add(txtTaxPer);
         add(btnSave);

         btnSave.addActionListener(e -> saveTax());

         setVisible(true);
     }

     private void saveTax() {

         String taxName = txtTaxName.getText().trim();
         String percentageText = txtTaxPer.getText().trim();

         
         double percentage;
         try {
             percentage = Double.parseDouble(percentageText);
         } catch(NumberFormatException e) {
             JOptionPane.showMessageDialog(
                 this,
                 "Percentage must be a valid number",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
             );
             return;
         }

         // Range check
         if(percentage <= 0 || percentage > 100) {
             JOptionPane.showMessageDialog(
                 this,
                 "Percentage must be between 0 and 100",
                 "Validation Error",
                 JOptionPane.WARNING_MESSAGE
             );
             return;
         }
         

         AllowanceDAO dao = new AllowanceDAO();

         boolean status =
                 dao.updateTaxPer(taxName, percentage);

         if(status) {
             JOptionPane.showMessageDialog(
                     this,
                     "Tax Updated Successfully"
             );
         } else {
             JOptionPane.showMessageDialog(
                     this,
                     "Update Failed"
             );
         }
     }
 }
 