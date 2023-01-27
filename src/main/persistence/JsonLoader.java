package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.*;
import org.json.*;

//PLEASE NOTE ATTRIBUTION: Multiple methods in this class are taken verbatim from the JsonSerializationDemo,
//which is permitted according to the section on Academic Honesty under Project/Phase 2

// Represents a reader that reads workroom from JSON data stored in file
public class JsonLoader {
    private EventLog el = EventLog.getInstance();
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonLoader(String source) {
        this.source = source;
    }

    // EFFECTS: reads data from file and loads it into display;
    // throws IOException if an error occurs reading data from file
    public void load(Universe display) throws IOException {

        el.logEvent(new Event("LOADING INITIATED///"));

        String jsonData = readFile(source);
        JSONObject allData = new JSONObject(jsonData);
        JSONArray galaxyData = allData.getJSONArray("Galaxies");
        JSONArray systemData = allData.getJSONArray("Solar systems");
        JSONArray planetData = allData.getJSONArray("Planets");

        loadGalaxy(galaxyData, display);
        loadSystem(systemData, display);
        loadPlanet(planetData, display);
        el.logEvent(new Event("///LOADING DONE"));
    }

    //EFFECTS: takes Galaxy JSON data and loads it into Universe
    public void loadGalaxy(JSONArray cosmicData, Universe display) {
        el.logEvent(new Event("Loading Galaxies:"));
        for (Object json : cosmicData) {
            JSONObject galaxyJson = (JSONObject) json;
            Galaxy galaxy = new Galaxy(galaxyJson.getString("name"),
                    galaxyJson.getInt("radius"),
                    galaxyJson.getDouble("stars"),
                    galaxyJson.getInt("age"));
            display.add(galaxy);
        }
        el.logEvent(new Event("Galaxies Loaded."));
    }

    //EFFECTS: takes System JSON data and loads it into Universe
    private void loadSystem(JSONArray cosmicData, Universe display) {
        el.logEvent(new Event("Loading Systems:"));
        for (Object json : cosmicData) {
            JSONObject solarJson = (JSONObject) json;
            SolarSystem system = new SolarSystem(solarJson.getString("name"),
                    (char) solarJson.getInt("stellarClass"),
                    solarJson.getDouble("absoluteMag"),
                    solarJson.getInt("numPlanets"),
                    solarJson.getDouble("mass"));
            display.add(system);
        }
        el.logEvent(new Event("Systems loaded"));
    }

    //EFFECTS: takes Planet JSON data and loads it into Universe
    private void loadPlanet(JSONArray cosmicData, Universe display) {
        el.logEvent(new Event("Loading Planets"));
        for (Object json : cosmicData) {
            JSONObject planetJson = (JSONObject) json;
            Planet planet = new Planet(planetJson.getString("name"),
                    planetJson.getDouble("planetMass"),
                    planetJson.getDouble("axialTilt"),
                    planetJson.getDouble("atmosphereMass"),
                    planetJson.getDouble("radioConc"));
            display.add(planet);
        }
        el.logEvent(new Event("Planets loaded"));
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: returns source as string
    public String getSource() {
        return this.source;
    }

}
