package com.example.demo.menus;

import com.example.demo.controller.Controller;
import com.example.demo.SoundManager;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SettingsMenu extends MenuParent {

    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/settingsBackground.jpg";
    private static final String BACKGROUND_MUSIC_PATH = "/com/example/demo/audio/backgroundMusic.wav";

    public SettingsMenu(Stage stage, double screenHeight, double screenWidth){
        super (stage, BACKGROUND_IMAGE_PATH, screenHeight, screenWidth, BACKGROUND_MUSIC_PATH);
        initializeControls();
    }

    @Override
    protected void initializeControls(){
        super.initializeControls();
        //soundManager.pauseSound();

        VBox layout = new VBox(20);
        layout.setLayoutX(screenWidth /3);
        layout.setLayoutY(screenHeight / 3);


        Slider volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.1);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) ->{
            System.out.println("volume set to: " + newValue.doubleValue());
            Controller controller = (Controller) stage.getUserData();
            if(controller != null && controller.getSoundManager() != null){
                controller.getSoundManager().setVolume(newValue.doubleValue());
            }
        });

        Button backButton = new Button("Back to Menu");
        backButton.setOnAction(e -> {
            //soundManager.resumeSound();
            System.out.println("Navigating back to homemenu");
            goToMenu("HomeMenu");
        });

        layout.getChildren().addAll(volumeSlider, backButton);
        root.getChildren().add(layout);
    }
}
