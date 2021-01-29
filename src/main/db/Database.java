package main.db;

import main.*;
import utils.IdGenerator;
import utils.Password;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    //Gets an array of exercises suited for given muscle group and person. n = workouts/week
    public static Exercise[] chooseExercises (Muscle mainMuscle, Person person, int n) throws SQLException {
        //SQL query to find exercise
        String q =  "SELECT * " +
                    "FROM Exercise e INNER JOIN Exercise_Goal eg ON (e.name = eg.exercise)" +
                    "WHERE e.difficulty < ? AND eg.goal = ? AND e.muscle = ? ;";
        //Execute query with parameters
        ResultSet result = executeQ(q, person, mainMuscle);

        //Check if result is empty
        if (!result.isBeforeFirst()) {
            //No exercise found for current muscle. Increase difficulty recursively until an exercise is found.
            int exp = person.getExperience();
            person.setExperience(++exp);
            return chooseExercises(mainMuscle, person, n);
        }
        //Return exercise objects
        return addExerciseObjects(result, n);
    }


    public static ResultSet executeQ(String q, Person person, Muscle mainMuscle) throws SQLException {
        int exp = person.getExperience();
        String goal = person.getGoal().toString();
        String mus = mainMuscle.toString();

        //Execute query with parameters
        PreparedStatement statement = DBConnection.prepareStmt(q);
        statement.setInt(1, exp);
        statement.setString(2, goal);
        statement.setString(3, mus);

        return statement.executeQuery();
    }


    public static Exercise[] addExerciseObjects(ResultSet result, int n) throws SQLException {
        //Add exercise objects
        Exercise[] exercises = new Exercise[n];
        for (int i = 0; i < n; i++) { //n = number of workouts per week
            if (result.next()) {
                //Create new exercise object
                String name = (String)(result.getObject(1));
                int difficulty = (int) result.getObject(2);
                java.math.BigDecimal defaultKg = (BigDecimal) result.getObject(3);
                double defaultKgDouble = defaultKg.doubleValue();
                String description = (String) result.getObject((4));
                Equipment equipment = Equipment.valueOf((String) result.getObject(5));
                Muscle muscle = Muscle.valueOf((String) result.getObject(6));
                String type = (String) result.getObject(7);

                Exercise exercise = new Exercise(name, description, difficulty, equipment, muscle, defaultKgDouble, type);
                exercises[i] = exercise;
            }
            else {
                //Empty row which means no new exercise was found for current muscle group. Reuse first exercise.
                exercises[i] = exercises[0];
            }
        }
        return exercises;
    }


    public static void addPerson(String name, int experience, String goal,
                                 String username, String password, int timesPerWeek) throws Exception {
        //Hash and salt the password before it is stored
        String hashedPassword = Password.getSaltedHash(password);
        //Generate unique ID
        int ID = IdGenerator.generateUniqueId();

        //Placeholders to prevent SQL injection
        String insert = "INSERT INTO Person (name, personID, experience, goal, password, workoutsPerWeek, username) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement statement = DBConnection.prepareStmt(insert);
        statement.setString(1, name);
        statement.setInt(2, ID);
        statement.setInt(3, experience);
        statement.setString(4, goal);
        statement.setString(5, hashedPassword);
        statement.setInt(6, timesPerWeek);
        statement.setString(7, username);
        statement.execute();
    }


    public static Person getPerson(String username) throws SQLException {
        //SQL query to find person and attributes for Person object
        String q =  "SELECT name, experience, workoutsperweek, goal " +
                "FROM person " +
                "WHERE username = ? ;";
        //Execute query
        DBConnection.connect();
        PreparedStatement statement = DBConnection.prepareStmt(q);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();

        //Retrieve person attributes
        String name = null;
        int difficulty = 0;
        int workoutsPerWeek = 0;
        Goal goal = null;
        while (result.next()) {
            name = result.getString(1);
            difficulty = result.getInt(2);
            workoutsPerWeek = result.getInt(3);
            goal = Goal.valueOf(result.getString(4));
        }
        DBConnection.close();

        Person person = new Person(name, difficulty, goal, workoutsPerWeek);
        return person;
    }


    public static boolean usernameExists(String username) throws SQLException {
        PreparedStatement pStmt = DBConnection.prepareStmt("SELECT username FROM Person;");
        ResultSet res = pStmt.executeQuery();
        String usernameCur;
        while (res.next()) {
            usernameCur = res.getString("username");
            if (usernameCur.equals(username)) return true;
        }
        return false;
    }


    private static String getPassword (String username) throws Exception {
        //SQL query to find stored password for given username
        String q =  "SELECT password " +
                "FROM person " +
                "WHERE username = ? ;";
        DBConnection.connect();
        PreparedStatement statement = DBConnection.prepareStmt(q);
        statement.setString(1, username);
        ResultSet result = statement.executeQuery();

        if (!result.isBeforeFirst()) return null;

        result.next();
        return result.getString(1);
    }


    public static boolean login(String username, String password) throws Exception {
        String passwordStored = getPassword(username);
        if (passwordStored == null) return false;
        return Password.check(password, passwordStored);
    }
}
