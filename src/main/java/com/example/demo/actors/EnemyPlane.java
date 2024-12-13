package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.EnemyProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents an enemy plane in the game, extending {@link FighterPlane}.
 * Enemy planes move horizontally across the screen and occasionally fire projectiles.
 * They are destructible and have a single unit of health by default.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "woodstockenemy.png";
	private static final int IMAGE_HEIGHT = 100;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = 0.01;

	/**
	 * Constructs an {@code EnemyPlane} with the specified initial position.
	 *
	 * @param initialXPos the initial X-coordinate of the enemy plane.
	 * @param initialYPos the initial Y-coordinate of the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane by moving it horizontally at a fixed velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile with a small probability defined by {@code FIRE_RATE}.
	 *
	 * The projectile is fired at an offset relative to the enemy plane's position.
	 *
	 * @return a new {@link EnemyProjectile} if the plane fires, or {@code null} otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, which primarily involves updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Gets the custom bounds of the enemy plane for collision detection.
	 *
	 * @return a {@link Bounds} object representing the adjusted bounds of the enemy plane.
	 */
	@Override
	public Bounds getCustomBounds() {
		double x = getLayoutX() + getTranslateX() + 5;
		double y = getLayoutY() + getTranslateY() + 5;
		double width = getBoundsInParent().getWidth() - 10;
		double height = getBoundsInParent().getHeight() - 10;
		return new BoundingBox(x, y, width, height);
	}
}
