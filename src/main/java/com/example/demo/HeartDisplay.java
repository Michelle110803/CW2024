package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;
	
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}
	
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}
	
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = createHeart();
			if (heart != null){
				container.getChildren().add(heart);
			}
		}
	}

	private ImageView createHeart(){
		try{
			Image heartImage = new Image(getClass().getResourceAsStream(HEART_IMAGE_NAME));
			ImageView heart = new ImageView(heartImage);
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			return heart;
		} catch(Exception e){
			System.out.println("heart image not found. please check the path" + HEART_IMAGE_NAME);
			return null;
		}
	}

	public void removeHeart() {
		if (!container.getChildren().isEmpty()) {
			container.getChildren().remove(0);
		}
	}
	
	public HBox getContainer() {
		return container;
	}

}