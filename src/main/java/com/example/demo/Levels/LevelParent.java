package com.example.demo.Levels;

import java.util.*;
import java.util.stream.Collectors;
import java.net.URL;


import com.example.demo.ActiveActorDestructible;
import com.example.demo.projectiles.EnemyProjectile;
import com.example.demo.displays.PowerUp;
import com.example.demo.actors.Boss;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

/**
 * represents the parent class for all game levels
 * the {@code LevelParent} class provides core functionality for level initialization,
 * actor management, collision handling, and game state transitions
 *
 * this abstract class serves as a base for specific levels, handling:
 * - Background setup
 * - actor and projectile management
 * - game state checks (win/lose condition)
 * - interactions like collisions and projectile firing
 *
 * subclasses must implement specific behaviour by overriding abstract methods
 *
 * @author michellealessandra
 * @version 1.0
 */

public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 40;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	protected final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<PowerUp> powerUps = new ArrayList<>();

	private int currentNumberOfEnemies;
	private final LevelView levelView;

	/**
	 * constructs a {@code LevelParent} with the specified parameters
	 *
	 * @param backgroundImageName the path to the background image for the level
	 * @param screenHeight the height of the game screen
	 * @param screenWidth the width of the game screen
	 * @param playerInitialHealth the initial health of the player
	 *
	 * @throws IllegalArgumentException if the background image cannot be found
	 */

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		// Load background image safely
		URL resourceURL = getClass().getResource(backgroundImageName);
		if (resourceURL == null) {
			throw new IllegalArgumentException("Background image not found: " + backgroundImageName);
		}
		this.background = new ImageView(new Image(resourceURL.toExternalForm()));

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);
	}

	/**
	 * abstract method for initializing friendly units in the level
	 */

	protected abstract void initializeFriendlyUnits();

	/**
	 * abstract method for checking if the game has ended due to win or loss conditions.
	 */

	protected abstract void checkIfGameOver();

	/**
	 * abstract method for spawning enemy units in the level
	 */

	protected abstract void spawnEnemyUnits();

	/**
	 * abstract method for creating a {@link LevelView} for the level
	 * @return a {@code LevelView } object representing the UI for the level
	 */

	protected abstract LevelView instantiateLevelView();

	/**
	 * initializes the scene by setting up the background, friendly units, and heart display
	 * @return the initialized {@link Scene}
	 */

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * starts the game by focusing on the background and playing the timeline
	 */

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * transitions to the next level by notifying observers
	 * @param levelName the fully qualified class name of the next level
	 */

	public void goToNextLevel(String levelName) {
		if (levelName != null) {
			setChanged();
			notifyObservers(levelName);
			System.out.println("Notifying controller to transition to: " + levelName);
			timeline.stop();
		} else {
			System.err.println("Level name is null; cannot transition to the next level.");
		}
	}

	/**
	 * updates teh scene, including actors, collisions, and game state
	 */

	protected void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * initializes the game timeline to control the game loop
	 */

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * initializes the background image and user input handling
	 */

	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case UP -> user.moveUp();
				case DOWN -> user.moveDown();
				case LEFT -> user.moveLeft();
				case RIGHT -> user.moveRight();
				case SPACE -> fireProjectile();
			}
		});
		background.setOnKeyReleased(e -> {
			switch (e.getCode()) {
				case UP, DOWN -> user.stopVertical();
				case LEFT, RIGHT -> user.stopHorizontal();
			}
		});
		root.getChildren().add(background);
	}

	/**
	 * fires a projectile from the user plane
	 */

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * spawns enemy projectiles by simulating enemy firing behaviour
	 */

	private void generateEnemyFire() {
		enemyUnits.stream()
				.filter(enemy -> enemy instanceof FighterPlane) // Filter only FighterPlanes
				.map(enemy -> ((FighterPlane) enemy).fireProjectile())
				.filter(Objects::nonNull) // Ignore null projectiles
				.forEach(this::spawnEnemyProjectile);
	}

	/**
	 * spawns an enemy projectile in the scene
	 * @param projectile the enemy projectile to be spawned
	 */


	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * updates all actors (friendly units, enemy units, and projectiles) in the game
	 * removes off-screen or destroyed actors and updates their positions
	 */

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.removeIf(enemy -> enemy.getLayoutX() < -50); // Remove enemies off-screen
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.removeIf(projectile -> projectile.getLayoutX() > getScreenWidth() + 50); // Remove off-screen projectiles
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.removeIf(projectile -> projectile.getLayoutX() < -50); // Remove enemy projectiles off-screen
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}

	/**
	 * removes all destroyed actors (friendly units, enemy units, and projectiles)
	 * from both the scene and their respective lists
	 */


	protected void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * removes destroyed actors from a give list and the game scene
	 * @param actors the list of actors to check and remove
	 */

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors); // Remove from scene
		actors.removeAll(destroyedActors);             // Remove from list
	}

	/**
	 * handles collisions between friendly units and enemy units
	 * both units take damage when a collisions occur
	 */


	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * handles collision between the user projectiles and enemy units
	 * damage is applied to the enemy, and the projectile is destroyed on impact
	 */

	protected void handleUserProjectileCollisions() {
		for(ActiveActorDestructible projectile : userProjectiles){
			for(ActiveActorDestructible enemy : enemyUnits){
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
	}

	/**
	 * handles collisions between enemy projectiles and friendly units
	 * damage is applied to the friendly unit, and the projectile is destroyed on impact
	 */


	protected void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

	/**
	 * handles generic collisions between two group of actors
	 * both actors take damage when a collision occur
	 *
	 * @param actors1 the first group of actors
	 * @param actors2 the second group of actors
	 */

	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		Set<ActiveActorDestructible> damagedActors = new HashSet<>();

		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getCustomBounds().intersects(otherActor.getCustomBounds())) {
					actor.takeDamage();
					otherActor.takeDamage();

					if(actor instanceof UserPlane && actor.isDestroyed()){
						loseGame();
					}

					damagedActors.add(actor);
					damagedActors.add(otherActor);
				}
			}
		}
	}

	/**
	 * checks if any enemies have passed through the player's defenses
	 * and applies damage to the user if an enemy has penetrated
	 */

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * updates the LevelView (e.g., the heart display) to reflect the user's current health
	 */

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * updates the user's kill count based on the number of destroyed enemies
	 */

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * checks if an enemy projectile has penetrated the player's defenses
	 *
	 * @param enemy the enemy projectile to check
	 * @return true if the projectile has passed through, false otherwise
	 */

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		if(enemy instanceof EnemyProjectile){
			return enemy.getX() <= 0;
		}

		return false;
	}

	/**
	 * ends the game with a win, stops the timeline, and displays the win image
	 */

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	/**
	 * ends the game with a loss, stops the timeline, and displays the game over image
	 */


	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	/**
	 * retrieves the user plane
	 * @return the user plane
	 */

	protected UserPlane getUser() {
		return user;
	}

	/**
	 * retrieves the root group for the scene graph
	 * @return the root group
	 */

	protected Group getRoot() {
		return root;
	}

	/**
	 * retrieves the screen width
	 * @return the screen width
	 */

	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * retrieves the screen height
	 * @return the screen height
	 */

	protected double getScreenHeight() {
		return screenHeight;
	}

	/**
	 * adds an enemy unit to the game scene
	 * @param enemy the enemy unit to add
	 */

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		if (!enemyUnits.contains(enemy)) {
			enemyUnits.add(enemy); // Add to the enemy list only if it's not already there
			if (!getRoot().getChildren().contains(enemy)) {
				getRoot().getChildren().add(enemy); // Add to the root only if it's not already present
			}
		}
	}

	/**
	 * updates the number of enemies currently in the game
	 */


	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	/**
	 * retrieves the number of enemies currently in the game
	 * @return the number of enemies
	 */

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * retrieves the maximum Y-position an enemy can spawn at
	 * @return the maximum Y-position
	 */

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * checks if the user plane is destroyed
	 * @return true if the user plane is destroyed, false otherwise
	 */

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * retrieves the level view associated with this level
	 * @return the level view
	 */

	protected LevelView getLevelView(){
		return levelView;
	}

	/**
	 * retrieves the list of power-ups currently in the game
	 * @return the list of power-ups
	 */

	protected List<PowerUp> getPowerUps(){
		return powerUps;
	}

	/**
	 * adds a power-up to the game scene and the list of power-ups
	 * @param powerUp the power-up to add
	 */

	protected void addPowerUp(PowerUp powerUp) {
		getPowerUps().add(powerUp);
		getRoot().getChildren().add(powerUp); // Add to the scene graph
		System.out.println("PowerUp added at X: " + powerUp.getLayoutX() + ", Y: " + powerUp.getLayoutY());
	}

}
