package service;

import dao.AllowanceDAO;

import model.Employee;

public class PayrollService{
	// SALARY CALCULATION
	Employee emp=new Employee();
	public double calculateNetSalary(Employee emp) {
	
		// salary from Employee in basicSalary
		
		double basicSalary= emp.getSalary();
		
		// to get total allowance
		
		double allowanceTotal= AllowanceDAO.gettotalAllowance(emp.getEmpId());
		
		// adding allowance to basic salary
		
		double grossSalary= basicSalary + allowanceTotal;
		
		//deductions
		
	    double deductionTotal=0;
	    double altaX=0;
	    if (emp.getEmpType().toUpperCase().equals("FULLTIME")) {
	    	double IT=basicSalary*(AllowanceDAO.gettaxPercentage("INCOME TAX","FULLTIME"))/100;
	    	double PT=basicSalary*(AllowanceDAO.gettaxPercentage("PROFESSIONAL TAX","FULLTIME"))/100;
	    	double PF=basicSalary*(AllowanceDAO.gettaxPercentage("PROVIDENT FUND","FULLTIME"))/100;
	    	double ESI=basicSalary*(AllowanceDAO.gettaxPercentage("ESI","FULLTIME"))/100;
	    	double HI=basicSalary*(AllowanceDAO.gettaxPercentage("HEALTH INSURANCE","FULLTIME"))/100;
	    	double PC=basicSalary*(AllowanceDAO.gettaxPercentage("PENSION CONTRIBUTION","FULLTIME"))/100;
	    	//ADDING
	    	deductionTotal=IT+PT+PF+ESI+HI+PC;
	    	// ALLOWANCE
	    	double HRA=basicSalary*(AllowanceDAO.gettaxPercentage("HRA","FULLTIME"))/100;
	    	double IA=basicSalary*(AllowanceDAO.gettaxPercentage("INTERNET ALLOWANCE","FULLTIME"))/100;
             altaX=HRA+IA;
	    }
	    else
	    {
	    	//deduction
	    	double IT=basicSalary*(AllowanceDAO.gettaxPercentage("INCOME TAX","PARTTIME"))/100;
	    	double PT=basicSalary*(AllowanceDAO.gettaxPercentage("PROFESSIONAL TAX","PARTTIME"))/100;
	    	deductionTotal=IT+PT;
	    	//allowance
	    	double IA=basicSalary*(AllowanceDAO.gettaxPercentage("INTERNET ALLOWANCE","PARTTIME"))/100;
	    	altaX=IA;
	    }
	    double netSalary=grossSalary-deductionTotal+altaX;
		return netSalary;
	    }
	    
		}

