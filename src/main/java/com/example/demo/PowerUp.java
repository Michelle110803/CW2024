package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class PowerUp extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "powerup.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int VERTICAL_SPEED = 2;
    private boolean isCollectedByUser = false;

    public PowerUp(double initialX, double initialY) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY);
    }

    @Override
    public void updatePosition() {
        // Power-up moves vertically downwards at a constant speed
        moveVertically(VERTICAL_SPEED);
    }

    @Override
    public void takeDamage() {
        // Power-ups don't "take damage"; they just disappear when collected or destroyed
        this.destroy();
    }

    @Override
    public Bounds getCustomBounds() {
        double x = getLayoutX() + getTranslateX();
        double y = getLayoutY() + getTranslateY();
        double width = getBoundsInParent().getWidth();
        double height = getBoundsInParent().getHeight();
        return new BoundingBox(x, y, width, height);
    }

    @Override
    public void updateActor() {
        // Call updatePosition to ensure the PowerUp moves as expected
        updatePosition();
    }

    private boolean collected = false;

    public boolean isCollectedByUser(UserPlane user) {
        if (collected) return false; // Ignore already collected PowerUps
        boolean intersects = this.getCustomBounds().intersects(user.getCustomBounds());
        if (intersects) {
            collected = true;
            System.out.println("Collision detected: PowerUp collected! Bounds: " + this.getCustomBounds());
        }
        return intersects;
    }




    public void destroy() {
        setVisible(false);
        setManaged(false); // Remove from layout
    }


}
