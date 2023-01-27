package model;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Test that universe correctly adds, stores, loads savefiles
public class UniverseTest {
    Universe uni;
    Galaxy gal1;
    SolarSystem sol1;
    Planet plan1;
    JSONArray correct;

    //Initial setup of all objects
    @BeforeEach
    void setup() {
        uni = new Universe();
        gal1 = new Galaxy("galaxy",100,2050,9);
        sol1 = new SolarSystem("sol",'G',-2,10,6);
        plan1 = new Planet("name",6,20,1000,-8);
    }

    //Test that we can add a galaxy multiple times to uni
    @Test
    void addGalaxyTest() {
        uni.add(gal1);
        assertTrue(uni.getGalaxies().contains(gal1));
        uni.add(gal1);
        assertTrue(uni.getGalaxies().size() == 2);
        assertTrue(uni.getGalaxies().contains(gal1));
    }

    //Test that we can add a solar system multiple times to uni
    @Test
    void addSolarSystem() {
        uni.add(sol1);
        assertTrue(uni.getSolarSystems().contains(sol1));
        uni.add(sol1);
        assertTrue(uni.getSolarSystems().size() == 2);
        assertTrue(uni.getSolarSystems().contains(sol1));
    }

    //Test that we can add a planet multiple times to uni
    @Test
    void addPlanetTest() {
        uni.add(plan1);
        assertTrue(uni.getPlanets().contains(plan1));
        uni.add(plan1);
        assertTrue(uni.getPlanets().size() == 2);
        assertTrue(uni.getPlanets().contains(plan1));
    }

    //Test that we get a correctly formatted JsonArray from galaxiesToJson()
    @Test
    void correctJSONGalaxyTest(){
        correct = getJSONArray(gal1);
        uni.add(gal1);
        assertEquals(correct.toString(),uni.galaxiesToJson().toString());
    }

    //Test that we get a correctly formatted JsonArray from systemsToJson()
    @Test
    void correctJSONSolarSystemTest(){
        correct = getJSONArray(sol1);
        uni.add(sol1);
        assertEquals(correct.toString(),uni.systemsToJson().toString());
    }

    //Test that we get a correctly formatted JsonArray from PlanetsToJson()
    @Test
    void correctJSONPlanetTest(){
        correct = getJSONArray(plan1);
        uni.add(plan1);
        assertEquals(correct.toString(),uni.planetsToJson().toString());
    }

    //REQUIRES: non-null cosmic body
    //EFFECTS: helper function for tests, returns JsonArray
    JSONArray getJSONArray(CosmicBody cosmic){
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(cosmic.toJson());
        return jsonArray;
    }
}
