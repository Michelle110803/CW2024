package com.example.demo;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;

import java.sql.Time;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int ENEMY_SPAWN_RATE = 90;
    private static final int OBSTACLE_SPAWN_RATE = 50;
    private static final int POWER_UP_SPAWN_RATE = 150;
    private static final int MAX_ENEMIES = 5;

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
            obstacle.increaseSpeed(3);
            addEnemyUnit(obstacle);
            obstacleSpawnTimer = 0;
        }
    }

    //private void spawnPowerUps() {
        //double xPosition = Math.random() * getScreenWidth();
        //double yPosition = 0;
        //PowerUp powerUp = new PowerUp(xPosition, yPosition, "");
        //root.getChildren().add(powerUp);
        //friendlyUnits.add(powerUp);
    //}


    @Override
    protected void checkIfGameOver(){
        if(userIsDestroyed()){
            loseGame();
        } else if (getUser().getNumberOfKills() >= MAX_ENEMIES) {
            levelTransitioned = true;
            winGame();

        }
    }


    protected void updateLevelThreeScene(){
        double randomChance = Math.random();

        if(randomChance < 0.6){
            spawnObstacles();
        } else{
            spawnEnemyUnits();
        }
        //spawnEnemyUnits();
        //spawnObstacles();
        //spawnPowerUps();
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
}