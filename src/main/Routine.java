package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class Routine {
    private LinkedList<Workout> workouts;
    private int timesPerWeek;
    private Person person;
    private RoutineGenerator rg;

    public Routine (Person person) throws SQLException {
        this.person = person;
        timesPerWeek = person.getWorkoutsPerWeek();
        rg = generateNewRoutine();
    }

    public int getTimesPerWeek() {
        return timesPerWeek;
    }

    public RoutineGenerator getRg() {
        return rg;
    }

    public RoutineGenerator generateNewRoutine() throws SQLException {
        RoutineGenerator generator = new RoutineGenerator(person, this);
        workouts = generator.getWorkouts();
        return generator;
    }

    public void writeToFile(File file) throws IOException {
        FileWriter fWriter = new FileWriter(file);
        PrintWriter pWriter = new PrintWriter(fWriter);
        int counter = 1;
        for (Workout w : workouts) {
            pWriter.print("------------------------ DAY " + (counter++) + " ------------------------\n");
            pWriter.print(w.toString());
        }
        pWriter.close();
    }

    public LinkedList<Workout> getWorkouts() {
        return workouts;
    }

    public String toString() {
        String s = "";
        Iterator<Workout> iterator = workouts.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            s += "\n\n";
            s += "-------------------- DAY " + i++ + " --------------------\n";
            s += iterator.next();
        }
        return s;
    }
}
