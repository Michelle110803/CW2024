# SKY BATTLE SNOOPY ADVENTURE

### Repository Link: 

Snoopy Adventure Game is a fun and interactive game where players navigate through levels
as Snoopy on his doghouse, encountering challenges and enjoying engaging gameplay.

## Instructions: 
1. Download and Extract the Game
    - Download the game's ZIP file from the github repository link above
    - Extract the contents
2. Set up the environment
    - With your preferred IDE (Intellij is recommended)
    - Verify Maven is installed
3. Open the Project 
    - open the project in your IDE
4. Run the game: 
    - Locate the Main.java class in the project directory:
        src/main/java/com/example/demo/controller/Main.java
    - run the Main class to launch the game
   
**Prerequisites:**
    - Java JDK 19 or higher
    - Intellij IDEA with JavaFX support.

## Features Implemented and Working Properly
1. Main Menu 
    - Description: A visually designed Snoopy-themed main menu 
    - Status: Fully functional with buttons and background music
2. Game levels 
    - Description: 
      - Level 2:  enemy's plane has a shield that will activate on a random time 
                      if the user's projectile hit the shield, it won't decrease the boss' health, after the boss takes
                        a certain amount of hits, it will move to level 3
      - Status: fully functional 
      - Level 3: similar to level 1 with multiple enemy's plane but added obstacles that will pop up randomly a
                across the screen and when the user plane hit the obstacle, it will decrease the user's health
      - Status: Obstacles fully functional 
   
3. Background Music
    - Description: Continuous music plays during the game and menus.
    - Status: Functional but needs improvements to avoid overlapping. 

4. Added Custom Bounds 
    - Description: Custom bounds for the planes and projectiles 
    - Status: Fully functional
   
5. Shield cooldown mechanics
    - Description: Cooldown mechanics for the shield 
    - Status: fully functional

## Features Implemented but Not Working Properly
1. Volume slider logic
    - Description: the slider adjusts volume, but overlapping audio persists between menus
    - Issue: Looping audio logic causes multiple layer of music
    - Steps taken: debugging the SoundManager class to handle stopping music 
2. Home button in Instructions Menu
    - Description: button does not  show in the Instructions Menu
    - Steps taken: added debugging comments
3. PowerUp in level 3 
    - Description: when userplane hit the powerup display, it should add the userplane's health
    - Issue: the display for the health is not adding up when the userplane collide with the powerup display
    - Steps Taken: added debugging message, which works in the debugging message but the image does not show up 

## Features not implemented
1. Pop up menus in home menu
    - Description: pop up menu for instructions menu and settings menu instead of taking up the whole screen
    - Reason: Javafx's integration with Mac
2. Menu on ongoing game (Pause, restart, volume button, resume, etc)
    - Description: Menu settings for ongoing game
    - Reason: Limited time for implementation 
3. Mini boss on level 3 
    - Description: To make it more challenging, add mini boss to spawn little minions that will attack 
                    the userplane, equipped with a shield that will pop up on a random time and projectile 
                    that will take double the user's health
   

## New Java classes
1. SettingsMenu.java
    - Purpose: Controls the settings menu, volume adjustment 
    - Location: 'src/main/java/com/example/demo/menus/SettingsMenu.java'
2. HomeMenu.java
    - Purpose: Direct the player with buttons available: Start game, instructions, settings, and exit game
    - Location: 'src/main/java/com/example/demo/menus/HomeMenu.java'
3. MenuParent.java
    - Purpose: Base class for all menus, handles common menu functionalities like navigation and background music
    - Location: 'src/main/java/com/example/demo/menus/Menuparent.java'
4. InstructionsMenu.java
    - Purpose: Displays the game instructions and navigates back to the main menu
    - Location: 'src/main/java/com/example/demo/menus/InstructionsMenu.java'
5. LevelThree.java
    - Purpose: represents the logic and structure for the third game level
    - Location: 'src/main/java/com/example/demo/Levels/LevelThree.java'
6. LevelViewLevelThree.java
    - Purpose: Manages the graphical view for level three, rendering the level's element
    - Location: 'src/main/java/com/example/demo/Levels/LevelViewLevelThree.java'
7. Obstacle.java
    - Purpose: Represents obstacles in the game, such as kites in level three
    - Location: 'src/main/java/com/example/demo/displays/Obstacle.java'
8. PowerUp.java
    - Purpose: Represents power-ups that players can collect during level three
    - Location: 'src/main/java/com/example/demo/displays/PowerUp.java'
9. SoundManager.java
    - Purpose: Handles audio playback, volume control, and looping functionality
    - Location: 'src/main/java/com/example/demo/SoundManager.java'



## Modified Java Classes
1. Controller.java
    - Added features and Modifications: 
        1. SoundManager integration
            - Purpose: to manage and control the background music or other sounds during the gameplay and menu transitions
        2. Menu Navigation Support
            - Purpose: Handles transitions to menus
        3. Enhanced update method
            - Distinguishes between level and menu notification using the startsWith condition
            - Handles unexpected notification gracefully with error logging. 
            - Debugging messages to log transition
        4. Improved error handling
            - Provides detailed error messages and displays alerts in the UI for runtime issues.
        5. Current level tracking
            - Stores the currently active level
        6. Stage user data
            - Stores the controller instance in the stage object for easy access. 
           
    - Removed or Changed Features: 
        1. Direct Level transition
            - Original code directly navigated to LEVEL_ONE_CLASS_NAME in launchGame
            - Replaced by navigation to HOME_MENU using goToMenu
        2. Observable update logic
        3. Removed redundant imports

2. ActiveActor.java
    1. Package Structure
        - organized under a new sub-package, actors. this improves code modularity and makes it easier to manage different
            types of classes
    2. Resource Loading Logic 
        - introduced a step by assigning the resource to URL resourceURL before creating the Image object. while this does not 
            add new functionality, it makes the process clearer and easier to debug if the image path is incorrect
    3. Imports 
        - added java.net.URL for managing the resourceURL object
        - split the image and imageView imports into separate statements for clarity 

3. ActiveActorDestructible.java
    1. Package organisation: 
        - the dependency on ActiveActor reflects the reorganization of related class
    2. Improved destroy method
        - Reason for change: added a check to avoid unnecessary operations if the actor is already destroyed. this enhances 
            the robustness and prevents redundant method calls 
    3. New abstract Method 
        - Purpose: this allows subclasses to define custom collision boundaries. the use of Bounds indicates that it relates
                    to collision detection
        - Impact: Adding this method enforces all subclasses to implement a custom boundary-checking logic, enhancing extensibility
                    for different game objects 
    4. Visibility change for isDestroyed
        - Reason: Changing isDestroyed to protected allows subclasses to access and modify this field directly if necessary
                    improving flexibility when extending class. 

4. Boss.java
    1. Added custom collision boundaries for better hit detection
    2. Enhanced destruction logic to ensure consistency 
    3. introduced three phases with distinct behaviours (aggressive, defensive, and rapid movement)
    4. Added shield cooldown mechanics with activation probabilities.
    5. Integrated LevelViewLevelTwo for dynamic updates
   
5. BossProjectile.java
    1. Added Custom bounding boxes for collision detection
    2. Adjusted image height and position for better visuals 

6. EnemyPlane.java
    1. Updated the image to woodstockenemy.png
    2. adjusted size for gameplay balancing
    3. added custom collision boundaries for accurate hit detection

7. EnemyProjectile.java
    1. Updated the image to woodstockProjectile.png
    2. adjusted size for gameplay balancing
    3. added custom bounds to provide precise collision boundaries improving hit detection

8. 