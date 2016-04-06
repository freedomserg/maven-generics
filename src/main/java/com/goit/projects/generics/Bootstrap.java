package com.goit.projects.generics;

import com.goit.projects.generics.employee.*;

public class Bootstrap {
    public static void main(String[] args) {
        Executor<Employee> employeeExecutor = new ExecutorImpl<>();
        Task<Employee> task1 = new EmployeeTaskImpl(new FixedPaymentEmployee("John", 700));
        Task<Employee> task2 = new EmployeeTaskImpl(new HourlyWageEmployee("Denis", 5));
        Task<Employee> task3 = new EmployeeTaskImpl(new Manager("Greg", 700, 25));
        Task<Employee> task4 = new EmployeeTaskImpl(new Manager("", 800, 15));
        Task<Employee> task5 = new EmployeeTaskImpl(new Manager("Clark", 1500, 50));
        Task<Employee> task6 = new EmployeeTaskImpl(new FixedPaymentEmployee("Ann", 750));

        employeeExecutor.addTask(task1);
        employeeExecutor.addTask(task2, result -> result.getMonthlySalary() <= 900);
        employeeExecutor.addTask(task3, result -> result.getMonthlySalary() <= 900);
        employeeExecutor.addTask(task4, result -> result.getMonthlySalary() <= 900);
        employeeExecutor.addTask(task5, result -> result.getMonthlySalary() <= 900);
        employeeExecutor.addTask(task6);

        employeeExecutor.execute();

        System.out.println("Valid results:");
        for (Employee employee : employeeExecutor.getValidResults()) {
            System.out.println(employee);
        }
        System.out.println();

        System.out.println("Invalid results:");
        for (Employee employee : employeeExecutor.getInvalidResults()) {
            System.out.println(employee);
        }
    }
}
