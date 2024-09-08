import org.example.CustomMath;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CustomMathTest {
    int x;
    int y;
    int expectedResult;
    Class<? extends Throwable> expectedException;

    public CustomMathTest(int x, int y, int expectedResult, Class<? extends Throwable> expectedException) {
        this.x = x;
        this.y = y;
        this.expectedResult = expectedResult;
        this.expectedException = expectedException;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> parametersForDivision() {
        return Arrays.asList(new Object[][]{
                {4, 2, 2, null},
                {2, 0, 0, IllegalArgumentException.class},
                {10,2, 5, null},
                {5, 0, 0, IllegalArgumentException.class},
                {34, 0, 0, IllegalArgumentException.class}
        });
    }

    @BeforeClass
    public static void setUpClass() {
    }
    @AfterClass
    public static void tearDownClass() {
    }
    /**
     * Test of sum method, of class CustomMath.
     */
    @Test
    public void testSum() {
        System.out.println("sum");
        int x = 0;
        int y = 0;
        int expResult = 0;
        int result = CustomMath.sum(x, y);
        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
    }
    /**
     * Test of division method, of class CustomMath.
     */
    @Test
    public void testDivision() {
        System.out.println("division");
        int result = CustomMath.division(x, y);
        fail("The test case is a prototype.");
    }
    /**
     * Test of main method, of class CustomMath.
     */

    @Test
    public void testDivisionByZero() {
        System.out.println("Running test with x=" + x + ", y=" + y);
        if (expectedException != null) {
            try {
                CustomMath.division(x, y);
                fail("Expected exception: " + expectedException.getName());
            } catch (Throwable e) {
                assertEquals(expectedException, e.getClass());
            }
        } else {
            int result = CustomMath.division(x, y);
            assertEquals(expectedResult, result);
        }
    }

    @Test
    public void testZeroAsDivisor() {
        if (y == 0) {
            assertTrue("Zero can't be a divisor", y == 0);
        } else {
            assertFalse("Your divisor is good", y == 0);
        }
    }

    @Test
    public void testPerfectSquareRoot() {
        assertTrue(CustomMath.isPerfectSquare(9));
        assertFalse(CustomMath.isPerfectSquare(33));
    }

    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        CustomMath.main(args);
        fail("The test case is a prototype.");
    }
}
