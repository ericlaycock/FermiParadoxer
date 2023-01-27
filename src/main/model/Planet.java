package model;

import org.json.JSONObject;

// The Galaxy class contains all methods and fields for the creation,
// calculation and description of Planet objects, based on their inputs
// The most important method (the 'main' method, so to speak) is calculateAllScores()
public class Planet extends CosmicBody {
    int habitabilityScore;
    double axialTilt;
    double atmosphereMass;
    double radioConc;
    String[] remarks = {"","","",""};

    //REQUIRES: planetName length max 20 char, 1 =< planetMass =< 35, 0 =< axialTilt =< 90,
    //          1 =< atmosphereMass =< 100,000, -10 =< radioConc =< -5
    //EFFECTS: constructs Planet object and calculates all scores based off initial inputs.
    public Planet(String planetName, double planetMass, double axialTilt, double atmosphereMass, double radioConc) {
        this.name = planetName;
        this.atmosphereMass = atmosphereMass;
        this.mass = planetMass;
        this.axialTilt = axialTilt;
        this.radioConc = radioConc;
        calculateAllScores();
        el.logEvent(new Event("Planet " + planetName + " created"));
    }

    //EFFECTS: individually assigns % chance of life by scoring mass, atmosphere,
    //etc one at a time, and then runs calculateOutcome() to finalize
    public void calculateAllScores() {
        el.logEvent(new Event("calculating planet score"));
        calculateMassScore();
        calculateAtmosphereScore();
        calculateAxialTiltScore();
        calculateRadioConcScore();
        calculateOutcome();
    }

    //MODIFIES: this
    //EFFECTS: increases/decreases chance of life based off of planet mass, and then
    //selects an appropriate description for the planet's mass
    private void calculateMassScore() {
        double earthMass = 6;
        if (mass < earthMass * 0.7) {
            this.habitabilityScore -= 100;
            remarks[0] = "It has low gravity: the current atmosphere will quickly sublimate away.";
        } else if (mass < earthMass * 1.5) {
            this.habitabilityScore += 15;
            remarks[0] = "It has a small iron core, which is poor for geological activity.";
        } else if (mass < earthMass * 2) {
            this.habitabilityScore += 25;
            remarks[0] = "It has a large iron core, which is good for geothermal activity.";
        } else if (mass < earthMass * 15) {
            this.habitabilityScore += 25;
            remarks[0] = "It's a Super-Earth! It has a massive iron core and robust geothermal"
                    + " activity, but because of how heavy it is, spaceflight from the surface is impossible.";
        } else {
            this.habitabilityScore -= 100;
            remarks[0] = "It's a gas giant. ";
        }
    }

    //MODIFIES: this
    //EFFECTS: increases/decreases chance of life based off of atmosphere mass, and then
    //selects an appropriate description for the planet's atmosphere mass
    private void calculateAtmosphereScore() {
        if (atmosphereMass < 1) {
            this.habitabilityScore -= 100;
            remarks[1] = "Its outer atmosphere is extremely thin, analogous to Mars. ";
        } else if (atmosphereMass < 10) {
            this.habitabilityScore -= 10;
            remarks[1] = "Its outer atmosphere is 1/10 as thick as Earth's.";
        } else if (atmosphereMass < 100) {
            this.habitabilityScore += 25;
            remarks[1] = "Its outer atmosphere is roughly same as on Earth.";
        } else if (atmosphereMass < 1000) {
            this.habitabilityScore += 10;
            remarks[1] = "Its outer atmosphere is like being 100m under water.";
        } else {
            this.habitabilityScore -= 100;
            remarks[1] = "Its outer atmosphere is like being 1km underwater, analogous to Venus.";
        }
    }

    //MODIFIES: this
    //EFFECTS: increases/decreases chance of life based off of axial tilt, and then
    //selects an appropriate description for the planet's tilt
    private void calculateAxialTiltScore() {
        if (axialTilt < 10) {
            this.habitabilityScore -= 5;
            remarks[2] = "The planet is dominated by cold polar weather systems.";
        } else if (axialTilt < 20) {
            this.habitabilityScore += 10;
            remarks[2] = "The planet is cooler than expected, given its distance to the sun.";
        } else if (axialTilt < 25) {
            this.habitabilityScore += 20;
            remarks[2] = "The planet experiences diverse but gentle changes in seasons.";
        } else if (axialTilt < 35) {
            this.habitabilityScore += 10;
            remarks[2] = "The planet experiences blistering summers and frigid winters.";
        } else { //axialTilt >= 35
            this.habitabilityScore -= 20;
            remarks[2] = "The planet experiences frequent changes in global currents; unpredictable changes"
                    + " in seasonality; non-stop storms dominate the surface. ";
        }
    }

    //MODIFIES: this
    //EFFECTS: increases/decreases chance of life based off of concentration of radiogenic elements,
    //and then selects an appropriate description for the planet's mass
    private void calculateRadioConcScore() {
        if (radioConc < -8.5) {
            this.habitabilityScore -= 90;
            remarks[3] = "The planet's core will quickly cool and shut down.";
        } else if (radioConc < -8) {
            this.habitabilityScore += 10;
            remarks[3] = "The planet's mantle is cool; planet's magnetic field is greatly strengthened.";
        } else if (radioConc < -7.5) {
            this.habitabilityScore += 25;
            remarks[3] = "The planet's mantle is warm; planet's magnetic field is strengthened.";
        } else if (radioConc < -7) {
            this.habitabilityScore += 10;
            remarks[3] = "The planet's mantle is hot; planet's magnetic field is weakened.";
        } else { //radioConc >= -7
            this.habitabilityScore -= 90;
            remarks[3] = "The planet's magnetic field is rapidly failing.";
        }
    }

    //MODIFIES: this
    //EFFECTS: converts score to simple, easily legible format
    public void calculateOutcome() {
        if (habitabilityScore <= 0) {
            this.outcome = "0% chance of life";
        } else {
            this.outcome = String.valueOf(habitabilityScore) + "% chance of life";
        }
    }

    //EFFECTS: returns all relevant outcomes and remarks into a single string
    public String explainOutcome() {
        el.logEvent(new Event("Creating flavour text for planet"));
        String allRemarks = "Your planet has a " + this.outcome + ". It has the following characteristics: "
                + remarks[0] + " " + remarks[1] + " " + remarks[2] + " " + remarks[3];
        return allRemarks.replaceAll("(.{80})", "$1\n");
    }

    //EFFECTS: packages and returns planet fields as a json object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("planetMass", this.mass);
        json.put("axialTilt", this.axialTilt);
        json.put("atmosphereMass", this.atmosphereMass);
        json.put("radioConc", this.radioConc);
        return json;
    }

    //EFFECTS: returns habitability score
    public int getHabitabilityScore() {
        return this.habitabilityScore;
    }

    //EFFECTS: returns axial tilt
    public double getAxialTilt() {
        return this.axialTilt;
    }

    //EFFECTS: returns atmosphere mass
    public double getAtmosphereMass() {
        return this.atmosphereMass;
    }

    //EFFECTS: returns concentration of Thorium, Uranium
    public double getRadioConc() {
        return this.radioConc;
    }

}