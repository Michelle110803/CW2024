package com.example.demo.Levels;

import com.example.demo.displays.ShieldImage;
import javafx.scene.Group;

/**
 * Represents the visual components and user interface for Level Two.
 *
 * Extends {@link LevelView} to include shield display and functionality.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private static final int OFFSET_X = -50;
	private static final int OFFSET_Y = -30;
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a new LevelViewLevelTwo instance with the specified root group and initial number of hearts.
	 *
	 * @param root            the root group to which UI elements will be added
	 * @param heartsToDisplay the initial number of hearts to display
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root group if not already present.
	 */
	private void addImagesToRoot() {
		try {
			if (!root.getChildren().contains(shieldImage)) {
				root.getChildren().add(shieldImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Displays the shield by making it visible and bringing it to the front.
	 */
	public void showShield() {
		shieldImage.showShield();
		shieldImage.toFront();
	}

	/**
	 * Hides the shield by making it invisible.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

	/**
	 * Updates the position of the shield relative to the specified coordinates.
	 * <p>
	 * The shield is repositioned using an offset to align with the player's position.
	 * </p>
	 *
	 * @param x the new X-coordinate for the shield
	 * @param y the new Y-coordinate for the shield
	 */
	public void updateShieldPosition(double x, double y) {
		shieldImage.setLayoutX(x + OFFSET_X);
		shieldImage.setLayoutY(y + OFFSET_Y);
		shieldImage.toFront();
	}
}
