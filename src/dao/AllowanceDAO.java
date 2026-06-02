package dao;

import java.sql.PreparedStatement;
public class AllowanceDAO{
public void addAllowance(
        int empId,
        String allowanceName,
        double amount) {
    Connection conn = DBConnection.getConnection();
    String sql =
        "INSERT INTO employee_allowances " +
        "(emp_id, allowance_name, amount) " +
        "VALUES (?, ?, ?)";

    PreparedStatement ps =
        conn.prepareStatement(sql);

    ps.setInt(1, empId);
    ps.setString(2, allowanceName);
    ps.setDouble(3, amount);

    ps.executeUpdate();
}
}