import com.areyes1.jgc.junit.integ.IntegrationTest;
import main.Exercise;
import main.Goal;
import main.Muscle;
import main.Person;
import main.db.DBConnection;
import main.db.Database;
import org.junit.experimental.categories.Category;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Category(IntegrationTest.class)
public class DatabaseIT {
    private final Person p = new Person("test", 1, Goal.muscleGrowth, 2);
    private final int n = p.getWorkoutsPerWeek();
    private final Muscle m = Muscle.quadriceps;

    private final String query = "SELECT * " +
            "FROM Exercise e INNER JOIN Exercise_Goal eg ON (e.name = eg.exercise)" +
            "WHERE e.difficulty < ? AND eg.goal = ? AND e.muscle = ? ;";

    @org.junit.jupiter.api.Test
    void addAndGetPerson() {
        DBConnection.connect();
        try {
            Database.addPerson("testName", 1, "muscleGrowth",
                    "usernameTest", "passwordTest", 2);
        } catch (Exception e) {
            System.out.println("Could not add person due to exception in Database.addPerson()");
        }
        finally {
            DBConnection.close();
        }
        Person pg = null;
        try {
            pg = Database.getPerson("usernameTest");
        } catch (SQLException ex) {
            System.out.println("Could not get person due to SQL Exception in Database.addPerson()");
        }
        assert pg != null;
        assertEquals(1, pg.getExperience());
        assertEquals("muscleGrowth", pg.getGoal().toString());
        assertEquals("testName", pg.getName());
        assertEquals(2, pg.getWorkoutsPerWeek());
    }

    @org.junit.jupiter.api.Test
    void chooseExercises() throws SQLException {
        DBConnection.connect();
        Exercise[] exercises = Database.chooseExercises(m, p, p.getWorkoutsPerWeek());
        assertEquals(exercises.length, n);
        for (int i = 0; i < n; i++) {
            assertEquals(m ,exercises[i].getMuscle());
        }
        DBConnection.close();
    }

    @org.junit.jupiter.api.Test
    void executeQ () throws SQLException {
        Person testP = p;
        testP.setExperience(10);

        DBConnection.connect();
        assertNotNull(Database.executeQ(query, testP, m));
        DBConnection.close();
    }

    @org.junit.jupiter.api.Test
    void addExerciseObjects() throws SQLException {
        Person testP = p;
        testP.setExperience(10);
        DBConnection.connect();
        ResultSet rs = Database.executeQ(query, testP, m);
        Exercise[] exercises = Database.addExerciseObjects(rs, n);
        for (Exercise exercise : exercises) {
            assertNotNull(exercise);
        }
        DBConnection.close();
    }

    @org.junit.jupiter.api.Test
    void usernameExists() throws Exception {
        DBConnection.connect();
        assertTrue(Database.usernameExists("usernameTest"));
        assertFalse(Database.usernameExists("usernameTestFalse"));
        DBConnection.close();
    }

    @org.junit.jupiter.api.Test
    void login () throws Exception {
        assertTrue(Database.login("usernameTest", "passwordTest"));
        DBConnection.close();
        assertFalse(Database.login("usernameFalse", "passwordTest"));
        DBConnection.close();
        assertFalse(Database.login("usernameTest", "passwordFalse"));
        DBConnection.close();
    }
}
