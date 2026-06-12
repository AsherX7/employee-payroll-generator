package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Employee;

public class EmployeeDAO {
	// ADD EMPLOYEE
		public void addEmployee(String employeeId,
                String name,
                String department,
                String type,
                int year_exp,
                double hourly_rate,
                int hours_worked,
                double salary) {
try {
Connection con=DBConnection.getConnection();
String query="INSERT INTO employee(employeeid,name,department,type,year_exp,hourly_rate,hours_worked,salary) VALUES(?,?,?,?,?,?,?,?)";
PreparedStatement pst=con.prepareStatement(query);
pst.setString(1, employeeId);
pst.setString(2,name);
pst.setString(3,department);
pst.setString(4,type);
pst.setInt(5,year_exp);
pst.setDouble(6,hourly_rate);
pst.setInt(7,hours_worked);
pst.setDouble(8,salary);
pst.executeUpdate();
System.out.println("Employee added successfully");
} catch(Exception e) {
e.printStackTrace();
}
}
		
		// DELETE EMPLOYEE
		public void deleteEmployee(String employeeId) {
			try {
				Connection con=DBConnection.getConnection();
				String query=
						"DELETE FROM employee WHERE employeeid=?";
				PreparedStatement pst=con.prepareStatement(query);
				pst.setString(1,employeeId);
				pst.executeUpdate();
				System.out.println("Employee deleted successfully");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//search code
		public Employee searchEmployee(String empId) {
		    Employee emp = null;
		    try {
		    Connection con = DBConnection.getConnection();
		    String query = "SELECT * FROM employee WHERE employeeid=?";
		    PreparedStatement pst = con.prepareStatement(query);
		    pst.setString(1, empId);
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {

		        emp = new Employee();
		        emp.setEmpId(rs.getString("employeeid"));
		        emp.setName(rs.getString("name"));
		        emp.setDep(rs.getString("department"));
		        emp.setEmpType(rs.getString("type"));
		        emp.setYearsExp(rs.getInt("year_exp"));
		        emp.setHrate(rs.getDouble("hourly_rate"));
		        emp.setHwork(rs.getInt("hours_worked"));
		        emp.setSalary(rs.getDouble("salary"));
		    

		    }
		    }catch(Exception e) {
		    	e.printStackTrace();
		    }
		    return emp;
		}
		// Update
		public boolean updateEmployee(Employee emp) {

		    try {

		        Connection con = DBConnection.getConnection();

		        String query = "UPDATE employee SET name=?, department=?, type=?, year_exp=?, hourly_rate=?, hours_worked=?, salary=? WHERE employeeid=?";

		        PreparedStatement pst = con.prepareStatement(query);

		        pst.setString(1, emp.getName());
		        pst.setString(2, emp.getDep());
		        pst.setString(3, emp.getEmpType());
		        pst.setInt(4, emp.getYearsExp());
		        pst.setDouble(5, emp.getHrate());
		        pst.setInt(6, emp.getHwork());
		        pst.setDouble(7, emp.getSalary());
		        pst.setString(8, emp.getEmpId());

		        int rows = pst.executeUpdate();

		        return rows > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}
		public Employee getEmployeeById(String empId) {
			Connection conn= DBConnection.getConnection();
		    Employee emp = null;

		    try {

		        String sql =
		            "SELECT * FROM employee WHERE employeeid=?";

		        PreparedStatement ps =
		            conn.prepareStatement(sql);

		        ps.setString(1, empId);

		        System.out.println("Searching for: " + empId);

		        ResultSet rs = ps.executeQuery();

		        if(rs.next()) {

		            System.out.println("Employee Found!");

		            emp = new Employee();

		            emp.setEmpId(rs.getString("employeeid"));
		            emp.setName(rs.getString("name"));
		            emp.setEmpType(rs.getString("type"));
		            emp.setSalary(rs.getDouble("salary"));
		        }
		        else {

		            System.out.println("No employee found");
		        }

		    } catch(Exception e) {

		        e.printStackTrace();
		    }

		    return emp;
		}
		
		//allowance
		public List<Object[]> getAllowances(String empId) {
		    List<Object[]> list = new ArrayList<>();
		    String sql = "SELECT allowance_name, amount FROM employee_allowances WHERE emp_id = ?";
		    try (Connection con = DBConnection.getConnection();
		         PreparedStatement pst = con.prepareStatement(sql)) {
		        pst.setString(1, empId);
		        try (ResultSet rs = pst.executeQuery()) {
		            while (rs.next()) {
		                list.add(new Object[]{
		                    rs.getString("allowance_name"),
		                    rs.getDouble("amount")
		                });
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return list;
		}
		
		 public double getTotalAllowance(String empId) {
			 Connection conn = DBConnection.getConnection();
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
//validating login credentials
		public boolean validateLogin(String username, String password) {

String sql =
"SELECT * FROM employee_login "
+ "WHERE employeeid=? AND password=?";

try {
	Connection conn=DBConnection.getConnection();
PreparedStatement ps =
   conn.prepareStatement(sql);

ps.setString(1, username);
ps.setString(2, password);

ResultSet rs = ps.executeQuery();

return rs.next();

}catch(Exception e) {

    e.printStackTrace();
}

return false;
}
}