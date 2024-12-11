package com.example.demo;

import com.example.demo.actors.ActiveActor;
import javafx.geometry.Bounds;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	protected boolean isDestroyed;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		if (!isDestroyed){
			setDestroyed(true);
		}
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public abstract Bounds getCustomBounds();

}
