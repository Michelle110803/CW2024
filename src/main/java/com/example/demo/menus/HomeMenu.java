package com.example.demo.menus;

import com.example.demo.controller.Controller;
import com.example.demo.SoundManager;
import javafx.stage.Stage;

/**
 * Represents the Home Menu of the application, providing options to start the game,
 * view instructions, adjust settings, or exit the application.
 * <p>
 * This menu includes background music and visual buttons for user interaction.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class HomeMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/MainMenuBackground.jpg";
    private static final String BACKGROUND_MUSIC = "/com/example/demo/audio/backgroundMusic.wav";
    private SoundManager soundManager;

    /**
     * Constructs the Home Menu with the specified stage, screen width, and screen height.
     *
     * @param stage        the stage on which the menu is displayed
     * @param screenWidth  the width of the screen
     * @param screenHeight the height of the screen
     */
    public HomeMenu(Stage stage, double screenWidth, double screenHeight) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC);
        this.soundManager = new SoundManager();
        playBackgroundMusic();
        addMenuButtons();
    }

    /**
     * Plays the background music in a loop for the home menu.
     */
    private void playBackgroundMusic() {
        String filePath = "/com/example/demo/audio/backgroundMusic.wav";
        soundManager.playSoundLoop(filePath);
    }

    /**
     * Adds the menu buttons to the screen, including Start Game, Instructions, Settings, and Exit buttons.
     * The buttons are positioned dynamically based on the screen size.
     */
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
                            controller.goToLevel("com.example.demo.Levels.LevelOne");
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
                e -> goToMenu("SettingsMenu"),
                posX, initialY + 2 * spacing, buttonWidth, buttonHeight);

        // Exit Button
        buttonImage("/com/example/demo/images/ExitButton.png",
                e -> System.exit(0),
                posX, initialY + 3 * spacing, buttonWidth, buttonHeight);
    }
}
