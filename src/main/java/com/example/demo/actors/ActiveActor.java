package com.example.demo.actors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Represents an active game actor that can move and display an image on the game screen.
 * This abstract class extends {@link  ImageView} and provides methods for updating the position
 * and managing the appearance of the actor
 *
 * Subclasses must implement the {@code updatePosition} method to define specific movement behaviour.
 *
 * @author michellealessandra
 * @version 1.0
 */

public abstract class ActiveActor extends ImageView {

	/**
	 * the base location of the image resources for the actors
	 */

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * constructs an {@code ActiveActor} with the specified image and initial position
	 *
	 * @param imageName the name of the image file to be loaded for the actor
	 * @param imageHeight the height of the image to be displayed
	 * @param initialXPos the initial X-coordinate position of the actor
	 * @param initialYPos the initial Y-coordinate position of the actor
	 *
	 * @throws NullPointerException if the image resource cannot be found
	 */

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		// Attempt to load the image resource
		URL resourceURL = getClass().getResource(IMAGE_LOCATION + imageName);

		// Set the image
		this.setImage(new Image(resourceURL.toExternalForm()));

		// Set position and appearance
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * updates the position of the actor
	 *
	 * subclasses must provide an implementation to specify the movement behaviour of the actor
	 */

	public abstract void updatePosition();

	/**
	 * moves the actor horizontally by the specified amount
	 *
	 * @param horizontalMove the distance to move the actor horizontally
	 */

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * moves the actor vertically by the specified amount
	 *
	 * @param verticalMove the distance to move the actor vertically
	 */

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
