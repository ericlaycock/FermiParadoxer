package persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;
import model.Universe;

//PLEASE NOTE ATTRIBUTION: Multiple methods in this class are taken verbatim from the JsonSerializationDemo,
//which is permitted according to the section on Academic Honesty under Project/Phase 2

//This class contains functionality uses to write to JSON files
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // EFFECTS: writes JSON representations of Galaxies,
    // systems and planets to file
    public void write(Universe display) {
        EventLog el = EventLog.getInstance();
        el.logEvent(new Event("Writing to file:"));
        JSONArray galaxiesJsonArray = display.galaxiesToJson();
        JSONArray systemsJsonArray = display.systemsToJson();
        JSONArray planetsJsonArray = display.planetsToJson();

        JSONObject allData = new JSONObject();
        allData.put("Galaxies",galaxiesJsonArray);
        allData.put("Solar systems",systemsJsonArray);
        allData.put("Planets",planetsJsonArray);

        saveToFile(allData.toString(TAB));
        el.logEvent(new Event("Saving complete."));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

    // EFFECTS: returns destination as String
    public String getDestination() {
        return this.destination;
    }
}
