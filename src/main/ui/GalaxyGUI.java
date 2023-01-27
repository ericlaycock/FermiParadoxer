package ui;

import model.Galaxy;
import model.Universe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

// Constructs all Galaxy-related GUI functionality (images, buttons, dialogues, etc)
public class GalaxyGUI {
    private JPanel container;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel rightPanel;
    private JSlider radiusSlider;
    private JSlider starsSlider;
    private JSlider ageSlider;
    private JTextField myGalaxyName;
    private Universe display;
    JLabel tooLittleMetalImg = new JLabel("");
    JLabel tooManySneImg = new JLabel("");
    JLabel tooHighMetalImg = new JLabel("");
    JLabel goodImg = new JLabel("");

    //EFFECTS: constructor which also serves initial message dialogues, populates panels
    //and assigns a listener to the button
    public GalaxyGUI(JPanel container, JPanel upperPanel, JPanel lowerPanel, JPanel rightPanel,
                     Universe display) {
        this.container = container;
        this.upperPanel = upperPanel;
        this.lowerPanel = lowerPanel;
        this.rightPanel = rightPanel;
        this.display = display;
        JOptionPane.showMessageDialog(null, "Welcome. Your goal is to create a galaxy "
                + "with the right conditions for habitable solar systems. ");
        setup();
        JButton createButton = new JButton("Create!");
        upperPanel.add(createButton);

        createButton.addActionListener(e -> {
            String outcome = handleNewGalaxy();
            if (outcome.equals("good")) {
                AtomicInteger dialogResult = new AtomicInteger(JOptionPane.showConfirmDialog(null,
                        "You built a habitable galaxy! Would you like to "
                                + "continue to the next level? (Y/N)"
                                + "\n You'll have to build a habitable solar system."));
                if (dialogResult.get() == JOptionPane.YES_OPTION) {
                    new SolarSystemGUI(container, upperPanel, lowerPanel, rightPanel, display);
                }
            }
        });
    }

    //EFFECTS: sets up panels + pictures
    private void setup() {
        populateGalaxyPanels();
        buildPictures();
    }

    //Creates the housing/panels for the sliders
    private void populateGalaxyPanels() {
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(5,1));
        JPanel namePanel = new JPanel();
        JPanel radiusPanel = new JPanel();
        JPanel starsPanel = new JPanel();
        JPanel agePanel = new JPanel();

        buildRadiusPanel(radiusPanel);
        buildStarsPanel(starsPanel);
        buildAgePanel(agePanel);
        buildNamePanel(namePanel);

