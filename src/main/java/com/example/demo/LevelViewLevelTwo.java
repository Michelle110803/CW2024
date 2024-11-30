package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		System.out.println("Initializing LevelViewLevelTwo");
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION,SHIELD_Y_POSITION);
		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
		try{
			root.getChildren().add(shieldImage);
		} catch(Exception e){
			System.out.println("Error adding shield image to root");
			e.printStackTrace();
		}
	}
	
	public void showShield() {
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

}
