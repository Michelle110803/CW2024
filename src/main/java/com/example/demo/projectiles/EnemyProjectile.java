package com.example.demo.projectiles;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "woodstockProjectile.png";
	private static final int IMAGE_HEIGHT = 70;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
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
