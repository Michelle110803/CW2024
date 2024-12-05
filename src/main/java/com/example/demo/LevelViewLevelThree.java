package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelThree extends LevelView {

    private static final int SHIELD_X_POSITION = 1150;
    private static final int SHIELD_Y_POSITION = 500;
    private static final int OFFSET_X = -50;
    private static final int OFFSET_Y = -30;
    private final ShieldImage shieldImage;

    public LevelViewLevelThree(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
        this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
        addShieldToRoot(root);
    }

    private void addShieldToRoot(Group root) {
        if (!root.getChildren().contains(shieldImage)) {
            root.getChildren().add(shieldImage);
        }
    }

    public void showShield() {
        shieldImage.showShield();
        shieldImage.toFront();
    }

    public void hideShield() {
        shieldImage.hideShield();
    }

    public void updateShieldPosition(double x, double y) {
        shieldImage.setLayoutX(x + OFFSET_X);
        shieldImage.setLayoutY(y + OFFSET_Y);
        shieldImage.toFront();
    }
}
