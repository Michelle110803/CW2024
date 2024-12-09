package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -10;
	private static final int LOSS_SCREEN_Y_POSISITION = -400;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}
	
	public void showHeartDisplay() {
		Platform.runLater(() ->{
			if(!root.getChildren().contains(heartDisplay.getContainer())){
				root.getChildren().add(heartDisplay.getContainer());
			}
		});
	}

	public void showWinImage() {
		if (!root.getChildren().contains(winImage)) { // Check if the winImage is already in the children list
			root.getChildren().add(winImage);
		} else {
			System.out.println("Win image already added, skipping duplicate addition.");
		}
	}

	
	public void showGameOverImage() {
		Platform.runLater(() -> {
			if(!root.getChildren().contains(gameOverImage)){
				root.getChildren().add(gameOverImage);
			}
		});
	}

	public void removeHearts(int heartsRemaining) {
		Platform.runLater(() -> {
			int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
			int adjustedHeartsRemaining = Math.max(0, heartsRemaining);
			int heartsToRemove = currentNumberOfHearts - adjustedHeartsRemaining;

			for (int i = 0; i < heartsToRemove; i++) {
				if (!heartDisplay.getContainer().getChildren().isEmpty()) {
					heartDisplay.removeHeart();
				}
			}

			// Ensure heart display matches the current health
			updateHearts(adjustedHeartsRemaining);
		});
	}


	public void updateHearts(int currentHealth) {
		Platform.runLater(() -> {
			int currentHearts = heartDisplay.getContainer().getChildren().size();

			// Add hearts incrementally
			while (currentHearts < currentHealth) {
				heartDisplay.addHeart();
				currentHearts++;
			}

			// Remove hearts incrementally
			while (currentHearts > currentHealth) {
				heartDisplay.removeHeart();
				currentHearts--;
			}

			System.out.println("Updated hearts to: " + currentHealth);
		});
	}










}
