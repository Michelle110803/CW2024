package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents the "You Win" image displayed in the game upon victory.
 * The {@code WinImage} is positioned at a specific location and can be shown when the player wins the game.
 *
 * This class extends {@link ImageView} to handle the display of the image.
 *
 * Provides methods to control the visibility of the image.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class WinImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/youWin.png";
	private static final int HEIGHT = 500;
	private static final int WIDTH = 600;

	/**
	 * Constructs a {@code WinImage} at the specified position with a fixed size.
	 *
	 * @param xPosition the X-coordinate where the "You Win" image should be positioned.
	 * @param yPosition the Y-coordinate where the "You Win" image should be positioned.
	 */
	public WinImage(double xPosition, double yPosition) {
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(HEIGHT);
		this.setFitWidth(WIDTH);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
	}

	/**
	 * Displays the "You Win" image by setting its visibility to {@code true}.
	 */
	public void showWinImage() {
		this.setVisible(true);
	}
}
