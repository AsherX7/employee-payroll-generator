package model;
/*
 * Base Employee class
 * Stores common employee details.
 */

public class Employee {

    // Attributes
    private int empId;
    private String name;
    private String employeeType;
    private double basicSalary;
    private int daysWorked;
   
    // Default Constructor
    public Employee() {

    }

    // Parameterized Constructor
    public Employee(int empId, String name, String employeeType,
                    double basicSalary,int daysWorked) {

        this.empId = empId;
        this.name = name;
        this.employeeType = employeeType;
        this.basicSalary = basicSalary;
        this.daysWorked=daysWorked;
       
    }

    // Getters and Setters

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

    // Display Employee Details
    public void displayEmployeeDetails() {

        System.out.println("Employee ID : " + empId);
        System.out.println("Name        : " + name);
        System.out.println("Type        : " + employeeType);
        System.out.println("Salary      : " + basicSalary);
        System.out.println("Days Worked : " + daysWorked);
    }
}
