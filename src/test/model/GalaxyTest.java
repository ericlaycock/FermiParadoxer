package model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GalaxyTest {
    String galaxyName;
    double minStars;
    double maxStars;
    int minAge;
    int maxAge;
    int minRadius;
    int maxRadius;
    Galaxy gal1;

    @BeforeEach
    void setup(){
        galaxyName = "test";
        minStars = 0.1;
        maxStars = 10000;
        minAge = 0;
        maxAge = 14;
        minRadius = 1;
        maxRadius = 100;

        gal1 = new Galaxy(galaxyName,minRadius,minStars,minAge);
    }

    // TEST: Check that inputted vars are correctly stored at all ranges.
    // OUTCOME: getter should return expected value
    @Test
    void constructorTest(){
        Galaxy gal1 = new Galaxy(galaxyName,minRadius,minStars,minAge);
        assertEquals(galaxyName,gal1.getName());
        assertEquals(minRadius,gal1.getRadius());
        assertEquals(minStars,gal1.getStars());
        assertEquals(minAge,gal1.getAge());

        Galaxy gal2 = new Galaxy(galaxyName,maxRadius,maxStars,maxAge);
        assertEquals(galaxyName,gal2.getName());
        assertEquals(maxRadius,gal2.getRadius());
        assertEquals(maxStars,gal2.getStars());
        assertEquals(maxAge,gal2.getAge());
    }

    // TEST:Check that expected paragraph is printed to screen, given outcome code.
    // OUTCOME: We should get textfile line 0, 1, 2, 3 and 4 for outcome codes
    //          "too many SNE, too low metal, good, too high metal and too little time", respectively.
    @Test
    void explainOutcomeTest(){
        ArrayList<String> explanationArray = grabTextFile("galaxyText.txt");
        //Here we grab and add newlines to all the text files
        String textFile0 = explanationArray.get(0).replaceAll("(.{80})", "$1\n");
        String textFile1 = explanationArray.get(1).replaceAll("(.{80})", "$1\n");
        String textFile2 = explanationArray.get(2).replaceAll("(.{80})", "$1\n");
        String textFile3 = explanationArray.get(3).replaceAll("(.{80})", "$1\n");
        String textFile4 = explanationArray.get(4).replaceAll("(.{80})", "$1\n");

        //Outcome: too many SNE
        Galaxy gal1 = new Galaxy(galaxyName,100,randRange(2050,10000),randRange(9,14));
        assertEquals("too many SNE", gal1.getOutcome());
        assertEquals(textFile0, gal1.explainOutcome());

        //Outcome: too low metal
        Galaxy gal2 = new Galaxy(galaxyName,1000,randRange(0,1000),randRange(0,2));
        assertEquals("too low metal",gal2.getOutcome());
        assertEquals(textFile1, gal2.explainOutcome());

        //Outcome: good
        Galaxy gal3 = new Galaxy(galaxyName,99,randRange(1000.0,1428.0),randRange(3,8));
        assertEquals("good",gal3.getOutcome());
        assertEquals(textFile2, gal3.explainOutcome());

        //Outcome: too high metal
        Galaxy gal4 = new Galaxy(galaxyName,100,randRange(4000,10000.0),randRange(0,5));
        assertEquals("too high metal",gal4.getOutcome());
        assertEquals(textFile3, gal4.explainOutcome());

        //Outcome: too little time
        Galaxy gal5 = new Galaxy(galaxyName,10,randRange(62.5,240),randRange(0,2));
        assertEquals("too little time",gal5.getOutcome());
        assertEquals(textFile4, gal5.explainOutcome());
    }

    //    TEST: Ensure that initial values results in expected outcome code
//    OUTCOME: tests are self-explanatory - given particular inputs, we expect a particular outcome code
//              to be generated, thus verifying the calculations behind the scene are working as expected
//    NOTE: because there are multiple boundaries not easily spotted in the code, I'm implementing
//    Math.random() to help verify that random intermediate/edge cases don't cause any bugs
    @Test
    void calculateOutcomeTest(){
        //NOTE: To explain this first test, we initialize a galaxy with radius of 100,
        // and some random number from 2500 to 10k, and an age
        //which is some random integer from 9 to 14

        //test different ages
        Galaxy galOld = new Galaxy(galaxyName,100,randRange(2500,10000),randRange(9,14));
        assertEquals("too many SNE",galOld.getOutcome());
        Galaxy galSemiOld = new Galaxy(galaxyName,100,randRange(2500,10000),randRange(6,9));
        assertEquals("too many SNE",galSemiOld.getOutcome());
        Galaxy galSemiYoung = new Galaxy(galaxyName,12,100,randRange(3,5));
        assertEquals("too little time",galSemiYoung.getOutcome());
        Galaxy galYoung = new Galaxy(galaxyName,10,randRange(62.5,240),randRange(0,2));
        assertEquals("too little time",galYoung.getOutcome());

        //too high metal
        Galaxy galHighMetal1 = new Galaxy(galaxyName,10,10000,randRange(0,5));
//        ...and it should calculate that the outcome is "too high metal"
        assertEquals("too high metal",galHighMetal1.getOutcome());
        Galaxy galHighMetal2 = new Galaxy(galaxyName,1,10000,randRange(0,5));
        assertEquals("too high metal",galHighMetal2.getOutcome());
        Galaxy galHighMetal3 = new Galaxy(galaxyName,100,randRange(4000,10000.0),randRange(0,5));
        assertEquals("too high metal",galHighMetal3.getOutcome());

//        OUTCOME: Too little time
        Galaxy galLittleTime1 = new Galaxy(galaxyName,10,randRange(62.5,240),randRange(0,2));
        assertEquals("too little time",galLittleTime1.getOutcome());
        Galaxy galLittleTime2 = new Galaxy(galaxyName,12,100,randRange(3,5));
        assertEquals("too little time",galLittleTime2.getOutcome());

//        OUTCOME: good
        Galaxy galGood1 = new Galaxy(galaxyName,99,randRange(1000.0,1428.0),randRange(3,8));
        assertEquals("good",galGood1.getOutcome());
        Galaxy galGood2 = new Galaxy(galaxyName,randRange(61,98),1000,randRange(3,8));
        assertEquals("good",galGood2.getOutcome());

        //Outcome: too low metal
        Galaxy galLowMetal1 = new Galaxy(galaxyName,1000,randRange(0,1000),randRange(0,2));
        assertEquals("too low metal",galLowMetal1.getOutcome());
        Galaxy galLowMetal2 = new Galaxy(galaxyName,1000,randRange(1,6000),randRange(3,5));
        assertEquals("too low metal",galLowMetal2.getOutcome());
        Galaxy galLowMetal3 = new Galaxy(galaxyName,1,1,randRange(6,8));
        assertEquals("too low metal",galLowMetal3.getOutcome());

        //Outcome: too many SNE
        Galaxy galSNE1 = new Galaxy(galaxyName,100,randRange(2050,10000),randRange(6,14));
        assertEquals("too many SNE",galSNE1.getOutcome());
        Galaxy galSNE2 = new Galaxy(galaxyName,1,randRange(25,10000),randRange(6,14));
        assertEquals("too many SNE",galSNE2.getOutcome());


    }

    //    TEST: Need to ensure that when fileName for getArrayFromFile is invalid
    //    exception is caught.
    @Test
    void getArrayFromFileExceptionTest(){
        gal1.setFileName("invalidText.txt");
        assertEquals("error", gal1.explainOutcome());
        gal1.setFileName("testOnly.txt");
        assertThrows(IndexOutOfBoundsException.class, () -> gal1.explainOutcome());
    }


    //    Helper function for our tests - this grabs our array, which we need to test explainOutcome()
    public ArrayList grabTextFile(String filepath){
        ArrayList<String> explanationArray = new ArrayList<>();
        String currentWorkingDir = System.getProperty("user.dir");
        File file = new File(currentWorkingDir + "/data/" + filepath);

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                explanationArray.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
//            //do nothing
        }
        return explanationArray;
    }

    //returns random integer in range
    public Integer randRange(int min, int max) {
        double data = (Math.random() * (max - min)) + min;
        return (int)data;
    }

    //returns random double in range
    public Double randRange(double min, double max){
        return (Math.random() * (max - min)) + min;
    }



}

