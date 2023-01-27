# Drake Equation Simulator

### Change different galactic, solar-system and planetary variables to see if intelligent life arises!

**Q: What are the features of this application?**

- This app will give the user the ability to 
discover possible solutions to the famous **Fermi
Paradox** (given the size/age of the universe,
why don't we see other intelligent life?)
- The user will be able to **create galaxies** with
different parameters (age, size, metallicity 
distribution), and in those galaxies change variables
on the level of the **solar system** (heliosphere size, 
goldilocks zone size, number of gas giants,
unary/binary/trinary stars, etc), and then
zoom in yet again and modify **planetary** variables.
- The goal is to see just how *perfect* circumstances
need to be just for life to have a shot at emerging,
and give the user a much more intuitive understanding
of the major possible factors underlying abiogenesis.


**Q: Who will use it?**

Anyone interested in improving their knowledge
of astrobiology (ie. how life may arise in
the universe) in a fun way. The Fermi Paradox asks
"Why does it seem like we're alone?", and the Drake
Equation seems like an important part of answering the
question - anyone interested in exploring possible
solutions may be interested in using the app!

**Q: Why is this project of interest to you?**

It revolves around one of those Big Questions -
how did life really get started on Earth? Just how
difficult *is it* to really get habitable environments
on a galactic, solar or planetary level?

## Level Walkthrough (Cheatsheet)
- For galaxy, select radius=20, stars=400, age=5
- For solar system, select class = G, magnitude = 2,
planets = 13, size = 1
- No hints for planets :)

## User Stories

As a user, I want to be able to:

**Phase 1**

- Add new galaxies to the display printout
- Add new solar systems to the display printout
- Add new planets to the display printout 
- Create new galactic, solar or planetary
objects by modifying variables (eg. mass, size)
- Use the display printout to see how different variable choices
affects habitability conditions
- Get colourful text describing how good my choices are.

**Phase 2**

- Every time I create a new galaxy, solar system or planet, I want
to be asked if I want to save my progress, and have the option to do so
or not.
- Every time I start up the program, I want the option to load my progress
from my savefile or not.
- If I load my progress from my save-file, I don't want to have to repeat
already completed levels.

**Phase 3**
- As a user, I want to be able to add multiple galaxies, systems and planets to my display
and see them stored in the "view" menu.
- As a user, I want to be able to load a previously saved file when starting the program,
or save it any time during the actual interface.
- Have a slider scale/radio buttons to more easily change parameters
- Have a visual display showing me what my
    galaxy/solar system/planet looks like

**Phase 4.2**
EventLog example upon user exit where we load a previous file, make
changes and save:
(note: we are logging creating Galaxies, 
creating Solar Systems,
creating Planets,
running calculations for galaxies/solarsystems/planets,
adding Galaxies to Universe, 
adding SolarSystems to Universe,
adding Planets to Universe, loading and saving)

Fri Dec 02 17:24:55 EST 2022

LOADING INITIATED///

Fri Dec 02 17:24:55 EST 2022

Loading Galaxies:

Fri Dec 02 17:24:55 EST 2022

calculating galactic outcome

Fri Dec 02 17:24:55 EST 2022

galaxy Newgalaxy

Fri Dec 02 17:24:55 EST 2022

Galaxy Newgalaxy added to Universe

Fri Dec 02 17:24:55 EST 2022

calculating galactic outcome

Fri Dec 02 17:24:55 EST 2022

galaxy googo

Fri Dec 02 17:24:55 EST 2022

Galaxy googo added to Universe

Fri Dec 02 17:24:55 EST 2022

calculating galactic outcome

Fri Dec 02 17:24:55 EST 2022

galaxy gababa

Fri Dec 02 17:24:55 EST 2022

Galaxy gababa added to Universe

Fri Dec 02 17:24:55 EST 2022

calculating galactic outcome

Fri Dec 02 17:24:55 EST 2022

galaxy yodelio

Fri Dec 02 17:24:55 EST 2022

Galaxy yodelio added to Universe

Fri Dec 02 17:24:55 EST 2022

Galaxies Loaded.

Fri Dec 02 17:24:55 EST 2022

Loading Systems:

Fri Dec 02 17:24:55 EST 2022

Systems loaded

Fri Dec 02 17:24:55 EST 2022

Loading Planets

Fri Dec 02 17:24:55 EST 2022

Planets loaded

Fri Dec 02 17:24:55 EST 2022

///LOADING DONE

Fri Dec 02 17:25:12 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:12 EST 2022

galaxy yumo

Fri Dec 02 17:25:12 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:12 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:25:22 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:22 EST 2022

