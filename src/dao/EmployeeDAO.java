package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Employee;

public class EmployeeDAO {
	// ADD EMPLOYEE
	public void addEmployee(int employeeId,
			                String name,
			                String department,
			                String type,
			                int year_exp,
			                double hourly_rate,
			                int hourly_worked,
			                double salary) {
		System.out.println("Employee Added Successfully");
		System.out.println("Employee ID:"+employeeId);
		System.out.println("Employee name:"+name);
		System.out.println("Department:"+department);
		System.out.println("Employee Type:"+type);
		System.out.println("Experience:"+year_exp);
		System.out.println("Hourly_Rate:"+hourly_rate);
		System.out.println("Hourly_Worked:"+hourly_worked);
		System.out.println("Salary:"+salary);
	}
		
		// DELETE EMPLOYEE
		public void deleteEmployee(int employeeId) {
			System.out.println("Employee deleted successfully");
			System.out.println("Employee ID:"+employeeId);
			
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
}