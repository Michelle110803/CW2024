package com.example.demo.Levels;

import com.example.demo.controller.Controller;
import com.example.demo.actors.Boss;
import javafx.stage.Stage;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background/levelTwoBackground.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private  Boss boss;
	private LevelViewLevelTwo levelView;
	private boolean levelTransitioned = false;
	private boolean isBossSpawned = false;
	private Controller controller;
	private Stage stage;



	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		levelView = (LevelViewLevelTwo) instantiateLevelView();
		boss = new Boss(levelView);
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed() && !levelTransitioned) {
			levelTransitioned = true;
			goToNextLevel(NEXT_LEVEL);
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !isBossSpawned) {
			boss = new Boss((LevelViewLevelTwo) levelView);
			addEnemyUnit(boss);
			isBossSpawned = true;
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}

}
