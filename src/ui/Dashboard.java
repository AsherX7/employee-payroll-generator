package ui;

import dao.DBConnection;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.*;
import java.util.*;
import java.util.List;

public class Dashboard extends JPanel {
    private static final long serialVersionUID = 1L;

    // ── Palette ────────────────────────────────────────────────────────────────
    private static final Color NAVY        = new Color(15,  35,  70);
    private static final Color NAVY_MID    = new Color(25,  55, 110);
    private static final Color ACCENT_GOLD = new Color(255, 193,   7);
    private static final Color WHITE       = Color.WHITE;
    private static final Color BG_PAGE     = new Color(235, 239, 245);
    private static final Color CARD_GREEN  = new Color(30,  150,  90);
    private static final Color CARD_BLUE   = new Color(30,  100, 185);
    private static final Color CARD_PURPLE = new Color(120,  60, 180);
    private static final Color CARD_ORANGE = new Color(210, 100,  30);
    private static final Color TEXT_DARK   = new Color(25,  40,  75);
    private static final Color TEXT_MUTED  = new Color(110, 125, 155);
    private static final Color BORDER_CLR  = new Color(210, 218, 230);

    // ── Fonts ──────────────────────────────────────────────────────────────────
    private static final Font FONT_TITLE   = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_CARD_V  = new Font("Segoe UI", Font.BOLD,  26);
    private static final Font FONT_CARD_T  = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_SECTION = new Font("Segoe UI", Font.BOLD,  14);
    private static final Font FONT_BODY    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_SMALL   = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_BOLD_SM = new Font("Segoe UI", Font.BOLD,  12);

    // ── Pie chart colours (one per department) ─────────────────────────────────
    private static final Color[] PIE_COLORS = {
        new Color( 52, 152, 219),  // blue
        new Color( 46, 204, 113),  // green
        new Color(231,  76,  60),  // red
        new Color(155,  89, 182),  // purple
        new Color(230, 126,  34),  // orange
        new Color( 26, 188, 156),  // teal
        new Color(241, 196,  15),  // yellow
        new Color(149, 165, 166),  // grey
    };

    // ── State ──────────────────────────────────────────────────────────────────
    private final int companyId;

    // Live data holders
    private String companyName  = "Loading…";
    private String companyEmail = "—";
    private int    totalEmp     = 0;
    private int    totalDept    = 0;
    private long   totalSalary  = 0;
    private double avgSalary    = 0;

    // Dept → count for pie chart
    private final java.util.LinkedHashMap<String, Integer> deptMap = new java.util.LinkedHashMap<>();

    // Recent activities list
    private final List<String[]> activities = new ArrayList<>();  // {description, timestamp}

    // Labels updated after data load
    private JLabel lblCompanyName, lblCompanyEmail, lblWelcome;
    private JLabel lblEmpVal, lblDeptVal, lblSalaryVal, lblAvgVal;
    private PieChartPanel piePanel;
    private JPanel legendPanel;
    private JPanel activitiesListPanel;

    // ══════════════════════════════════════════════════════════════════════════
    public Dashboard(int companyId) {
        this.companyId = companyId;
        setLayout(new BorderLayout(0, 0));
        setBackground(BG_PAGE);

        // Build UI first, then load data into it
        JScrollPane scroll = buildScrollableBody();
        add(scroll, BorderLayout.CENTER);

        SwingUtilities.invokeLater(this::loadAllData);
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  SCROLLABLE BODY
    // ══════════════════════════════════════════════════════════════════════════
    private JScrollPane buildScrollableBody() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG_PAGE);
        body.setBorder(BorderFactory.createEmptyBorder(24, 28, 24, 28));

        // ── Page header ───────────────────────────────────────────────────────
        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setOpaque(false);
        pageHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        lblWelcome = new JLabel("Dashboard");
        lblWelcome.setFont(FONT_TITLE);
        lblWelcome.setForeground(TEXT_DARK);

        lblCompanyName = new JLabel("Loading company…");
        lblCompanyName.setFont(FONT_SMALL);
        lblCompanyName.setForeground(TEXT_MUTED);

        JPanel headerText = new JPanel(new GridLayout(2, 1, 0, 2));
        headerText.setOpaque(false);
        headerText.add(lblWelcome);
        headerText.add(lblCompanyName);
        pageHeader.add(headerText, BorderLayout.WEST);

