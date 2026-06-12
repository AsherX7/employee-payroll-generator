package ui;

import dao.EmployeeDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ModifyEmp extends JPanel {
	private static final long serialVersionUID = 1L;

    // Form Input Fields
    private JTextField txtId, txtName, txtDept, txtType, txtExp, txtRate, txtHours, txtSalary;
    
    // Control Buttons
    private JButton btnAdd, btnDelete, btnUpdate, btnSearch, btnBack, btnAddAllowance;
    
    // Database Reference
    private EmployeeDAO empDAO;
    
    // Parent Panel Container for Back Button Navigation
    private JPanel mainContentPanel;

    // Constructor matching the component setup
    public ModifyEmp() {
    	java.awt.CardLayout layoutManager = new java.awt.CardLayout();
        this.mainContentPanel = new javax.swing.JPanel(layoutManager);
        this.mainContentPanel.add(new javax.swing.JPanel(), "Home");
    	this.empDAO=new EmployeeDAO();
    
        
        // 1. Layout Setup
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250)); // Matches your modern light gray look
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 2. Window Title
        JLabel title = new JLabel("Employee Modification & Records Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(11, 26, 49));
        add(title, BorderLayout.NORTH);

        // 3. Form Input Panel Grid
        JPanel gridForm = new JPanel(new GridLayout(8, 2, 12, 12));
        gridForm.setBackground(Color.WHITE);
        gridForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        gridForm.add(new JLabel("Employee ID:"));       txtId = new JTextField();     gridForm.add(txtId);
        gridForm.add(new JLabel("Full Name:"));         txtName = new JTextField();   gridForm.add(txtName);
        gridForm.add(new JLabel("Department:"));        txtDept = new JTextField();   gridForm.add(txtDept);
        gridForm.add(new JLabel("Employment Type:"));   txtType = new JTextField();   gridForm.add(txtType);
        gridForm.add(new JLabel("Years Experience:"));  txtExp = new JTextField();    gridForm.add(txtExp);
        gridForm.add(new JLabel("Hourly Rate:"));       txtRate = new JTextField();   gridForm.add(txtRate);
        gridForm.add(new JLabel("Hours Worked:"));      txtHours = new JTextField();  gridForm.add(txtHours);
        gridForm.add(new JLabel("Calculated Salary:")); txtSalary = new JTextField(); gridForm.add(txtSalary);
        JTextField[] fields = {
        	    txtId, txtName, txtDept, txtType,
        	    txtExp, txtRate, txtHours, txtSalary
        	};

        	for (JTextField field : fields) {
        	    field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        	    field.setBorder(BorderFactory.createCompoundBorder(
        	        BorderFactory.createLineBorder(new Color(220, 225, 230)),
        	        BorderFactory.createEmptyBorder(5, 8, 5, 8)
        	    ));
        	}
        	for (Component c : gridForm.getComponents()) {
        	    if (c instanceof JLabel) {
        	        c.setFont(new Font("Segoe UI", Font.BOLD, 14));
        	    }
        	}
        add(gridForm, BorderLayout.CENTER);

        // 4. Action Button Control Panel (Bottom Row)
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controls.setOpaque(false);

        btnAdd = new JButton("Add Employee");
        btnSearch = new JButton("Search ID");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete Record");
        btnAddAllowance = new JButton("Add Allowance ➕");
        btnBack = new JButton("Back");
     // Define the custom corporate navy color theme
        java.awt.Color primaryNavy = new java.awt.Color(11, 26, 49);

        // Style the Add Button
        btnAdd.setBackground(primaryNavy);
        btnAdd.setForeground(java.awt.Color.WHITE);
        btnAdd.setFocusPainted(false);

        // Style the Search Button
        btnSearch.setBackground(primaryNavy);
        btnSearch.setForeground(java.awt.Color.WHITE);
        btnSearch.setFocusPainted(false);

        // Style the Update Button
        btnUpdate.setBackground(primaryNavy);
        btnUpdate.setForeground(java.awt.Color.WHITE);
        btnUpdate.setFocusPainted(false);

        // Style the Delete Button
        btnDelete.setBackground(primaryNavy);
        btnDelete.setForeground(java.awt.Color.WHITE);
        btnDelete.setFocusPainted(false);
     // // Style the Add Allowance Button
        btnAddAllowance.setBackground(primaryNavy);
        btnAddAllowance.setForeground(java.awt.Color.WHITE);
        btnAddAllowance.setContentAreaFilled(true);
        btnAddAllowance.setOpaque(true);
        btnAddAllowance.setBorderPainted(false);
        btnAddAllowance.setFocusPainted(false);

        // // Style the Back Button
        btnBack = new JButton("Back");

        btnBack.setUI(new javax.swing.plaf.basic.BasicButtonUI());
        btnBack.setBackground(new java.awt.Color(11, 26, 49));
        btnBack.setForeground(java.awt.Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(true);
        btnBack.setContentAreaFilled(true);
        btnBack.setBorderPainted(false);
            btnBack.setPreferredSize(new java .awt.Dimension(120,35));
          // Style adjustments for buttons to make them uniform
        Dimension btnSize = new Dimension(130, 35);
        Component[] buttons = {btnAdd, btnSearch, btnUpdate, btnDelete, btnAddAllowance, btnBack};
        for (Component c : buttons) {
            c.setPreferredSize(btnSize);
            controls.add(c);
        }
        
        add(controls, BorderLayout.SOUTH);

        // 5. Setup Operational Event Listeners
        setupActionListeners();
    }

    private void setupActionListeners() {
        
        // ➕ ADD RECORD OPERATION
    	btnAdd.addActionListener(e -> {
    	    String id = txtId.getText().trim();
    	    String name = txtName.getText().trim();
    	    
    	    // 1. Validation check for primary keys
    	    if (id.isEmpty() || name.isEmpty()) {
    	        JOptionPane.showMessageDialog(this, "Employee ID and Full Name are mandatory fields.");
    	        return;
    	    }

    	    String query = "INSERT INTO employee (employeeid, name, department, type, year_exp, hourly_rate, hours_worked, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    	    try {
    	        java.sql.Connection con = dao.DBConnection.getConnection();
    	        java.sql.PreparedStatement pst = con.prepareStatement(query);

    	        // 2. Bind all the form values to the insert statement columns
    	        pst.setString(1, id);
    	        pst.setString(2, name);
    	        pst.setString(3, txtDept.getText().trim());
    	        pst.setString(4, txtType.getText().trim());
    	        
    	        // Handle numerical defaults if inputs are empty
    	        pst.setInt(5, txtExp.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtExp.getText().trim()));
    	        pst.setDouble(6, txtRate.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtRate.getText().trim()));
    	        pst.setInt(7, txtHours.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtHours.getText().trim()));
    	        pst.setDouble(8, txtSalary.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtSalary.getText().trim()));

    	        int rowsAffected = pst.executeUpdate();
    	        if (rowsAffected > 0) {
    	            JOptionPane.showMessageDialog(this, "New employee added successfully!");
    	            clearFields(); // Empties out fields for the next entry
    	        }
    	        
    	        pst.close();
    	    } catch (java.sql.SQLIntegrityConstraintViolationException ex) {
    	        JOptionPane.showMessageDialog(this, "Error: Employee ID '" + id + "' already exists inside the system!");
    	    } catch (Exception ex) {
    	        JOptionPane.showMessageDialog(this, "Insertion Error: " + ex.getMessage());
    	        ex.printStackTrace();
    	    }
    	});

        // ❌ DELETE RECORD OPERATION
        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please provide an Employee ID to delete.");
                return;
            }

            // Confirm before erasing data completely
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to permanently delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; 
            }

            String query = "DELETE FROM employee WHERE employeeid = ?";

            try {
                java.sql.Connection con = dao.DBConnection.getConnection();
                java.sql.PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, id);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee record deleted successfully!");
                    clearFields(); // Clears all text boxes after successful deletion
                } else {
                    JOptionPane.showMessageDialog(this, "No record found to delete.");
                }
                
                pst.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Delete Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 🔍 SEARCH RECORD OPERATION (Placeholder template action layout hook)
        btnSearch.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please provide an ID to locate.");
                return;
            }
            
            String query = "SELECT * FROM employee WHERE employeeid = ?";
            
            try {
                // 1. Establish direct connection right here in your file
                java.sql.Connection con = dao.DBConnection.getConnection();
                java.sql.PreparedStatement pst = con.prepareStatement(query);
                pst.setString(1, id);
                java.sql.ResultSet rs = pst.executeQuery();
                
                if (rs.next()) {
                    // 2. Fetch directly from database columns into your text fields
                    txtName.setText(rs.getString("name"));
                    txtDept.setText(rs.getString("department"));
                    txtType.setText(rs.getString("type"));
                    txtExp.setText(String.valueOf(rs.getInt("year_exp")));
                    txtRate.setText(String.valueOf(rs.getDouble("hourly_rate")));
                    txtHours.setText(String.valueOf(rs.getInt("hours_worked")));
                    txtSalary.setText(String.valueOf(rs.getDouble("salary")));
                    
                    JOptionPane.showMessageDialog(this, "Record found successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No record found for ID: " + id);
                    clearFields();
                }
                
                // Close resources safely
                rs.close();
                pst.close();
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Database Connection Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
               // 🔄 UPDATE RECORD OPERATION (Placeholder template action layout hook)
        btnUpdate.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please search for an employee first.");
                return;
            }

            String query = "UPDATE employee SET name=?, department=?, type=?, year_exp=?, hourly_rate=?, hours_worked=?, salary=? WHERE employeeid=?";

            try {
                java.sql.Connection con = dao.DBConnection.getConnection();
                java.sql.PreparedStatement pst = con.prepareStatement(query);

                // Bind the updated text field inputs to the SQL statement
                pst.setString(1, txtName.getText().trim());
                pst.setString(2, txtDept.getText().trim());
                pst.setString(3, txtType.getText().trim());
                pst.setInt(4, Integer.parseInt(txtExp.getText().trim()));
                pst.setDouble(5, Double.parseDouble(txtRate.getText().trim()));
                pst.setInt(6, Integer.parseInt(txtHours.getText().trim()));
                pst.setDouble(7, Double.parseDouble(txtSalary.getText().trim()));
                pst.setString(8, id);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee record updated successfully!");
                }
                
                pst.close();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // 📊 MANAGE ALLOWANCES SYSTEM (Launches Pop-up Modal Window)
        btnAddAllowance.addActionListener(e -> {
            String currentId = txtId.getText().trim();
            if (currentId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Validation Notice: Enter an Employee ID before allocating allowances.");
                return;
            }
            
            // Generate pop-up overlay instance referenced to our parent framework tree top-element
            JFrame rootFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            AllowanceDialog allowanceDialog = new AllowanceDialog(rootFrame, currentId);
            allowanceDialog.setVisible(true);
        });

        // ⬅ BACK ROUTING NAVIGATION
        btnBack.addActionListener(e -> {
            if (mainContentPanel != null && mainContentPanel.getLayout() instanceof CardLayout) {
                CardLayout layoutEngine = (CardLayout) mainContentPanel.getLayout();
                // "DashboardView" should match the string key register label name your friend set in HomeFrame
                layoutEngine.show(mainContentPanel, "DashboardView"); 
            } else {
                JOptionPane.showMessageDialog(this, "Navigation Configuration Warning: Base container layout structure missing CardLayout reference.");
            }
        });
    }

    private void clearFields() {
        txtId.setText("");   txtName.setText("");    txtDept.setText("");  txtType.setText("");
        txtExp.setText("");  txtRate.setText("");   txtHours.setText(""); txtSalary.setText("");
    }

    // ==========================================
    // INNER SUB-CLASS: THE ALLOWANCE POP-UP WINDOW
    // ==========================================
    private class AllowanceDialog extends JDialog {
        private JTable table;
        private DefaultTableModel model;
        private JButton btnAddRow, btnDeleteRow, btnBack,btnSave;

        public AllowanceDialog(JFrame parent, String empId) {
            super(parent, "Allowance Ledger Sheet - Employee ID: " + empId, true); // Modal window toggle active
            setSize(600, 380);
            setLocationRelativeTo(parent);
            setLayout(new BorderLayout(10, 10));

            // Config tracking grid variables 
            String[] headers = {"Employee ID (Locked)", "Allowance Description Category", "Total Value (₹)"};

            // Custom table structure override preventing cell mutation operations selectively across tracking index target 0
            model = new DefaultTableModel(headers, 0) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return col != 0; // Completely locks Employee ID column
                }
            };
            

            table = new JTable(model);
            table.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(
                        JTable table, Object value, boolean isSelected,
                        boolean hasFocus, int row, int column) {

                    java.awt.Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);

                    if (row == 0) {
                        c.setBackground(new java.awt.Color(220, 235, 255));
                        c.setForeground(java.awt.Color.BLACK);
                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                        c.setForeground(java.awt.Color.BLACK);
                    }

                    return c;
                }
            });
            table.setFillsViewportHeight(true);
            table.setRowHeight(22);
            add(new JScrollPane(table), BorderLayout.CENTER);
            java.awt.Color headerNavy = new java.awt.Color(11, 26, 49);
            table.getTableHeader().setUI(new javax.swing.plaf.basic.BasicTableHeaderUI());
            table.getTableHeader().setBackground(headerNavy);
            table.getTableHeader().setForeground(java.awt.Color.WHITE);
            table.getTableHeader().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));

            // Populate workspace default structure row entry bound to current context scope target parameter context index
            model.addRow(new Object[]{empId, "Basic Allowance Item", "0.00"});

            // Dialog Window Controls
            JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            btnAddRow = new JButton("Add Row");
            btnDeleteRow = new JButton("Delete Row");
            btnBack = new JButton("Back");
            JButton btnsave=new JButton("Save");
            // Using your updated variable name

            // Define the uniform button size for the pop-up window
            java.awt.Color primaryNavy = new java.awt.Color(11, 26, 49);
            java.awt.Dimension popupBtnSize = new java.awt.Dimension(130, 35);

            // 1. Style the Navy "Add  Row" Button
            btnAddRow.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnAddRow.setBackground(primaryNavy);
            btnAddRow.setForeground(java.awt.Color.WHITE);
            btnAddRow.setPreferredSize(new Dimension(130,28));
            btnAddRow.setFocusPainted(false);

            // 2. Style the Flat-White "Delete Row" Button
            btnDeleteRow.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnDeleteRow.setBackground(primaryNavy);
            btnDeleteRow.setForeground(Color.WHITE);
            btnDeleteRow.setOpaque(true);
            btnDeleteRow.setBorderPainted(false);
            btnDeleteRow.setFocusPainted(false);
            
            btnSave=new javax.swing.JButton("Save");
            btnSave.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnSave.setBackground(primaryNavy);
            btnSave.setForeground(Color.WHITE);
            btnSave.setFocusPainted(false);
            btnSave.setOpaque(true);
            btnSave.setBorderPainted(false);
            btnSave.setPreferredSize(new Dimension(130,28));
            
            

            btnBack.setUI(new javax.swing.plaf.basic.BasicButtonUI());
            btnBack.setBackground(primaryNavy);
            btnBack.setForeground(Color.WHITE);
            btnBack.setOpaque(true);
            btnBack.setBorderPainted(false);
            btnBack.setFocusPainted(false);
            

            // 4. Add them to the layout bar panel cleanly
            bar.add(btnAddRow); 
            bar.add(btnDeleteRow);
            bar.add(btnSave);
            bar.add(btnBack);
            
            add(bar, BorderLayout.SOUTH);
            
            // Sheet interaction triggers actions configuration bindings
