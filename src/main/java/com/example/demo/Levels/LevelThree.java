package com.example.demo.Levels;

import com.example.demo.displays.Obstacle;
import com.example.demo.displays.PowerUp;
import com.example.demo.actors.EnemyPlane;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import java.util.Iterator;

/**
 * Represents Level Three in the game, which includes enemies, obstacles, and power-ups.
 * <p>
 * This level introduces a mix of gameplay elements, such as dynamic spawning of enemies, obstacles, and power-ups,
 * providing an engaging challenge for the player.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class LevelThree extends LevelParent {
    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int ENEMY_SPAWN_RATE = 60;
    private static final int OBSTACLE_SPAWN_RATE = 150;
    private static final int POWER_UP_SPAWN_RATE = 150;
    private static final int MAX_ENEMIES = 10;
    private int enemySpawnTimer = 0;
    private int obstacleSpawnTimer = 0;
    private int powerUpSpawnTimer = 0;
    private boolean levelTransitioned = false;

    /**
     * Constructs a new LevelThree instance with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    /**
     * Initializes friendly units (e.g., the user's plane) and adds them to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units periodically based on the spawn rate and ensures the number of enemies
     * does not exceed the maximum allowed.
     */
    @Override
    protected void spawnEnemyUnits() {
        enemySpawnTimer++;
        if (enemySpawnTimer >= ENEMY_SPAWN_RATE && getCurrentNumberOfEnemies() < MAX_ENEMIES) {
            double randomY = Math.random() * getEnemyMaximumYPosition();
            EnemyPlane enemy = new EnemyPlane(getScreenWidth(), randomY);
            addEnemyUnit(enemy);
            enemySpawnTimer = 0;
        }
    }

    /**
     * Spawns obstacles at regular intervals to add difficulty to the level.
     */
    private void spawnObstacles() {
        obstacleSpawnTimer++;
        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_RATE) {
            double randomX = Math.random() * getScreenWidth();
            Obstacle obstacle = new Obstacle(randomX, 0);
            addEnemyUnit(obstacle);
            obstacleSpawnTimer = 0;
        }
    }

    /**
     * Spawns power-ups at regular intervals to provide bonuses to the player.
     */
    private void spawnPowerUps() {
        powerUpSpawnTimer++;
        if (powerUpSpawnTimer >= POWER_UP_SPAWN_RATE) {
            double randomX = Math.random() * getScreenWidth();
            double randomY = Math.random() * getScreenHeight();
            PowerUp powerUp = new PowerUp(randomX, randomY);
            addPowerUp(powerUp);
            powerUpSpawnTimer = 0;
            System.out.println("Spawned a PowerUp at X: " + randomX + ", Y: -50");
        }
    }

    /**
     * Checks if the game is over due to the player's destruction or if the player has
     * defeated enough enemies to win the level.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (getUser().getNumberOfKills() >= MAX_ENEMIES) {
            levelTransitioned = true;
            winGame();
        }
    }

    /**
     * Updates the scene by spawning enemies, obstacles, and power-ups, and handling their behavior.
     */
    protected void updateLevelThreeScene() {
        spawnEnemyUnits();
        spawnObstacles();
        spawnPowerUps();

        // Update power-ups and check for collisions
        for (Iterator<PowerUp> iterator = getPowerUps().iterator(); iterator.hasNext();) {
            PowerUp powerUp = iterator.next();
            powerUp.updatePosition(); // Move the power-up

            if (powerUp.isCollectedByUser(getUser())) {
                System.out.println("Collision detected! PowerUp collected.");
                getUser().incrementHealth(getLevelView()); // Increment user health
                powerUp.destroy(); // Remove the power-up
                iterator.remove(); // Remove from active list
            } else {
                System.out.println("No collision. PowerUp Bounds: " + powerUp.getCustomBounds()
                        + ", UserPlane Bounds: " + getUser().getCustomBounds());
            }
        }

        removeAllDestroyedActors();
        checkIfGameOver();
    }

    /**
     * Initializes the scene logic, including the game loop for Level Three.
     */
    protected void initializeSceneLogic() {
        Timeline levelThreeTimeLine = new Timeline(new KeyFrame(Duration.millis(40),
                e -> {
                    updateLevelThreeScene();
                    checkIfGameOver();
                    removeAllDestroyedActors();
                }
        ));
        levelThreeTimeLine.setCycleCount(Timeline.INDEFINITE);
        levelThreeTimeLine.play();
    }

    /**
     * Instantiates the LevelView specific to Level Three.
     *
     * @return the LevelView instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    /**
     * Initializes the scene and starts the scene logic for Level Three.
     *
     * @return the initialized scene
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        initializeSceneLogic();
        return scene;
    }

    /**
     * Applies the effect of a power-up to the player (e.g., restoring health).
     */
    private void applyPowerUpEffect() {
        getUser().heal(1);
        System.out.println("Health restored by 1! Current health: " + getUser().getHealth());
    }
}
