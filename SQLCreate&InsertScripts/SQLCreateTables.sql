CREATE TABLE Equipment (name varchar(30) PRIMARY KEY)
/
CREATE TABLE Muscle    (name varchar(40) PRIMARY KEY)
/
CREATE TABLE Goal      (name varchar(20) PRIMARY KEY)
/
CREATE TABLE Exercise_Type (name varchar(20) PRIMARY KEY)
/
CREATE TABLE Person (name varChar(30),
                     personID int PRIMARY KEY,
                     experience int NOT NULL,
                     goal varChar(20) NOT NULL,
                     password text varChar(72) NOT NULL,
                     workoutsPerWeek int NOT NULL,
                     username varChar(20) NOT NULL,
                     FOREIGN KEY (goal) REFERENCES Goal(name))
/
CREATE TABLE Routine   (name text,
                        personID int,
                        CONSTRAINT routine_pk PRIMARY KEY (name, personID),
                        FOREIGN KEY (personID) REFERENCES Person(personID))
/
CREATE TABLE Workout   (workoutID int PRIMARY KEY,
                        personID int NOT NULL,
                        FOREIGN KEY (personID) REFERENCES Person(personID))
/
CREATE TABLE Equipment_Access  (personID int,
                                equipment varChar(30),
                                CONSTRAINT eAccess_pk PRIMARY KEY (personID, equipment),
                                FOREIGN KEY (personID) REFERENCES Person(personID),
                                FOREIGN KEY (equipment) REFERENCES Equipment(name))
/
CREATE TABLE Exercise  (name varchar(30) PRIMARY KEY,
                       difficulty int NOT NULL,
                       defaultKg decimal NOT NULL,
                       description text,
                       equipment varchar(30) NOT NULL,
                       muscle varChar(40) NOT NULL,
                       type varchar(20) NOT NULL,
                       FOREIGN KEY (muscle) REFERENCES Muscle(name),
                       FOREIGN KEY (equipment) REFERENCES Equipment(name),
                       FOREIGN KEY (type) REFERENCES Exercise_Type(name))
/
CREATE TABLE Log       (weight int,
                        reps int,
                        set int,
                        pause int,
                        date DATE,
                        personID int,
                        workoutID int,
                        CONSTRAINT log_pk PRIMARY KEY (date, personID),
                        FOREIGN KEY (workoutID) REFERENCES Workout(workoutID),
                        FOREIGN KEY (personID) REFERENCES Person(personID))
/
CREATE TABLE Exercise_Goal (goal varChar(20),
                            exerse varChar(30),
                            CONSTRAINT eGoal_pk PRIMARY KEY (goal, exercise),
                            FOREIGN KEY (goal) REFERENCES Goal(name),
                            FOREIGN KEY (exercise) REFERENCES Exercise(name))
/
CREATE TABLE Exercises_In_Workout (workoutID int,
                                   exercise varChar(30),
                                   CONSTRAINT eWorkout_pk PRIMARY KEY (workoutID, exercise),
                                   FOREIGN KEY (exercise) REFERENCES Exercise(name),
                                   FOREIGN KEY (workoutID) REFERENCES Workout(workoutID))

CREATE TABLE Muscles_In_Exercise (muscle varChar(40),
                                  exercise varChar(30),
                                  CONSTRAINT mExercise_pk PRIMARY KEY (muscle, exercise),
                                  FOREIGN KEY (muscle) REFERENCES Muscle(name),
                                  FOREIGN KEY (exercise) REFERENCES Exercise(name));
/