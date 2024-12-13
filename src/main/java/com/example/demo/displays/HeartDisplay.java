package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The {@code HeartDisplay} class is responsible for displaying a set of heart images,
 * representing the player's health or lives in the game. It uses a {@link HBox} to
 * organize the hearts horizontally and provides methods to add or remove hearts dynamically.
 *
 * This class initializes the hearts and manages their visual representation on the screen.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class HeartDisplay {

	public static final String HEART_IMAGE_NAME = "/com/example/demo/images/live.png";
	public static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a {@code HeartDisplay} at the specified position with the given number of hearts.
	 *
	 * @param xPosition        the X-coordinate of the heart display container.
	 * @param yPosition        the Y-coordinate of the heart display container.
	 * @param heartsToDisplay  the initial number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the {@link HBox} container for the hearts and positions it on the screen.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Adds the initial number of hearts to the container based on {@code numberOfHeartsToDisplay}.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes the last heart from the container, if any hearts are present.
	 */
	public void removeHeart() {
		if (!getContainer().getChildren().isEmpty()) {
			getContainer().getChildren().remove(getContainer().getChildren().size() - 1);
		}
	}

	/**
	 * Adds a heart to the container.
	 */
	public void addHeart() {
		ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Returns the {@link HBox} container holding the hearts.
	 *
	 * @return the {@code HBox} containing the heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}
