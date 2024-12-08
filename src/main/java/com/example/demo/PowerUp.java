package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class PowerUp extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "powerup.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int VERTICAL_SPEED = 2;

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
        double x = getLayoutX() + getTranslateX() + 10;
        double y = getLayoutY() + getTranslateY() + 10;
        double width = getBoundsInParent().getWidth() - 20;
        double height = getBoundsInParent().getHeight() - 20;
        return new BoundingBox(x, y, width, height);
    }

    @Override
    public void updateActor() {
        // Call updatePosition to ensure the PowerUp moves as expected
        updatePosition();
    }

    public boolean isCollectedByUser(UserPlane user) {
        boolean intersects = this.getCustomBounds().intersects(user.getCustomBounds());
        System.out.println("Checking collision: PowerUp (" + getLayoutX() + ", " + getLayoutY() +
                ") with UserPlane (" + user.getLayoutX() + ", " + user.getLayoutY() + ") - Intersects: " + intersects);
        return intersects;
    }


    @Override
    public void destroy(){
        super.destroy();
    }

}
