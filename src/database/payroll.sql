CREATE SCHEMA `payroll_schema` ;



//database1

use payroll_schema;
create table employee(
employeeid varchar(10) primary key,
name varchar(20) not null,
department varchar(25) not null,
type varchar(20),
year_exp int,
hourly_rate int,
hours_worked int,
Salary int
);
iNSERT INTO employee
VALUES
('E01','Febi Shaji','HR','Full-time',2,0,0,25000),
('E02','Stephy Wilson','IT','Part-time',1,200,50,10000),
('E03','Sophie Edward','Finance','Full-time',4,0,0,40000),
('E04','Meera Krishna','Marketing','Part-time',2,150,40,6000),
('E05','NikhiL Dev','IT','Full-time',3,0,0,35000),
('E06','Johin P','HR','Part-time',1,180,45,8100);
UPDATE employee
SET salary = 30000
WHERE employeeid = 'E01';
DELETE FROM employee
WHERE employeeid = 'E06';
ALTER TABLE `payroll_schema`.`employee` 
CHANGE COLUMN `hourly_rate` `hourly_rate` DOUBLE NULL DEFAULT NULL ,
CHANGE COLUMN `Salary` `Salary` DOUBLE NULL DEFAULT NULL ;

//database2

CREATE TABLE payroll_schema.payroll (
	emp_type VARCHAR(10) NOT NULL,tax_name VARCHAR(30) NULL,tax_per DOUBLE NULL,tax_type VARCHAR(20)
NULL);
INSERT INTO payroll_schema.payroll
(emp_type,tax_name,tax_per,tax_type)
VALUES
("FULLTIME","INCOME TAX",10.0,"DEDUCTION"),
("FULLTIME","PROFESSIONAL TAX",2.0,"DEDUCTION"),
("FULLTIME","PROVIDENT FUND",12.0,"DEDUCTION"),
("FULLTIME","ESI",1,"DEDUCTION"),
("FULLTIME","HEALTH INSURANCE",1.5,"DEDUCTION"),
("FULLTIME","PENSION CONTRIBUTION",5.0,"DEDUCTION"),
("FULLTIME","HRA",20.0,"ALLOWANCE"),
("FULLTIME","INTERNET ALLOWANCE",2.0,"ALLOWANCE"),
("PARTTIME","INCOME TAX",5.0,"DEDUCTION"),
("PARTTIME","PROFESSIONAL TAX",1.0,"DEDUCTION"),
("PARTTIME","INTERNET ALLOWANCE",2.0,"ALLOWANCE");

//database3

CREATE TABLE `payroll_schema`.`employee_allowances` (
  `emp_id` VARCHAR(10) NOT NULL,
  `allowance_name` VARCHAR(45) NULL,
  `amount` DOUBLE NULL);
  INSERT INTO employee_allowances
VALUES
('E01','Overtime',5000),
('E02','DA',500),
('E03','MA',1000),
('E04','MA',1000),
('E04','TA',800);

// database4

CREATE TABLE employee_login (
    employeeid VARCHAR(10) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    company_id INT NOT NULL,
    FOREIGN KEY (employeeid) REFERENCES employee(employeeid),
    FOREIGN KEY (company_id) REFERENCES company(company_id)
);
INSERT INTO employee_login VALUES
('E01','1234',1),
('E02','1111',2),
('E03','2222',1),
('E04','3333',2),
('E05','4444',1);

// database5

use payroll_schema;
CREATE TABLE company (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100)
);

INSERT INTO company (company_name, email, password)
VALUES
('PayTrack Pvt Ltd','admin@pay.com','admin123'),
('ABC Tech','abc@tech.com','abc123');
