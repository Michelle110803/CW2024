package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The {@code GameOverImage} class represents the "Game Over" image displayed in the game.
 * It extends {@link ImageView} to allow the display of an image at a specified position on the screen.
 *
 * This class loads the "Game Over" image resource and sets its position based on the provided coordinates.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class GameOverImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/gameOver.png";

	/**
	 * Constructs a {@code GameOverImage} and positions it on the screen.
	 *
	 * @param xPosition the X-coordinate where the image should be positioned.
	 * @param yPosition the Y-coordinate where the image should be positioned.
	 */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}
}
