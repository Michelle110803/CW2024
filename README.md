# SKY BATTLE SNOOPY ADVENTURE

### Repository Link: https://github.com/Michelle110803/CW2024.git

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
4. High scores and leaderboard
    - a system to save and display high scores across multiple sessions has not been implemented

5. Power-Up mechanics 
    - although power-ups are partially implemented, they lack diversity and functionality like: 
        - temporary invincibility
        - increased fire rate
        - health regeneration
6. multiplayer mode
7. Dynamic difficulty adjustment
    - the game does not adapt difficulty based on player's performance
8. In-Game tutorial
    - a dedicated tutorial level or guide for new player
9. Achievement system
    - a system to track and reward player achievements 
10. Save and load system
    - the ability to save progress and resume from the last completed level is missing
   

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

8. FighterPlane.java
    1. Moved class to specific package
    2. added custom bounds as an abstract method to enforce precise hit box implementation in subclasses

9. HeartDisplay.java
    1. Moved class to specific package
    2. change the image file 
    3. enhanced heart manipulation
        - new method addHeart() to add heart to the display
        - improved removedHeart() to remove the last heart instead of the first, maintaining gameplay logic
    4. Improved method names and streamlined logic for better clarity and maintainability 

10. LevelOne.java 
    1. Moved class to specific package
    2. updated NEXT_LEVEL to reference to LevelTwo 
    3. reduced KILLS_TO_ADVANCE from 10 to 5
    4. added leveltwoinprogress and leveltransitioned (unused, but potentially for tracking level transitions in future enhancement)

11. LevelParent.java
    1. Added support for customized collision detection to handle new gameplay mechanics, such as boss shields and power-ups 
    2. enhanced background initialization to handle missing resources
    3. introduced more robust enemy projectile spawning and removal logic
    4. added methods to handle power-ups and remove collected ones 

12. LevelTwo.java
    1. Boss Behaviour
        - Boss is dynamically created and integrated with LevelViewLevelTwo for shield and phase updates
        - Added a isBossSpawned to ensure the boss spawns only once
    2. Level Transition
        - Introduced NEXT_LEVEL constant for transitioning to LevelThree
        - Added a LevelTransitioned flag to prevent multiple transitions when the boss is defeated
    3. Level View Enhancements
        - Level view dynamically updates to handle boss-specific visuals like shields
    4. Controller integration
        - Added reference to Controller for managing level flow

13. LevelView.java
    1. Enhancements
        - Concurrency: 
            - All UI modification are wrapped in Platform.runLater() to ensure thread-safe operations when interacting with the javaFX application thread
        - Win and Game-Over Image Management:
            - Prevents duplicate additions of win or game-over images by checking if they are already in the root's children
            - prints debug messages if attempts are made to re-add existing images
        - Dynamic Heart Updates: 
            - improved logic to adjust the number of hearts dynamically to match the player's current health
            - ensures that hearts are added or removed incrementally to avoid abrupt UI changes
            - introduced an updateHearts method to synchronize the heart display with the player's health accurately 
    2. Position changes: 
        - Slightly adjusted the game-over image position for better alignment 

14. LevelViewLevelTwo.java
    1. Enhancements
        - Dynamic Shield Positioning: 
            - Introduced updateShieldPosition to dynamically adjust the shield's position to the boss
            - added offsets for more precise alignment of the shield with the boss' position
        - Visibility checks: 
            - Added a method isShieldVisible to check the shield's current visibility state
        - Prevent duplicate shield addition: 
            - ensures the shield image is only added to the root once, avoiding runtime exceptions or duplicate visuals 
        - Improved rendering
          - utilizes toFront() to ensure the shield always appears in front of other elements

15. Projectile.java
    - Structure and functionality are retained from the original version
    - Fully compatible with the design of destructible game elements 
    - updatePosition() remains abstract to ensure subclass-specific behaviour is enforced

16. ShieldImage.java
    - image source: updated the image file path to specific package
    - default visibility: the shield is now visible upon creation (setVisible(true))
    - size adjustment: reduced the shield's size for better scaling

17. UserPlane.java
    1. New features
        - Horizontal movement
          - added moveleft and moveright methods
          - enforced horizontal bounds using X_LEFT_BOUND and X_RIGHT_BOUND
        - Custom Bounds
          - implemented precise collision detection with the getCustomBounds method
        - Shield mechanic
          - added activateShield and deactivateShield methods 
          - prevented damage while the shield is active
        - health management
          - introduced methods for healing and incrementing health 
          - updated health display through integration with levelView
    2. Projectile Firing
        - Dynamically calculated projectile spawn position using getLayoutX() and getTranslateX()
    3. Improved bound handling
        - separated methods for stopping vertical (stopVertical) and horizontal (stopHorizontal) movement
    4. LevelView integration
        - integrated with LevelView for real-time updates on health changes
    5. Enhanced Logging
        - added logging for debugging shield activation, healing, and health updates

18. UserProjectile.java
    1. Image Update
        - changed the projectile image
    2. velocity adjustment
        - reduced horizontal velocity
    3. custom collision detection
        - added getCustomBounds to define a precise bounding box for collision detection
            - bounds are dynamically calculated based on the projectile's current position and dimensions
    4. consistency with modified framework
        - updated the class to a specific package


## Unexpected Problems 

1. Music Duplication
    - Issue: Background music overlaps or duplicates when navigating between menus or levels 
    - Cause: multiple instances of the SoundManager were triggered without stopping previous tracks
    - Resolution Attempts: 
      - implemented a singleton pattern for SoundManager
      - Added checks to stop existing music before starting a new instance
    - Status: partially resolved, but occasional overlaps persist during rapid transitions
2. Level Transition Delays
    - Issue: delays during transitions between levels, especially after boss fights
    - Cause: heavy computations for spawning new entities and updating view elements
    - Resolution attempts: 
      - Preloaded assets for the next level to reduce transition time
      - optimized the goToNextLevel logic by ensuring the timeline stops immediately 
    - Status: Significantly improved, but minor delays remain
3. Encountered difficulties running the game in Eclipse during initial setup:
    - In Eclipse, the whole project has a problem, preventing it from building hence it could not run properly. 
    - Syncing the project with github in Eclipse was a problem as it keeps asking token 
4. Time Management
    - Juggling between other Courseworks and tests all at the same time
