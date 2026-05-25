/**
 * 
 */
package in.ac.tkmit.payroll;
import java.util.Scanner;
/**
 * 
 */
public class EmployeeInput {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		System.out.println("Experience:"+experience + "years");
		System.out.println("Salary:"+salary);
		

	}

}

