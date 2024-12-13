package com.example.demo;

import com.example.demo.actors.ActiveActor;
import javafx.geometry.Bounds;

/**
 * Represents an active actor in the game that can be destroyed.
 * <p>
 * This class serves as a base for all game entities that have destructible behavior.
 * It extends {@link ActiveActor} and implements the {@link Destructible} interface.
 * Subclasses are required to provide specific implementations for updating positions,
 * taking damage, and defining custom collision bounds.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Indicates whether the actor has been destroyed.
	 */
	protected boolean isDestroyed;

	/**
	 * Constructs a new `ActiveActorDestructible` with the specified image properties and initial position.
	 *
	 * @param imageName the name of the image file for the actor
	 * @param imageHeight the height of the actor's image
	 * @param initialXPos the initial X-coordinate of the actor
	 * @param initialYPos the initial Y-coordinate of the actor
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.isDestroyed = false;
	}

	/**
	 * Updates the position of the actor.
	 * <p>
	 * This method must be implemented by subclasses to define how the actor's position is updated.
	 * </p>
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the actor's state.
	 * <p>
	 * Subclasses should implement this method to specify how the actor updates during the game loop.
	 * </p>
	 */
	public abstract void updateActor();

	/**
	 * Handles damage taken by the actor.
	 * <p>
	 * This method must be implemented by subclasses to define the behavior when the actor takes damage.
	 * </p>
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed.
	 * <p>
	 * When an actor is destroyed, it is flagged as such and can be removed from the game.
	 * </p>
	 */
	@Override
	public void destroy() {
		if (!isDestroyed) {
			setDestroyed(true);
		}
	}

	/**
	 * Sets the destroyed status of the actor.
	 *
	 * @param isDestroyed the new destroyed status
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor has been destroyed.
	 *
	 * @return {@code true} if the actor is destroyed, {@code false} otherwise
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Gets the custom collision bounds for the actor.
	 * <p>
	 * Subclasses must implement this method to provide a precise bounding box for collision detection.
	 * </p>
	 *
	 * @return a {@link Bounds} object representing the collision area of the actor
	 */
	public abstract Bounds getCustomBounds();
}
