package dao;

import java.sql.*;
import java.util.ArrayList;

public class AllowanceDAO {

    Connection conn = DBConnection.getConnection();

    // =========================
    // EMPLOYEE ALLOWANCE PART
    // =========================

    public void addAllowance(String empId, String allowanceName, double amount) {

        try {

            String sql = "INSERT INTO employee_allowances (emp_id, allowance_name, amount) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empId);
            ps.setString(2, allowanceName);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAllowance(String empId, String allowanceName, double amount) {

        try {

            String sql = "UPDATE employee_allowances SET allowance_name=?, amount=? WHERE emp_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, allowanceName);
            ps.setDouble(2, amount);
            ps.setString(3, empId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getTotalAllowance(String empId) {

        try {

            String sql = "SELECT SUM(amount) AS total FROM employee_allowances WHERE emp_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<String[]> getAllowances(String empId) {

        ArrayList<String[]> list = new ArrayList<>();

        try {

            String sql = "SELECT allowance_name, amount FROM employee_allowances WHERE emp_id=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new String[] {
                        rs.getString("allowance_name"),
                        String.valueOf(rs.getDouble("amount"))
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean exists(String empId, String allowanceName) {

        try {

            String sql = "SELECT * FROM employee_allowances WHERE emp_id=? AND allowance_name=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empId);
            ps.setString(2, allowanceName);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // PAYROLL TAX PART
    // =========================

    public void addTax(String empType, String taxName, double taxPer, String taxType) {

        try {

            String sql = "INSERT INTO payroll (emp_type, tax_name, tax_per, tax_type) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empType);
            ps.setString(2, taxName);
            ps.setDouble(3, taxPer);
            ps.setString(4, taxType);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateTaxPer(String taxName, double percentage) {

        try {

            String sql = "UPDATE payroll SET tax_per=? WHERE tax_name=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setDouble(1, percentage);
            ps.setString(2, taxName);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void deleteTax(String taxName) {

        try {

            String sql = "DELETE FROM payroll WHERE tax_name=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, taxName);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getTaxPercentage(String taxName, String empType) {

        try {

            String sql = "SELECT tax_per FROM payroll WHERE tax_name=? AND emp_type=?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, taxName);
            ps.setString(2, empType);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("tax_per");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public ResultSet getAllTaxes() {

        try {

            String sql = "SELECT * FROM payroll";

            PreparedStatement ps = conn.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}