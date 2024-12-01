package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.LevelViewLevelTwo;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000;
	private static final double INITIAL_Y_POSITION = 400.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.01;
	private static final double SHIELD_DEACTIVATION_PROBABILITY = 0.05;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 5;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 500;
	private static final int SHIELD_COOLDOWN_DURATION = 300;
	private static final int MIN_TIME_BETWEEN_ACTIVATIONS = 100;
	private int framesSinceLastActivation = 0;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private boolean shieldCooldown = false;
	private int shieldTimer = 0;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private ShieldImage shieldImage;
	private LevelViewLevelTwo levelView;

	public Boss(LevelViewLevelTwo levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.levelView = levelView;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
		//shieldImage = new ShieldImage(getLayoutX(), getLayoutY());
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		System.out.println("Boss moving to: Y=" + currentPosition);
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
			System.out.println("Boss movement restricted within bounds");
		}
	}

	@Override
	public Bounds getCustomBounds(){
		double x = getLayoutX() + getTranslateX() + 10;
		double y = getLayoutY() + getTranslateY() + 10;
		double width = getBoundsInParent().getWidth() - 20;
		double height = getBoundsInParent().getHeight() - 20;
		return new BoundingBox(x, y, width, height);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();

		if(levelView != null){
			double bossX = getLayoutX() + getTranslateX();
			double bossY = getLayoutY() + getTranslateY();
			levelView.updateShieldPosition(bossX,bossY);
			System.out.println("Boss position: X= " + getLayoutX() + ",Y=" + getLayoutY());
		}
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
			System.out.println("Boss health: " + getHealth());
		} else{
			System.out.println("Damage blocked by shield");
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		framesSinceLastActivation++;

		if (isShielded) {
			framesWithShieldActivated++;
			System.out.println("Shield is active. Frame count: " + framesWithShieldActivated);

			if(Math.random() < SHIELD_DEACTIVATION_PROBABILITY){
				deactivateShield();
			}
		} else if (!shieldCooldown && framesSinceLastActivation >= MIN_TIME_BETWEEN_ACTIVATIONS) {
			if(Math.random() < BOSS_SHIELD_PROBABILITY){
				activateShield();
				framesSinceLastActivation = 0;
			}
		}

		if(shieldCooldown){
			shieldTimer++;
			System.out.println("Shield is in cooldown. timer: " + shieldTimer);

			if(shieldTimer >= SHIELD_COOLDOWN_DURATION){
				shieldCooldown = false;
				shieldTimer = 0;
				System.out.println("Shield cooldown over. ready to activate");
			}
		}
	}


	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		if(levelView != null){
			levelView.showShield();
		}
		System.out.println("Shield activated");
	}

	private void deactivateShield() {
		isShielded = false;
		shieldCooldown = true;
		shieldTimer = 0;
		if(levelView != null){
			levelView.hideShield();
		}
		System.out.println("Shield deactivated");
	}

}
