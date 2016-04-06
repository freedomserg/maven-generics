package com.goit.projects.generics.employee;

import com.goit.projects.generics.Validator;

public class EmployeeValidatorImpl implements Validator<Employee> {
    public static final int BOUNDARY_SALARY = 2000;

    @Override
    public boolean isValid(Employee employee) {
        String name = employee.getName();
        double salary = employee.getMonthlySalary();

        return (!(name == null || name.equals("")) && !(salary <= 0 || salary > BOUNDARY_SALARY));
    }
}
