package com.example.demo.menus;

import com.example.demo.controller.Controller;
import com.example.demo.SoundManager;

import javafx.stage.Stage;

public class HomeMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/MainMenuBackground.jpg";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.wav";
    private SoundManager soundManager;

    public HomeMenu(Stage stage, double screenWidth, double screenHeight) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC);
        soundManager = new SoundManager();
        playBackgroundMusic();
        addMenuButtons();
    }

    private void playBackgroundMusic() {
        String filePath = "/com/example/demo/audio/backgroundMusic.wav";
        System.out.println("Attempting to loop background music: " + filePath);
        soundManager.playSoundLoop(filePath);
    }


    private void addMenuButtons() {
        double buttonWidth = screenWidth * 0.38; // Bigger buttons
        double buttonHeight = buttonWidth / 2.5; // Maintain aspect ratio
        double posX = screenWidth * 0.62 - buttonWidth / 2; // Center between Snoopy and Woodstock
        double initialY = screenHeight * 0.2; // Start higher
        double spacing = buttonHeight * 0.65; // Reduce spacing between buttons (closer)

        // Start Game Button
        buttonImage("/com/example/demo/images/StartGameButton.png",
                e -> {
                    soundManager.stopSound();
                    Controller controller = (Controller) stage.getUserData();
                    if (controller != null) {
                        try {
                            System.out.println("Navigating to Level One...");
                            controller.goToLevel("com.example.demo.LevelOne");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.err.println("Controller is not set in stage user data.");
                    }
                },
                posX, initialY, buttonWidth, buttonHeight);

        // Instructions Button
        buttonImage("/com/example/demo/images/InstructionsButton.png",
                event -> goToMenu("InstructionsMenu"),
                posX, initialY + spacing, buttonWidth, buttonHeight);

        // Settings Button
        buttonImage("/com/example/demo/images/SettingsButton.png",
                e -> System.out.println("Settings clicked!"),
                posX, initialY + 2 * spacing, buttonWidth, buttonHeight);

        // Exit Button
        buttonImage("/com/example/demo/images/ExitButton.png",
                e -> {
                    System.out.println("Exit clicked!");
                    System.exit(0);
                },
                posX, initialY + 3 * spacing, buttonWidth, buttonHeight);
    }


    public void onExit(){
        soundManager.stopSound();
    }


}