        JLabel galaxyDescriptor = new JLabel("What kind of galaxy do you want to make?");
        galaxyDescriptor.setForeground(Color.green);
        topLeftPanel.add(galaxyDescriptor);
        topLeftPanel.add(radiusPanel);
        topLeftPanel.add(starsPanel);
        topLeftPanel.add(agePanel);
        topLeftPanel.add(namePanel);
        topLeftPanel.setBackground(Color.black);
        topLeftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        upperPanel.add(topLeftPanel);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a galactic name
    private void buildNamePanel(JPanel namePanel) {
        namePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel nameDescription = new JLabel("Choose a name for your galaxy: ");
        nameDescription.setForeground(Color.green);
        namePanel.add(nameDescription);
        namePanel.setBackground(Color.black);
        myGalaxyName = new JTextField(17);
        myGalaxyName.setBackground(Color.green);
        namePanel.add(myGalaxyName);

    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a galactic age
    private void buildAgePanel(JPanel agePanel) {
        agePanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel ageDescription = new JLabel("Age (billion years old): 0");
        ageDescription.setForeground(Color.green);
        agePanel.add(ageDescription);
        ageSlider = new JSlider(0,14);
        ageSlider.setBackground(Color.black);
        agePanel.add(ageSlider);
        JLabel endRange = new JLabel("14");
        endRange.setForeground(Color.green);
        agePanel.add(endRange);
        agePanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a galactic num of stars
    private void buildStarsPanel(JPanel starsPanel) {
        starsPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel numDescription = new JLabel("Number of stars (billions): 1");
        numDescription.setForeground(Color.green);
        starsPanel.add(numDescription);
        starsSlider = new JSlider(1,10000);
        starsSlider.setBackground(Color.black);
        starsPanel.add(starsSlider);
        JLabel endRange = new JLabel("10000");
        endRange.setForeground(Color.green);
        starsPanel.add(endRange);
        starsPanel.setBackground(Color.black);
    }

    //MODIFIES: this
    //EFFECTS: builds panel and functionality for entering a galactic radius
    private void buildRadiusPanel(JPanel radiusPanel) {
        radiusPanel.setBorder(BorderFactory.createEmptyBorder());
        JLabel numDescription = new JLabel("Radius (kiloparsecs): 1");
        numDescription.setForeground(Color.green);
        radiusPanel.add(numDescription);
        radiusSlider = new JSlider(1,100);
        radiusSlider.setBackground(Color.black);
        radiusPanel.add(radiusSlider);
        JLabel endRange = new JLabel("100");
        endRange.setForeground(Color.green);
        radiusPanel.add(endRange);
        radiusPanel.setBackground(Color.black);
    }

    //EFFECTS: (on button click) create galaxy with selected parameters,
    //add it to Universe, and update the table for user to see.
    //Returns outcome for progressing to next level if successful.
    private String handleNewGalaxy() {
        Galaxy galaxy = new Galaxy(myGalaxyName.getText(), radiusSlider.getValue(),
                starsSlider.getValue(), ageSlider.getValue());
        display.add(galaxy);
        visualizeOutcome(galaxy);
        return galaxy.getOutcome();
    }

    //REQUIRES: non-null galaxy
    //EFFECTS: updates feedback for user (description, image and table)
    private void visualizeOutcome(Galaxy galaxy) {
        updateLowerPanel(galaxy);
        updateRightPanel();

    }

    //EFFECTS: updates the table (populates it with all created galaxies)
    private void updateRightPanel() {
        rightPanel.removeAll();
        rightPanel.revalidate();
        rightPanel.repaint();

        JTable galTable = new JTable();
        DefaultTableModel tm = new DefaultTableModel(new Object[] {"Name","# of Stars (billions)",
                "Radius", "Age (bya)","Outcome"},0);
        galTable.setModel(tm);
        tm.addRow(new Object[] {"Name","# of Stars (billions)",
                "Radius", "Age (bya)","Outcome"});
        for (Galaxy galaxy : display.getGalaxies()) {
            tm.addRow(new Object[]{galaxy.getName(),galaxy.getStars(),galaxy.getRadius(),
                    galaxy.getAge(),galaxy.getOutcome()});
        }
        galTable.setBackground(Color.black);
        galTable.setForeground(Color.green);
        rightPanel.add(galTable);
        container.repaint();
    }

    //REQUIRES: non-null galaxy
    //EFFECTS: updates image and description available to user
    private void updateLowerPanel(Galaxy galaxy) {
        lowerPanel.removeAll();
        lowerPanel.revalidate();
        buildPictures();

        if (galaxy.getOutcome().equals("too many SNE")) {
            tooManySneImg.setVisible(true);
        } else if (galaxy.getOutcome().equals("too high metal")) {
            tooHighMetalImg.setVisible(true);
        } else if (galaxy.getOutcome().equals("good")) {
            goodImg.setVisible(true);
        } else {
            tooLittleMetalImg.setVisible(true);
        }
        String formatted = "<html>" + galaxy.explainOutcome().replace("\n","")
                + "</html>";
        JLabel outcome = new JLabel(formatted.replace("Your galaxy",galaxy.getName()));
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
            File tooManySneFile = new File(currentWorkingDir + "/assets/tooManySNE.JPG");
            tooManySneImg = new JLabel(new ImageIcon(getFormattedImage(tooManySneFile)));
            File tooHighMetalFile = new File(currentWorkingDir + "/assets/tooHighMetal.JPG");
            tooHighMetalImg = new JLabel(new ImageIcon(getFormattedImage(tooHighMetalFile)));
            File tooLowMetalFile = new File(currentWorkingDir + "/assets/DefaultGalaxy.JPG");
            tooLittleMetalImg = new JLabel(new ImageIcon(getFormattedImage(tooLowMetalFile)));
            File goodFile = new File(currentWorkingDir + "/assets/good.JPG");
            goodImg = new JLabel(new ImageIcon(getFormattedImage(goodFile)));

            lowerPanel.add(tooManySneImg);
            lowerPanel.add(tooHighMetalImg);
            lowerPanel.add(tooLittleMetalImg);
            lowerPanel.add(goodImg);
        } catch (IOException e) {
            System.out.println("Image not found");
        }
        tooLittleMetalImg.setVisible(false);
        tooHighMetalImg.setVisible(false);
        tooManySneImg.setVisible(false);
        goodImg.setVisible(false);
    }

    //EFFECTS: scales and reads image from file
    private static Image getFormattedImage(File tooManySNE) throws IOException {
        BufferedImage buffImage;
        buffImage = ImageIO.read(tooManySNE);
        Image resizedDefault = buffImage.getScaledInstance(250, 150, Image.SCALE_DEFAULT);
        return resizedDefault;
    }
}
