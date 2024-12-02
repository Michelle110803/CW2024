package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private static final int OFFSET_X = -50;
	private static final int OFFSET_Y = -30;
	private final Group root;
	private final ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION,SHIELD_Y_POSITION);
		addImagesToRoot();
	}
	
	private void addImagesToRoot() {
		try{
			if(!root.getChildren().contains(shieldImage)){
				root.getChildren().add(shieldImage);
				System.out.println("ShieldImage added to the root");
			}
			//root.getChildren().add(shieldImage);
		} catch(Exception e){
			System.out.println("Error adding shield image to root");
			e.printStackTrace();
		}
	}
	
	public void showShield() {
		shieldImage.showShield();
		shieldImage.toFront();
		System.out.println("Shield brought to the front");
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateShieldPosition(double x, double y){
		shieldImage.setLayoutX(x + OFFSET_X);
		shieldImage.setLayoutY(y + OFFSET_Y);
		shieldImage.toFront();
		System.out.println("Shield position updated: X= " + x + ",Y= " + y);
	}

	public boolean isShieldVisible(){
		return shieldImage.isVisible();
	}


}
