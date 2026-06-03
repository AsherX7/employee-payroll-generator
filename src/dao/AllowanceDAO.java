package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class AllowanceDAO{
	Connection conn = DBConnection.getConnection();
	// FOR ADDING ALLOWANCES TO THE TABLE
public void addAllowance(
        int empId,
        String allowanceName,
        double amount) {
    
    String sql =
        "INSERT INTO employee_allowances " +
        "(emp_id, allowance_name, amount) " +
        "VALUES (?, ?, ?)";

    PreparedStatement ps =conn.prepareStatement(sql);

    ps.setInt(1, empId);
    ps.setString(2, allowanceName);
    ps.setDouble(3, amount);

    ps.executeUpdate();
}
// FOR UPDATING TABLE
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
// TO GET TOTAL ALLOWANCE FOR AN EMPLOYEE
public static double gettotalAllowance(int empId)
         {
    String sql =
        "SELECT SUM(allowance_amount) FROM  employee_allowances WHERE empID= ?" ;

    PreparedStatement ps =
        conn.prepareStatement(sql);

    ps.setInt(1, empId);
    ResultSet rs =ps.executeQuery();
    if(rs.next()) {
    	return rs.getDouble("SUM(allowance_amount)");
    }
   
         }

// total fulltimers & parttimers deduction separately

public static  double gettaxPercentage(String tax_name,String emp_type)
{
String sql =
"SELECT tax_per FROM  payroll WHERE tax_name= ? and emp_type= ?" ;

PreparedStatement ps =
conn.prepareStatement(sql);

ps.setString(1, tax_name);
ps.setString(2, emp_type );

ResultSet rs =ps.executeQuery();
if(rs.next()) {
	return rs.getDouble("tax_per");
}

}

}