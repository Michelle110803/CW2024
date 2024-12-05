package com.example.demo;

import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE = "/com/example/demo/images/background3.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int OBSTACLE_SPAWN_RATE = 150;
    private static final int MAX_OBSTACLES = 5;

    private int obstacleSpawnTimer = 0;
    private BossLevelThree boss;

    public LevelThree(double screenHeight, double screenWidth) {
        super(BACKGROUND_IMAGE, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
    }

    @Override
    protected void initializeFriendlyUnits() {
        if (!getRoot().getChildren().contains(getUser())) {
            getRoot().getChildren().add(getUser());
        }

        if (boss == null) {
            boss = new BossLevelThree((LevelViewLevelThree) getLevelView(), getRoot()); // Use the isolated BossLevelThree
        }

        if (!getRoot().getChildren().contains(boss)) {
            getRoot().getChildren().add(boss);
        }
    }


    protected void spawnObstacle() {
        obstacleSpawnTimer++;
        if (obstacleSpawnTimer >= OBSTACLE_SPAWN_RATE && getCurrentNumberOfObstacles() < MAX_OBSTACLES) {
            double randomX = Math.random() * getScreenWidth();
            Obstacle obstacle = new Obstacle(randomX, 0);
            obstacle.increaseSpeed(2);
            addEnemyUnit(obstacle);
            obstacleSpawnTimer = 0;
        }
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), PLAYER_INITIAL_HEALTH);
    }

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        initializeSceneLogic();
        return scene;
    }

    private void initializeSceneLogic() {
        Timeline levelThreeTimeLine = new Timeline(new KeyFrame(Duration.millis(40), e -> {
            spawnObstacle();
            checkIfGameOver();
            removeAllDestroyedActors();
        }));
        levelThreeTimeLine.setCycleCount(Timeline.INDEFINITE);
        levelThreeTimeLine.play();
    }

    private int getCurrentNumberOfObstacles() {
        return (int) getEnemyUnits().stream().filter(unit -> unit instanceof Obstacle).count();
    }

    @Override
    protected void spawnEnemyUnits() {
        // No regular enemies in Level 3, so this is intentionally left empty
    }

}
