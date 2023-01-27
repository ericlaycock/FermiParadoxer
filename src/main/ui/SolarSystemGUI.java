package ui;

import model.SolarSystem;
import model.Universe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public class SolarSystemGUI {
    private JPanel container;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel rightPanel;
    private JSlider massSlider;
    private JSlider absMagSlider;
    private JSlider numPlanetsSlider;
    private JTextField myGalaxyName;
    private Universe display;
    private ButtonGroup buttonGroup;
    JLabel highEccentricityImg = new JLabel("");
    JLabel noPlanetsImg = new JLabel("");
    JLabel multiInZoneImg = new JLabel("");
    JLabel oneInOrbitImg = new JLabel("");
    JLabel overcrowdedOrbitImg = new JLabel("");

    //EFFECTS: constructor which also serves initial message dialogues, populates panels
    //and assigns a listener to the button
    public SolarSystemGUI(JPanel container, JPanel upperPanel, JPanel lowerPanel, JPanel rightPanel,
                     Universe display) {
        this.container = container;
        this.upperPanel = upperPanel;
        this.lowerPanel = lowerPanel;
        this.rightPanel = rightPanel;
        this.display = display;
        setup();

        JButton createButton = new JButton("Create!");
        upperPanel.add(createButton);
        JOptionPane.showMessageDialog(null, "Welcome. Your goal is to create a solar system with"
                + " the right conditions for planets. ");

        createButton.addActionListener(e -> {
            String outcome = handleNewSolarSystem();
            if (outcome.equals("one in GZ") || outcome.equals("multi in GZ")) {
                AtomicInteger dialogResult = new AtomicInteger((JOptionPane.showConfirmDialog(null,
                        "You built a habitable system! Would you like to "
                                + "continue to the next level? (Y/N)"
                                + "\n You'll have to build a habitable planet.")));
                if (dialogResult.get() == JOptionPane.YES_OPTION) {
                    new PlanetGUI(container,upperPanel,lowerPanel,rightPanel,display);
                }
            }
        });
    }

    //EFFECTS: sets up, restores panels + pictures
    private void setup() {
        cleanPanels();
        populateSolarPanels();
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
    private void populateSolarPanels() {
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,1));
        JPanel namePanel = new JPanel();
        JPanel classPanel = new JPanel();
        JPanel absMagPanel = new JPanel();
        JPanel numPlanetsPanel = new JPanel();
        JPanel massPanel = new JPanel();

        buildClassPanel(classPanel);
        buildAbsMagPanel(absMagPanel);
        buildNumPlanetsPanel(numPlanetsPanel);
        buildNamePanel(namePanel);
        buildMassPanel(massPanel);

        JLabel galaxyDescriptor = new JLabel("What kind of solar system do you want to make?");
        galaxyDescriptor.setForeground(Color.green);
        topLeftPanel.add(galaxyDescriptor);
        topLeftPanel.add(classPanel);
        topLeftPanel.add(absMagPanel);
        topLeftPanel.add(numPlanetsPanel);
        topLeftPanel.add(massPanel);
        topLeftPanel.add(namePanel);
        topLeftPanel.setBackground(Color.black);
        upperPanel.add(topLeftPanel);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a system mass
    private void buildMassPanel(JPanel massPanel) {
        massPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel ageDescription = new JLabel("mass of star:   1% of sun");
        ageDescription.setForeground(Color.green);
        massPanel.add(ageDescription);
        massSlider = new JSlider(1,26500);
        massSlider.setBackground(Color.black);
        massPanel.add(massSlider);
        JLabel endRange = new JLabel("265% of sun");
        endRange.setForeground(Color.green);
        massPanel.add(endRange);
        massPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a system numOfPlanets
    private void buildNumPlanetsPanel(JPanel numPlanetsPanel) {
        numPlanetsPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel ageDescription = new JLabel("Number of planets: 1");
        ageDescription.setForeground(Color.green);
        numPlanetsPanel.add(ageDescription);
        numPlanetsSlider = new JSlider(1,30);
        numPlanetsSlider.setBackground(Color.black);
        numPlanetsPanel.add(numPlanetsSlider);
        JLabel endRange = new JLabel("30");
        endRange.setForeground(Color.green);
        numPlanetsPanel.add(endRange);
        numPlanetsPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a system luminosity
    private void buildAbsMagPanel(JPanel absMagPanel) {
        absMagPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel ageDescription = new JLabel("Luminosity of star:   -10 (bright)");
        ageDescription.setForeground(Color.green);
        absMagPanel.add(ageDescription);
        absMagSlider = new JSlider(-10,20);
        absMagSlider.setBackground(Color.black);
        absMagPanel.add(absMagSlider);
        JLabel endRange = new JLabel("20 (dull)");
        endRange.setForeground(Color.green);
        absMagPanel.add(endRange);
        absMagPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a stellar class
    private void buildClassPanel(JPanel classPanel) {
        classPanel.setBackground(Color.black);
        buttonGroup = new ButtonGroup();
        JRadioButton j1 = new JRadioButton("B");
        JRadioButton j2 = new JRadioButton("A");
        JRadioButton j3 = new JRadioButton("F");
        JRadioButton j4 = new JRadioButton("G");
        JRadioButton j5 = new JRadioButton("K");
        JRadioButton j6 = new JRadioButton("M");
        stylizeButtons(j1,j2,j3,j4,j5,j6);
        buttonGroup.add(j1);
        buttonGroup.add(j2);
        buttonGroup.add(j3);
        buttonGroup.add(j4);
        buttonGroup.add(j5);
        buttonGroup.add(j6);
        JLabel descriptor = new JLabel("Choose stellar class: ");
        descriptor.setForeground(Color.green);
        addToClass(classPanel,j1,j2,j3,j4,j5,j6, descriptor);
    }

    //EFFECTS: actually add the radio buttons to the panel
    private void addToClass(JPanel classPanel, JRadioButton j1,
                            JRadioButton j2, JRadioButton j3, JRadioButton j4,
                            JRadioButton j5, JRadioButton j6, JLabel descriptor) {
        classPanel.add(descriptor);
        classPanel.add(j1);
        classPanel.add(j2);
        classPanel.add(j3);
        classPanel.add(j4);
        classPanel.add(j5);
        classPanel.add(j6);
    }

    //EFFECTS: stylize the buttons to match theme
    private void stylizeButtons(JRadioButton j1, JRadioButton j2, JRadioButton j3,
                                JRadioButton j4, JRadioButton j5, JRadioButton j6) {
        j1.setBackground(Color.black);
        j1.setForeground(Color.green);
        j2.setBackground(Color.black);
        j2.setForeground(Color.green);
        j3.setBackground(Color.black);
        j3.setForeground(Color.green);
        j4.setBackground(Color.black);
        j4.setForeground(Color.green);
        j5.setBackground(Color.black);
        j5.setForeground(Color.green);
        j6.setBackground(Color.black);
        j6.setForeground(Color.green);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a system name
    private void buildNamePanel(JPanel namePanel) {
        namePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel nameDescription = new JLabel("Choose a name for your solar system: ");
        nameDescription.setForeground(Color.green);
        namePanel.add(nameDescription);
        namePanel.setBackground(Color.black);
        myGalaxyName = new JTextField(17);
        myGalaxyName.setBackground(Color.green);
        namePanel.add(myGalaxyName);

    }


    //EFFECTS: (on button click) create solarSystem with selected parameters,
    //add it to Universe, and update the table for user to see.
    //Returns outcome for progressing to next level if successful.
    private String handleNewSolarSystem() {
        char selectedButton = getSelectedButtonText(buttonGroup).charAt(0);
        SolarSystem solarSystem = new SolarSystem(myGalaxyName.getText(), selectedButton,
                absMagSlider.getValue() / 1.0,
                numPlanetsSlider.getValue(),massSlider.getValue() / 100.0);
        display.add(solarSystem);
        visualizeOutcome(solarSystem);
        return solarSystem.getOutcome();
    }

    //REQUIRES: non-null system
    //EFFECTS: updates feedback for user (description, image and table)
    private void visualizeOutcome(SolarSystem system) {
        updateLowerPanel(system);
        updateRightPanel();

    }

    //REQUIRES: non-null buttonGroup
    //EFFECTS: (helper function) returns text of selected button
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }

    //EFFECTS: updates the table (populates it with all created systems)
    private void updateRightPanel() {
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();

        JTable galTable = new JTable();
        DefaultTableModel tm = new DefaultTableModel(new Object[] {"Name","# of Stars (billions)",
                "Radius", "Age (bya)","Outcome"},0);
        galTable.setModel(tm);
        tm.addRow(new Object[] {"Name","# of Planets",
                "luminosity of star", "mass","star class", "Outcome"});
        for (SolarSystem system : display.getSolarSystems()) {
            tm.addRow(new Object[]{system.getName(),system.getNumPlanets(),
                    system.getAbsMag(),system.getMass(),system.getStellarClass(),system.getOutcome()});
        }
        galTable.setBackground(Color.black);
        galTable.setForeground(Color.green);
        rightPanel.add(galTable);
        container.repaint();
    }

    //REQUIRES: non-null system
    //EFFECTS: updates image and description available to user
    private void updateLowerPanel(SolarSystem system) {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        buildPictures();

        if (system.getOutcome().equals("no planets in GZ")) {
            noPlanetsImg.setVisible(true);
        } else if (system.getOutcome().equals("high eccentricity")) {
            highEccentricityImg.setVisible(true);
        } else if (system.getOutcome().equals("one in GZ")) {
            oneInOrbitImg.setVisible(true);
        } else if (system.getOutcome().equals("multi in GZ")) {
            multiInZoneImg.setVisible(true);
        } else { //overcrowded orbits
            overcrowdedOrbitImg.setVisible(true);
        }
        String formatted = "<html>" + system.explainOutcome().replace("\n","")
                + "</html>";
        JLabel outcome = new JLabel(formatted.replace("Your solar system",system.getName()));
        outcome.setForeground(Color.green);
        lowerPanel.add(outcome);
        lowerPanel.repaint();
        container.repaint();
    }

    //MODIFIES: this
    //EFFECTS: initializes images from file-directory
    private void buildPictures() {
        try {
            String currentWorkingDir = System.getProperty("user.dir");
            File tooManySneFile = new File(currentWorkingDir + "/assets/noPlanets.JPG");
            noPlanetsImg = new JLabel(new ImageIcon(getFormattedImage(tooManySneFile)));
            File tooHighMetalFile = new File(currentWorkingDir + "/assets/highEccentricity.JPG");
            highEccentricityImg = new JLabel(new ImageIcon(getFormattedImage(tooHighMetalFile)));
            File tooLowMetalFile = new File(currentWorkingDir + "/assets/oneInOrbit.JPG");
            oneInOrbitImg = new JLabel(new ImageIcon(getFormattedImage(tooLowMetalFile)));
            File goodFile = new File(currentWorkingDir + "/assets/multiInZone.JPG");
            multiInZoneImg = new JLabel(new ImageIcon(getFormattedImage(goodFile)));
            File goodFile2 = new File(currentWorkingDir + "/assets/overcrowdedOrbits.JPG");
            overcrowdedOrbitImg = new JLabel(new ImageIcon(getFormattedImage(goodFile2)));

            lowerPanel.add(noPlanetsImg);
            lowerPanel.add(highEccentricityImg);
            lowerPanel.add(oneInOrbitImg);
            lowerPanel.add(multiInZoneImg);
            lowerPanel.add(overcrowdedOrbitImg);
        } catch (IOException e) {
            System.out.println("Image not found");
        }
        setVisibility();

    }

    //EFFECTS: sets initial visibility of images to false
    private void setVisibility() {
        noPlanetsImg.setVisible(false);
        highEccentricityImg.setVisible(false);
        oneInOrbitImg.setVisible(false);
        multiInZoneImg.setVisible(false);
        overcrowdedOrbitImg.setVisible(false);
    }

    //EFFECTS: (helper function) scales and reads image from file
    private static Image getFormattedImage(File tooManySNE) throws IOException {
        BufferedImage buffImage;
        buffImage = ImageIO.read(tooManySNE);
        Image resizedDefault = buffImage.getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        return resizedDefault;
    }
}
