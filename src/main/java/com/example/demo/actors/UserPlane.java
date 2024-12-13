package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.Levels.LevelView;
import com.example.demo.projectiles.UserProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents the user-controlled plane in the game. The {@code UserPlane} can move
 * in both horizontal and vertical directions, fire projectiles, and take damage.
 * It supports a shield mechanism and tracks the number of kills and health.
 *
 * This class extends {@link FighterPlane} to inherit basic fighter plane behavior.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "snoopyPlane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_LEFT_BOUND = 0.0;
	private static final double X_RIGHT_BOUND = 800.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;

	private int velocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private int health;
	private boolean shieldActive = false;

	/**
	 * Constructs a {@code UserPlane} with the specified initial health.
	 *
	 * @param initialHealth the starting health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.health = initialHealth;
		velocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Updates the position of the user plane based on its movement direction.
	 * Ensures the plane stays within the screen boundaries.
	 */
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			if (newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}

	/**
	 * Gets the custom bounds of the user plane for collision detection.
	 *
	 * @return a {@link Bounds} object representing the adjusted bounds of the user plane.
	 */
	@Override
	public Bounds getCustomBounds() {
		double x = getLayoutX() + getTranslateX() + 10;
		double y = getLayoutY() + getTranslateY() + 10;
		double width = getBoundsInParent().getWidth() - 20;
		double height = getBoundsInParent().getHeight() - 20;
		return new BoundingBox(x, y, width, height);
	}

	/**
	 * Updates the state of the user plane, primarily its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Reduces the health of the user plane unless the shield is active.
	 * If the shield is active, no damage is taken.
	 */
	@Override
	public void takeDamage() {
		if (shieldActive) {
			System.out.println("User shielded, no damage taken");
			return;
		}
		super.takeDamage();
	}

	/**
	 * Fires a projectile from the user plane's position.
	 *
	 * @return a {@link UserProjectile} representing the fired projectile.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		double currentX = getLayoutX() + getTranslateX();
		double currentY = getLayoutY() + getTranslateY();

		double projectileX = currentX + PROJECTILE_X_POSITION;
		double projectileY = currentY + PROJECTILE_Y_POSITION_OFFSET;

		return new UserProjectile(projectileX, projectileY);
	}

	/**
	 * Checks if the user plane is currently moving vertically.
	 *
	 * @return {@code true} if the plane is moving vertically, {@code false} otherwise.
	 */
	private boolean isMovingVertically() {
		return velocityMultiplier != 0;
	}

	/**
	 * Checks if the user plane is currently moving horizontally.
	 *
	 * @return {@code true} if the plane is moving horizontally, {@code false} otherwise.
	 */
	private boolean isMovingHorizontally() {
		return horizontalVelocityMultiplier != 0;
	}

	/**
	 * Moves the user plane upward.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the user plane downward.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the vertical movement of the user plane.
	 */
	public void stopVertical() {
		velocityMultiplier = 0;
	}

	/**
	 * Stops the horizontal movement of the user plane.
	 */
	public void stopHorizontal() {
		horizontalVelocityMultiplier = 0;
	}

	/**
	 * Moves the user plane to the left.
	 */
	public void moveLeft() {
		horizontalVelocityMultiplier = -1;
	}

	/**
	 * Moves the user plane to the right.
	 */
	public void moveRight() {
		horizontalVelocityMultiplier = 1;
	}

	/**
	 * Gets the number of kills achieved by the user plane.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user plane by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Increments the health of the user plane by one, up to a maximum value.
	 * Updates the {@link LevelView} with the new health.
	 *
	 * @param levelView the {@link LevelView} to update with the new health.
	 */
	public void incrementHealth(LevelView levelView) {
		if (health < 10) { // Ensure health doesn't exceed maximum
			health++;
			System.out.println("Health incremented. Current health: " + health);
			levelView.updateHearts(health); // Update LevelView with the new health
		} else {
			System.out.println("Health is already at maximum!");
		}
	}

	/**
	 * Heals the user plane by a specified number of health points, up to a maximum value.
	 *
	 * @param healthPoints the number of health points to restore.
	 */
	public void heal(int healthPoints) {
		int maxHealth = 10; // Adjust this value based on your game's design
		this.health = Math.min(this.health + healthPoints, maxHealth);
		System.out.println("Healed! Current health: " + this.health);
	}
}