galaxy yumo

Fri Dec 02 17:25:22 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:22 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:25:27 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:27 EST 2022

galaxy yumo

Fri Dec 02 17:25:27 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:27 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:25:33 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:33 EST 2022

galaxy yumo

Fri Dec 02 17:25:33 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:33 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:25:41 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:41 EST 2022

galaxy yumo

Fri Dec 02 17:25:41 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:41 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:25:50 EST 2022

calculating galactic outcome

Fri Dec 02 17:25:50 EST 2022

galaxy yumo

Fri Dec 02 17:25:50 EST 2022

Galaxy yumo added to Universe

Fri Dec 02 17:25:50 EST 2022

selecting flavour text for galaxy

Fri Dec 02 17:26:14 EST 2022

Running calculations for solar system

Fri Dec 02 17:26:14 EST 2022

solarSystem dow created

Fri Dec 02 17:26:14 EST 2022

Solar system dow added to Universe

Fri Dec 02 17:26:22 EST 2022

Running calculations for solar system

Fri Dec 02 17:26:22 EST 2022

solarSystem dow created

Fri Dec 02 17:26:22 EST 2022

Solar system dow added to Universe

Fri Dec 02 17:26:32 EST 2022

calculating planet score

Fri Dec 02 17:26:32 EST 2022

Planet attempt1 created

Fri Dec 02 17:26:32 EST 2022

Planet attempt1 added to Universe

Fri Dec 02 17:26:33 EST 2022

Creating flavour text for planet

Fri Dec 02 17:26:53 EST 2022

Writing to file:

Fri Dec 02 17:26:53 EST 2022

Saving complete.

Process finished with exit code 0


**Phase 4: Task 3**
As evidenced by my UML Design Diagram, there is considerable overlap 
between GalaxyGUI, PlanetGUI and SolarSystemGUI. If I was going to refactor,
I'd extract many shared methods and put it into an abstract class which
they extend - that would considerably reduce repetition in the code.

Specific changes:
- add new Abstract Class, AbstractGUI
- Extract from gGUI, pGUI and sGUI all JPanel fields, JTextField and 
Universe vield
- Extract from gGUI, pGUI and sGUI constructor, and add into 
the AbstractGUI container, the JPanel intializing, but leave message dialogue
and button listeners.
In AbstractGUI, extract from gGUI, pGUI and sGUI these methods:
- _buildParameterPanel_ which takes as arguments
Jpanel panel, string decription, min and max for the JSlider (this alone would 
remove over 150 lines of code)
- make visualizeOutcome(CosmicBody body) an abstract method
- make updateLowerPanel and update RightPanel abstract
- extract cleanPanels()
- extract getFormatedImage, getImage
- extract addImagesToLowerPanel, which now should take as argument
an array of panels
- extract populateCosmicBodyPanels, which now should take as arguments
an array of panels to populate and a string description

Doing this will not add any new features, but will dramatically improve
legibility and reduce duplication.

# Instructions for Grader
 
- You can generate the first required event related to 
adding Xs to a Y by (1) running main, and sliding the slider scale
and clicking "create" a few times
to make a few different galaxies. Select "View" from the menubar
and click "View galaxies" to see the galaxies you've made.
- You can generate the second required event related to adding Xs to a Y by
proceeding to the solar system level, where you can also see radio buttons.
  (Note - my interface includes buttons, radio buttons, text fields and sliders).
- You can locate my visual component by clicking create! The visual changes
from galaxy to solar system to planet level, and also changes depending on what
kind of galaxy/system/planet you make.
- You can save the state of my application by selecting save from the menu.
- You can reload the state of my application by, on the initial start of the program,
entering the name of your save file.

# Scientific Background
- Galactic variables: https://arxiv.org/ftp/astro-ph/papers/0401/0401024.pdf
- Solar system variables (1): https://www.planetarybiology.com/calculating_habitable_zone.html
- Solar system variables (2): https://iopscience.iop.org/article/10.1088/0004-637X/810/2/105
- Calculating average distribution of planets: https://www.researchgate.net/publication/289693620_Distribution_of_Moons_in_the_Solar_System
- Planetary variables (1): Aguilar, D. A. (2008). Earth: a borderline planet for life? Harvard-Smithsonian Center for Astrophysics.
- Size of planet & habitability: https://adsabs.harvard.edu/full/1960PASP...72..489H
- How mass of planet affects feasibility of rocket launches: https://space.stackexchange.com/questions/14383/how-much-bigger-could-earth-be-before-rockets-wouldnt-work
- Radiogenic heating, habitability: https://iopscience.iop.org/article/10.3847/2041-8213/abc251