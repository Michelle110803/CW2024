package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.scene.control.Button;

/**
 * represents the instructions menu of the application, providing information
 * on how to play the game and a button to navigate back to the Home Menu
 *
 * this menu includes a background image and background music to enhance user experience
 *
 * @author michellealessandra
 * @version 1.0
 */


public class InstructionsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/InstructionsMenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.wav";

    /**
     * constructs the instructions menu with the specified stage, screen height, and screen width
     *
     * @param stage the stage on which the menu is displayed
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */

    public InstructionsMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    /**
     * initializes the menu controls, including a back button to navigate to the home menu
     */

    @Override
    protected void initializeControls() {
        super.initializeControls(); // Ensure parent class controls are initialized

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            goToMenu("HomeMenu");
        });

    }
}
