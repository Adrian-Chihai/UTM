import org.example.SalaryCalculator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SalaryCalculatorTest {

    @Test
    public void testMinHoursPerWeek() {
        SalaryCalculator salaryCalculator = new SalaryCalculator((double) 16, (double) 40);
        double calculatedSalary = salaryCalculator.calculateSalary();
        assertEquals(640, calculatedSalary, 0.01);
    }

    @Test
    public void testOvertimeHours() {
        SalaryCalculator salaryCalculator = new SalaryCalculator(20, 50);
        double calculatedSalary = salaryCalculator.calculateSalary();
        assertEquals(1060, calculatedSalary, 0.01);
    }

    @Test
    public void testInvalidHoursPerWeek_BellowMin() {
        SalaryCalculator salaryCalculator = new SalaryCalculator(14, -4);
        try {
            salaryCalculator.calculateSalary();
            fail("Expected RuntimeException for invalid hours");
        } catch (RuntimeException e) {
            assertEquals("Invalid number of hours", e.getMessage());
        }
    }

    @Test
    public void testInvalidHoursPerWeek_ExceedMax() {
        SalaryCalculator salaryCalculator = new SalaryCalculator(14, 70);
        try {
            salaryCalculator.calculateSalary();
            fail("Expected RuntimeException for invalid hours");
        } catch (RuntimeException e) {
            assertEquals("Invalid number of hours", e.getMessage());
        }
    }
}
