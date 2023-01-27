package model;

import org.json.JSONObject;
import java.util.ArrayList;

// The Galaxy class contains all methods and fields for the creation,
// calculation and description of Galaxy objects, based on their inputs
// The most important method (the 'main' method, so to speak) is calculateOutcome()
public class Galaxy extends CosmicBody {
    private int age; //age of the galaxy, from 0-14 BYA
    private int radius; //radius of galaxy, from 1 to 100 kiloparsecs
    private double stars; //billions of stars, from 0.1 to 10,000

    //REQUIRES: 0 =< age =< 14, 1 =< radius =<  100
    //REQUIRES: 0.1 =< stars =< 10,000
    //EFFECTS: Constructs a galaxy object given age,size, numStars and age
    //EFFECTS: ... and then calculates how habitable it is
    public Galaxy(String galaxyName, int radius, double stars, int age) {
        this.radius = radius;
        this.stars = stars;
        this.age = age;
        this.name = galaxyName;
        this.fileName = "galaxyText.txt"; //filepath where we store descriptions of galaxies
        calculateOutcome();
        el.logEvent(new Event("galaxy " + galaxyName));
    }

    //MODIFIES: this
    //EFFECTS: given age of galaxy, calculate density of galaxy (different depending
    //on the age) and use that to determine the galaxy's habitability.
    public void calculateOutcome() {
        el.logEvent(new Event("calculating galactic outcome"));
        String outcome;
        Double[] veryOldNumArray = {0.05,10000.0};
        String[] veryOldStringArray = {"too many SNE","too low metal"};
        Double[] oldNumArray = {0.06,0.1,10000.0};
        String[] oldStringArray = {"too many SNE","good","too low metal"};
        Double[] youngNumArray = {0.025,0.1,0.14,10000.0};
        String[] youngStringArray = {"too high metal","good","too little time", "too low metal"};
        Double[] veryYoungNumArray = {0.04,0.16,10000.0};
        String[] veryYoungStringArray = {"too high metal","too little time", "too low metal"};

        if (this.age >= 9) {
            outcome = getTextForRadstarValue(veryOldNumArray,veryOldStringArray);
        } else if (this.age >= 6) {
            outcome = getTextForRadstarValue(oldNumArray,oldStringArray);
        } else if (this.age >= 3) {
            outcome = getTextForRadstarValue(youngNumArray,youngStringArray);
        } else {
            outcome = getTextForRadstarValue(veryYoungNumArray,veryYoungStringArray);
        }
        this.outcome = outcome;
    }

    //REQUIRES: numArray, strArray of same length; doubles and strings respectively
    //EFFECTS: selects text that matches the corresponding galactic density
    private String getTextForRadstarValue(Double[] numArray, String[] strArray) {
        String returnText = "";
        double radStar = this.radius / this.stars;

        //TODO: try to replace this with a While Loop or with a Stream
        for (int i = 0; i < numArray.length; i++) {
            if (radStar <= numArray[i]) {
                returnText = strArray[i];
                break;
            }
        }


        return returnText;
    }

    //EFFECTS: matches the outcome code to the longer description,
    //which it grabs from the .txt file
    public String explainOutcome() {
        el.logEvent(new Event("selecting flavour text for galaxy"));
        String explainOutcomeText;
        ArrayList<String> explanationArray = getArrayFromFile(this.fileName);

        if (this.outcome.equals("too many SNE")) {
            explainOutcomeText = explanationArray.get(0);
            //Jacoco tells me I don't have branch coverage here,
            //yet these are all evaluated on every test.
        } else if (this.outcome.equals("too low metal")) {
            explainOutcomeText = explanationArray.get(1);
        } else if (this.outcome.equals("good")) {
            explainOutcomeText = explanationArray.get(2);
        } else if (this.outcome.equals("too high metal")) {
            explainOutcomeText = explanationArray.get(3);
        } else {
            explainOutcomeText = explanationArray.get(4);
        }

        return explainOutcomeText.replaceAll("(.{80})", "$1\n");
    }

    //EFFECTS: returns number of stars
    public Double getStars() {
        return this.stars;
    }

    //EFFECTS: returns radius
    public Integer getRadius() {
        return this.radius;
    }

    //EFFECTS: returns age
    public Integer getAge() {
        return this.age;
    }

    //EFFECTS: packages and returns galaxy fields as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("radius", this.radius);
        json.put("stars", this.stars);
        json.put("age", this.age);
        return json;
    }
}