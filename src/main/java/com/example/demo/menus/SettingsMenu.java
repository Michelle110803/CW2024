package com.example.demo.menus;

import com.example.demo.controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Represents the settings menu in the application, allowing users to adjust
 * volume settings and navigate back to the home menu.
 *
 * <p>Extends {@link MenuParent} to inherit common menu functionality.</p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class SettingsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/settingsBackground.jpg";
    private static final String BACKGROUND_MUSIC_PATH = "/com/example/demo/audio/backgroundMusic.wav";

    /**
     * Constructs a new SettingsMenu instance.
     *
     * @param stage        the primary stage on which the menu is displayed
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public SettingsMenu(Stage stage, double screenHeight, double screenWidth) {
        super(stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC_PATH);
        initializeControls();
    }

    /**
     * Initializes the controls for the settings menu, including a volume slider
     * and a back button.
     */
    @Override
    protected void initializeControls() {
        super.initializeControls();

        // Create a VBox layout for positioning controls
        VBox layout = new VBox(20);
        layout.setLayoutX(screenWidth / 3);
        layout.setLayoutY(screenHeight / 3);

        // Create a volume slider for adjusting sound levels
        Slider volumeSlider = new Slider(0, 1, 0.5); // Range: 0 (mute) to 1 (max volume)
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.1); // Tick mark interval
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Update volume in the sound manager
            Controller controller = (Controller) stage.getUserData();
            if (controller != null && controller.getSoundManager() != null) {
                controller.getSoundManager().setVolume(newValue.doubleValue());
            }
        });

        // Create a back button for returning to the home menu
        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            goToMenu("HomeMenu");
        });

        // Add controls to the layout and the root group
        layout.getChildren().addAll(volumeSlider, backButton);
        root.getChildren().add(layout);
    }
}
