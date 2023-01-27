package ui;

import model.Planet;
import model.Universe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PlanetGUI {
    private JPanel container;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel rightPanel;
    private JSlider massSlider;
    private JSlider tiltSlider;
    private JSlider atmoMassSlider;
    private JSlider radioConcSlider;
    private JTextField myPlanetName;
    private Universe display;
    JLabel lowHabitableImg = new JLabel("");
    JLabel medHabitableImg = new JLabel("");
    JLabel highHabitableImg = new JLabel("");
    JLabel perfectHabitableImg = new JLabel("");
    JLabel frigidImg = new JLabel("");
    JLabel highPressureImg = new JLabel("");
    JLabel lowPressureImg = new JLabel("");
    JLabel highTiltImg = new JLabel("");

    //EFFECTS: constructor which also serves initial message dialogues, populates panels
    //and assigns a listener to the button
    public PlanetGUI(JPanel container, JPanel upperPanel, JPanel lowerPanel, JPanel rightPanel,
                     Universe display) {
        this.container = container;
        this.upperPanel = upperPanel;
        this.lowerPanel = lowerPanel;
        this.rightPanel = rightPanel;
        this.display = display;
        setup();
        JButton createButton = new JButton("Create!");
        upperPanel.add(createButton);
        JOptionPane.showMessageDialog(null, "Your goal now: create a habitable planet!");
        createButton.addActionListener(e -> {
            int score = handleNewPlanet();
            if (score > 0 && score < 95) {
                JOptionPane.showMessageDialog(null,
                        "Your planet is habitable, but you have not reached the maximum possible score!");
            } else if (score == 95) {
                JOptionPane.showMessageDialog(null, "You win!");
            }
        });
    }

    //EFFECTS: sets up, restores panels + pictures
    private void setup() {
        cleanPanels();
        populatePlanetPanels();
        buildPictures();
    }

    //EFFECTS: restores panels to empty default state
    private void cleanPanels() {
        upperPanel.removeAll();
        upperPanel.revalidate();
        upperPanel.repaint();
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        lowerPanel.repaint();
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    //Creates the housing/panels for the sliders + radio buttons
    private void populatePlanetPanels() {
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,1));
        JPanel namePanel = new JPanel();
        JPanel massPanel = new JPanel();
        JPanel tiltPanel = new JPanel();
        JPanel atmoMassPanel = new JPanel();
        JPanel radioConcPanel = new JPanel();

        buildNamePanel(namePanel);
        buildMassPanel(massPanel);
        buildTiltPanel(tiltPanel);
        buildAtmoMassPanel(atmoMassPanel);
        buildRadioConcPanel(radioConcPanel);

        JLabel galaxyDescriptor = new JLabel("What kind of planet do you want to make?");
        galaxyDescriptor.setForeground(Color.green);
        topLeftPanel.add(galaxyDescriptor);
        topLeftPanel.add(massPanel);
        topLeftPanel.add(tiltPanel);
        topLeftPanel.add(atmoMassPanel);
        topLeftPanel.add(radioConcPanel);
        topLeftPanel.add(namePanel);
        topLeftPanel.setBackground(Color.black);
        topLeftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        upperPanel.add(topLeftPanel);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a planet name
    private void buildNamePanel(JPanel namePanel) {
        namePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel nameDescription = new JLabel("Choose a name for your planet: ");
        nameDescription.setForeground(Color.green);
        namePanel.add(nameDescription);
        namePanel.setBackground(Color.black);
        myPlanetName = new JTextField(17);
        myPlanetName.setBackground(Color.green);
        namePanel.add(myPlanetName);

    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a planet mass
    private void buildMassPanel(JPanel agePanel) {
        agePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel ageDescription = new JLabel("Mass (10^24kg): 1");
        ageDescription.setForeground(Color.green);
        agePanel.add(ageDescription);
        massSlider = new JSlider(1,35);
        massSlider.setValue(1);
        massSlider.setBackground(Color.black);
        agePanel.add(massSlider);
        JLabel endRange = new JLabel("35");
        endRange.setForeground(Color.green);
        agePanel.add(endRange);
        agePanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a planet tilt
    private void buildTiltPanel(JPanel starsPanel) {
        starsPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel numDescription = new JLabel("Tilt degree: 0");
        numDescription.setForeground(Color.green);
        starsPanel.add(numDescription);
        tiltSlider = new JSlider(0,90);
        tiltSlider.setValue(0);
        tiltSlider.setBackground(Color.black);
        starsPanel.add(tiltSlider);
        JLabel endRange = new JLabel("90");
        endRange.setForeground(Color.green);
        starsPanel.add(endRange);
        starsPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a planet radioConc
    private void buildRadioConcPanel(JPanel starsPanel) {
        starsPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel numDescription = new JLabel("RadioConc.: 10^-10");
        numDescription.setForeground(Color.green);
        starsPanel.add(numDescription);
        radioConcSlider = new JSlider(-100,-50);
        radioConcSlider.setValue(-100);
        radioConcSlider.setBackground(Color.black);
        starsPanel.add(radioConcSlider);
        JLabel endRange = new JLabel("10^-5");
        endRange.setForeground(Color.green);
        starsPanel.add(endRange);
        starsPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a planet atmo-mass
    private void buildAtmoMassPanel(JPanel radiusPanel) {
        radiusPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel numDescription = new JLabel("Atmosphere mass: (light) 1");
        numDescription.setForeground(Color.green);
        radiusPanel.add(numDescription);
        atmoMassSlider = new JSlider(1,100000);
        atmoMassSlider.setValue(1);
        atmoMassSlider.setBackground(Color.black);
        radiusPanel.add(atmoMassSlider);
        JLabel endRange = new JLabel("(heavy) 100000");
        endRange.setForeground(Color.green);
        radiusPanel.add(endRange);
        radiusPanel.setBackground(Color.black);
    }

    //EFFECTS: (on button click) create planet with selected parameters,
    //add it to Universe, and update the table for user to see.
    //Returns score for displaying "WIN"  if successful.
    private int handleNewPlanet() {
        Planet planet = new Planet(myPlanetName.getText(), massSlider.getValue() / 1.0,
                tiltSlider.getValue() / 1.0, atmoMassSlider.getValue() / 1.0,
                radioConcSlider.getValue() / 10.0);
        display.add(planet);
        visualizeOutcome(planet);
        return planet.getHabitabilityScore();
    }

    //REQUIRES: non-null planet
    //EFFECTS: updates feedback for user (description, image and table)
    private void visualizeOutcome(Planet planet) {
        updateLowerPanel(planet);
        updateRightPanel();

    }

    //EFFECTS: updates the table (populates it with all created galaxies)
    private void updateRightPanel() {
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();

        JTable galTable = new JTable();
        DefaultTableModel tm = new DefaultTableModel(new Object[] {"Name","Mass (10^24 kg)",
                "Tilt", "Atmosphere Mass","Radionuclide conc.","Habitability %"},0);
        galTable.setModel(tm);
        tm.addRow(new Object[] {"Name","Mass (10^24 kg)",
                "Tilt", "Atmosphere Mass","Radionuclide conc.","Habitability %"});
        for (Planet planet : display.getPlanets()) {
            tm.addRow(new Object[]{planet.getName(),planet.getMass(),planet.getAxialTilt(),
                    planet.getAtmosphereMass(),planet.getRadioConc(),planet.getHabitabilityScore()});
        }
        galTable.setBackground(Color.black);
        galTable.setForeground(Color.green);
        rightPanel.add(galTable);
        container.repaint();
    }

    //REQUIRES: non-null planet
    //EFFECTS: updates image and description available to user
    private void updateLowerPanel(Planet planet) {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        buildPictures();
        assignImageVisibility(planet);

        String formatted = "<html>" + planet.explainOutcome().replace("\n","")
                + "</html>";
        JLabel outcome = new JLabel(formatted.replace("Your planet",planet.getName()));
        outcome.setForeground(Color.green);
        lowerPanel.add(outcome);
        lowerPanel.repaint();
        container.repaint();
    }

    private void assignImageVisibility(Planet planet) {
        if (planet.getHabitabilityScore() == 95) {
            perfectHabitableImg.setVisible(true);
        } else if (planet.getHabitabilityScore() <= 80 && 90 < planet.getHabitabilityScore()) {
            highHabitableImg.setVisible(true);
        } else if (50 < planet.getHabitabilityScore() && planet.getHabitabilityScore() <= 80) {
            medHabitableImg.setVisible(true);
        } else if (planet.getHabitabilityScore() > 1 && planet.getHabitabilityScore() <= 50) {
            lowHabitableImg.setVisible(true);
        } else if (planet.getAxialTilt() > 40) {
            highTiltImg.setVisible(true);
        } else if (planet.getRadioConc() < -7) {
            frigidImg.setVisible(true);
        } else if (planet.getAtmosphereMass() > 1000) {
            highPressureImg.setVisible(true);
        } else if (planet.getAtmosphereMass() < 10) {
            lowPressureImg.setVisible(true);
        } else {
            lowHabitableImg.setVisible(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes images from file-directory and sets visibility to false
    private void buildPictures() {
        try {
            
            lowHabitableImg = new JLabel(getImage("/assets/lowHabitability.JPG"));
            frigidImg = new JLabel(getImage("/assets/frigid.JPG"));
            highPressureImg = new JLabel(getImage("/assets/massivePressure.JPG"));
            lowPressureImg = new JLabel(getImage("/assets/noAtmo.jpg"));
            highTiltImg = new JLabel(getImage("/assets/highTilt.JPG"));
            medHabitableImg = new JLabel(getImage("/assets/mediumhabitability.jpg"));
            highHabitableImg = new JLabel(getImage("/assets/highHabitability.JPG"));
            perfectHabitableImg = new JLabel(getImage("/assets/perfectHabitability.jpg"));

            addImagesToLowerPanel(lowHabitableImg, frigidImg, highPressureImg, lowPressureImg,
                    highTiltImg, medHabitableImg, highHabitableImg, perfectHabitableImg);

        } catch (IOException e) {
            System.out.println("Image not found");
        }
        
        lowHabitableImg.setVisible(false);
        frigidImg.setVisible(false);
        highPressureImg.setVisible(false);
        lowPressureImg.setVisible(false);
        highTiltImg.setVisible(false);
        medHabitableImg.setVisible(false);
        highHabitableImg.setVisible(false);
        perfectHabitableImg.setVisible(false);
    }

    //EFFECTS: (helper function) passes user dir to format-helper
    private static ImageIcon getImage(String fileLoc) throws IOException {
        String currentWorkingDir = System.getProperty("user.dir");
        return new ImageIcon(getFormattedImage(new File(currentWorkingDir + fileLoc)));
    }

    //EFFECTS: adds images to the panel
    private void addImagesToLowerPanel(JLabel lowHabitableImg, JLabel frigidImg, 
                                       JLabel highPressureImg, JLabel lowPressureImg, 
                                       JLabel highTiltImg, JLabel medHabitableImg, 
                                       JLabel highHabitableImg, JLabel perfectHabitableImg) {
        lowerPanel.add(lowHabitableImg);
        lowerPanel.add(frigidImg);
        lowerPanel.add(highPressureImg);
        lowerPanel.add(lowPressureImg);
        lowerPanel.add(highTiltImg);
        lowerPanel.add(medHabitableImg);
        lowerPanel.add(highHabitableImg);
        lowerPanel.add(perfectHabitableImg);
    }

    //EFFECTS: (helper function) scales and reads image from file
    private static Image getFormattedImage(File tooManySNE) throws IOException {
        BufferedImage buffImage;
        buffImage = ImageIO.read(tooManySNE);
        Image resizedDefault = buffImage.getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        return resizedDefault;
    }
}
