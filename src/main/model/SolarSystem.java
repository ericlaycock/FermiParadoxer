package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;


// The Galaxy class contains all methods and fields for the creation,
// calculation and description of SolarSystem objects, based on their inputs
// The most important method (the 'main' method, so to speak) is runCalculations()
public class SolarSystem extends CosmicBody {
    private double[] radii = {0.0,0.0};
    private double absoluteMag;
    private int numPlanets;
    private int numGoodPlanets;
    private char stellarClass;

    //REQUIRES: starname <= 28 char, starClass one of B, A, F, G, K, M, -10 <= absMag <= 20
    //          1 <= numPlanets <= 30, 0.01 <= mass <= 265
    // EFFECTS: Builds a star object (sets fields accordingly) and then calculates chance of life
    public SolarSystem(String starName, char starClass, double absMag, int numPlanets, double mass) {
        this.name = starName;
        this.stellarClass = starClass;
        this.absoluteMag = absMag;
        this.numPlanets = numPlanets;
        this.mass = mass;
        this.fileName = "solarText.txt";
        runCalculations();
        el.logEvent(new Event("solarSystem " + starName + " created"));
    }

    //EFFECTS: runs calculations determining chance of life
    public void runCalculations() {
        el.logEvent(new Event("Running calculations for solar system"));
        calculateHabitableZoneRadii();
        placePlanets();
        calculateOutcome();
    }

    //MODIFIES: this
    //EFFECTS: returns a pair of radii which correspond to the
    //    size of the habitable zone around a star
    private void calculateHabitableZoneRadii() {
        int index;
        double absLum;
        double radiusInner;
        double radiusOuter;
        char[] stellarClasses = {'B','A','F','G','K','M'};
        //these are values specific to stellar-class; need for our calculations
        double[] bolConstants = {-2.0,-0.3,-0.15,-0.4,-0.8,-2.0};

        // calculate absolute luminosity of the star
        index = new String(stellarClasses).indexOf((stellarClass));
        absLum = Math.pow(10,((absoluteMag + bolConstants[index] - 4.72) / -2.5));
        radiusInner = Math.sqrt(absLum / 1.1);
        radiusOuter = Math.sqrt(absLum / 0.53);

        this.radii[0] = radiusInner;
        this.radii[1] = radiusOuter;
    }

    // MODIFIES: this
    // EFFECTS: given the inner and outer radius, and the solar mass,
    // determine how many (if any) planets are generated in the Goldilock Zone
    private void placePlanets() {
        double distance;
        double standardDeviation = 2.95 * this.mass;
        double mean = 5.9 * this.mass;
        Random random = new Random();

        for (int i = 0; i < this.numPlanets; i++) {
            // Get random normalized variable and multiply by normal distribution,
            // where mean = 5.9*mass, standard deviation = 2.95*mass
            distance = random.nextGaussian() * standardDeviation + mean;
            if ((this.radii[0] < distance) && (distance < this.radii[1])) {
                this.numGoodPlanets += 1;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: assigns an outcome code based on number of planets (in Goldlock's Zone)
    public void calculateOutcome() {
        if (this.numGoodPlanets == 0) {
            this.outcome = "no planets in GZ";
        } else if (this.numPlanets <= 5) {
            this.outcome = "high eccentricity";
        } else if (this.numPlanets > 15) {
            this.outcome = "overcrowded orbits";
        } else if (this.numGoodPlanets == 1) {
            this.outcome = "one in GZ";
        } else {
            this.outcome = "multi in GZ";
        }
    }

    //EFFECTS: returns descriptive text detailing why the star is / is not habitable
    //based on outcome code
    public String explainOutcome() {
        String explainOutcomeText;
        ArrayList<String> explanationArray = getArrayFromFile(this.fileName);

        if (this.outcome.equals("no planets in GZ")) {
            explainOutcomeText = explanationArray.get(0);
            //no idea why jacoco is marking these as missing branches...
            //... all branches are evaluated
        } else if (this.outcome.equals("high eccentricity")) {
            explainOutcomeText = explanationArray.get(1);
        } else if (this.outcome.equals("one in GZ")) {
            explainOutcomeText = explanationArray.get(2);
        } else if (this.outcome.equals("multi in GZ")) {
            explainOutcomeText = explanationArray.get(3);
        } else {
            explainOutcomeText = explanationArray.get(4);
        }
        return explainOutcomeText.replaceAll("(.{80})", "$1\n");
    }

    //EFFECTS: packages and returns system fields as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("stellarClass", this.stellarClass);
        json.put("absoluteMag", this.absoluteMag);
        json.put("numPlanets", this.numPlanets);
        json.put("mass", this.mass);
        return json;
    }

    //MODIFIES: this
    //EFFECTS: sets number of planets in Goldilock Zone
    public void setNumGoodPlanets(int numGoodPlanets) {
        this.numGoodPlanets = numGoodPlanets;
    }

    //MODIFIES: this
    //EFFECTS: sets number of planets
    public void setNumPlanets(int numPlanets) {
        this.numPlanets = numPlanets;
    }

    //EFFECTS: returns stellar class
    public char getStellarClass() {
        return this.stellarClass;
    }

    //EFFECTS: returns absolute magnitude of star
    public double getAbsMag() {
        return this.absoluteMag;
    }

    //EFFECTS: returns number of planets around star
    public int getNumPlanets() {
        return this.numPlanets;
    }

    //EFFECTS: returns number of planets in Goldilocks Zone (GZ)
    public int getNumGoodPlanets() {
        return this.numGoodPlanets;
    }

    //EFFECTS: returns inner, outer radii of GZ
    public double[] getRadii() {
        return this.radii;
    }

}