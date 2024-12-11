package com.example.demo.actors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public abstract class ActiveActor extends ImageView {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

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

	public abstract void updatePosition();

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
}
