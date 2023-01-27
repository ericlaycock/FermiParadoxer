package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class SolarSystemTest {
    String starName;
    char stellarClass;
    double absMag; // -10 <= absMag <= 20
    int numPlanets; // 1 to 30
    double mass; //.01 to 265
    SolarSystem sol1;

    @BeforeEach
    void setup(){
        absMag = randRange(-10.0,20.0);
        numPlanets = randRange(1,30);
        mass = randRange(0.01,265);
        starName = "test";
        stellarClass = 'G';
        sol1 = new SolarSystem(starName, stellarClass,absMag,numPlanets, mass);

    }

    // TEST: Check that inputted vars are correctly stored at all ranges.
// OUTCOME: getter should return expected value
    @Test
    void constructorTest(){
        SolarSystem sol2 = new SolarSystem(starName,stellarClass,absMag,numPlanets, mass);
        assertEquals(starName,sol2.getName());
        assertEquals(stellarClass,sol2.getStellarClass());
        assertEquals(absMag,sol2.getAbsMag());
        assertEquals(numPlanets,sol2.getNumPlanets());
        assertEquals(mass,sol2.getMass());

    }

//    NOTE: We can't test the PlacePlanets() class, as it randomly assigns values
//

    // TEST:Check that expected remarks are generated, 
// OUTCOME: We should get textfile line 0, 1, 2, 3 and 4 for outcome codes
//          "too many SNE, too low metal, good, too high metal and too little time", respectively.
    @Test
    void explainOutcomeTest(){
        ArrayList<String> explanationArray = grabTextFile();
        SolarSystem solNoPlanets = new SolarSystem("test",'G',
                2,0, 1);
        SolarSystem solHighEccentricity = new SolarSystem("test",'G',2,2,1);
        SolarSystem solMultiPlanets = new SolarSystem("test",'G',
                2,13,1);
        SolarSystem solManyPlanets = new SolarSystem("test",'G',
                2,30, 1);

//        Here we grab and add newlines to all the text files
        String textFile0 = explanationArray.get(0).replaceAll("(.{80})", "$1\n");
        String textFile1 = explanationArray.get(1).replaceAll("(.{80})", "$1\n");
        String textFile2 = explanationArray.get(2).replaceAll("(.{80})", "$1\n");
        String textFile3 = explanationArray.get(3).replaceAll("(.{80})", "$1\n");
        String textFile4 = explanationArray.get(4).replaceAll("(.{80})", "$1\n");

        //...and verify that we're getting expected explanation strings for outcome
        //no planets in GZ
        assertEquals(textFile0,solNoPlanets.explainOutcome());
        assertNotEquals(textFile0, solManyPlanets.explainOutcome());

        //high eccentricity
        solHighEccentricity.setNumGoodPlanets(1);
        solHighEccentricity.calculateOutcome();
        assertEquals(textFile1,solHighEccentricity.explainOutcome());
        assertNotEquals(textFile0,solHighEccentricity.explainOutcome());

        //one in GZ
        solNoPlanets.setNumPlanets(6);
        solNoPlanets.setNumGoodPlanets(1);
        solNoPlanets.calculateOutcome();
        assertEquals(textFile2, solNoPlanets.explainOutcome());
        assertNotEquals(textFile1, solNoPlanets.explainOutcome());

        //multi in GZ
        solMultiPlanets.setNumGoodPlanets(3);
        solMultiPlanets.calculateOutcome();
        assertEquals(textFile3,solMultiPlanets.explainOutcome());
        assertNotEquals(textFile1,solMultiPlanets.explainOutcome());

        //Overcrowded orbits
        assertEquals(textFile4,solManyPlanets.explainOutcome());
        assertNotEquals(textFile3,solManyPlanets.explainOutcome());
    }

    //    TEST: Ensure that outcome codes are mapped depending on number of planets and planets in GZ
//    NOTE: because there are multiple boundaries not easily spotted in the code, I'm implementing
//    custom function randRange (which uses Math.random()) to help verify that random
//    intermediate/edge cases don't cause any bugs
//    OUTCOME: we should get outcomes "no planets in GZ", "high eccentricity", etc. as per code below
    @Test
    void calculateOutcomeTest(){
        sol1.setNumGoodPlanets(0);
        sol1.calculateOutcome();
        assertEquals(sol1.getOutcome(),"no planets in GZ");

        sol1.setNumGoodPlanets(1);
        sol1.setNumPlanets(randRange(0,5));
        sol1.calculateOutcome();
        assertEquals(sol1.getOutcome(),"high eccentricity");

        sol1.setNumPlanets(randRange(16,30));
        sol1.calculateOutcome();
        assertEquals(sol1.getOutcome(),"overcrowded orbits");

        sol1.setNumPlanets(randRange(6,16));
        sol1.setNumGoodPlanets(1);
        sol1.calculateOutcome();
        assertEquals(sol1.getOutcome(),"one in GZ");

        sol1.setNumGoodPlanets(randRange(2,30));
        sol1.calculateOutcome();
        assertEquals(sol1.getOutcome(),"multi in GZ");
    }

    //    TEST: Check that habitable zone radii and planet numbers are calculated as expected
//    OUTCOME: radii should match values below
    @Test
    void runCalculationsTest(){
        //Test at hottest class (B) and brightest magnitude (-10)
        SolarSystem solHot = new SolarSystem("test",'B', -10,1,1);
        assertEquals(solHot.getRadii()[0],Math.sqrt(Math.pow(10, 6.688) / 1.1));
        assertEquals(solHot.getRadii()[1],Math.sqrt(Math.pow(10, 6.688) / 0.53));

        //Test intermediate case of medium-hot class (G) and middle magnitude (5)
        SolarSystem solMed = new SolarSystem("test",'G', 5,1,1);
        assertEquals(solMed.getRadii()[0],Math.sqrt(Math.pow(10, 0.048) / 1.1));
        assertEquals(solMed.getRadii()[1],Math.sqrt(Math.pow(10, 0.048) / 0.53));

        //Test coldest class (M) and dullest magnitude (20)
        SolarSystem solCold = new SolarSystem("test",'M', 20,1,1);
        assertEquals(solCold.getRadii()[0],Math.sqrt(Math.pow(10, -5.312) / 1.1));
        assertEquals(solCold.getRadii()[1],Math.sqrt(Math.pow(10, -5.312) / 0.53));
    }

    //Test: Here we essentially want to test if the private placePlanets()
    // method is working
//    The problem is, because placePlanets uses Math.random(), we cannot predict
//    specific outcomes.
//    What we *can* check is that numGoodPlanets is either the same value or
//    higher as before.
    @Test
    public void numGoodPlanetsTest(){
        SolarSystem solOnePlanet = new SolarSystem("test",'G',2,1,1);
        int firstVal = solOnePlanet.getNumGoodPlanets();
        solOnePlanet.setNumPlanets(14);
        solOnePlanet.runCalculations();
        int secondVal = solOnePlanet.getNumGoodPlanets();
        assertTrue(firstVal <= secondVal);
    }

    //    TEST: Need to ensure that when fileName for getArrayFromFile is invalid
//    exception is caught.
    @Test
    void getArrayFromFileExceptionTest(){
        sol1.setFileName("invalidText.txt");
        sol1.explainOutcome();
        assertEquals("error", sol1.explainOutcome());
    }

    //    Helper function for our tests - this grabs our array, which we need to test explainOutcome()
    public ArrayList grabTextFile(){
        ArrayList<String> explanationArray = new ArrayList<>();
        String currentWorkingDir = System.getProperty("user.dir");
        File file = new File(currentWorkingDir + "/data/solarText.txt");

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                explanationArray.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
//            do nothing
        }
        return explanationArray;
    }

    //Helper function: generates random range (integer)
    public Integer randRange(int min, int max) {
        double data = (Math.random() * (max - min)) + min;
        return (int)data;
    }

    //Helper function: generates random range (double)
    public Double randRange(double min, double max){
        return (Math.random() * (max - min)) + min;
    }


}

