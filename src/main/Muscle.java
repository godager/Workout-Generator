package main;

public enum Muscle {
    biceps("biceps"),
    triceps("triceps"),
    chest("chest"),
    shoulders("shoulders"),
    hamstrings("hamstrings"),
    back("back"),
    lats("lats"),
    abs("abs"),
    quadriceps ("quadriceps"),
    calves ("calves"),
    glutes("glutes");

    String name;

    Muscle(String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}
