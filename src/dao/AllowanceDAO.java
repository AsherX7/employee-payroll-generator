package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllowanceDAO {

    Connection conn = DBConnection.getConnection();

    // FOR ADDING ALLOWANCES TO THE TABLE
    public void addAllowance(int empId, String allowanceName, double amount) {

        String sql = "INSERT INTO employee_allowances "
                   + "(emp_id, allowance_name, amount) "
                   + "VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, empId);
            ps.setString(2, allowanceName);
            ps.setDouble(3, amount);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FOR UPDATING TABLE
    public void updateAllowance(int empId, String allowanceName, double amount) {

        String sql = "UPDATE employee_allowances "
                   + "SET allowance_name=?, amount=? "
                   + "WHERE emp_id=?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, allowanceName);
            ps.setDouble(2, amount);
            ps.setInt(3, empId);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TO GET TOTAL ALLOWANCE FOR AN EMPLOYEE
    public double getTotalAllowance(int empId) {

        String sql =
            "SELECT SUM(amount) AS total " +
            "FROM employee_allowances " +
            "WHERE emp_id = ?";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, empId);

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
}