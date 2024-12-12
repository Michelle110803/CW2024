package com.example.demo.actors;

import com.example.demo.*;
import com.example.demo.Levels.LevelViewLevelTwo;
import com.example.demo.displays.ShieldImage;
import com.example.demo.projectiles.BossProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.*;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "WoodstockBoss.png";
	private static final double INITIAL_X_POSITION = 1000;
	private static final double INITIAL_Y_POSITION = 400.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.01;
	private static final double SHIELD_DEACTIVATION_PROBABILITY = 0.01;
	private static final int IMAGE_HEIGHT = 200;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 5;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 800;
	private static final int SHIELD_COOLDOWN_DURATION = 300;
	private static final int MIN_TIME_BETWEEN_ACTIVATIONS = 100;
	private int framesSinceLastActivation = 0;
	private final List<Integer> movePattern;
	private boolean isShielded = false;
	private boolean shieldCooldown = false;
	private int shieldTimer = 0;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private int attackPhase = 1;
	private int phaseTimer = 0;
	private int currentPhase = 1;
	private ShieldImage shieldImage;
	private LevelViewLevelTwo levelView;
	private double fireRate = BOSS_FIRE_RATE;

	public Boss(LevelViewLevelTwo levelView) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		this.levelView = levelView;
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < 0) {
			setTranslateY(-getLayoutY());
		} else if (currentPosition + IMAGE_HEIGHT > Y_POSITION_LOWER_BOUND) {
			setTranslateY(Y_POSITION_LOWER_BOUND - getLayoutY() - IMAGE_HEIGHT);

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
		updatePhase();

		if(levelView != null){
			double bossX = getLayoutX() + getTranslateX();
			double bossY = getLayoutY() + getTranslateY();
			levelView.updateShieldPosition(bossX,bossY);
		}
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		return Math.random() < fireRate ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	@Override
	public void takeDamage() {
		if (isShielded) {
		} else{
			super.takeDamage();
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

	private void adjustMovementPattern(int newVelocity){
		movePattern.clear();
		for(int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++){
			movePattern.add(newVelocity);
			movePattern.add(-newVelocity);
			movePattern.add(ZERO);
		}

		Collections.shuffle(movePattern);
	}

	private void updateShield() {

		if (isShielded) {
			framesWithShieldActivated++;

			if(Math.random() < SHIELD_DEACTIVATION_PROBABILITY){
				deactivateShield();
			}
		} else if (!shieldCooldown) {
			if(Math.random() < BOSS_SHIELD_PROBABILITY){
				activateShield();
			}
		}

		if(shieldCooldown){
			shieldTimer++;

			if(shieldTimer >= SHIELD_COOLDOWN_DURATION){
				shieldCooldown = false;
				shieldTimer = 0;
			}
		}

		if(levelView != null){
			if(isShielded){
				levelView.showShield();
			} else{
				levelView.hideShield();
			}
		}
	}

	private void updatePhase(){
		phaseTimer++;

		if(phaseTimer > 500){
			phaseTimer = 0;
			currentPhase++;

			if(currentPhase > 3){
				currentPhase = 1;
			}

			switch (currentPhase){
				case 1:
					System.out.println("Boss entered Phase 1: Aggresive fire rate");
					setFireRate(0.08);
					break;
				case 2:
					System.out.println("Boss entered phase 2: defensive shield");
					activateShield();
					setFireRate(0.03);
					break;
				case 3:
					System.out.println("Boss entered Phase 3: Rapid movement ");
					adjustMovementPattern(12);
					deactivateShield();
					break;
			}
		}
	}

	private void setFireRate(double rate){
		fireRate = rate;
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

	public boolean isShielded(){
		return isShielded;
	}

	private void activateShield() {
		isShielded = true;
		if(levelView != null){
			levelView.showShield();
		}
	}

	private void deactivateShield() {
		isShielded = false;
		//shieldCooldown = true;
		//shieldTimer = 0;
		if(levelView != null){
			levelView.hideShield();
		}
	}

}