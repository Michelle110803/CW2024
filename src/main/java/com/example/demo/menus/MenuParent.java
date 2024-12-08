package com.example.demo.menus;


import java.util.Observable;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;

public abstract class MenuParent extends Observable {
    protected final Stage stage;
    private final Group root;
    private final Timeline timeline;
    private final Scene scene;
    private final ImageView background;
    protected final double screenWidth;
    protected final double screenHeight;
    private static final String LEVEL_ONE = "com.example.demo.levels.LevelOne";

    public MenuParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth) {
        this.stage = stage;
        this.timeline = new Timeline();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        initializeBackground();
        initializeControls();
    }
    public Scene initializeScene() {
        return scene;
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true); // Stretch to fit the screen dimensions
        background.setFitWidth(screenWidth); // Set the width to match the screen width
        background.setFitHeight(screenHeight); // Set the height to match the screen height
        root.getChildren().add(background); // Add the background to the scene
    }

    private void initializeControls(){
        timeline.stop();
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.SPACE) {
                    System.out.println("Space");
                    goToNextLevel(LEVEL_ONE);
                }
            }
        });
    }
    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);
        timeline.stop();
    }

    protected Button buttonImage(String buttonImagePath, EventHandler<ActionEvent> eventHandler,
                                 double posX, double posY, double buttonWidth, double buttonHeight) {
        // Load the image
        Image image = new Image(getClass().getResource(buttonImagePath).toExternalForm());
        ImageView buttonImageView = new ImageView(image);

        // Set the button dimensions
        buttonImageView.setFitWidth(buttonWidth);
        buttonImageView.setFitHeight(buttonHeight);
        buttonImageView.setPreserveRatio(true);

        // Create the button
        Button button = new Button();
        button.setGraphic(buttonImageView);
        button.setStyle("-fx-background-color: transparent;");
        button.setLayoutX(posX);
        button.setLayoutY(posY);

        // Attach event handler
        button.setOnAction(eventHandler);

        // Add the button to the root
        root.getChildren().add(button);

        // Debugging: Log button bounds
        System.out.println("Button Bounds: X=" + posX + ", Y=" + posY + ", Width=" + buttonWidth + ", Height=" + buttonHeight);

        return button;
    }



    protected Bounds getCustomBounds(Button button) {
        double x = button.getLayoutX() + button.getTranslateX() + 10;
        double y = button.getLayoutY() + button.getTranslateY() + 10;
        double width = button.getBoundsInParent().getWidth() - 20;
        double height = button.getBoundsInParent().getHeight() - 20;
        return new BoundingBox(x, y, width, height);
    }

}