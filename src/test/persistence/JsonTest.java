package persistence;

import model.Galaxy;
import model.Planet;
import model.SolarSystem;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Test that toJSON() method implementations perform correctly
public class JsonTest {

    //Test that packaging a planet as JSON stores fields correctly
    @Test
    void planetJsonTest(){
        Planet planet = new Planet("name",8,20,100,-8);
        JSONObject planetJson = planet.toJson();
        assertEquals("name",planetJson.get("name"));
        assertEquals(20.0,planetJson.get("axialTilt"));
        assertEquals(100.0,planetJson.get("atmosphereMass"));
        assertEquals(8.0,planetJson.get("planetMass"));
        assertEquals(-8.0,planetJson.get("radioConc"));
    }

    //Test that packaging a galaxy as JSON stores fields correctly
    @Test
    void galaxyJsonTest(){
        Galaxy galaxy = new Galaxy("name",20,10,10);
        JSONObject planetJson = galaxy.toJson();
        assertEquals("name",planetJson.get("name"));
        assertEquals(20,planetJson.get("radius"));
        assertEquals(10.0,planetJson.get("stars"));
        assertEquals(10,planetJson.get("age"));
    }

    //Test that packaging a system as JSON stores fields correctly
    @Test
    void systemJsonTest(){
        SolarSystem system = new SolarSystem("starName",'G',-2,10,7);
        JSONObject systemJson = system.toJson();
        assertEquals("starName",systemJson.get("name"));
        char starClass = (char)systemJson.getInt("stellarClass");
        assertEquals('G',starClass);
        assertEquals(-2.0,systemJson.get("absoluteMag"));
        assertEquals(7.0,systemJson.get("mass"));
        assertEquals(10,systemJson.get("numPlanets"));
    }
}
