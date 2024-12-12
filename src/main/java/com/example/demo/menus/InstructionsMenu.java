package com.example.demo.menus;

import javafx.stage.Stage;
import javafx.scene.control.Button;
import com.example.demo.controller.Controller;
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

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            System.out.println("Navigating back to homemenu");
            goToMenu("HomeMenu");
        });

    }
}
