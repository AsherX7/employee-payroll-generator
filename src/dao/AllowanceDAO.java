package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class AllowanceDAO {

    Connection conn = DBConnection.getConnection();

    // FOR ADDING ALLOWANCES TO THE TABLE
    public void addAllowance(String i, String allowanceName, double amount) {

        String sql = "INSERT INTO employee_allowances "
                   + "(emp_id, allowance_name, amount) "
                   + "VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, i);
            ps.setString(2, allowanceName);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FOR UPDATING TABLE
    public void updateAllowance(String empId, String allowanceName, double amount) {

        String sql = "UPDATE employee_allowances "
                   + "SET allowance_name=?, amount=? "
                   + "WHERE emp_id=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, allowanceName);
            ps.setDouble(2, amount);
            ps.setString(3, empId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TO GET TOTAL ALLOWANCE FOR AN EMPLOYEE
    public double getTotalAllowance(String empId) {

        String sql =
            "SELECT SUM(amount) AS total " +
            "FROM employee_allowances " +
            "WHERE emp_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, empId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    // GET TAX PERCENTAGE
    public double getTaxPercentage(String taxName, String empType) {

        String sql =
            "SELECT tax_per " +
            "FROM payroll " +
            "WHERE tax_name = ? AND emp_type = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, taxName);
            ps.setString(2, empType);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("tax_per");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
    // to update tax percentage in payroll table
    public boolean updateTaxPer(
            String taxName,
            double percentage) {

        try {

            String sql =
                "UPDATE payroll SET tax_per=? WHERE tax_name=?";

            PreparedStatement ps =
                conn.prepareStatement(sql);

            ps.setDouble(1, percentage);
            ps.setString(2, taxName);

            int rows = ps.executeUpdate();

            return rows > 0;

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // used in allowanceframe
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
    
    //exist
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
}