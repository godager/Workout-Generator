package main;

public enum Goal {
    muscleGrowth("muscleGrowth"),
    weightLoss("weightLoss"),
    maxStrength("maxStrength"),
    xSkiing("xSkiing");

    String name;

    Goal (String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}