package persistence;

import model.Galaxy;
import model.Planet;
import model.SolarSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Universe;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {
    String JSON_STORE;
    JsonWriter jsonWriter;
    JsonWriter jsonWriter1;
    Universe display;

    @BeforeEach
    void setup(){
        JSON_STORE = "./data/writeTestData.json";
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonWriter1 = new JsonWriter("./data\032error.json");
        display = new Universe();
    }

    //Test that the constructors returned stored string
    @Test
    void constructorTest(){
        assertEquals(JSON_STORE,jsonWriter.getDestination());
    }

    @Test
    void openExpectNoExceptionsTest() {
        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException not expected");
        }
    }

    @Test
    void openExpectFileNotFoundExceptionTest(){
        try {
            jsonWriter1.open();
            fail("FileNotFoundException should've been thrown");
        } catch (FileNotFoundException e) {
            //Correct failure
        }
    }

    @Test
    void writeExpectNoExceptionsTest(){
        addCosmicBodiesToDisplay();
        try{jsonWriter.open();
        jsonWriter.write(display);
        jsonWriter.close();

        JsonLoader jsonLoader = new JsonLoader(JSON_STORE);
        jsonLoader.load(display);
        ArrayList<Galaxy> galaxies = display.getGalaxies();
        ArrayList<SolarSystem> systems = display.getSolarSystems();
        ArrayList<Planet> planets = display.getPlanets();

        assertEquals("gal1", galaxies.get(0).getName());
        assertEquals('G', systems.get(1).getStellarClass());
        assertEquals(30, planets.get(2).getAxialTilt());

        } catch (IOException e) {
            fail();
        }

    }

    //Helper function: adds several galaxies, solar systems and planets to Display
    public void addCosmicBodiesToDisplay(){
        Galaxy gal1 = new Galaxy("gal1",100,4000,10);
        Galaxy gal2 = new Galaxy("gal2",10,399,1);
        Galaxy gal3 = new Galaxy("gal3",120,4000,12);
        SolarSystem sol1 = new SolarSystem("test",'B', -10,1,1);
        SolarSystem sol2 = new SolarSystem("test",'G', -1,12,2);
        SolarSystem sol3 = new SolarSystem("test",'F', 2,10,1);
        Planet plan1 = new Planet("test",2,0,0,-10);
        Planet plan2 = new Planet("test",2,20,10,-12);
        Planet plan3 = new Planet("test",2,30,20,-13);
        display.add(gal1);
        display.add(gal2);
        display.add(gal3);
        display.add(sol1);
        display.add(sol2);
        display.add(sol3);
        display.add(plan1);
        display.add(plan2);
        display.add(plan3);
    }
}
