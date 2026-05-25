
package in.ac.tkmit.payroll;
import java.util.Scanner;

public class EmployeeInput {

	public static void main(String[] args) {
		// EMPLOYEE PAYROLL GENERATOR UI AREA
		Scanner sc=new Scanner(System.in);
		System.out.println("=========================================================");
		System.out.println("                   ADD EMPLOYEE");
		System.out.println("=========================================================");
		// Input Fields
		System.out.print("Enter the Employee id: ");
		System.out.println(" ");
		int id= sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter the Employee Name: ");
		String name=sc.nextLine();
		
		System.out.println("Enter the Department: ");
		String department=sc.nextLine();
		
		System.out.println("Select Employee Type");
		System.out.println("1.Full Time");
		System.out.println("2.Part Time");
		System.out.println("Enter Choice:");
		int choice=sc.nextInt();
		sc.nextLine();
		String employeeType;
		if(choice==1) {
			employeeType="Full Time";
		}
		else if(choice==2) {
			employeeType="Part Time";
		}
		else {
			employeeType="Invalid";
		}
		System.out.println("Enter the  Employee Experience(years): ");
		int experience=sc.nextInt();
		sc.nextLine();
		
		System.out.println("Enter Salary: ");
		double salary=sc.nextDouble();
		
		
		System.out.println("\n Employee Added Successfully");
		System.out.println("\n============== EMPLOYEE DETAILS=================");
		System.out.println("Employee id:"+id);
		System.out.println("Employee Name:"+name);
		System.out.println("Deapartment:"+department);
		System.out.println("Employee Type:"+employeeType);
		System.out.println("Experience:"+experience + "years");
		System.out.println("Salary:"+salary);
	
		
		

	}

}
