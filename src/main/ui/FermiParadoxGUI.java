package ui;

import model.*;
import model.Event;
import persistence.JsonLoader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

//This is the principal GUI class; it builds the initial, globally shared frame and panel objects
//it also handles loading, saving, and displays previously created objects
public class FermiParadoxGUI extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 650;
    private JFrame frame;
    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JPanel rightPanel;
    private JPanel container;
    private EventLog el;

    private Universe display = new Universe();
    private String filePath;
    private Boolean solarLevel = false;
    private Boolean planetLevel = false;

    //EFFECTS: constructs frame/panels and executes the program
    public FermiParadoxGUI() {
        frame = new JFrame();
        frame.addMouseListener(new DesktopFocusAction());
        logOnClose(frame);
        configurePanels();
        addMenu();

        // Welcome/startup events
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Would you like to load a previous save?");
        if (dialogResult == JOptionPane.YES_OPTION) {
            loadAGame();
        }

        // Determine current level
        determineBooleans();
        //Run game
        runGame();

        frame.add(container, BorderLayout.CENTER);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fermi Paradoxer");
        frame.pack();
        frame.setVisible(true);
    }

    //EFFECTS:
    //assign Window Listener with lambda function WindowClosing
    //whose EFFECTS clause is to print out the eventLog to console and close
    private void logOnClose(JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                el = EventLog.getInstance();
                for (Event next : el) {
                    System.out.println(next.toString());
                }

                System.exit(0);
            }
        });
    }

    //MODIFIES: technically "doesn't modify this", but it calls Jswing methods that do.
    //EFFECTS: formats panels colouring, layout and adds scrollbar
    private void configurePanels() {
        container = new JPanel();
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setLayout(new GridLayout(2,2));
        container.setBackground(Color.black);

        upperPanel = new JPanel();
        upperPanel.setBorder(BorderFactory.createEmptyBorder());
        upperPanel.setLayout(new GridBagLayout());
        upperPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        upperPanel.setBackground(Color.black);

        lowerPanel = new JPanel();
        lowerPanel.setBorder(BorderFactory.createEmptyBorder());
        lowerPanel.setLayout(new BoxLayout(lowerPanel,BoxLayout.PAGE_AXIS));
        lowerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lowerPanel.setBackground(Color.black);

        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createEmptyBorder());
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.PAGE_AXIS));
        rightPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.setBackground(Color.black);

        JScrollPane rightPane = new JScrollPane(rightPanel);

        container.add(upperPanel);
        container.add(rightPane);
        container.add(lowerPanel);

    }

    //EFFECTS: determines what level to start you on, based off of previous success
    private void runGame() {
        if (!solarLevel) {
            runGalaxyLevel();
        }
        if (!planetLevel && solarLevel) {
            runSolarSystemLevel();
        }
        if (planetLevel) {
            runPlanetLevel();
        }
    }

    //EFFECTS: constructs a new PlanetGUI (and launches its corresponding functionality)
    private void runPlanetLevel() {
        new PlanetGUI(container, upperPanel, lowerPanel, rightPanel, display);
    }

    //EFFECTS: constructs a new solarsystemGUI (and launches its corresponding functionality)
    private void runSolarSystemLevel() {
        new SolarSystemGUI(container, upperPanel, lowerPanel, rightPanel, display);
    }

    //EFFECTS: constructs a new GalaxyGUI (and launches its corresponding functionality)
    private void runGalaxyLevel() {
        new GalaxyGUI(container, upperPanel, lowerPanel, rightPanel, display);

    }

    //MODIFIES: this
    //EFFECTS: Loads saved games
    private void loadAGame() {
        Boolean goodPath = false;
        while (!goodPath) {
            try {
                String fileName = JOptionPane.showInputDialog(null,
                        "What file would you like to load? Leave empty for the default",
                        "Enter file name",
                        JOptionPane.QUESTION_MESSAGE);
                if (fileName.equals("")) {
                    filePath = "./data/" + "savedData" + ".json";
                } else {
                    filePath = "./data/" + fileName + ".json";
                }
                JsonLoader newLoad = new JsonLoader(filePath);
                newLoad.load(display);
                goodPath = true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(new JFrame(), "That file name does not exist",
                        "File not found", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //EFFECTS: populates a menu bar with save and view functionality
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu systemMenu = new JMenu("Save");
        systemMenu.setMnemonic('S');
        addMenuItem(systemMenu, new AddSaveAction(),
                KeyStroke.getKeyStroke("control S"));
        menuBar.add(systemMenu);

        JMenu view = new JMenu("View");
        view.setMnemonic('v');
        addMenuItem(view, new AddGalaxyViewAction(),
                KeyStroke.getKeyStroke("control G"));
        addMenuItem(view, new AddSolarViewAction(),
                KeyStroke.getKeyStroke("control O"));
        addMenuItem(view, new AddPlanetViewAction(),
                KeyStroke.getKeyStroke("control P"));
        menuBar.add(view);

        frame.setJMenuBar(menuBar);
    }


    //ties the save click to the menu bar
    private class AddSaveAction extends AbstractAction {
        AddSaveAction() {
            super("Save current file");
        }

        //MODIFIES: this
        //EFFECTS: ties the save action to the menu bar.
        @Override
        public void actionPerformed(ActionEvent evt) {
            String fileName = JOptionPane.showInputDialog(null,
                    "Choose a save name for your file (leave blank for default)",
                    "Enter file name",
                    JOptionPane.QUESTION_MESSAGE);
            if (fileName.equals("")) {
                filePath = "./data/" + "savedData" + ".json";
            } else {
                filePath = "./data/" + fileName + ".json";
            }
            JsonWriter writer = new JsonWriter(filePath);
            try {
                writer.open();
                writer.write(display);
                writer.close();
            } catch (IOException e) {
                //
            }

        }
    }

    //ties the galaxy-view click to the menu bar
    private class AddGalaxyViewAction extends AbstractAction {
        AddGalaxyViewAction() {
            super("View all galaxies");
        }

        //EFFECTS: populates the right panel with all previously created galaxies
        @Override
        public void actionPerformed(ActionEvent evt) {
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
    }

    //Ties the solar-view click to the menu bar
    private class AddSolarViewAction extends AbstractAction {
        AddSolarViewAction() {
            super("View all solar systems");
        }

        //EFFECTS: populates the right panel with all previously created systems
        @Override
        public void actionPerformed(ActionEvent evt) {
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
    }

    //ties the planet-view click to the menu bar
    private class AddPlanetViewAction extends AbstractAction {
        AddPlanetViewAction() {
            super("View all planets");
        }

        //EFFECTS: populates the right panel with all previously created planets
        @Override
        public void actionPerformed(ActionEvent evt) {
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
    }

    /**
     * Effects: Adds an item with given handler to the given menu
     * @param theMenu  menu to which new item is added
     * @param action   handler for new menu item
     * @param accelerator    keystroke accelerator for this menu item
     */
    private void addMenuItem(JMenu theMenu, AbstractAction action, KeyStroke accelerator) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setMnemonic(menuItem.getText().charAt(0));
        menuItem.setAccelerator(accelerator);
        theMenu.add(menuItem);
    }

    //Gives mouse click appropriate focus functionality
    private class DesktopFocusAction extends MouseAdapter {

        //Effects: Gives mouse click appropriate focus functionality
        @Override
        public void mouseClicked(MouseEvent e) {
            FermiParadoxGUI.this.requestFocusInWindow();
        }
    }

    //MODIFIES: this
    //EFFECTS: establishes Boolean values for the levels (ie. avoid repeating levels)
    private void determineBooleans() {
        for (Galaxy galaxy : display.getGalaxies()) {
            if (galaxy.getOutcome().equals("good")) {
                solarLevel = true;
                break;
            }
        }
        for (SolarSystem system: display.getSolarSystems()) {
            if (system.getOutcome().equals("one in GZ")
                    || system.getOutcome().equals("multi in GZ")) {
                planetLevel = true;
                break;
            }
        }
        if (solarLevel && !planetLevel) {
            System.out.println("Since you already made a successful galaxy, it's time to make a solar system.");
        } else if (planetLevel) {
            System.out.println("As you've already made good galaxies/systems, it's time to make a planet.");
        }
    }
}
