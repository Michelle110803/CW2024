package com.example.demo.displays;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.UserPlane;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

/**
 * Represents a power-up in the game that provides benefits to the user when collected.
 * The {@code PowerUp} moves vertically downwards and interacts with the user's plane upon collision.
 *
 * This class extends {@link ActiveActorDestructible} and defines behavior specific to power-ups.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class PowerUp extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "powerup.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int VERTICAL_SPEED = 2;

    private boolean collected = false;

    /**
     * Constructs a {@code PowerUp} at the specified initial position.
     *
     * @param initialX the initial X-coordinate of the power-up.
     * @param initialY the initial Y-coordinate of the power-up.
     */
    public PowerUp(double initialX, double initialY) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY);
    }

    /**
     * Updates the position of the power-up, moving it vertically downwards at a constant speed.
     */
    @Override
    public void updatePosition() {
        moveVertically(VERTICAL_SPEED);
    }

    /**
     * Destroys the power-up. This method is called when the power-up is collected or removed.
     * The power-up becomes invisible and unmanaged in the layout.
     */
    @Override
    public void destroy() {
        setVisible(false);
        setManaged(false); // Remove from layout
    }

    /**
     * Handles the "damage" behavior for the power-up. Power-ups don't take damage but disappear when destroyed.
     */
    @Override
    public void takeDamage() {
        this.destroy();
    }

    /**
     * Gets the custom bounds of the power-up for collision detection.
     *
     * @return a {@link Bounds} object representing the custom bounds of the power-up.
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
     * Updates the state of the power-up by calling {@link #updatePosition()}.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Checks if the power-up is collected by the user. A power-up is collected if its
     * bounds intersect with the bounds of the {@link UserPlane}.
     *
     * @param user the {@link UserPlane} to check for collision.
     * @return {@code true} if the power-up is collected, {@code false} otherwise.
     */
    public boolean isCollectedByUser(UserPlane user) {
        if (collected) return false; // Ignore already collected power-ups
        boolean intersects = this.getCustomBounds().intersects(user.getCustomBounds());
        if (intersects) {
            collected = true;
            System.out.println("Collision detected: PowerUp collected! Bounds: " + this.getCustomBounds());
        }
        return intersects;
    }
}
