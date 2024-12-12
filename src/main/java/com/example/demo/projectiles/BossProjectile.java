package com.example.demo.projectiles;

import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "BossProjectile.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 920;

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}

	@Override
	public Bounds getCustomBounds(){
		double x = getLayoutX() + getTranslateX();
		double y = getLayoutY() + getTranslateY();
		double width = getBoundsInParent().getWidth();
		double height = getBoundsInParent().getHeight();
		return new BoundingBox(x, y, width, height);
	}
	
}
