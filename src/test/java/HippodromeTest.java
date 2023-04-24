import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

    List<Horse> horses = new ArrayList<>();
    Hippodrome hippodrome;

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
                    new Hippodrome(null);
                }
        );
    }

    @Test
    public void tryFirstArgumentWithNullExceptionMessage() {
        Throwable messageException = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Hippodrome(null);
                }
        );
        assertEquals("Horses cannot be null.", messageException.getMessage());
    }

    @Test
    public void tryFirstArgumentWithEmptyListOfHorses() {
        assertThrows(IllegalArgumentException.class,
                () -> {
                    new Hippodrome(horses);
                }
        );
    }

    @Test
    public void tryFirstArgumentWithEmptyListOfHorsesExceptionMessage() {
        Throwable messageException = assertThrows(IllegalArgumentException.class,
                () -> {
                    new Hippodrome(horses);
                }
        );
        assertEquals("Horses cannot be empty.", messageException.getMessage());
    }

    @Test
    public void tryToReturnTheListByGetHorsesWhichLikeListFromArgument() {
        for (int i = 0; i < 30; i++) {
            horses.add(i, new Horse("Horse" + i, 2, 4));
        }
        hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void methodCallsTheMoveMethodForAllHorses() {
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(horse);
        }
        hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (int i = 0; i < 50; i++) {
            Mockito.verify(horses.get(i)).move();
        }
    }

@Test
void tryFindsHorseWhichHasLargestDistanceValue() {
    Horse expectedHorse = new Horse("testName", 1, 100);
    for (int i = 0; i < 30; i++) {
        horses.add(new Horse("testName" + i, 1, i));
    }
    horses.add(expectedHorse);
    hippodrome = new Hippodrome(horses);
    Horse actualHorse = hippodrome.getWinner();
    assertEquals(expectedHorse, actualHorse);
}
}
