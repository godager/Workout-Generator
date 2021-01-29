import main.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RoutineTest {

    private Person p = new Person("test", 1, Goal.muscleGrowth, 2);
    private Routine r = new Routine(p);
    private RoutineGenerator rg = r.getRg();

    RoutineTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    void getTimesPerWeek() {
        assertEquals(2, r.getTimesPerWeek());
    }
}