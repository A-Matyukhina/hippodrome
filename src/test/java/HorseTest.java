import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

    Horse horse;

    @BeforeAll
    public static void init() {
        System.out.println("Tests started");
    }

    @AfterAll
    public static void end() {
        System.out.println("Tests finished");
    }

    @Test
    public void tryFirstArgumentWithNull() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(null, 12.2, 10.23);
                }
        );
    }

    @Test
    public void tryFirstArgumentWithNullExceptionMessage() {
        Throwable messageException = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(null, 12.2, 10.23);
                }
        );
        assertEquals("Name cannot be null.", messageException.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r\n"})
    public void testConstructorWithBlankName(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> new Horse(name, 10));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    ", "\t", "\n"})
    public void testConstructorWithBlankNameThrowsException(String name) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 10.0, 0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Hello, -7.2, 8",
            "Hi, -5.1, 5.0"
    })
    public void trySecondArgumentIsNegative(String str, double a, double b) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(str, a, b);
                }
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Hello, -7.2, 8",
            "Hi, -5.1, 5.0"
    })
    public void trySecondArgumentIsNegativeExceptionMessage(String str, double a, double b) {
        Throwable exceptionMessage = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(str, a, b);
                }
        );
        assertEquals("Speed cannot be negative.", exceptionMessage.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "Hello, 7.2, -8.1",
            "Hi, 5.1, -5.0"
    })
    public void tryThirdArgumentIsNegative(String str, double a, double b) {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(str, a, b);
                }
        );
    }

    @ParameterizedTest
    @CsvSource({
            "Hello, 7.2, -8.1",
            "Hi, 5.1, -5.0"
    })
    public void tryThirdArgumentIsNegativeExceptionMessage(String str, double a, double b) {
        Throwable exceptionMessage = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Horse(str, a, b);
                }
        );
        assertEquals("Distance cannot be negative.", exceptionMessage.getMessage());
    }

    @Test
    public void shouldReturnTheFirstArgumentByGetName() {
        horse = new Horse("Masha", 2.2, 3.3);
        String actual = horse.getName();
        assertEquals("Masha", actual);
    }

    @Test
    public void shouldReturnTheSecondArgumentByGetSpeed() {
        horse = new Horse("Masha", 2.2, 3.3);
        double actual = horse.getSpeed();
        assertEquals(2.2, actual);
    }

    @Test
    public void shouldReturnTheThirdArgumentByGetDistance() {
        horse = new Horse("Masha", 2.2, 3.3);
        double actual = horse.getDistance();
        assertEquals(3.3, actual);
    }

    @Test
    public void shouldReturnZeroWithoutThirdArgumentByGetDistance() {
        horse = new Horse("Masha", 2.2);
        double actual = horse.getDistance();
        assertEquals(0, actual);
    }

    @Test
    public void moveShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> mockedHorse = mockStatic(Horse.class)) {
            horse = new Horse("Test", 5.0, 0.0);
            horse.move();
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1, 1.5",
            "1, 2, 2.5",
            "2.2, 3.3, 4.4",
            "1.1, 2.2, 2.75",
    })
    void methodAssignsDistanceValueCalculatedUsingTheFormulaFromTheMoveMethod(double speed, double distance, double expectedDistance) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            horse = new Horse("testName", speed, distance);
            horse.move();
            assertEquals(expectedDistance, horse.getDistance());
        }
    }
}

