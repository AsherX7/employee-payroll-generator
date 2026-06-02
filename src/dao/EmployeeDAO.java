package dao;

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
}
