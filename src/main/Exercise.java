package main;

public class Exercise {
    private String name;
    private String description;
    private int difficulty;
    private Equipment equipment;
    private Muscle muscle;
    private double defaultKg;
    private boolean used;
    private String type;

    public Exercise (String name, String description, int difficulty, Equipment equipment,
                      Muscle muscle, double defaultKg, String type) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.equipment = equipment;
        this.muscle = muscle;
        this.defaultKg = defaultKg;
        used = false;
        this.type = type;
    }

    public void use() {
        used = true;
    }

    public String toString() {
        return name;
    }

    public boolean getUsed() {
        return used;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public double getDefaultKg() {
        return defaultKg;
    }

    public String getName (){
        return name;
    }

}
