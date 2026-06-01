
package ui;
import java.util.Scanner;


/**
 * 
 */
public class Main {

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
		String id= sc.nextLine();
		sc.nextLine();
		
		System.out.println("Enter the Employee Name: ");
		String name=sc.nextLine();
		
		System.out.println("Enter the Department: ");
		String department=sc.nextLine();
		System.out.println("Enter the  Employee Experience(years): ");
		int experience=sc.nextInt();
		
		System.out.println("Select Employee Type");
		System.out.println("1.Full Time");
		System.out.println("2.Part Time");
		System.out.println("Enter Choice:");
		int choice=sc.nextInt();
		double basicSalary=0;
		double allowance=0;
		double deduction=0;
		int hoursWorked=0;
		double hourlyRate=0;
		double salary=0;
	
		if(choice==1) {
		System.out.println("Basic Salary");
		basicSalary=sc.nextDouble();
			System.out.println("Enter Allowance:");
			 allowance=sc.nextDouble();
			System.out.println("Enter Deduction:");
			 deduction=sc.nextDouble();
			System.out.println("Enter the Salary:");
			 salary=sc.nextDouble();
			
			
			
		}
		else if(choice==2) {
			
			System.out.println("Enter hours Worked:");
			 hoursWorked=sc.nextInt();
			System.out.println("Enter Hourly Rate:");
			hourlyRate=sc.nextDouble();
			System.out.println("Enter the Salary:");
			 salary=sc.nextDouble();
			
			
		}
		else {
			System.out.println("Invalid Choice");
			return;
		}
		
		
		System.out.println("\n Employee Added Successfully");
		System.out.println("\n============== EMPLOYEE DETAILS=================");
		System.out.println("Employee id:"+id);
		System.out.println("Employee Name:"+name);
		System.out.println("Deapartment:"+department);
		System.out.println("Experience:"+experience + "years");
		
		if(choice==1) {
			System.out.println("Employee Type:Full Time");
			System.out.println("Basic Salary:"+ basicSalary);
			System.out.println("Allowance:"+ allowance);
			System.out.println("Deduction:"+ deduction);
		}
		else {
			System.out.println("Employee Type: Part Time");
			System.out.println("Hours Worked:"+hoursWorked);
			System.out.println("Hourly Rate:"+hourlyRate);
		}
		System.out.println("Salary:"+ salary);
	}
}
		
