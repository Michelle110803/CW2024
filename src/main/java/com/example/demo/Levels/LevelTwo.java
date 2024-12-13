package com.example.demo.Levels;

import com.example.demo.controller.Controller;
import com.example.demo.actors.Boss;

/**
 * Represents Level Two of the game, introducing a boss battle.
 * <p>
 * This level features a single boss enemy that the player must defeat to proceed to the next level.
 * </p>
 *
 * @author michellealessandra
 * @version 1.0
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/levelTwoBackground.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelThree";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private Boss boss;
	private LevelViewLevelTwo levelView;
	private boolean levelTransitioned = false;
	private boolean isBossSpawned = false;

	/**
	 * Constructs a new LevelTwo instance with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the screen
	 * @param screenWidth  the width of the screen
	 */
	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		levelView = (LevelViewLevelTwo) instantiateLevelView();
		boss = new Boss(levelView);
	}

	/**
	 * Initializes friendly units, such as the player's plane, and adds them to the scene.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Checks if the game is over due to the player's destruction or the boss being defeated.
	 * <p>
	 * If the boss is defeated and the level transition hasn't occurred yet, the game proceeds
	 * to the next level.
	 * </p>
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (boss.isDestroyed() && !levelTransitioned) {
			levelTransitioned = true;
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Spawns enemy units, specifically the boss for this level.
	 * <p>
	 * The boss is spawned once all other enemies are cleared and hasn't been spawned yet.
	 * </p>
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0 && !isBossSpawned) {
			boss = new Boss((LevelViewLevelTwo) levelView);
			addEnemyUnit(boss);
			isBossSpawned = true;
		}
	}

	/**
	 * Instantiates the level-specific view for Level Two.
	 *
	 * @return a {@link LevelViewLevelTwo} instance representing the level view
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
	}
}
