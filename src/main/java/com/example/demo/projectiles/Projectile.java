package com.example.demo.projectiles;

import com.example.demo.ActiveActorDestructible;

/**
 * Represents an abstract base class for all projectile entities in the game.
 * <p>
 * A projectile is an active destructible object that moves across the screen
 * and interacts with other game entities, such as enemies or the player.
 * </p>
 * <p>
 * This class provides common functionality for all projectiles, including
 * destruction upon damage and initialization with specific properties. Subclasses
 * must define their movement logic.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a new projectile with the specified image, dimensions, and initial position.
	 *
	 * @param imageName     the name of the image representing the projectile
	 * @param imageHeight   the height of the projectile image
	 * @param initialXPos   the initial X-coordinate of the projectile
	 * @param initialYPos   the initial Y-coordinate of the projectile
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the logic for taking damage. Projectiles are destroyed immediately
	 * when they take damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 * <p>
	 * Subclasses must implement this method to define the specific movement
	 * behavior of the projectile (e.g., horizontal, vertical, or diagonal movement).
	 * </p>
	 */
	@Override
	public abstract void updatePosition();
}
