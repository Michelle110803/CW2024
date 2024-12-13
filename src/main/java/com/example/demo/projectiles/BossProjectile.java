package com.example.demo.projectiles;

import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;

/**
 * Represents a projectile fired by the boss in the game.
 * This projectile moves horizontally with a fixed velocity and has specific
 * visual and positional properties.
 * Extends {@link Projectile}, inheriting common projectile functionality.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "BossProjectile.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 920;

	/**
	 * Constructs a new BossProjectile instance.
	 *
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the actor, calling the position update method.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Gets the custom bounding box for collision detection.
	 *
	 * @return a {@link Bounds} object representing the projectile's bounds
	 */
	@Override
	public Bounds getCustomBounds() {
		double x = getLayoutX() + getTranslateX();
		double y = getLayoutY() + getTranslateY();
		double width = getBoundsInParent().getWidth();
		double height = getBoundsInParent().getHeight();
		return new BoundingBox(x, y, width, height);
	}
}
