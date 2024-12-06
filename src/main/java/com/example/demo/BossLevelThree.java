package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BossLevelThree extends FighterPlane {

    private static final String IMAGE_NAME = "woodstockenemy.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_WIDTH = 50;
    private static final double INITIAL_X_POSITION = 1100;
    private static final double INITIAL_Y_POSITION = 300;
    private static final int SCREEN_HEIGHT = 750;
    private static final int HEALTH = 10;
    private static final double FIRE_RATE = 0.04;
    private static final int VERTICAL_VELOCITY = 8; // Vertical movement speed
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;

    private final LevelViewLevelThree levelView;
    private final List<Integer> movePattern = new ArrayList<>();
    private int consecutiveMovesInSameDirection = 0;
    private int indexOfCurrentMove = 0;

    public BossLevelThree(LevelViewLevelThree levelView) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        this.levelView = levelView;
        initializeMovePattern();
    }

    private void initializeMovePattern() {
        // Add movement patterns (up, down, stationary)
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(0); // Stay stationary
        }
        Collections.shuffle(movePattern); // Shuffle the pattern for randomness
    }

    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection >= MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern); // Shuffle after a while
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove >= movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

    @Override
    public void updatePosition() {
        // Get the next move and apply it to vertical position
        int moveY = getNextMove();
        setLayoutY(getLayoutY() + moveY);

        // Keep the boss within the screen bounds
        if (getLayoutY() < 0) {
            setLayoutY(0);
        } else if (getLayoutY() + IMAGE_HEIGHT > SCREEN_HEIGHT) {
            setLayoutY(SCREEN_HEIGHT - IMAGE_HEIGHT);
        }
    }

    @Override
    public void updateActor() {
        updatePosition(); // Update vertical position
        fireProjectiles(); // Call fire logic
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        // Create and return a projectile
        BossProjectileLevelThree projectile = new BossProjectileLevelThree(getLayoutY() + IMAGE_HEIGHT / 2);
        if (getParent() instanceof javafx.scene.Group group) {
            if (!group.getChildren().contains(projectile)) {
                group.getChildren().add(projectile);
                System.out.println("Projectile added to scene!");
            }
        }
        if (levelView.getLevelThree() != null) {
            levelView.getLevelThree().addEnemyProjectile(projectile);
            System.out.println("Projectile added to enemyProjectiles!");
        }
        return projectile;
    }

    private void fireProjectiles() {
        if (Math.random() < FIRE_RATE) {
            System.out.println("Firing projectile!");
            fireProjectile();
        }
    }

    @Override
    public Bounds getCustomBounds() {
        double x = getLayoutX() + getTranslateX();
        double y = getLayoutY() + getTranslateY();
        double width = IMAGE_WIDTH;
        double height = IMAGE_HEIGHT;
        return new BoundingBox(x, y, width, height);
    }
}
