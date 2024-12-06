package com.example.demo;

import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.geometry.Bounds;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int OBSTACLE_SPAWN_RATE = 150;
    private static final int MAX_OBSTACLES = 5;

    private int obstacleSpawnTimer = 0;
    private BossLevelThree boss; // Level 3 boss

    private final List<ActiveActorDestructible> enemyProjectiles = new ArrayList<>();


    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        // Add the user plane to the scene
        if (!getRoot().getChildren().contains(getUser())) {
            getRoot().getChildren().add(getUser());
        }

        // Initialize the boss if it's null
        if (boss == null) {
            // Get the LevelViewLevelThree instance
            LevelViewLevelThree levelView = (LevelViewLevelThree) getLevelView();

            // Create the BossLevelThree instance
            boss = new BossLevelThree(levelView);
        }

        // Add the boss to the scene if it's not already added
        if (!getRoot().getChildren().contains(boss)) {
            getRoot().getChildren().add(boss);
        }
    }



    @Override
    protected void spawnEnemyUnits() {
        // No regular enemies in Level 3
    }

    private void spawnObstacle() {
        obstacleSpawnTimer++;
        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_RATE && getCurrentNumberOfObstacles() < MAX_OBSTACLES) {
            double randomX = Math.random() * getScreenWidth();
            Obstacle obstacle = new Obstacle(randomX, 0);
            obstacle.increaseSpeed(2); // Adjust obstacle speed
            addEnemyUnit(obstacle);
            obstacleSpawnTimer = 0;
        }
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame(); // Transition to the next level or win the game
        }
    }


    protected void updateLevelThreeScene() {
        boss.updateActor(); // Update the boss
        enemyProjectiles.forEach(ActiveActorDestructible::updatePosition); // Update projectile positions
        handleEnemyProjectileCollisions(); // Check for collisions
        spawnObstacle(); // Spawn obstacles if needed
    }






    //@Override
    protected void initializeSceneLogic() {
        Timeline levelThreeTimeLine = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            updateLevelThreeScene();
            checkIfGameOver();
            removeAllDestroyedActors();
        }));
        levelThreeTimeLine.setCycleCount(Timeline.INDEFINITE);
        levelThreeTimeLine.play();
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH, this);
    }


    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        initializeSceneLogic();
        return scene;
    }

    private int getCurrentNumberOfObstacles() {
        return (int) getEnemyUnits().stream().filter(unit -> unit instanceof Obstacle).count();
    }


    protected void handleUserProjectileCollisions() {
        for(ActiveActorDestructible projectile : getUserProjectiles()){
            for(ActiveActorDestructible enemy : getEnemyUnits()){
                if(projectile.getCustomBounds().intersects(enemy.getCustomBounds())){
                    if(enemy instanceof Boss){
                        Boss boss = (Boss) enemy;
                        if(boss.isShielded()){
                            System.out.println("Projectile hit shield. boss takes no damage");
                        } else{
                            boss.takeDamage();
                            System.out.println("Projectile hit boss. health reduced, remaining health: " + boss.getHealth());
                        }
                    } else{
                        enemy.takeDamage();
                    }
                    projectile.destroy();
                }
            }
        }
        //handleCollisions(userProjectiles, enemyUnits);
    }

    protected void handleEnemyProjectileCollisions() {
        for (ActiveActorDestructible projectile : getEnemyProjectiles()) {
            Bounds projectileBounds = projectile.getCustomBounds();
            Bounds userBounds = getUser().getCustomBounds();

            System.out.println("Projectile bounds: " + projectileBounds);
            System.out.println("User bounds: " + userBounds);

            // Manual intersection check
            if (projectileBounds.getMaxX() >= userBounds.getMinX() &&
                    projectileBounds.getMinX() <= userBounds.getMaxX() &&
                    projectileBounds.getMaxY() >= userBounds.getMinY() &&
                    projectileBounds.getMinY() <= userBounds.getMaxY()) {
                System.out.println("Manual collision detected between projectile and user!");
                getUser().takeDamage();
                projectile.destroy();
            }
        }
    }



    public void addEnemyProjectile(ActiveActorDestructible projectile) {
        if (!enemyProjectiles.contains(projectile)) {
            enemyProjectiles.add(projectile);
            if (!getRoot().getChildren().contains(projectile)) {
                getRoot().getChildren().add(projectile);
            }
        }
    }




    protected void removeAllDestroyedActors() {
        super.removeAllDestroyedActors(); // Call parent logic
        enemyProjectiles.removeIf(ActiveActorDestructible::isDestroyed); // Remove destroyed projectiles
    }




}
