package in.ac.tkmit.model;

public class Employee {
		protected int id;
		protected String name;
		protected double basicSalary;
		public Employee(int id,String name,double basicSalary) {
			this.id=id;
			this.name=name;
			this.basicSalary=basicSalary;
			}
		public double calculateSalary() {
			return basicSalary; // Base class: no spcl logic
			
		}
		public void display() {
			System.out.println("ID:"+id+",Name:"+name+",Salary:"+calculateSalary());
		}

	}


