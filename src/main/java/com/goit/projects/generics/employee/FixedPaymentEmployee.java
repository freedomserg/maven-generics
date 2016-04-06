package com.goit.projects.generics.employee;

public class FixedPaymentEmployee extends Employee {
    private int fixedMonthlyPayment;

    public FixedPaymentEmployee(String name, int fixedMonthlyPayment) {
        super(name);
        this.fixedMonthlyPayment = fixedMonthlyPayment;
    }

    @Override
    public void calculateMonthlySalary() {
        monthlySalary = fixedMonthlyPayment;
    }
}
