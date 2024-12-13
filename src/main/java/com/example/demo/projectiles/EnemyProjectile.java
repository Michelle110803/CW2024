package com.example.demo.projectiles;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents a projectile fired by enemy planes in the game.
 * <p>
 * This projectile moves horizontally at a fixed speed and has specific visual
 * and positional properties. It is used for enemy attacks in the game.
 * </p>
 * <p>
 * Extends {@link Projectile}, inheriting common projectile functionality.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "woodstockProjectile.png";
	private static final int IMAGE_HEIGHT = 70;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * Constructs a new EnemyProjectile instance.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
