import org.example.TelephoneBill;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TelephoneBillTest {

    @Test
    public void testShortCall() {
        LocalDateTime start = LocalDateTime.of(2023, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 4, 1, 10, 10);
        double expected = 0.50;
        double actual = TelephoneBill.calculateCost(start, end);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testLongCall() {
        LocalDateTime start = LocalDateTime.of(2023, 4, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2023, 4, 1, 11, 0);
        double expected = 5.00;
        double actual = TelephoneBill.calculateCost(start, end);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testSpringDST() {
        LocalDateTime start = LocalDateTime.of(2023, 3, 12, 1, 45);
        LocalDateTime end = LocalDateTime.of(2023, 3, 12, 3, 15);
        double expected = 8.00;
        double actual = TelephoneBill.calculateCost(start, end);
        assertEquals(expected, actual, 0.001);
    }

    @Test
    public void testFallDST() {
        LocalDateTime start = LocalDateTime.of(2023, 11, 5, 0, 30);
        LocalDateTime end = LocalDateTime.of(2023, 11, 5, 2, 30);
        double expected = 11.00;
        double actual = TelephoneBill.calculateCost(start, end);
        assertEquals(expected, actual, 0.001);
    }

}
