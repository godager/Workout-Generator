package main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Workout {
    LocalDate date;

    Exercise [] exercises;
    double [] weights;
    int [][] reps;
    int [] set;
    int [] pauses;

    public Workout(Exercise[] exercises, int[][] reps, int[] set, int[]pauses) {
        this.exercises = exercises;
        this.reps = reps;
        this.set = set;
        this.pauses = pauses;
        this.weights = new double[exercises.length];
        for (int i = 0; i < exercises.length; i++) {
            weights[i] = exercises[i].getDefaultKg();
        }
    }

    public void doWorkout(int intensity, String filename) throws IOException {
        date = LocalDate.now();
    }

    public void writeToFile(String filename) throws IOException {
        String fName = filename + date + "_.txt";
        FileWriter fWriter = new FileWriter(fName);
        PrintWriter pWriter = new PrintWriter(fWriter);
        pWriter.println(date);
        pWriter.print(toString());
        pWriter.close();
    }

    public int[] getPauses() {
        return pauses;
    }

    public int[] getReps() {
        int[] r = new int [2];
        r[0] = reps[0][0];
        r[1] = reps[0][1];
        return r;
    }

    public int[] getSet() {
        return set;
    }

    public Exercise[] getExercises() {
        return exercises;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < exercises.length; i++) {
            String line = String.format("%26s %9s %12s %8s",
                    exercises[i], weights[i] + " kg",
                    reps[0][i] + "-" + reps[1][i] + " reps", set[i] + " sets");
            s += line + "\n";
        }
        return s + "\n";
    }
}
