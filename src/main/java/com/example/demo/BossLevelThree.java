package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;

public class BossLevelThree extends FighterPlane {

    private static final String IMAGE_NAME = "woodstockenemy.png";
    private static final double INITIAL_X_POSITION = 1100;
    private static final double INITIAL_Y_POSITION = 300;
    private static final double VERTICAL_VELOCITY = 5;
    private static final double HORIZONTAL_VELOCITY = 3;
    private static final int HEALTH = 10;

    private boolean isShielded = false;
    private final LevelViewLevelThree levelView;
    private final Group root;

    public BossLevelThree(LevelViewLevelThree levelView, Group root) {
        super(IMAGE_NAME, 70, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        this.levelView = levelView;
        this.root = root;
    }

    @Override
    public void updateActor() {
        updatePosition(); // This should handle movement
        updateShield();   // Handle shield activation and deactivation

        if (levelView != null) {
            levelView.updateShieldPosition(getLayoutX() + getTranslateX(), getLayoutY() + getTranslateY());
        }

        // Fire projectiles occasionally
        if (Math.random() < 0.05) { // Adjust fire rate here
            ActiveActorDestructible projectile = fireProjectile();
            if (projectile != null) {
                projectile.setLayoutX(getLayoutX() - 20);
                projectile.setLayoutY(getLayoutY() + 20);
                root.getChildren().add(projectile); // Ensure projectiles are added to the scene
            }
        }
    }





    private void moveRandomly() {
        double randomDirection = Math.random();
        if (randomDirection < 0.33) {
            moveVertically(VERTICAL_VELOCITY);
        } else if (randomDirection < 0.66) {
            moveHorizontally(HORIZONTAL_VELOCITY);
        } else {
            moveVertically(-VERTICAL_VELOCITY);
        }
    }

    private void updateShield() {
        if (Math.random() < 0.01) { // Randomly toggle shield
            isShielded = !isShielded;
            if (isShielded) {
                levelView.showShield();
                System.out.println("Shield activated!");
            } else {
                levelView.hideShield();
                System.out.println("Shield deactivated!");
            }
        }
    }

    @Override
    public void takeDamage() {
        if (!isShielded) {
            super.takeDamage();
        } else {
            System.out.println("Boss shield is active. No damage taken!");
        }
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
    public ActiveActorDestructible fireProjectile() {
        return Math.random() < 0.04 ? new BossProjectile(getLayoutY() + getTranslateY()) : null;
    }

    @Override
    public void updatePosition() {
        // Move the boss randomly in a limited area
        double randomDirection = Math.random();
        if (randomDirection < 0.33) {
            moveVertically(5); // Move down
        } else if (randomDirection < 0.66) {
            moveVertically(-5); // Move up
        } else {
            moveHorizontally(-3); // Move left
        }

        // Ensure the boss stays within bounds
        if (getLayoutY() + getTranslateY() < 0) {
            setTranslateY(0 - getLayoutY());
        } else if (getLayoutY() + getTranslateY() > 600) { // Adjust boundary as per your screen height
            setTranslateY(600 - getLayoutY());
        }

        if (getLayoutX() + getTranslateX() < 900) { // Prevent moving too far left
            setTranslateX(900 - getLayoutX());
        }
    }


}
