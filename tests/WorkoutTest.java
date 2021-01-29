import main.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private Person p = new Person("test", 1, Goal.muscleGrowth, 2);
    private Routine r = new Routine(p);
    private RoutineGenerator rg = r.getRg();
    private LinkedList<Workout> workouts = rg.getWorkouts();
    private Workout w = workouts.getFirst();

    WorkoutTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    void getPauses() {
        assertEquals(60, w.getPauses());
    }

    @org.junit.jupiter.api.Test
    void getReps() {
        int[] a = new int[2];
        a[0] = 8;
        a[0] = 12;
        assertArrayEquals(a, w.getReps());
    }

    @org.junit.jupiter.api.Test
    void getSet() {
        assertEquals(1, w.getSet());
    }

    @org.junit.jupiter.api.Test
    void exercisesNotNull() {
        for (int i = 0; i < rg.getNumExercises(); i++) {
            assertNotNull(w.getExercises()[i]);
        }
    }
}