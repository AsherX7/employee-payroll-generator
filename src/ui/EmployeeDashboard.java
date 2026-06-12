package ui;

import dao.DBConnection;
import dao.EmployeeDAO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.*;
import java.util.List;

public class EmployeeDashboard extends JFrame {
    private static final long serialVersionUID = 1L;

    // ── Palette ────────────────────────────────────────────────────────────────
    private static final Color NAVY        = new Color(15,  35,  70);
    private static final Color NAVY_MID    = new Color(25,  55, 110);
    private static final Color ACCENT_GOLD = new Color(255, 193,   7);
    private static final Color WHITE       = Color.WHITE;
    private static final Color BG_PAGE     = new Color(235, 239, 245);
    private static final Color CARD_GREEN  = new Color(30,  150,  90);
    private static final Color CARD_RED    = new Color(185,  50,  40);
    private static final Color CARD_BLUE   = new Color(30,  100, 185);
    private static final Color TABLE_HDR   = new Color(20,  45,  90);
    private static final Color ROW_ALT     = new Color(245, 248, 252);

    // ── Fonts ──────────────────────────────────────────────────────────────────
    private static final Font FONT_COMPANY = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_CARD_V  = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_CARD_T  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_INFO_B  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_TABLE   = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BTN     = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD,  12);

    // ── State ──────────────────────────────────────────────────────────────────
    private final String currentEmpId;
    private String companyName = "—";

    private JLabel lblName, lblDept, lblType, lblBasic, lblEmpId;
    private JLabel lblGrossVal, lblDeductVal, lblNetVal;
    private JLabel lblCompanyHeader;

    // FIX 2: Models declared once here, never recreated
    private DefaultTableModel allowanceModel;
    private DefaultTableModel deductionModel;

    // ══════════════════════════════════════════════════════════════════════════
    public EmployeeDashboard(String empId) {
        this.currentEmpId = empId;

        setTitle("PayTrack — Employee Dashboard");
        setSize(1200, 720);
        setMinimumSize(new Dimension(1000, 640));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG_PAGE);

        root.add(buildTopBar(),    BorderLayout.NORTH);
        root.add(buildBody(),      BorderLayout.CENTER);
        root.add(buildBottomBar(), BorderLayout.SOUTH);

        setContentPane(root);
        setVisible(true);

        SwingUtilities.invokeLater(this::loadEmployeeData);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  TOP BAR
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), 0, NAVY_MID));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(ACCENT_GOLD);
                g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                g2.dispose();
            }
        };
        bar.setPreferredSize(new Dimension(0, 72));
        bar.setLayout(new BorderLayout());

        // Left — brand + company name
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 22, 0));
        left.setOpaque(false);

        JLabel brand = new JLabel(
            "<html><span style='color:#FFFFFF;font-size:20pt;font-weight:bold;font-family:Segoe UI'>PAY</span>"
          + "<span style='color:#FFC107;font-size:20pt;font-weight:bold;font-family:Segoe UI'>TRACK</span></html>");
        left.add(brand);

        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 32));
        sep.setForeground(new Color(255, 255, 255, 60));
        left.add(sep);

        lblCompanyHeader = new JLabel("Loading…");
        lblCompanyHeader.setFont(FONT_COMPANY);
        lblCompanyHeader.setForeground(new Color(200, 215, 240));
        left.add(lblCompanyHeader);

        // Right — emp chip + logout
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 0));
        right.setOpaque(false);

        JLabel empChip = new JLabel("EMP ID:  " + currentEmpId);
        empChip.setFont(new Font("Segoe UI", Font.BOLD, 12));
        empChip.setForeground(ACCENT_GOLD);
        empChip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 193, 7, 120), 1, true),
            BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        right.add(empChip);

        JButton logoutBtn = makeIconButton("x Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        right.add(logoutBtn);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(18, 0, 18, 0));
        wrapper.add(left,  BorderLayout.WEST);
        wrapper.add(right, BorderLayout.EAST);

        bar.add(wrapper, BorderLayout.CENTER);
        return bar;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  BODY
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildBody() {
        JPanel body = new JPanel(new BorderLayout(0, 14));
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(18, 22, 0, 22));

        body.add(buildInfoStrip(), BorderLayout.NORTH);

        JPanel mid = new JPanel(new BorderLayout(0, 14));
        mid.setOpaque(false);
        mid.add(buildSummaryCards(), BorderLayout.NORTH);
        mid.add(buildTables(),       BorderLayout.CENTER);

        body.add(mid, BorderLayout.CENTER);
        return body;
    }

    // ── Info strip ─────────────────────────────────────────────────────────────
    private JPanel buildInfoStrip() {
        JPanel strip = new JPanel(new GridLayout(1, 5, 1, 0));
        strip.setBackground(NAVY);
        strip.setPreferredSize(new Dimension(0, 52));

        lblName  = addInfoCell(strip, "Name",        "—",          false);
        lblEmpId = addInfoCell(strip, "Employee ID",  currentEmpId, false);
        lblDept  = addInfoCell(strip, "Department",  "—",          false);
        lblType  = addInfoCell(strip, "Type",        "—",          false);
        lblBasic = addInfoCell(strip, "Basic Salary","—",           true);

        return strip;
    }

    private JLabel addInfoCell(JPanel strip, String caption, String initial, boolean last) {
        JPanel cell = new JPanel(new GridLayout(2, 1, 0, 0));
        cell.setBackground(NAVY);
        cell.setBorder(BorderFactory.createCompoundBorder(
            last ? BorderFactory.createEmptyBorder()
                 : BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255, 255, 255, 40)),
            BorderFactory.createEmptyBorder(5, 14, 5, 14)));

        JLabel cap = new JLabel(caption.toUpperCase());
        cap.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        cap.setForeground(new Color(160, 185, 230));

        JLabel val = new JLabel(initial);
        val.setFont(FONT_INFO_B);
        val.setForeground(WHITE);

        cell.add(cap);
        cell.add(val);
        strip.add(cell);
        return val;
    }

    // ── Summary cards ──────────────────────────────────────────────────────────
    private JPanel buildSummaryCards() {
        JPanel row = new JPanel(new GridLayout(1, 3, 14, 0));
        row.setOpaque(false);
        row.setPreferredSize(new Dimension(0, 95));

        lblGrossVal  = new JLabel("Rs. 0.00");
        lblDeductVal = new JLabel("Rs. 0.00");
        lblNetVal    = new JLabel("Rs. 0.00");

        row.add(buildCard("GROSS SALARY",     lblGrossVal,  CARD_GREEN, "↑"));
        row.add(buildCard("TOTAL DEDUCTIONS", lblDeductVal, CARD_RED,   "↓"));
        row.add(buildCard("NET TAKE-HOME",    lblNetVal,    CARD_BLUE,  "+"));

        return row;
    }

    private JPanel buildCard(String title, JLabel valLabel, Color bg, String icon) {
        JPanel card = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, bg, 0, getHeight(), bg.darker()));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        card.setOpaque(false);

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI", Font.BOLD, 22));
        iconLbl.setForeground(new Color(255, 255, 255, 70));
        iconLbl.setBounds(14, 10, 40, 32);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(FONT_CARD_T);
        titleLbl.setForeground(new Color(220, 235, 255));
        titleLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        titleLbl.setBounds(0, 12, 280, 16);

        valLabel.setFont(FONT_CARD_V);
        valLabel.setForeground(WHITE);
        valLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valLabel.setBounds(0, 34, 280, 36);

        card.add(iconLbl);
        card.add(titleLbl);
        card.add(valLabel);

        card.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                int w = card.getWidth();
                titleLbl.setBounds(0, 12, w - 14, 16);
                valLabel.setBounds(0, 34, w - 14, 44);
            }
        });

        return card;
    }

    // ── Tables ─────────────────────────────────────────────────────────────────
    private JPanel buildTables() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 14, 0));
        panel.setOpaque(false);

        allowanceModel = new DefaultTableModel(new String[]{"Allowance", "Amount (Rs.)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
                   };
                   deductionModel = new DefaultTableModel(new String[]{"Deduction", "Amount (Rs.)"}, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
         
        panel.add(styledTable(allowanceModel, "EARNINGS & ALLOWANCES", CARD_GREEN));
        panel.add(styledTable(deductionModel, "DEDUCTIONS & TAXES",    CARD_RED));

        return panel;
    }

    private JScrollPane styledTable(DefaultTableModel model, String sectionTitle, Color accent) {
        JTable table = new JTable(model) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? WHITE : ROW_ALT);
                    c.setForeground(new Color(30, 40, 60));
                } else {
                    c.setBackground(new Color(200, 220, 255));
                    c.setForeground(NAVY);
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                return c;
            }
        };
        table.setFont(FONT_TABLE);
        table.setRowHeight(26);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 226, 235));
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(200, 220, 255));

        JTableHeader hdr = table.getTableHeader();
        hdr.setBackground(TABLE_HDR);
        hdr.setForeground(Color.BLACK);
        hdr.setFont(new Font("Segoe UI", Font.BOLD, 11));
        hdr.setPreferredSize(new Dimension(0, 30));
        hdr.setReorderingAllowed(false);

        // FIX 5: Right-align column index 1 (Amount), not 3
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(1).setCellRenderer(right);

        JLabel secLabel = new JLabel("  " + sectionTitle);
        secLabel.setFont(FONT_SECTION);
        secLabel.setForeground(accent);
        secLabel.setBackground(new Color(248, 250, 253));
        secLabel.setOpaque(true);
        secLabel.setPreferredSize(new Dimension(0, 28));
        secLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 226, 235)));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 230), 1));
        sp.getViewport().setBackground(WHITE);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(secLabel, BorderLayout.NORTH);
        wrapper.add(sp,       BorderLayout.CENTER);

        JScrollPane outer = new JScrollPane();
        outer.setViewportView(wrapper);
        outer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, accent),
            BorderFactory.createLineBorder(new Color(210, 218, 230), 1)));
        outer.getViewport().setBackground(WHITE);
        return outer;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  BOTTOM BAR
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(225, 230, 240));
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 210, 225)));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 8));
        left.setOpaque(false);
        JLabel status = new JLabel("Payroll data is calculated based on the latest approved rates.");
        status.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        status.setForeground(new Color(120, 135, 160));
        left.add(status);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 8));
        right.setOpaque(false);

        JButton refreshBtn = makeSolidButton("Refresh",new Color(80, 100, 140), WHITE);
        JButton payslipBtn = makeSolidButton("Download Payslip", NAVY, ACCENT_GOLD);

        refreshBtn.addActionListener(e -> loadEmployeeData());
        payslipBtn.addActionListener(e -> generatePayslip());

        right.add(refreshBtn);
        right.add(payslipBtn);

        bar.add(left,  BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DATA LOADING
    // ══════════════════════════════════════════════════════════════════════════
    private void loadEmployeeData() {
        // Clear tables — models are reused, NOT recreated
        allowanceModel.setRowCount(0);
        deductionModel.setRowCount(0);

        try (Connection con = DBConnection.getConnection()) {

            // FIX 1: Declare ALL variables before try blocks
            double basicSalary = 0;
            String companyId   = null;
            String empType     = null;   // declared here so section 4 can use it

            // 1. Employee core details
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT e.*, el.company_id " +
                    "FROM employee e " +
                    "JOIN employee_login el ON e.employeeid = el.employeeid " +
                    "WHERE e.employeeid = ?")) {
                pst.setString(1, currentEmpId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (!rs.next()) {
                        JOptionPane.showMessageDialog(this, "Employee record not found.");
                        return;
                    }
                    basicSalary = rs.getDouble("salary");
                    companyId   = rs.getString("company_id");
                    empType     = rs.getString("type");   // assigned here
                    lblName.setText(rs.getString("name"));
                    lblDept.setText(rs.getString("department"));
                    lblType.setText(rs.getString("type"));
                    lblBasic.setText("Rs. " + String.format("%,.2f", basicSalary));
                }
            }

            // 2. Company name
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT company_name FROM company WHERE company_id = ?")) {
                pst.setString(1, companyId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        companyName = rs.getString("company_name");
                        lblCompanyHeader.setText(companyName);
                    }
                }
            }

            // 3. Allowances
            allowanceModel.addRow(new Object[]{
                "Basic Salary",
                String.format("%,.2f", basicSalary)});

            EmployeeDAO empDao = new EmployeeDAO();
            List<Object[]> allowances = empDao.getAllowances(currentEmpId);
            for (Object[] row : allowances) {
                allowanceModel.addRow(new Object[]{
                    row[0],
                    String.format("%,.2f", (double) row[1])});
            }

            double totalAllowances = empDao.getTotalAllowance(currentEmpId);
            double grossSalary     = basicSalary + totalAllowances;

            // 4. Deductions
            double totalDeductions = 0;
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT * FROM payroll WHERE UPPER(REPLACE(emp_type, '-', '')) = UPPER(REPLACE(?, '-', ''))")) {
                pst.setString(1, empType);
                try (ResultSet rs2 = pst.executeQuery()) {
                    while (rs2.next()) {
                        String taxType = rs2.getString("tax_type");
                        double taxPer  = rs2.getDouble("tax_per");

                        double amt = taxType.equalsIgnoreCase("BASIC") ? (basicSalary * taxPer / 100.0)
                                   : taxType.equalsIgnoreCase("GROSS") ? (grossSalary * taxPer / 100.0)
                                   : taxPer;

                        amt = Math.round(amt * 100.0) / 100.0;
                        totalDeductions += amt;

                        deductionModel.addRow(new Object[]{
                            rs2.getString("tax_name"),
                            String.format("%,.2f", amt)});
                    }
                }
            }

            // 5. Update summary cards
            double net = grossSalary - totalDeductions;
            lblGrossVal.setText("Rs. "  + String.format("%,.2f", grossSalary));
            lblDeductVal.setText("Rs. " + String.format("%,.2f", totalDeductions));
            lblNetVal.setText("Rs. "    + String.format("%,.2f", net));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading payroll data:\n" + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PAYSLIP GENERATION + FILE DOWNLOAD
    // ══════════════════════════════════════════════════════════════════════════
    private void generatePayslip() {
        if (lblName.getText().equals("—")) {
            JOptionPane.showMessageDialog(this, "No payroll data loaded yet.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("════════════════════════════════════════════════\n");
        sb.append("                   P A Y T R A C K\n");
        sb.append("               Salary Payslip\n");
        sb.append("════════════════════════════════════════════════\n");
        sb.append(String.format("  Company      : %s%n", companyName));
        sb.append(String.format("  Employee ID  : %s%n", currentEmpId));
        sb.append(String.format("  Name         : %s%n", lblName.getText()));
        sb.append(String.format("  Department   : %s%n", lblDept.getText()));
        sb.append(String.format("  Type         : %s%n", lblType.getText()));
        sb.append(String.format("  Basic Salary : %s%n", lblBasic.getText()));
        sb.append("────────────────────────────────────────────────\n");
        sb.append("  EARNINGS\n");
        sb.append("────────────────────────────────────────────────\n");

        // FIX 4: Read columns 0 and 1 (not 1 and 3)
        for (int i = 0; i < allowanceModel.getRowCount(); i++) {
            sb.append(String.format("  %-28s Rs. %s%n",
                allowanceModel.getValueAt(i, 0),
                allowanceModel.getValueAt(i, 1)));
        }

        sb.append("────────────────────────────────────────────────\n");
        sb.append("  DEDUCTIONS\n");
        sb.append("────────────────────────────────────────────────\n");

        for (int i = 0; i < deductionModel.getRowCount(); i++) {
            sb.append(String.format("  %-28s Rs. %s%n",
                deductionModel.getValueAt(i, 0),
                deductionModel.getValueAt(i, 1)));
        }

        sb.append("════════════════════════════════════════════════\n");
        sb.append(String.format("  Gross Salary     : %s%n", lblGrossVal.getText()));
        sb.append(String.format("  Total Deductions : %s%n", lblDeductVal.getText()));
        sb.append(String.format("  NET TAKE-HOME    : %s%n", lblNetVal.getText()));
        sb.append("════════════════════════════════════════════════\n");
        sb.append("  Generated by PayTrack Payroll System\n");

        JTextArea txt = new JTextArea(sb.toString());
        txt.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txt.setEditable(false);
        txt.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scroll = new JScrollPane(txt);
        scroll.setPreferredSize(new Dimension(560, 400));

        int choice = JOptionPane.showOptionDialog(
            this, scroll, "Payslip Preview",
            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null,
            new String[]{"Download as .txt", "Close"}, "Download as .txt");

        if (choice == 0) {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("Payslip_" + currentEmpId + ".txt"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                    fw.write(sb.toString());
                    JOptionPane.showMessageDialog(this,
                        "Payslip saved to:\n" + fc.getSelectedFile().getAbsolutePath(),
                        "Saved", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Error saving file:\n" + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  BUTTON HELPERS
    // ══════════════════════════════════════════════════════════════════════════
    private JButton makeSolidButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? bg.brighter() : bg);
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
        btn.setPreferredSize(new Dimension(170, 34));
        return btn;
    }

    private JButton makeIconButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(new Color(200, 215, 240));
        btn.setBackground(new Color(255, 255, 255, 0));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(WHITE); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(new Color(200, 215, 240)); }
        });
        return btn;
    }

    // ── TEST ENTRY POINT (remove before final release) ─────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new EmployeeDashboard("E01"));
    }
}