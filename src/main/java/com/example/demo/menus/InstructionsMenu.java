package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.scene.control.Button;


public class InstructionsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/InstructionsMenu.png";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.wav";

    public InstructionsMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC);
        initializeControls();
    }

    @Override
    protected void initializeControls() {
        super.initializeControls(); // Ensure parent class controls are initialized

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            goToMenu("HomeMenu");
        });

    }
}
