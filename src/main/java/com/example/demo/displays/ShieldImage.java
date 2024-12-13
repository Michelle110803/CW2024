package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a shield image in the game. The shield is displayed to visually indicate
 * that a shield is active around a player or object. This class extends {@link ImageView}
 * to display the shield image at a specific position and size.
 *
 * Provides methods to show and hide the shield dynamically.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_SIZE = 145;

	/**
	 * Constructs a {@code ShieldImage} at the specified position with a fixed size.
	 *
	 * @param xPosition the X-coordinate where the shield image should be positioned.
	 * @param yPosition the Y-coordinate where the shield image should be positioned.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));
		this.setVisible(true);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Displays the shield image by setting its visibility to {@code true}.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield image by setting its visibility to {@code false}.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}