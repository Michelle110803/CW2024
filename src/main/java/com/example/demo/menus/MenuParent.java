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
import java.net.URL;

import com.example.demo.SoundManager;

/**
 * Abstract class representing a parent menu for the application.
 * Provides common functionality for initializing menus with background,
 * music, and buttons, as well as transitioning between menus or levels.
 *
 * <p>All child menu classes should extend this class to inherit the shared functionality.</p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public abstract class MenuParent extends Observable {

    protected final Stage stage;
    protected final Group root;
    private final Timeline timeline;
    private final Scene scene;
    private final ImageView background;
    protected final double screenWidth;
    protected final double screenHeight;
    private static final String LEVEL_ONE = "com.example.demo.Levels.LevelOne";
    private SoundManager soundManager;

    /**
     * Constructs a new menu with the specified parameters.
     *
     * @param stage              the stage on which the menu will be displayed
     * @param backgroundImageName the path to the background image resource
     * @param screenHeight       the height of the screen
     * @param screenWidth        the width of the screen
     * @param musicFilePath      the path to the background music file
     */
    public MenuParent(Stage stage, String backgroundImageName, double screenHeight, double screenWidth, String musicFilePath) {
        this.stage = stage;
        this.timeline = new Timeline();
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        initializeBackground();
        initializeControls();

        soundManager = new SoundManager();
        soundManager.playSound(musicFilePath);
    }

    /**
     * Initializes the scene for this menu.
     *
     * @return the initialized Scene object
     */
    public Scene initializeScene() {
        return scene;
    }

    /**
     * Initializes the background image and adds it to the root group.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setPreserveRatio(true); // Maintain aspect ratio
        background.setFitWidth(screenWidth); // Stretch to screen width
        background.setFitHeight(screenHeight); // Stretch to screen height
        root.getChildren().add(background);
    }

    /**
     * Initializes the controls for the menu, including background interaction.
     *
     * <p>For example, pressing the SPACE key navigates to the first level.</p>
     */
    protected void initializeControls() {
        timeline.stop(); // Stop any previous animations

        // Log for debugging
        System.out.println("Initializing controls...");

        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.SPACE) {
                    goToNextLevel(LEVEL_ONE);
                }
            }
        });
    }

    /**
     * Navigates to the next level specified by the level name.
     *
     * @param levelName the fully qualified class name of the next level
     */
    public void goToNextLevel(String levelName) {
        setChanged();
        notifyObservers(levelName);
        timeline.stop();
    }

    /**
     * Navigates to a different menu specified by the menu name.
     *
     * @param menuName the class name of the menu to navigate to
     */
    protected void goToMenu(String menuName) {
        try {
            Class<?> menuClass = Class.forName("com.example.demo.menus." + menuName);
            Object menuInstance = menuClass.getConstructor(Stage.class, double.class, double.class)
                    .newInstance(stage, screenHeight, screenWidth);

            Scene scene = ((MenuParent) menuInstance).initializeScene();
            stage.setScene(scene);

            setChanged();
            notifyObservers(menuClass.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a button with an image and adds it to the menu.
     *
     * @param buttonImagePath the path to the button image
     * @param eventHandler    the event handler for button clicks
     * @param posX            the X position of the button
     * @param posY            the Y position of the button
     * @param buttonWidth     the width of the button
     * @param buttonHeight    the height of the button
     * @return the created Button object
     */
    protected Button buttonImage(String buttonImagePath, EventHandler<ActionEvent> eventHandler,
                                 double posX, double posY, double buttonWidth, double buttonHeight) {
        URL resource = getClass().getResource(buttonImagePath);
        if (resource == null) {
            return null;
        }

        Image image = new Image(resource.toExternalForm());
        ImageView buttonImageView = new ImageView(image);

        buttonImageView.setFitWidth(buttonWidth);
        buttonImageView.setFitHeight(buttonHeight);
        buttonImageView.setPreserveRatio(true);

        Button button = new Button();
        button.setGraphic(buttonImageView);
        button.setStyle("-fx-background-color: transparent;");
        button.setLayoutX(posX);
        button.setLayoutY(posY);
        button.setOnAction(eventHandler);

        root.getChildren().add(button);

        // Log for debugging
        System.out.println("Button Bounds: X=" + posX + ", Y=" + posY + ", Width=" + buttonWidth + ", Height=" + buttonHeight);

        return button;
    }

    /**
     * Calculates custom bounds for a button.
     *
     * @param button the Button object to calculate bounds for
     * @return the custom Bounds object
     */
    protected Bounds getCustomBounds(Button button) {
        double x = button.getLayoutX() + button.getTranslateX() + 10;
        double y = button.getLayoutY() + button.getTranslateY() + 10;
        double width = button.getBoundsInParent().getWidth() - 20;
        double height = button.getBoundsInParent().getHeight() - 20;
        return new BoundingBox(x, y, width, height);
    }
}
