package com.example.demo;

/**
 * Represents an interface for objects that can take damage and be destroyed.
 * <p>
 * Classes implementing this interface must provide specific implementations for handling
 * damage and destruction. This is typically used for game entities that interact with other
 * objects in a destructible manner, such as enemies, projectiles, and obstacles.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public interface Destructible {

	/**
	 * Handles the action of taking damage.
	 * <p>
	 * Implementing classes should define how damage affects the object, such as
	 * reducing health or triggering specific animations or behaviors.
	 * </p>
	 */
	void takeDamage();

	/**
	 * Destroys the object.
	 * <p>
	 * Implementing classes should define what happens when the object is destroyed,
	 * such as removing it from the game or triggering destruction effects.
	 * </p>
	 */
	void destroy();
}
