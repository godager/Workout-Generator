import main.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person p = new Person("test", 1, Goal.muscleGrowth, 2);

    @org.junit.jupiter.api.Test
    void experience() {
        assertEquals(1, p.getExperience());
        p.setExperience(2);
        assertEquals(2, p.getExperience());
    }

    @org.junit.jupiter.api.Test
    void getWorkoutsPerWeek() {
        assertEquals(2, p.getWorkoutsPerWeek());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("test", p.getName());
    }

    @org.junit.jupiter.api.Test
    void getGoal() {
        assertEquals(Goal.muscleGrowth, p.getGoal());
    }

    @org.junit.jupiter.api.Test
    void toStringtest() {
        assertEquals("test", p.getName());
    }
}