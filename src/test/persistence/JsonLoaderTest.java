package persistence;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import model.Universe;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//Test that JsonLoader works as expected
public class JsonLoaderTest {
    String JSON_STORE;
    JsonLoader jsonLoader;

    //Initial setup
    @BeforeEach
    void setup(){
        JSON_STORE = "./data/loadTestData.json";
        jsonLoader = new JsonLoader(JSON_STORE);
    }

    //Test that the constructors returned stored string
    @Test
    void constructorTest(){
        assertEquals(JSON_STORE,jsonLoader.getSource());
    }

    //Test that load correctly executes if pathname is valid,
    //or fails if IOException is thrown.
    @Test
    void loadTestExpectNoException(){
        //before loading:
        Universe display = new Universe();
        assertEquals(0,display.getGalaxies().size());
        assertEquals(0,display.getSolarSystems().size());
        assertEquals(0,display.getPlanets().size());

        try {
            jsonLoader.load(display);
        } catch (IOException e) {
            fail("IOException not expected.");
        }
        //after loading:
        //Instead of testing every element in display,
        //we can just verify lengths for each sub-array,
        //as they are sufficiently idiosyncratic
        assertEquals(3,display.getGalaxies().size());
        assertEquals(1,display.getSolarSystems().size());
        assertEquals(4,display.getPlanets().size());

    }

    //Test that load does not execute, as the pathname is invalid
    @Test
    void loadTestExpectIOException(){
        //before loading:
        JsonLoader jsonLoader1 = new JsonLoader("./data/error.json");
        Universe display = new Universe();
        assertEquals(0,display.getGalaxies().size());
        assertEquals(0,display.getSolarSystems().size());
        assertEquals(0,display.getPlanets().size());

        try {
            jsonLoader1.load(display);
            fail("Invalid pathname should not have succeeded.");
        } catch (IOException e) {
            //correctly functioning
        }
        //after loading:
        //Instead of testing every element in display,
        //we can just verify lengths for each sub-array,
        //as they are sufficiently idiosyncratic
        assertEquals(0,display.getGalaxies().size());
        assertEquals(0,display.getSolarSystems().size());
        assertEquals(0,display.getPlanets().size());

    }

}
