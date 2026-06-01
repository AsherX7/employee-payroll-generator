package model;
/*
 * Base Employee class
 * Stores common employee details.
 */

public class Employee {

    // Attributes
    private int empId;
    private String name;
    private String department;
    private String employeeType;
    private int yearsExp;
    private double hourlyRate;
    private int hoursWorked;
    private double salary;
    
   
    // Default Constructor
    public Employee() {

    }

    // Parameterized Constructor
    public Employee(int empId, String name, String department,String employeeType,
    		int yearsExp,double hourlyRate, int hoursWorked,double salary) {

        this.empId = empId;
        this.name = name;
        this.department=department;
        this.employeeType = employeeType;
        this.yearsExp=yearsExp;
        this.hourlyRate=hourlyRate;
        this.hoursWorked=hoursWorked;
        this.salary = salary;
        
       
    }

    // Getters and Setters

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String getEmpType() {
        return employeeType;
    }

    public void setEmpType(String employeeType) {
        this.employeeType = employeeType;
    }
    
    public void setYearsExp(int yearsExp) {
    	this.yearsExp=yearsExp;
    }
    
    public int getYearsExp() {
    	return yearsExp;
    }

    public double getHrate() {
        return hourlyRate;
    }

    public void setHrate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
    public int getHwork() {
        return hoursWorked;
    }

    public void setHwork(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
    
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

	public String getDep() {
		return department;
	}

	public void setD(String department) {
		this.department = department;
	}
}

    
