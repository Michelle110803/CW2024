package com.example.demo.actors;

import com.example.demo.*;
import com.example.demo.Levels.LevelViewLevelTwo;
import com.example.demo.displays.ShieldImage;
import com.example.demo.projectiles.BossProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.util.*;

/**
 * represents the boss in the game, extending {@link FighterPlane}
 * the boss has unique behaviours such as shields, movement patterns, and different phases
 * with varying fire rates and movement patterns
 *
 * @author michellealessandra
 * @version 1.0
 */

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
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int SHIELD_COOLDOWN_DURATION = 300;
	private final List<Integer> movePattern;
	private boolean isShielded = false;
	private boolean shieldCooldown = false;
	private int shieldTimer = 0;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private int phaseTimer = 0;
	private int currentPhase = 1;
	private LevelViewLevelTwo levelView;
	private double fireRate = BOSS_FIRE_RATE;

	/**
	 * constructs the boss with specified level view
	 *
	 * @param levelView the {@link LevelViewLevelTwo} to which the boss belongs
	 */

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

	/**
	 * updates the boss' position based on its movement pattern
	 * ensures the boss stays within defined screen boundaries
	 */

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

	/**
	 * gets custom bound of the boss for collision detection
	 *
	 * @return a {@link Bounds} object representing the custom bounds of the boss
	 */

	@Override
	public Bounds getCustomBounds(){
		double x = getLayoutX() + getTranslateX() + 10;
		double y = getLayoutY() + getTranslateY() + 10;
		double width = getBoundsInParent().getWidth() - 20;
		double height = getBoundsInParent().getHeight() - 20;
		return new BoundingBox(x, y, width, height);
	}

	/**
	 * updates the state of the boss, including position, shield, and phase logic
	 */

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

	/**
	 * fires a projectile from the boss with a probability determined by the fire rate
	 *
	 * @return a new {@link BossProjectile} if fired, or {@code null} otherwise
	 */

	@Override
	public ActiveActorDestructible fireProjectile() {
		return Math.random() < fireRate ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * takes damage. if the boss' shield is active, damage is ignored
	 */

	@Override
	public void takeDamage() {
		if (isShielded) {
		} else{
			super.takeDamage();
		}
	}

	/**
	 * initializes the movement pattern of the boss with vertical movements and pauses
	 */

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * adjusts the boss' movement pattern based on the specified velocity
	 *
	 * @param newVelocity the new velocity for the movement pattern
	 */

	private void adjustMovementPattern(int newVelocity){
		movePattern.clear();
		for(int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++){
			movePattern.add(newVelocity);
			movePattern.add(-newVelocity);
			movePattern.add(ZERO);
		}

		Collections.shuffle(movePattern);
	}

	/**
	 * updates the shield state of the boss, including activation, deactivation, and cooldown logic
	 */

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

	/**
	 * updates the boss' phase logic, changing behaviour such as fire rate and movement patterns
	 */

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
					setFireRate(0.08);
					break;
				case 2:
					activateShield();
					setFireRate(0.03);
					break;
				case 3:
					adjustMovementPattern(12);
					deactivateShield();
					break;
			}
		}
	}

	/**
	 * sets the boss' fire rate
	 *
	 * @param rate the new fire rate
	 */

	private void setFireRate(double rate){
		fireRate = rate;
	}

	/**
	 * retrieves the next move in the boss' movement pattern
	 *
	 * @return the next move value
	 */


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

	/**
	 * calculates the initial y-position for the boss' projectile
	 *
	 * @return the y-coordinate for the projectile's initial position
	 */


	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * checks whether the boss' shield is active
	 *
	 * @return {@code true } if the shield is active, {@code false } otherwise
	 */


	public boolean isShielded(){
		return isShielded;
	}

	/**
	 * activates the boss' shield
	 */

	private void activateShield() {
		isShielded = true;
		if(levelView != null){
			levelView.showShield();
		}
	}

	/**
	 * deactivates the boss' shield
	 */

	private void deactivateShield() {
		isShielded = false;
		if(levelView != null){
			levelView.hideShield();
		}
	}

}