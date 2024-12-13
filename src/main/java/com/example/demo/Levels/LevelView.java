package com.example.demo.Levels;

import com.example.demo.displays.GameOverImage;
import com.example.demo.displays.HeartDisplay;
import com.example.demo.displays.WinImage;
import javafx.application.Platform;
import javafx.scene.Group;

/**
 * Represents the visual components and user interface for a level.
 * <p>
 * Handles the display of hearts (representing player health), the win image,
 * and the game over image. Provides methods to update the UI dynamically based on game events.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class LevelView {

	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 520;
	private static final int LOSS_SCREEN_Y_POSITION = 280;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;

	/**
	 * Constructs a new LevelView instance with the specified root group and initial number of hearts.
	 *
	 * @param root            the root group to which UI elements will be added
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSITION);
	}

	/**
	 * Displays the heart container in the UI if not already present.
	 */
	public void showHeartDisplay() {
		Platform.runLater(() -> {
			if (!root.getChildren().contains(heartDisplay.getContainer())) {
				root.getChildren().add(heartDisplay.getContainer());
			}
		});
	}

	/**
	 * Displays the win image in the UI if not already present.
	 * <p>
	 * Ensures the win image is not added multiple times.
	 * </p>
	 */
	public void showWinImage() {
		if (!root.getChildren().contains(winImage)) { // Check if the winImage is already in the children list
			root.getChildren().add(winImage);
			winImage.showWinImage();
		} else {
			System.out.println("Win image already added, skipping duplicate addition.");
		}
	}

	/**
	 * Displays the game over image in the UI if not already present.
	 */
	public void showGameOverImage() {
		Platform.runLater(() -> {
			if (!root.getChildren().contains(gameOverImage)) {
				root.getChildren().add(gameOverImage);
			}
		});
	}

	/**
	 * Removes hearts from the heart display to match the remaining health.
	 * <p>
	 * Ensures the heart display accurately reflects the player's health.
	 * </p>
	 *
	 * @param heartsRemaining the number of hearts the player should have
	 */
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

	/**
	 * Updates the heart display to reflect the current health.
	 * <p>
	 * Adds or removes hearts incrementally to match the current health value.
	 * </p>
	 *
	 * @param currentHealth the player's current health
	 */
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
