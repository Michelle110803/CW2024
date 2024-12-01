package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

	//public void resizeGameOverImage(double width, double height){
		//setFitWidth(width);
		//setFitHeight(height);
	//}

}
