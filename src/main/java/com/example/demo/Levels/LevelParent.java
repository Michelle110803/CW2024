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

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

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

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

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

	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.stream()
				.filter(enemy -> enemy instanceof FighterPlane) // Filter only FighterPlanes
				.map(enemy -> ((FighterPlane) enemy).fireProjectile())
				.filter(Objects::nonNull) // Ignore null projectiles
				.forEach(this::spawnEnemyProjectile);
	}



	protected void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(ActiveActorDestructible::updateActor);
		enemyUnits.removeIf(enemy -> enemy.getLayoutX() < -50); // Remove enemies off-screen
		enemyUnits.forEach(ActiveActorDestructible::updateActor);
		userProjectiles.removeIf(projectile -> projectile.getLayoutX() > getScreenWidth() + 50); // Remove off-screen projectiles
		userProjectiles.forEach(ActiveActorDestructible::updateActor);
		enemyProjectiles.removeIf(projectile -> projectile.getLayoutX() < -50); // Remove enemy projectiles off-screen
		enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
	}


	protected void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream()
				.filter(ActiveActorDestructible::isDestroyed)
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors); // Remove from scene
		actors.removeAll(destroyedActors);             // Remove from list
	}


	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

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


	protected void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}

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

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		if(enemy instanceof EnemyProjectile){
			return enemy.getX() <= 0;
		}

		return false;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}


	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected double getScreenHeight() {
		return screenHeight;
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		if (!enemyUnits.contains(enemy)) {
			enemyUnits.add(enemy); // Add to the enemy list only if it's not already there
			if (!getRoot().getChildren().contains(enemy)) {
				getRoot().getChildren().add(enemy); // Add to the root only if it's not already present
			}
		}
	}


	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}


	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}


	protected LevelView getLevelView(){
		return levelView;
	}


	protected List<PowerUp> getPowerUps(){
		return powerUps;
	}

	protected void addPowerUp(PowerUp powerUp) {
		getPowerUps().add(powerUp);
		getRoot().getChildren().add(powerUp); // Add to the scene graph
		System.out.println("PowerUp added at X: " + powerUp.getLayoutX() + ", Y: " + powerUp.getLayoutY());
	}

}
