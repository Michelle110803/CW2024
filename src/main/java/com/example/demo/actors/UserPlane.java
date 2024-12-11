package com.example.demo.actors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.displays.HeartDisplay;
import com.example.demo.Levels.LevelView;
import com.example.demo.projectiles.UserProjectile;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;


public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "snoopyPlane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_LEFT_BOUND = 0.0;
	private static final double X_RIGHT_BOUND = 800.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 150;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int horizontalVelocityMultiplier;
	private int numberOfKills;
	private int health;
	private HeartDisplay heartDisplay;
	private LevelView levelView;

	private boolean shieldActive = false;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		this.health = initialHealth;
		velocityMultiplier = 0;
		horizontalVelocityMultiplier = 0;
	}
	
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()){
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * horizontalVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			if(newXPosition < X_LEFT_BOUND || newXPosition > X_RIGHT_BOUND){
				this.setTranslateX(initialTranslateX);
			}
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
	}

	public void activateShield(){
		shieldActive = true;
	}

	public void deactivateShield(){
		shieldActive = false;
	}

	@Override
	public void takeDamage(){
		if(shieldActive){
			System.out.println("User shielded, no damage taken");
			return;
		}
		super.takeDamage();
	}

	public boolean isShieldActive(){
		return shieldActive;
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		double currentX = getLayoutX() + getTranslateX();
		double currentY = getLayoutY() + getTranslateY();

		double projectileX = currentX + PROJECTILE_X_POSITION;
		double projectileY = currentY + PROJECTILE_Y_POSITION_OFFSET;

		return new UserProjectile(projectileX, projectileY);
	}

	private boolean isMovingVertically() {
		return velocityMultiplier != 0;
	}

	private boolean isMovingHorizontally(){
		return horizontalVelocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stopVertical() {
		velocityMultiplier = 0;
	}

	public void stopHorizontal(){
		horizontalVelocityMultiplier = 0;
	}

	public void moveLeft(){
		horizontalVelocityMultiplier = -1;
	}

	public void moveRight(){
		horizontalVelocityMultiplier = 1;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}


	public void incrementHealth(LevelView levelView) {
		if (health < 10) { // Ensure health doesn't exceed maximum
			health++;
			System.out.println("Health incremented. Current health: " + health);
			levelView.updateHearts(health); // Update LevelView with the new health
		} else {
			System.out.println("Health is already at maximum!");
		}
	}




	public void heal(int healthPoints) {
		int maxHealth = 10; // Adjust this value based on your game's design
		this.health = Math.min(this.health + healthPoints, maxHealth);
		System.out.println("Healed! Current health: " + this.health);
	}

	public void setLevelView(LevelView levelView){
		this.levelView = levelView;
	}


}
