import main.Equipment;
import main.Exercise;
import main.Muscle;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private Exercise e = new Exercise("test", "for test", 2, Equipment.bar,
            Muscle.abs, 10.5, "TestType");

    @org.junit.jupiter.api.Test
    void getDifficulty() {
        assertEquals(10.5, e.getDefaultKg());
    }

    @org.junit.jupiter.api.Test
    void getUsed() {
        assertFalse(e.getUsed());
        e.use();
        assertTrue(e.getUsed());
    }

    @org.junit.jupiter.api.Test
    void toStringTest() {
        assertEquals("test", e.toString());
    }

    @org.junit.jupiter.api.Test
    void getMuscle() {
        assertEquals(Muscle.abs, e.getMuscle());
    }

    @org.junit.jupiter.api.Test
    void getDefaultKg() {
        assertEquals(10.5, e.getDefaultKg());
    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("test", e.getName());
    }
}