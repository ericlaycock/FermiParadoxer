package model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlanetTest {
    Planet planet0;

    //    Test constructor
    @BeforeEach
    void setup() {
        planet0 = new Planet("test",1, 0,1,-10);
    }

    @Test
    void planetConstructorTest() {
        assertEquals("test",planet0.getName());
        assertEquals(1,planet0.getMass());
        assertEquals(0,planet0.getAxialTilt());
        assertEquals(1,planet0.getAtmosphereMass());
        assertEquals(-10,planet0.getRadioConc());
    }

    //TEST: determine that, given initial inputs,
//    a specific habitability score is returned

    @Test
    void calculateAllScoresTest() {
        Planet planetOptimal = new Planet("test",12,23,50,-7.6);
        assertEquals("95% chance of life",planetOptimal.getOutcome());

        Planet planetGood = new Planet("test",12,23,500,-7.6);
        assertEquals("80% chance of life",planetGood.getOutcome());

        Planet planetOkay = new Planet("test",6,23,500,-7.6);
        assertEquals("70% chance of life",planetOkay.getOutcome());

        Planet planetPoor = new Planet("test",6,50,500,-7.6);
        assertEquals("30% chance of life",planetPoor.getOutcome());

        Planet planetFail = new Planet("test",1,23,50,-7.6);
        assertEquals("0% chance of life",planetFail.getOutcome());

    }

    //    TEST: determine that calculateMassScore() assigns
//    correct value to score and string to remarks[] array
//    OUTCOMES: all else being equal between multiple planets,
//    we should see change in mass affect habitabilityScore
//    by -100 to +25
    @Test
    void calculateMassScoreTest(){
        Planet lightPlanet = new Planet("test",randRange(0,0.7*5.9),0,0,-10);
        Planet lightishPlanet = new Planet("test",randRange(0.7*6.1,1.5*6),0,0,-10);
        Planet mediumPlanet = new Planet("test",randRange(1.5*6,2*5.9),0,0,-10);
        Planet heavieshPlanet = new Planet("test",randRange(2*6,14.9*6),0,0,-10);
        Planet heavyPlanet = new Planet("test",randRange(15*6,30*6),0,0,-10);

        //Test first condition, where mass <= 0.7*6
        assertEquals(-295,lightPlanet.getHabitabilityScore());
        assertEquals("It has low gravity: the current atmosphere will quickly sublimate away.",
                lightPlanet.remarks[0]);

        //Test second condition, where 6 * 0.7 =< mass < 6 * 1.5
        assertEquals(-180,lightishPlanet.getHabitabilityScore());
        assertEquals("It has a small iron core, which is poor for geological activity.",
                lightishPlanet.remarks[0]);

        //Test third condition
        assertEquals(-170,mediumPlanet.getHabitabilityScore());
        assertEquals("It has a large iron core, which is good for geothermal activity.",
                mediumPlanet.remarks[0]);

        //Test fourth condition
        assertEquals(-170,heavieshPlanet.getHabitabilityScore());
        assertEquals("It's a Super-Earth! It has a massive iron core and robust geothermal"
                        + " activity, but because of how heavy it is, spaceflight from the surface is impossible.",
                heavieshPlanet.remarks[0]);

        //Test fifth condition
        assertEquals(-295,heavyPlanet.getHabitabilityScore());
        assertEquals("It's a gas giant. ", heavyPlanet.remarks[0]);
    }

    //    TEST: determine that calculateAtmosphereScore() assigns
//    correct value to score and string to remarks[] array
//    OUTCOMES: all else being equal between multiple planets,
//    we should see change in atmo mass affect habitabilityScore
//    by -100 to +25
    @Test
    void calculateAtmosphereScoreTest(){
        Planet planet1 = new Planet("test",0,0,randRange(0,0.99),-10);
        Planet planet2 = new Planet("test",0,0,randRange(1,9.99),-10);
        Planet planet3 = new Planet("test",0,0,randRange(10,99.9),-10);
        Planet planet4 = new Planet("test",0,0,randRange(100,999),-10);
        Planet planet5 = new Planet("test",0,0,randRange(1000,10000),-10);

        assertEquals(-295,planet1.getHabitabilityScore());
        assertEquals("Its outer atmosphere is extremely thin, analogous to Mars. ",
                planet1.remarks[1]);
        assertEquals(-205,planet2.getHabitabilityScore());
        assertEquals("Its outer atmosphere is 1/10 as thick as Earth's.",
                planet2.remarks[1]);
        assertEquals(-170,planet3.getHabitabilityScore());
        assertEquals("Its outer atmosphere is roughly same as on Earth.",
                planet3.remarks[1]);
        assertEquals(-185,planet4.getHabitabilityScore());
        assertEquals("Its outer atmosphere is like being 100m under water.",
                planet4.remarks[1]);
        assertEquals(-295,planet5.getHabitabilityScore());
        assertEquals("Its outer atmosphere is like being 1km underwater, analogous to Venus.",
                planet5.remarks[1]);
    }

    //    TEST: determine that calculateAxialTiltScore() assigns
//    correct value to score and string to remarks[] array
//    OUTCOMES: all else being equal between multiple planets,
//    we should see change in tilt affect habitabilityScore
//    by -20 to +20
    @Test
    void calculateTiltScoreTest(){
        Planet planet1 = new Planet("test",0,randRange(0,9.99),0,-10);
        Planet planet2 = new Planet("test",0,randRange(10,19.99),0,-10);
        Planet planet3 = new Planet("test",0,randRange(20,24.99),0,-10);
        Planet planet4 = new Planet("test",0,randRange(25,34.99),0,-10);
        Planet planet5 = new Planet("test",0,randRange(35,90),0,-10);

        assertEquals(-295,planet1.getHabitabilityScore());
        assertEquals("The planet is dominated by cold polar weather systems.",
                planet1.remarks[2]);
        assertEquals(-280,planet2.getHabitabilityScore());
        assertEquals("The planet is cooler than expected, given its distance to the sun.",
                planet2.remarks[2]);
        assertEquals(-270,planet3.getHabitabilityScore());
        assertEquals("The planet experiences diverse but gentle changes in seasons.",
                planet3.remarks[2]);
        assertEquals(-280,planet4.getHabitabilityScore());
        assertEquals("The planet experiences blistering summers and frigid winters.",
                planet4.remarks[2]);
        assertEquals(-310,planet5.getHabitabilityScore());
        assertEquals("The planet experiences frequent changes in global currents; unpredictable changes"
                        + " in seasonality; non-stop storms dominate the surface. ",
                planet5.remarks[2]);
    }

    //    TEST: determine that calculateRadioConcScore() assigns
//    correct value to score and string to remarks[] array
//    OUTCOMES: all else being equal between multiple planets,
//    we should see change in radiogenic concentration effect habScore
//    by -90 to +25

    @Test
    void calculateRadioConcScoreTest() {
        Planet planet1 = new Planet("test", 0, 0, 0, randRange(-10, -8.6));
        Planet planet2 = new Planet("test", 0, 0, 0, randRange(-8.5, -8.1));
        Planet planet3 = new Planet("test", 0, 0, 0, randRange(-8, -7.6));
        Planet planet4 = new Planet("test", 0, 0, 0, randRange(-7.5, -7.1));
        Planet planet5 = new Planet("test", 0, 0, 0, randRange(-7, -5));

        assertEquals(-205 - 90, planet1.getHabitabilityScore());
        assertEquals("The planet's core will quickly cool and shut down.",
                planet1.remarks[3]);
        assertEquals(-205 + 10, planet2.getHabitabilityScore());
        assertEquals("The planet's mantle is cool; planet's magnetic field is greatly strengthened.",
                planet2.remarks[3]);
        assertEquals(-205 + 25, planet3.getHabitabilityScore());
        assertEquals("The planet's mantle is warm; planet's magnetic field is strengthened.",
                planet3.remarks[3]);
        assertEquals(-205 + 10, planet4.getHabitabilityScore());
        assertEquals("The planet's mantle is hot; planet's magnetic field is weakened.",
                planet4.remarks[3]);
        assertEquals(-205 - 90, planet5.getHabitabilityScore());
        assertEquals("The planet's magnetic field is rapidly failing.",
                planet5.remarks[3]);
    }

    //    TEST: ensure explainOutcome is combining strings appropriately
    //    NOTE: that the individual "characteristics" strings are tested extensively in the
//    calculateMethodnameTests() above - here we just ensure they are all combined
//    and formatted as expected into a string, so we need only test a few cases.
    @Test
    void explainOutcomeTest() {
        Planet planet0 = new Planet("test",12,23,50,-7.6);
        Planet planet1 = new Planet("test",1,80,50,-7.6);

        String expectedText0 = "Your planet has a 95% chance of life. "
                + "It has the following characteristics:"
                + " It's a Super-Earth! It has a massive iron core and robust geothermal"
                + " activity, but because of how heavy it is, spaceflight from the surface is impossible."
                + " Its outer atmosphere is roughly same as on Earth."
                + " The planet experiences diverse but gentle changes in seasons."
                + " The planet's mantle is warm; planet's magnetic field is strengthened.";
        expectedText0 = expectedText0.replaceAll("(.{80})", "$1\n");

        String expectedText1 = "Your planet has a 0% chance of life. "
                + "It has the following characteristics:"
                + " It has low gravity: the current atmosphere will quickly sublimate away."
                + " Its outer atmosphere is roughly same as on Earth."
                + " The planet experiences frequent changes in global currents; unpredictable changes"
                + " in seasonality; non-stop storms dominate the surface. "
                + " The planet's mantle is warm; planet's magnetic field is strengthened.";
        expectedText1 = expectedText1.replaceAll("(.{80})", "$1\n");

        assertEquals(expectedText0,planet0.explainOutcome());
        assertEquals(expectedText1,planet1.explainOutcome());
    }

    //Returns random integer in range
    public Integer randRange(int min, int max) {
        double data = (Math.random() * (max - min)) + min;
        return (int)data;
    }

    //returns random double in range
    public Double randRange(double min, double max){
        return (Math.random() * (max - min)) + min;
    }




}