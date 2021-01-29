package main;

public enum Equipment {
    rack("rack"),
    dumbells("dumbells"),
    gymBall("gymBall"),
    bar("bar"),
    plates("plates"),
    bench("bench"),
    benchPressRack("benchPressRack"),
    cableMachine ("cableMachine"),
    cableRow ("cableRow"),
    dipsBar("dipsBar"),
    pullupBar("pullupBar"),
    pulldownMachine("pulldownMachine"),
    kettlebell("kettlebell"),
    stepBox("stepBox"),
    band("band"),
    mat("mat"),
    smithMachine("smithMachine"),
    kneeExtension("KneeExtension"),
    legCurl("legCurl"),
    legPress("legPress"),
    redCord("redCord"),
    stick("stick"),
    plyoBox("plyoBox"),
    pole("pole"),
    backExtension("backExtension"),
    rolloutWheel("rolloutWheel"),
    none("none");

    String name;

    Equipment(String s) {
        name = s;
    }

    public String toString() {
        return name;
    }
}