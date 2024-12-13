package com.example.demo.displays;

import com.example.demo.ActiveActorDestructible;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents an obstacle in the game. Obstacles move vertically on the screen
 * and are destroyed when they go out of bounds or take damage.
 *
 * This class extends {@link ActiveActorDestructible} to inherit destructible behavior.
 *
 * Obstacles can increase their vertical speed dynamically.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class Obstacle extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "Obstacle.png";
    private static final int IMAGE_HEIGHT = 100;
    private int verticalVelocity;

    /**
     * Constructs an {@code Obstacle} with the specified initial position.
     *
     * @param initialXPos the initial X-coordinate of the obstacle.
     * @param initialYPos the initial Y-coordinate of the obstacle.
     */
    public Obstacle(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.verticalVelocity = 2;
    }

    /**
     * Updates the position of the obstacle by moving it vertically.
     * If the obstacle moves out of bounds, it is destroyed.
     */
    @Override
    public void updatePosition() {
        moveVertically(verticalVelocity);
        if (getLayoutY() > 600) {
            destroy();
        }
    }

    /**
     * Updates the state of the obstacle. Currently, this only updates its position.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Destroys the obstacle when it takes damage.
     */
    @Override
    public void takeDamage() {
        destroy();
    }

    /**
     * Gets the custom bounds of the obstacle for collision detection.
     *
     * @return a {@link Bounds} object representing the obstacle's bounds.
     */
    @Override
    public Bounds getCustomBounds() {
        double x = getLayoutX() + getTranslateX();
        double y = getLayoutY() + getTranslateY();
        double width = getBoundsInParent().getWidth();
        double height = getBoundsInParent().getHeight();
        return new BoundingBox(x, y, width, height);
    }

    /**
     * Increases the vertical speed of the obstacle.
     *
     * @param increment the amount to increase the speed by.
     */
    public void increaseSpeed(int increment) {
        verticalVelocity += increment;
    }
}
