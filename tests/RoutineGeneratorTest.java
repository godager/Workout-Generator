import main.*;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RoutineGeneratorTest {

    private Person p = new Person("test", 1, Goal.muscleGrowth, 2);
    private Routine r = new Routine(p);
    private RoutineGenerator rg = r.getRg();
    private LinkedList<Workout> workouts = rg.getWorkouts();

    RoutineGeneratorTest() throws SQLException {
    }

    @org.junit.jupiter.api.Test
    void findNumExercises() {
        assertEquals(9, rg.getNumExercises());
    }

    @org.junit.jupiter.api.Test
    void WorkoutsNotNull() {
        Iterator<Workout> iterator = workouts.iterator();
        for (int i = 0; i < p.getWorkoutsPerWeek(); i++) {
            assertNotNull(iterator.next());
        }
    }

    @org.junit.jupiter.api.Test
    void sets() {
        Iterator<Workout> iterator = workouts.iterator();
        while (iterator.hasNext()) {
            Workout curWorkout = iterator.next();
            assertEquals(1, curWorkout.getSet()[0]);
        }
    }

    @org.junit.jupiter.api.Test
    void reps() {
        Iterator<Workout> iterator = workouts.iterator();
        while (iterator.hasNext()) {
            Workout curWorkout = iterator.next();
            int[] reps = new int [2];
            reps[0] = 8;
            reps[1] = 12;
            assertArrayEquals(reps, curWorkout.getReps());
        }
    }

    @org.junit.jupiter.api.Test
    void pauses() {
        Iterator<Workout> iterator = workouts.iterator();
        while (iterator.hasNext()) {
            Workout curWorkout = iterator.next();
            assertEquals(60, curWorkout.getPauses());
        }
    }

    @org.junit.jupiter.api.Test
    void exerciseNotNull() {
        Iterator<Workout> iterator = workouts.iterator();

        while (iterator.hasNext()) {
            Workout curWorkout = iterator.next();
            Exercise[] exercises = curWorkout.getExercises();

            for (int i = 0; i < exercises.length; i++) {
                assertNotNull(exercises[i]);
            }
        }
    }
}