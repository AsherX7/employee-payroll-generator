
package utils;

public class Validation {
	private Validation() {}

    // Employee ID validation
    public static boolean isValidEmployeeId(String empId) {
        return empId != null && empId.matches("E\\d+");
    }

    // Name validation
    public static boolean isValidName(String name) {
        return name != null && name.matches("[A-Za-z]+(\\s[A-Za-z]+)*");
    }

    // Salary validation
    public static boolean isValidSalary(double salary) {
        return salary >= 1000.0 && salary <= 10_000_000.0;
    }

    // Hours worked validation
    public static boolean isValidHoursWorked(int hours) {
        return hours >= 0 && hours <= 168;
    }

    // Username validation
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("[A-Za-z0-9_]{3,20}");
    }
}
