package com.example.demo;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.lang.reflect.Constructor;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		System.out.println("Initializing Level Two");
		boss = new Boss();
		if(boss==null){
			System.out.println("Error: Boss not initialized");
		} else{
			System.out.println("boss initialized successfully");
		}
	}

	public void update(Observable o, Object arg){
		String nextLevelName = (String) arg;
		System.out.println("Observer notified of level: " + nextLevelName);

		try{
			Constructor<?> constructor = Class.forName(nextLevelName).getConstructor(double.class, double.class, Stage.class);
			LevelParent nextLevel = (LevelParent)  constructor.newInstance(getScreenHeight(), getScreenWidth());

			System.out.println("Successfully created" + nextLevelName);
			Scene nextScene = nextLevel.initializeScene();
			Scene levelScene = getScene();
			nextLevel.startGame();
		}catch (Exception e){
			System.out.println("Error transitioning to " + nextLevelName);
			e.printStackTrace();
		}
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
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !boss.isDestroyed()) {
			System.out.println("Spawning boss enemy");
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

	//@Override
	//protected void onLevelComplete(){

	//}

}
