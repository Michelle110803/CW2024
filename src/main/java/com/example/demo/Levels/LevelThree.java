package com.example.demo.Levels;
import com.example.demo.displays.Obstacle;
import com.example.demo.displays.PowerUp;
import com.example.demo.actors.EnemyPlane;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
//import java.sql.Time;
import java.util.Iterator;


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


    public LevelThree(double screenHeight, double screenWidth){
        super (BACKGROUND_IMAGE, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }
    @Override
    protected void initializeFriendlyUnits(){
        getRoot().getChildren().add(getUser());
    }
    @Override
    protected void spawnEnemyUnits(){
        enemySpawnTimer++;
        if(enemySpawnTimer >= ENEMY_SPAWN_RATE && getCurrentNumberOfEnemies() < MAX_ENEMIES){
            double randomY = Math.random() * getEnemyMaximumYPosition();
            EnemyPlane enemy = new EnemyPlane(getScreenWidth(), randomY);
            addEnemyUnit(enemy);
            enemySpawnTimer = 0;
        }
    }
    private void spawnObstacles(){
        obstacleSpawnTimer++;
        if(obstacleSpawnTimer >= OBSTACLE_SPAWN_RATE){
            double randomX = Math.random() * getScreenWidth();
            Obstacle obstacle = new Obstacle (randomX , 0);
            addEnemyUnit(obstacle);
            obstacleSpawnTimer = 0;
        }
    }


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




    @Override
    protected void checkIfGameOver(){
        if(userIsDestroyed()){
            loseGame();
        } else if (getUser().getNumberOfKills() >= MAX_ENEMIES) {
            levelTransitioned = true;
            winGame();
        }
    }


    protected void updateLevelThreeScene() {
        spawnEnemyUnits();
        spawnObstacles();
        spawnPowerUps();

        // Update power-ups and check for collisions
        for (Iterator<PowerUp> iterator = getPowerUps().iterator(); iterator.hasNext();) {
            PowerUp powerUp = iterator.next();
            powerUp.updatePosition(); // Move the power-up

            // Collision check with debugging logs
            if (powerUp.isCollectedByUser(getUser())) {
                System.out.println("Collision detected! PowerUp collected.");
                getUser().incrementHealth(getLevelView()); // Increment user health
                //getLevelView().updateHearts(getUser().getHealth()); // Update heart display
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



    protected void initializeSceneLogic(){
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


    @Override
    protected LevelView instantiateLevelView(){
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }
    @Override
    public Scene initializeScene(){
        Scene scene = super.initializeScene();
        initializeSceneLogic();
        return scene;
    }

    private void applyPowerUpEffect() {
        // Example effect: restore health
        getUser().heal(1);
        System.out.println("Health restored by 1! Current health: " + getUser().getHealth());
    }

}