package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class MiniBoss extends FighterPlane {

    private static final String IMAGE_NAME = "miniboss.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int HEALTH = 3;
    private final LevelParent levelParent;
    private static final int FIRE_RATE = 100; // Frames between shots
    private int fireCooldown = FIRE_RATE;

    private static final int VERTICAL_VELOCITY = 3;
    private static final int Y_UPPER_BOUND = 50;
    private static final int Y_LOWER_BOUND = 500;
    private int verticalDirection = 1;

    public MiniBoss(LevelParent levelParent, double initialX, double initialY) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialX, initialY, HEALTH);
        this.levelParent = levelParent;
    }

    @Override
    public void updateActor() {
        updatePosition(); // Move the MiniBoss
        handleFiring();  // Handle projectile firing
    }

    @Override
    public void updatePosition() {
        double newYPosition = getLayoutY() + getTranslateY() + (VERTICAL_VELOCITY * verticalDirection);
        if (newYPosition <= Y_UPPER_BOUND || newYPosition >= Y_LOWER_BOUND) {
            verticalDirection *= -1; // Reverse direction at bounds
        }
        moveVertically(VERTICAL_VELOCITY * verticalDirection);
    }

    private void handleFiring() {
        if (fireCooldown > 0) {
            fireCooldown--; // Decrement cooldown each frame
        } else {
            fireProjectile(); // Fire a single projectile
            fireCooldown = FIRE_RATE; // Reset cooldown
        }
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        double projectileX = getProjectileXPosition(-50);
        double projectileY = getProjectileYPosition(IMAGE_HEIGHT / 2);
        ActiveActorDestructible projectile = new EnemyProjectile(projectileX, projectileY);

        // Add projectile to the scene and game logic
        levelParent.getRoot().getChildren().add(projectile);
        levelParent.getEnemyProjectiles().add(projectile);

        System.out.println("MiniBoss fired a projectile at X: " + projectile.getLayoutX() + ", Y: " + projectile.getLayoutY());
        return projectile;
    }

    @Override
    public Bounds getCustomBounds() {
        double x = getLayoutX() + getTranslateX() + 10;
        double y = getLayoutY() + getTranslateY() + 10;
        double width = getBoundsInParent().getWidth() - 20;
        double height = getBoundsInParent().getHeight() - 20;
        return new BoundingBox(x, y, width, height);
    }
}
