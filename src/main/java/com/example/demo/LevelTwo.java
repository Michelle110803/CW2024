package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.Boss;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.lang.reflect.Constructor;

import com.example.demo.controller.Controller;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private boolean levelTransitioned = false;
	private boolean isBossSpawned = false;
	private Controller controller;
	private Stage stage;



	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		boss = new Boss();
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			System.out.println("Player destroyed in Leveltwo");
			loseGame();
		}
		else if (boss.isDestroyed() && !levelTransitioned) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !boss.isDestroyed() && !isBossSpawned) {
			System.out.println("Spawning boss enemy");
			addEnemyUnit(boss);
			isBossSpawned = true;
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		System.out.println("LevelView for leveltwo initialized");
		return levelView;
	}

}
