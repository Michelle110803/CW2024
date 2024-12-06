package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class BossProjectileLevelThree extends Projectile {

    private static final String IMAGE_NAME = "woodstockprojectile.png";
    private static final int IMAGE_HEIGHT = 60;
    private static final int HORIZONTAL_VELOCITY = -10; // Adjust speed as needed
    private static final double INITIAL_X_POSITION = 1100; // Matches the boss's starting X

    public BossProjectileLevelThree(double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    @Override
    public void updatePosition() {
        setLayoutX(getLayoutX() - 5); // Example: move left
        System.out.println("Projectile X position updated: " + getLayoutX());
    }




    @Override
    public void updateActor() {
        updatePosition();
    }

    @Override
    public Bounds getCustomBounds() {
        double x = getLayoutX() + getTranslateX();
        double y = getLayoutY() + getTranslateY();
        double width = getBoundsInParent().getWidth();
        double height = getBoundsInParent().getHeight();
        System.out.println("Bounds for " + getClass().getSimpleName() + ": x=" + x + ", y=" + y + ", width=" + width + ", height=" + height);
        return new BoundingBox(x, y, width, height);
    }

}
