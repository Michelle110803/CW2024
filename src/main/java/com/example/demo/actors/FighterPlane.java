package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import javafx.geometry.Bounds;

/**
 * Represents a generic fighter plane in the game, extending {@link ActiveActorDestructible}.
 * Fighter planes have health, can fire projectiles, and can take damage.
 * Subclasses must define specific projectile behavior and custom bounds for collision detection.
 *
 * This class serves as a base for both player and enemy fighter planes.
 *
 * @author michellealessandra
 * @version 1.0
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	/**
	 * The health of the fighter plane, representing how many hits it can take before being destroyed.
	 */
	private int health;

	/**
	 * Constructs a {@code FighterPlane} with the specified image, initial position, and health.
	 *
	 * @param imageName    the name of the image file for the fighter plane.
	 * @param imageHeight  the height of the image.
	 * @param initialXPos  the initial X-coordinate of the plane.
	 * @param initialYPos  the initial Y-coordinate of the plane.
	 * @param health       the initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 *
	 * Subclasses must implement this method to define the specific behavior of the projectiles.
	 *
	 * @return an {@link ActiveActorDestructible} projectile, or {@code null} if no projectile is fired.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the health of the fighter plane by one.
	 * If the health reaches zero, the plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X-coordinate position for a projectile based on a given offset.
	 *
	 * @param xPositionOffset the offset to apply to the X-coordinate.
	 * @return the adjusted X-coordinate for the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y-coordinate position for a projectile based on a given offset.
	 *
	 * @param yPositionOffset the offset to apply to the Y-coordinate.
	 * @return the adjusted Y-coordinate for the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the fighter plane has reached zero.
	 *
	 * @return {@code true} if the health is zero, {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Returns the current health of the fighter plane.
	 *
	 * @return the health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Gets the custom bounds of the fighter plane for collision detection.
	 *
	 * Subclasses must implement this method to define the specific bounding box.
	 *
	 * @return a {@link Bounds} object representing the custom bounds of the fighter plane.
	 */
	@Override
	public abstract Bounds getCustomBounds();
}
