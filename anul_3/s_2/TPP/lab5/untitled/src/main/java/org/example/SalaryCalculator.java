package org.example;

public class SalaryCalculator {
    private double MIN_HOURS_PER_WEEK = 40;
    private double MAX_HOURS_PER_WEEK = 55;
    private double hourlyRate;
    private double workedHours;
    private double salary;

    public SalaryCalculator(double hourlyRate, double workedHours) {
        this.hourlyRate = hourlyRate;
        this.workedHours = workedHours;
    }

    public double calculateSalary() throws RuntimeException {
        if (!isValidNrOfHours()) {
            throw new RuntimeException("Invalid number of hours");
        }

        if (hasMinimumHours()) {
            salary = MIN_HOURS_PER_WEEK * hourlyRate;

            if (hasWorkedOvertime()) {
                salary += calculateOvertimeSalary();
            }

        } else {
            salary = workedHours * hourlyRate;
        }

        return salary;
    }

    private double calculateOvertimeSalary() {
        return (workedHours - MIN_HOURS_PER_WEEK) * calculateOvertimeBonus();
    }

    private double calculateOvertimeBonus() {
        return hourlyRate * 130 / 100;
    }

    private boolean hasMinimumHours() {
        return workedHours >= 40;
    }

    private boolean hasWorkedOvertime() {
        return workedHours > 40;
    }

    private boolean isValidNrOfHours() {
        return workedHours >= 0 && workedHours <= MAX_HOURS_PER_WEEK;
    }



}
