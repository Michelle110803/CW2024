package com.example.demo.Levels;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.actors.EnemyPlane;

/**
 * Represents the first level of the game.
 * The {@code LevelOne} class defines the behavior, enemy spawning, and win/lose conditions specific to this level.
 *
 * Extends {@link LevelParent} to inherit core level functionality.
 *
 * Key features of this level:
 * - Background image specific to Level One.
 * - Spawns enemy planes with a defined probability.
 * - The player advances to the next level upon achieving a kill target.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class LevelOne extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/levelOneBackground.png";
	private static final String NEXT_LEVEL = "com.example.demo.Levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 5;
	private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
	 * Constructs a {@code LevelOne} with the specified screen dimensions.
	 *
	 * @param screenHeight the height of the game screen.
	 * @param screenWidth  the width of the game screen.
	 */
	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks the game's state to determine if the game is over or if the player should advance to the next level.
	 * - If the user's plane is destroyed, the game ends.
	 * - If the user has reached the required number of kills, the next level is loaded.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		} else if (userHasReachedKillTarget()) {
			goToNextLevel(NEXT_LEVEL);
		}
	}

	/**
	 * Initializes the friendly units for the level, adding the user's plane to the scene.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
	 * Spawns enemy units for the level. The number of enemies is determined by the current count
	 * and the total number allowed for the level. Enemies are spawned with a random Y position.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Creates and returns a {@link LevelView} for this level.
	 *
	 * @return a {@code LevelView} representing the UI and display for Level One.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Checks if the user has achieved the required number of kills to advance to the next level.
	 *
	 * @return {@code true} if the user has reached the kill target, {@code false} otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
