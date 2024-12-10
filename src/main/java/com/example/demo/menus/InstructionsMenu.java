package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InstructionsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/InstructionsMenu.png";
    private static final String HOME_BUTTON_IMAGE = "/com/example/demo/images/HomeButton.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.wav";
    private Button homeButton;

    public InstructionsMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    @Override
    protected void initializeControls() {
        super.initializeControls(); // Ensure parent class controls are initialized

        if (homeButton == null) { // Create the Home Button if it doesn't already exist
            double buttonWidth = 150; // Width of the button
            double buttonHeight = 50; // Height of the button
            double posX = (screenWidth - buttonWidth) / 2; // Center horizontally
            double posY = screenHeight - 100; // Position near the bottom of the screen

            homeButton = buttonImage(
                    getClass().getResource(HOME_BUTTON_IMAGE).toExternalForm(), // Updated path
                    e -> {
                        System.out.println("Navigating back to HomeMenu...");
                        goToMenu("com.example.demo.menus.HomeMenu");
                    },
                    posX, posY, buttonWidth, buttonHeight
            );


            if (homeButton != null) {
                root.getChildren().add(homeButton); // Add the Home Button to the root
                System.out.println("Added Home Button: X = " + posX + ", Y = " + posY);
            }
        }
    }


}
