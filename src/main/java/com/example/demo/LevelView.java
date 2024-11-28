package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = -160;
	private static final int LOSS_SCREEN_Y_POSISITION = -375;
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
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}
	
	public void showGameOverImage() {
		Platform.runLater(() -> {
			if(!root.getChildren().contains(gameOverImage)){
				root.getChildren().add(gameOverImage);
			}
		});
	}
	
	public void removeHearts(int heartsRemaining) {
		final int finalHeartsRemaining = heartsRemaining;
		Platform.runLater(() -> {
			int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();

			int adjustedHeartsRemaining = Math.max(0, finalHeartsRemaining);
			int heartsToRemove = currentNumberOfHearts - adjustedHeartsRemaining;

			for(int i=0; i<heartsToRemove; i++){
				if(!heartDisplay.getContainer().getChildren().isEmpty()){
					heartDisplay.removeHeart();
				}
			}

		});
	}
}
