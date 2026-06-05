package service;

import dao.AllowanceDAO;

import model.Employee;

public class PayrollService{
	// SALARY CALCULATION
	AllowanceDAO dao = new AllowanceDAO();
	public double calculateNetSalary(Employee emp) {
	
		// salary from Employee in basicSalary
		
		double basicSalary= emp.getSalary();
		
		// to get total allowance
		
		double allowanceTotal= dao.getTotalAllowance(emp.getEmpId());
		
		// adding allowance to basic salary
		
		double grossSalary= basicSalary + allowanceTotal;
		
		//deductions
		
	    double deductionTotal=0;
	    double altaX=0;
	    
	    if (emp.getEmpType().toUpperCase().equals("FULLTIME")) {
	    	double IT=basicSalary*(dao.getTaxPercentage("INCOME TAX","FULLTIME"))/100;
	    	double PT=basicSalary*(dao.getTaxPercentage("PROFESSIONAL TAX","FULLTIME"))/100;
	    	double PF=basicSalary*(dao.getTaxPercentage("PROVIDENT FUND","FULLTIME"))/100;
	    	double ESI=basicSalary*(dao.getTaxPercentage("ESI","FULLTIME"))/100;
	    	double HI=basicSalary*(dao.getTaxPercentage("HEALTH INSURANCE","FULLTIME"))/100;
	    	double PC=basicSalary*(dao.getTaxPercentage("PENSION CONTRIBUTION","FULLTIME"))/100;
	    	//ADDING
	    	deductionTotal=IT+PT+PF+ESI+HI+PC;
	    	// ALLOWANCE
	    	double HRA=basicSalary*(dao.getTaxPercentage("HRA","FULLTIME"))/100;
	    	double IA=basicSalary*(dao.getTaxPercentage("INTERNET ALLOWANCE","FULLTIME"))/100;
             altaX=HRA+IA;
	    }
	    else
	    {
	    	//deduction
	    	double IT=basicSalary*(dao.getTaxPercentage("INCOME TAX","PARTTIME"))/100;
	    	double PT=basicSalary*(dao.getTaxPercentage("PROFESSIONAL TAX","PARTTIME"))/100;
	    	deductionTotal=IT+PT;
	    	//allowance
	    	double IA=basicSalary*(dao.getTaxPercentage("INTERNET ALLOWANCE","PARTTIME"))/100;
	    	altaX=IA;
	    }
	    double netSalary=grossSalary-deductionTotal+altaX;
		return netSalary;
	    }
	    
		}

