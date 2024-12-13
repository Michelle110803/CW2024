package com.example.demo.projectiles;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents a projectile fired by the user in the game.
 * <p>
 * The `UserProjectile` moves horizontally across the screen at a constant speed
 * and interacts with other game entities, such as enemies. It inherits from the
 * {@link Projectile} class, providing specific implementation details for the user’s projectile behavior.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "snoopy_projectile.png";
	private static final int IMAGE_HEIGHT = 70;
	private static final int HORIZONTAL_VELOCITY = 10;

	/**
	 * Constructs a new `UserProjectile` with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the projectile
	 * @param initialYPos the initial Y-coordinate of the projectile
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the horizontal position of the projectile.
	 * <p>
	 * The projectile moves from left to right across the screen at a constant
	 * speed defined by {@code HORIZONTAL_VELOCITY}.
	 * </p>
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile.
	 * <p>
	 * This method ensures the projectile’s position is updated as part of the
	 * game loop.
	 * </p>
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Retrieves the custom collision bounds for the projectile.
	 * <p>
	 * This method provides a more precise bounding box for collision detection,
	 * based on the projectile's current position and size.
	 * </p>
	 *
	 * @return a {@link Bounds} object representing the collision area of the projectile
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
