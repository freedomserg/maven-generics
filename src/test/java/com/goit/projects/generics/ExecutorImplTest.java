package com.goit.projects.generics;

import com.goit.projects.generics.employee.*;
import com.goit.projects.generics.exceptions.*;
import com.goit.projects.generics.shape.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ExecutorImplTest {

    @Test
    public void testEmployeeExecutorWithAnonymosValidators() {
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

        Assert.assertEquals("Wrong number of valid results", 4, employeeExecutor.getValidResults().size());
        Assert.assertEquals("Wrong salary",  Double.valueOf(840.0),
                Double.valueOf(employeeExecutor.getValidResults().get(1).getMonthlySalary()));
    }

    @Test(expected = TasksAreNotExecutedException.class)
    public void testEmployeeExecutorWithTasksAreNotExecutedException() throws Exception {
        Executor<Employee> employeeExecutor = new ExecutorImpl<>();
        Task<Employee> task1 = new EmployeeTaskImpl(new FixedPaymentEmployee("Rick", 1000));

        employeeExecutor.addTask(task1);

        List<Employee> validResults = employeeExecutor.getValidResults();
    }

    @Test
    public void testShapeExecutorWithValidator() {
        Executor<Shape> shapeExecutor = new ExecutorImpl<>();
        Validator<Shape> validator = new ShapeValidatorImpl();
        Task<Shape> task1 = new ShapeTaskImpl(new Circle(4));
        Task<Shape> task2 = new ShapeTaskImpl(new Circle(50));
        Task<Shape> task3 = new ShapeTaskImpl(new Rectangle(5, 15));
        Task<Shape> task4 = new ShapeTaskImpl(new Rectangle(2, 12));
        Task<Shape> task5 = new ShapeTaskImpl(new Rectangle(-1, 9));
        Task<Shape> task6 = new ShapeTaskImpl(new Circle(-5));

        shapeExecutor.addTask(task1);
        shapeExecutor.addTask(task2);
        shapeExecutor.addTask(task3);
        shapeExecutor.addTask(task4, validator);
        shapeExecutor.addTask(task5);
        shapeExecutor.addTask(task6);

        shapeExecutor.execute();

        Assert.assertEquals("Wrong number of invalid results", 3, shapeExecutor.getInvalidResults().size());
        Assert.assertEquals("Wrong square", Double.valueOf(24.0),
                Double.valueOf(shapeExecutor.getValidResults().get(2).getSquare()));
    }

    @Test(expected = AllTasksWereExecutedException.class)
    public void testShapeExecutorWithAllTasksWereExecutedException() throws Exception {
        Executor<Shape> shapeExecutor = new ExecutorImpl<>();
        Task<Shape> task1 = new ShapeTaskImpl(new Circle(7));

        shapeExecutor.addTask(task1, result -> result.getSquare() >= 0);

        shapeExecutor.execute();

        shapeExecutor.addTask(task1);
    }
}