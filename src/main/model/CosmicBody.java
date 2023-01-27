package model;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//CosmicBody is an abstract class holding various methods and fields which are
//inherited by or implemented by Galaxy, SolarSystem and Planet class
//the most important of these are the getArrayFromFile method,
//and the get/set name, mass and outcome methods and fields.
public abstract class CosmicBody {
    protected String name;
    protected String outcome;
    protected String fileName;
    protected double mass;
    protected EventLog el = EventLog.getInstance();

    //REQUIRES: none (bad pathname is handled by catch, see below)
    //EFFECTS: scans and returns a text file as an array
    protected ArrayList getArrayFromFile(String textName) {
        ArrayList<String> explanationArray = new ArrayList<>();
        String currentWorkingDir = System.getProperty("user.dir");
        File file = new File(currentWorkingDir + "/data/" + textName);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                explanationArray.add(scanner.nextLine());
                //no idea why this isn't "evaluated" in my tests
                //when I add println("running") and just run my tests,
                //this method is obviously called.
            }
        } catch (FileNotFoundException e) {
            //Because path names are hardcoded, this catch method should never
            //be needed. We force explanationArray to have four "error" values
            //for testing purposes (see GalaxyTest and solarSystemTest).
            explanationArray.add("error");
            explanationArray.add("error");
            explanationArray.add("error");
            explanationArray.add("error");
            explanationArray.add("error");
        }
        return explanationArray;
    }

    //EFFECTS: returns name of instantiated cosmic object
    public String getName() {
        return this.name;
    }

    //EFFECTS: returns the outcome string of the cosmic object
    public String getOutcome() {
        return this.outcome;
    }


    //EFFECTS: returns mass
    public double getMass() {
        return this.mass;
    }

    //MODIFIES: this
    //EFFECTS: sets the fileName to given string
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //These methods are implemented differently in each class
    abstract String explainOutcome();

    abstract JSONObject toJson();

    abstract void calculateOutcome();


}