        // Refresh button
        JButton refreshBtn = solidBtn("Refresh", NAVY, ACCENT_GOLD);
        refreshBtn.addActionListener(e -> loadAllData());
        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        btnWrap.setOpaque(false);
        btnWrap.add(refreshBtn);
        pageHeader.add(btnWrap, BorderLayout.EAST);

        body.add(pageHeader);
        body.add(vgap(16));

        // ── Company info banner ───────────────────────────────────────────────
        body.add(buildCompanyBanner());
        body.add(vgap(20));

        // ── KPI cards ─────────────────────────────────────────────────────────
        body.add(sectionLabel("Overview"));
        body.add(vgap(10));
        body.add(buildKpiRow());        body.add(vgap(20));

        // ── Charts + Activities row ───────────────────────────────────────────
        body.add(sectionLabel("Workforce & Activity"));
        body.add(vgap(10));
        body.add(buildBottomRow());
        body.add(vgap(20));

        JScrollPane sp = new JScrollPane(body);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setBackground(BG_PAGE);
        return sp;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  COMPANY INFO BANNER
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildCompanyBanner() {
        JPanel banner = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, NAVY, getWidth(), 0, NAVY_MID));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                // gold left accent bar
                g2.setColor(ACCENT_GOLD);
                g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        banner.setOpaque(false);
        banner.setPreferredSize(new Dimension(0, 80));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        banner.setLayout(new BorderLayout());

        JPanel inner = new JPanel(new GridLayout(2, 1, 0, 4));
        inner.setOpaque(false);
        inner.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        lblWelcome = new JLabel("Welcome back!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblWelcome.setForeground(WHITE);

        lblCompanyEmail = new JLabel("—");
        lblCompanyEmail.setFont(FONT_SMALL);
        lblCompanyEmail.setForeground(new Color(180, 200, 235));

        inner.add(lblWelcome);
        inner.add(lblCompanyEmail);
        banner.add(inner, BorderLayout.WEST);

        // Right side — company ID chip
        JLabel idChip = new JLabel("Company ID:  " + companyId);
        idChip.setFont(FONT_BOLD_SM);
        idChip.setForeground(ACCENT_GOLD);
        idChip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 193, 7, 100), 1, true),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        JPanel chipWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 24));
        chipWrap.setOpaque(false);
        chipWrap.add(idChip);
        banner.add(chipWrap, BorderLayout.EAST);

        return banner;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  KPI CARDS ROW
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildKpiRow() {

        JPanel row = new JPanel(new GridLayout(1, 4, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 170));

        lblEmpVal = new JLabel("0");
        lblDeptVal = new JLabel("0");
        lblSalaryVal = new JLabel("₹0");
        lblAvgVal = new JLabel("₹0");

        row.add(buildKpiCard("Total Employees", lblEmpVal, CARD_GREEN, "👥"));
        row.add(buildKpiCard("Departments", lblDeptVal, CARD_BLUE, "🏢"));
        row.add(buildKpiCard("Monthly Payroll", lblSalaryVal, CARD_PURPLE, "₹"));
        row.add(buildKpiCard("Average Salary", lblAvgVal, CARD_ORANGE, "~"));

        return row;
    }
    
    private JPanel buildKpiCard(String title, JLabel valLabel, Color bg, String icon) {

        JPanel card = new JPanel(null);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        card.setPreferredSize(new Dimension(250, 140));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLbl.setForeground(new Color(100, 100, 100));

        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valLabel.setForeground(Color.BLACK);

        JLabel detailsLbl = new JLabel("View Details →");
        detailsLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        detailsLbl.setForeground(new Color(0, 102, 204));
        detailsLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        detailsLbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (title.equals("Total Employees")) {
                    JOptionPane.showMessageDialog(
                            Dashboard.this,
                            "Total Employees: " + totalEmp);
                }

                else if (title.equals("Departments")) {
                    JOptionPane.showMessageDialog(
                            Dashboard.this,
                            "Departments: " + totalDept);
                }

                else if (title.equals("Monthly Payroll")) {
                    JOptionPane.showMessageDialog(
                            Dashboard.this,
                            "Monthly Payroll: ₹" +
                            String.format("%,d", totalSalary));
                }

                else if (title.equals("Average Salary")) {
                    JOptionPane.showMessageDialog(
                            Dashboard.this,
                            "Average Salary: ₹" +
                            String.format("%,.0f", avgSalary));
                }
            }
        });

        card.add(titleLbl);
        card.add(valLabel);
        card.add(detailsLbl);

        titleLbl.setBounds(20, 18, 220, 25);
        valLabel.setBounds(20, 55, 220, 40);
        detailsLbl.setBounds(20, 110, 150, 20);

        return card;
    }
        
    // ══════════════════════════════════════════════════════════════════════════
    //  BOTTOM ROW — Pie chart left, Recent activities right
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildBottomRow() {
        JPanel row = new JPanel(new GridLayout(1, 2, 16, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));

        row.add(buildPieChartCard());
        row.add(buildActivitiesCard());

        return row;
    }

    // ── Pie chart card ─────────────────────────────────────────────────────────
    private JPanel buildPieChartCard() {
        JPanel card = whiteCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, CARD_BLUE),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel title = new JLabel("Employee Distribution by Department");
        title.setFont(FONT_SECTION);
        title.setForeground(TEXT_DARK);
        card.add(title, BorderLayout.NORTH);

        // Pie chart + legend side by side
        JPanel chartRow = new JPanel(new BorderLayout(12, 0));
        chartRow.setOpaque(false);

        piePanel = new PieChartPanel(deptMap);
        piePanel.setPreferredSize(new Dimension(220, 220));
        chartRow.add(piePanel, BorderLayout.WEST);

        legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setOpaque(false);
        chartRow.add(legendPanel, BorderLayout.CENTER);

        card.add(chartRow, BorderLayout.CENTER);
        return card;
    }

    // ── Activities card ────────────────────────────────────────────────────────
    private JPanel buildActivitiesCard() {
        JPanel card = whiteCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(3, 0, 0, 0, CARD_GREEN),
            BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        JLabel title = new JLabel("Recent Activities");
        title.setFont(FONT_SECTION);
        title.setForeground(TEXT_DARK);
        card.add(title, BorderLayout.NORTH);

        activitiesListPanel = new JPanel();
        activitiesListPanel.setLayout(new BoxLayout(activitiesListPanel, BoxLayout.Y_AXIS));
        activitiesListPanel.setOpaque(false);

        JScrollPane sp = new JScrollPane(activitiesListPanel);
        sp.setBorder(null);
        sp.getViewport().setBackground(WHITE);
        sp.getVerticalScrollBar().setUnitIncrement(8);
        card.add(sp, BorderLayout.CENTER);

        return card;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  DATA LOADING
    // ══════════════════════════════════════════════════════════════════════════
    private void loadAllData() {
        deptMap.clear();
        activities.clear();

        try (Connection con = DBConnection.getConnection()) {

            // 1. Company info
            try (PreparedStatement pst = con.prepareStatement(
                    "SELECT company_name, email FROM company WHERE company_id = ?")) {
                pst.setInt(1, companyId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        companyName  = rs.getString("company_name");
                        companyEmail = rs.getString("email");
                    }
                }
           
            }
            // 2. KPI stats
                try (PreparedStatement pst = con.prepareStatement(
                        "SELECT COUNT(*) AS total_emp, " +
                        "COUNT(DISTINCT e.department) AS total_dept, " +
                        "SUM(e.salary) AS total_salary, " +
                        "AVG(e.salary) AS avg_salary " +
                        "FROM employee e " +
                        "JOIN employee_login el ON e.employeeid = el.employeeid " +
                        "WHERE el.company_id = ?")) {

                    pst.setInt(1, companyId);

                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            totalEmp = rs.getInt("total_emp");
                            totalDept = rs.getInt("total_dept");
                            totalSalary = rs.getLong("total_salary");
                            avgSalary = rs.getDouble("avg_salary");
                        }
                    }
                }
            // 3. Department breakdown for pie chart
            try (	PreparedStatement pst = con.prepareStatement(
            		"SELECT e.department, COUNT(*) AS cnt " +
            			    "FROM employee e " +
            			    "JOIN employee_login ec ON e.employeeid = ec.employeeid " +
            			    "WHERE ec.company_id = ? " +
            			    "GROUP BY e.department " +
            			    "ORDER BY cnt DESC")){
                   	pst.setInt(1, companyId);
            	try (ResultSet rs = pst.executeQuery()) {
            		while (rs.next()) {
                        deptMap.put(rs.getString("department"), rs.getInt("cnt"));
                    }
                }
            }

            // 4. Recent activities — derived from real DB changes
            //    Shows latest employees added + latest salary records
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                    "SELECT name, department, type, Salary FROM employee ORDER BY employeeid DESC LIMIT 5")) {
                while (rs.next()) {
                    activities.add(new String[]{
                        "Employee \"" + rs.getString("name") + "\" (" + rs.getString("department") + " · " + rs.getString("type") + ") — Salary: ₹" + String.format("%,d", rs.getInt("Salary")),
                        "Registered in system"
                    });
                }
            }

            // 5. Department changes — shows which depts have most recent entries
            try (Statement st = con.createStatement();
                 ResultSet rs = st.executeQuery(
                    "SELECT department, COUNT(*) AS cnt FROM employee GROUP BY department ORDER BY cnt DESC LIMIT 3")) {
                while (rs.next()) {
                    activities.add(new String[]{
                        "Department \"" + rs.getString("department") + "\" has " + rs.getInt("cnt") + " employee(s) on record.",
                        "Current headcount"
                    });
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(this,
                    "Error loading dashboard data:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        // Update UI on EDT
        SwingUtilities.invokeLater(this::refreshUI);
    }

    private void refreshUI() {
        // Company banner
        lblWelcome.setText("Welcome back, " + companyName + "!");
        lblCompanyEmail.setText("email: " + companyEmail);
        lblCompanyName.setText(companyName);

        // KPI cards
        lblEmpVal.setText(String.valueOf(totalEmp));
        lblDeptVal.setText(String.valueOf(totalDept));
        lblSalaryVal.setText("₹" + String.format("%,d", totalSalary));
        lblAvgVal.setText("₹" + String.format("%,.0f", avgSalary));

        // Pie chart
        piePanel.setData(deptMap);
        piePanel.repaint();

        // Legend
        legendPanel.removeAll();
        legendPanel.add(Box.createVerticalStrut(10));
        int i = 0;
        for (Map.Entry<String, Integer> entry : deptMap.entrySet()) {
            Color c = PIE_COLORS[i % PIE_COLORS.length];
            double pct = totalEmp > 0 ? (entry.getValue() * 100.0 / totalEmp) : 0;
            legendPanel.add(buildLegendRow(c, entry.getKey(), entry.getValue(), pct));
            legendPanel.add(Box.createVerticalStrut(6));
            i++;
        }
        legendPanel.revalidate();
        legendPanel.repaint();

        // Activities
        activitiesListPanel.removeAll();
        activitiesListPanel.add(Box.createVerticalStrut(6));
        for (String[] act : activities) {
            activitiesListPanel.add(buildActivityRow(act[0], act[1]));
            activitiesListPanel.add(buildDivider());
        }
        if (activities.isEmpty()) {
            JLabel empty = new JLabel("No recent activity found.");
            empty.setFont(FONT_SMALL);
            empty.setForeground(TEXT_MUTED);
            empty.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
            activitiesListPanel.add(empty);
        }
        activitiesListPanel.revalidate();
        activitiesListPanel.repaint();
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  LEGEND ROW
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildLegendRow(Color color, String dept, int count, double pct) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 26));

        // Colour dot
        JPanel dot = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillOval(0, 3, 12, 12);
                g2.dispose();
            }
        };
        dot.setOpaque(false);
        dot.setPreferredSize(new Dimension(14, 18));

        JLabel deptLbl = new JLabel(dept);
        deptLbl.setFont(FONT_BODY);
        deptLbl.setForeground(TEXT_DARK);

        JLabel pctLbl = new JLabel(count + "  (" + String.format("%.1f", pct) + "%)");
        pctLbl.setFont(FONT_SMALL);
        pctLbl.setForeground(TEXT_MUTED);

        row.add(dot,    BorderLayout.WEST);
        row.add(deptLbl, BorderLayout.CENTER);
        row.add(pctLbl,  BorderLayout.EAST);
        return row;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  ACTIVITY ROW
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel buildActivityRow(String description, String timestamp) {
        JPanel row = new JPanel(new BorderLayout(0, 3));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(8, 4, 4, 4));

        // Green dot indicator
        JPanel indicator = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD_GREEN);
                g2.fillOval(0, 4, 8, 8);
                g2.dispose();
            }
        };
        indicator.setOpaque(false);
        indicator.setPreferredSize(new Dimension(14, 18));

        JPanel text = new JPanel(new GridLayout(2, 1, 0, 2));
        text.setOpaque(false);

        JLabel desc = new JLabel("<html><body style='width:280px'>" + description + "</body></html>");
        desc.setFont(FONT_BODY);
        desc.setForeground(TEXT_DARK);

        JLabel time = new JLabel(timestamp);
        time.setFont(FONT_SMALL);
        time.setForeground(TEXT_MUTED);

        text.add(desc);
        text.add(time);

        row.add(indicator, BorderLayout.WEST);
        row.add(text,      BorderLayout.CENTER);
        return row;
    }

    private JSeparator buildDivider() {
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_CLR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  PIE CHART PANEL
    // ══════════════════════════════════════════════════════════════════════════
    static class PieChartPanel extends JPanel {
        private java.util.LinkedHashMap<String, Integer> data;

        PieChartPanel(java.util.LinkedHashMap<String, Integer> data) {
            this.data = data;
            setOpaque(false);
        }

        void setData(java.util.LinkedHashMap<String, Integer> data) {
            this.data = data;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int total = data.values().stream().mapToInt(Integer::intValue).sum();
            if (total == 0) {
                g2.setColor(new Color(210, 218, 230));
                g2.fillOval(10, 10, getWidth() - 20, getHeight() - 20);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g2.drawString("No data", getWidth() / 2 - 24, getHeight() / 2 + 4);
                g2.dispose();
                return;
            }

            int size    = Math.min(getWidth(), getHeight()) - 20;
            int x       = (getWidth()  - size) / 2;
            int y       = (getHeight() - size) / 2;
            double start = -90;  // start from top

            int i = 0;
            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                double sweep = (entry.getValue() * 360.0) / total;
                Color c = PIE_COLORS[i % PIE_COLORS.length];

                g2.setColor(c);
                g2.fill(new Arc2D.Double(x, y, size, size, start, sweep, Arc2D.PIE));

                // Thin white border between slices
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2));
                g2.draw(new Arc2D.Double(x, y, size, size, start, sweep, Arc2D.PIE));

                start += sweep;
                i++;
            }

            // Centre hole — donut style
            int hole = size / 3;
            int hx   = (getWidth()  - hole) / 2;
            int hy   = (getHeight() - hole) / 2;
            g2.setColor(WHITE);
            g2.fillOval(hx, hy, hole, hole);

            // Centre label
            g2.setColor(TEXT_DARK);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
            String totalStr = String.valueOf(total);
            FontMetrics fm  = g2.getFontMetrics();
            g2.drawString(totalStr,
                getWidth() / 2 - fm.stringWidth(totalStr) / 2,
                getHeight() / 2 + 4);
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
            g2.setColor(TEXT_MUTED);
            String empStr = "employees";
            g2.drawString(empStr,
                getWidth() / 2 - g2.getFontMetrics().stringWidth(empStr) / 2,
                getHeight() / 2 + 16);

            g2.dispose();
        }
    }

    // ══════════════════════════════════════════════════════════════════════════
    //  HELPERS
    // ══════════════════════════════════════════════════════════════════════════
    private JPanel whiteCard() {
        JPanel card = new JPanel();
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createLineBorder(BORDER_CLR, 1));
        return card;
    }

    private Component vgap(int h) {
        return Box.createRigidArea(new Dimension(0, h));
    }

    private JLabel sectionLabel(String text) {
        JLabel lbl = new JLabel(text.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(130, 145, 175));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
        return lbl;
    }

    private JButton solidBtn(String text, Color bg, Color fg) {
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
        btn.setFont(FONT_BOLD_SM);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 32));
        return btn;
    }

    // ── TEST ENTRY POINT ───────────────────────────────────────────────────────
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Dashboard Test");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(1100, 750);
            f.setLocationRelativeTo(null);
            f.setContentPane(new Dashboard(1));  // ← replace 1 with real company_id
            f.setVisible(true);
        });
    }
}