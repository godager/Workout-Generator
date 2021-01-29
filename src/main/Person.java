package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Person {
    private String name;
    private int experience;
    private Goal goal;
    private ArrayList<Equipment> equipment;
    private int workoutsPerWeek;

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Person(String name, int experience, Goal goal, int workoutsPerWeek) {
        this.name = name;
        this.experience = experience;
        this.goal = goal;
        this.workoutsPerWeek = workoutsPerWeek;
        equipment = new ArrayList<>();
        //All equipment available by default
        equipment.addAll(Arrays.asList(Equipment.values()));
    }

    public int getWorkoutsPerWeek() {
        return workoutsPerWeek;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public Goal getGoal() {
        return goal;
    }

    public ArrayList<Equipment> getEquipment() {
        return equipment;
    }

    @Override
    public String toString() {
        return name;
    }
}
