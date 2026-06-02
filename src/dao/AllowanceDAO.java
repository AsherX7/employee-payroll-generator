package dao;

import java.sql.PreparedStatement;
public class AllowanceDAO{
	Connection conn = DBConnection.getConnection();
public void addAllowance(
        int empId,
        String allowanceName,
        double amount) {
    
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
public void updateAllowance(
        int empId,
        String allowanceName,
        double amount) {
    
    String sql =
        "UPDATE employee_allowances " +
        "SET allowance_name=?,amount=? WHERE emp_id=?";

    PreparedStatement ps =
        conn.prepareStatement(sql);

    ps.setString(1, allowanceName); 
    ps.setDouble(2, amount);         
    ps.setInt(3, empId);            


    ps.executeUpdate();
}
}