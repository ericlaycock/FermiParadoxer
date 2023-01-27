package model;

import org.json.JSONArray;
import java.util.ArrayList;

//Universe is used to store and display the outcomes of cosmic bodies
public class Universe {
    private EventLog el = EventLog.getInstance();
    private ArrayList<Galaxy> galaxies = new ArrayList<>();
    private ArrayList<SolarSystem> solarSystems = new ArrayList<>();
    private ArrayList<Planet> planets = new ArrayList<>();

    //MODIFIES: this
    //EFFECTS: adds new galaxy to list of galaxies
    public void add(Galaxy galaxy) {
        this.galaxies.add(galaxy);
        el.logEvent(new Event("Galaxy " + galaxy.getName() + " added to Universe"));
    }

    //MODIFIES: this
    //EFFECTS: adds new solar system to list of solar systems
    public void add(SolarSystem solarSystem) {
        this.solarSystems.add(solarSystem);
        el.logEvent(new Event("Solar system " + solarSystem.getName() + " added to Universe"));
    }

    //MODIFIES: this
    //EFFECTS: adds new planet to list of planets
    public void add(Planet planet) {
        planets.add(planet);
        el.logEvent(new Event("Planet " + planet.getName() + " added to Universe"));
    }


    //EFFECTS: parses and returns galaxy JSONArrayData
    public JSONArray galaxiesToJson() {
        JSONArray galaxyJsonArray = new JSONArray();
        for (Galaxy galaxy : galaxies) {
            galaxyJsonArray.put(galaxy.toJson());
        }
        return galaxyJsonArray;
    }

    //EFFECTS: parses and returns systemic JSONArrayData
    public JSONArray systemsToJson() {
        JSONArray systemJsonArray = new JSONArray();

        for (SolarSystem system : solarSystems) {
            systemJsonArray.put(system.toJson());
        }

        return systemJsonArray;
    }

    //EFFECTS: parses and returns planetary JSONArrayData
    public JSONArray planetsToJson() {
        JSONArray planetJsonArray = new JSONArray();

        for (Planet planet : planets) {
            planetJsonArray.put(planet.toJson());
        }
        return planetJsonArray;
    }

    //EFFECTS: returns list of galaxies
    public ArrayList<Galaxy> getGalaxies() {
        return this.galaxies;
    }

    //EFFECTS: returns list of solar systems
    public ArrayList<SolarSystem> getSolarSystems() {
        return this.solarSystems;
    }

    //EFFECTS: returns list of planets
    public ArrayList<Planet> getPlanets() {
        return this.planets;
    }

}
