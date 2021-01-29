package main;

import main.db.DBConnection;
import main.db.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class RoutineGenerator {
    private LinkedList<Workout> workouts;
    private Routine routine;
    private Person person;
    private Goal goal;
    private ArrayList<Equipment> equipment;
    private int experience;
    private int timesPerWeek;
    private LinkedList<Workout> workoutList;
    private int numExercises;


    public RoutineGenerator (Person person, Routine routine) throws SQLException {
        this.person = person;
        this.routine = routine;
        goal = person.getGoal();
        equipment = person.getEquipment();
        experience = person.getExperience();
        timesPerWeek = routine.getTimesPerWeek();
        workoutList = new LinkedList<>();
        numExercises = findNumExercises();
        workouts = generateRoutine();
    }

    public LinkedList<Workout> getWorkouts () {
        return workouts;
    }


    private int findNumExercises() {
        int num = 7;
        if (goal == Goal.muscleGrowth) {
            num = 9;
        }
        return num;
    }

    public int getNumExercises() {
        return numExercises;
    }

    private LinkedList<Workout> generateRoutine () throws SQLException {
        //Find exercises from database
        Exercise[][] routine = findExercises(goal);

        //Determine reps, pauses and sets
        int[][] reps = reps(numExercises);
        int[] pauses = pauses(numExercises);
        int[]sets = sets(numExercises);

        //Create each workout session in the routine
        for (int i = 0; i < timesPerWeek; i++) {
            Workout workout = new Workout(routine[i], reps, sets, pauses);
            workoutList.add(workout);
        }
        return workoutList;
    }


    private int[] sets (int n) {
        int numSets = 0;
        if (experience == 10) {
            numSets = 5;
        }
        else if (experience > 7) {
            numSets = 4;
        }
        else if (experience > 4) {
            numSets = 3;
        }
        else if (experience > 1) {
            numSets = 2;
        }
        else if (experience == 1){
            numSets = 1;
        }
        int[] sets = new int[n];
        for (int j = 0; j < sets.length; j++) {
            sets[j] = numSets;
        }
        return  sets;
    }

    private int[] pauses (int n) {
        int [] pauses = new int[n];
        switch (goal) {
            case muscleGrowth:
                for (int i = 0; i < n; i++) {
                    pauses[i] = 60;
                }
                break;
            case weightLoss:
                for (int i = 0; i < n; i++) {
                    pauses[i] = 30;
                }
                break;
            case maxStrength:
                for (int i = 0; i < n; i++) {
                    pauses[i] = 200;
                }
                break;
            case xSkiing:
                for (int i = 0; i < n; i++) {
                    pauses[i] = 45;
                }
                break;
        }
        return pauses;
    }


    private int[][] reps (int n) {
        int [][] reps = new int[2][n];
        int[] interval = new int[2];

        switch (goal) {
            case muscleGrowth:
                interval[0] = 8; interval[1] = 12;
                break;
            case weightLoss:
                interval[0] = 20; interval[1] = 25;
                break;
            case maxStrength:
                interval[0] = 3; interval[1] = 7;
                break;
            case xSkiing:
                interval[0] = 13; interval[1] = 20;
                break;
        }
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < n; k++) {
                reps[j][k] = interval[j];
            }
        }
        return reps;
    }


    private Exercise[][] findExercises (Goal goal) throws SQLException {
        DBConnection.connect();
        Exercise[][] exercises = new Exercise[timesPerWeek][numExercises];

        for (int i = 0; i < timesPerWeek; i++) {
            int c = 0;
            //Legs
            exercises[i][c++] = Database.chooseExercises(Muscle.quadriceps, person, timesPerWeek)[i];
            exercises[i][c++] = Database.chooseExercises(Muscle.hamstrings, person, timesPerWeek)[i];
            exercises[i][c++] = Database.chooseExercises(Muscle.glutes, person, timesPerWeek)[i];
            //Back
            exercises[i][c++] = Database.chooseExercises(Muscle.lats, person, timesPerWeek)[i];
            exercises[i][c++] = Database.chooseExercises(Muscle.back, person, timesPerWeek)[i];
            //Chest
            exercises[i][c++] = Database.chooseExercises(Muscle.chest, person, timesPerWeek)[i];
            //Abs
            exercises[i][c++] = Database.chooseExercises(Muscle.abs, person, timesPerWeek)[i];
            //Arms
            if (goal == Goal.muscleGrowth) {
                exercises[i][c++] = (Database.chooseExercises(Muscle.triceps, person, timesPerWeek) [i]);
                exercises[i][c] = (Database.chooseExercises(Muscle.biceps, person, timesPerWeek) [i]);
            }
        }
        DBConnection.close();
        return exercises;
    }
}