btnAddRow.addActionListener(ev -> model.addRow(new Object[]{empId, "", "0.00"}));
            
            btnDeleteRow.addActionListener(ev -> {
                int rowIdx = table.getSelectedRow();
                if (rowIdx != -1) {
                    model.removeRow(rowIdx);
                } else {
                    JOptionPane.showMessageDialog(this, "Select an allowance row from the table first.");
                }
            });
            btnSave.addActionListener(e -> {

                try {
                    java.sql.Connection con = dao.DBConnection.getConnection();

                    String sql = "INSERT INTO employee_allowances(emp_id, allowance_name, amount) VALUES (?, ?, ?)";
                    java.sql.PreparedStatement pst = con.prepareStatement(sql);

                    for (int i = 0; i < model.getRowCount(); i++) {

                        pst.setString(1, model.getValueAt(i, 0).toString());
                        pst.setString(2, model.getValueAt(i, 1).toString());
                        pst.setDouble(3,
                            Double.parseDouble(model.getValueAt(i, 2).toString()));

                        pst.addBatch();
                    }

                    pst.executeBatch();

                    JOptionPane.showMessageDialog(this,
                            "Allowance saved successfully!");

                    pst.close();
                    con.close();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error : " + ex.getMessage());
                }
            });

            btnBack.addActionListener(ev -> dispose());
        }
    } // <-- This closes the AllowanceDialog inner class
    
 
//Paste this right before the last closing brace of the class
public static void main(String[] args) {
	try {
		javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
	}catch(Exception e) {
		e.printStackTrace();
	}
    javax.swing.JFrame frame = new javax.swing.JFrame("Modify Employee Test");
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    frame.setSize(900, 600);
    frame.setLocationRelativeTo(null);
    
    // This creates and adds your panel directly
    frame.add(new ModifyEmp()); 
    
    frame.setVisible(true);
}}