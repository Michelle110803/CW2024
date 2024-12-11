package com.example.demo.displays;

import com.example.demo.ActiveActorDestructible;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

public class Obstacle extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "Obstacle.png";
    private static final int IMAGE_HEIGHT = 100;
    private int verticalVelocity;

    public Obstacle(double initialXPos, double initialYPos){
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.verticalVelocity = 2;
    }

    @Override
    public void updatePosition(){
        moveVertically(verticalVelocity);
        if(getLayoutY() > 600){
            destroy();
        }
    }

    @Override
    public void updateActor(){
        updatePosition();
    }

    @Override
    public void takeDamage(){
        destroy();
    }

    @Override
    public Bounds getCustomBounds(){
        double x = getLayoutX() + getTranslateX();
        double y = getLayoutY() + getTranslateY();
        double width = getBoundsInParent().getWidth();
        double height = getBoundsInParent().getHeight();
        return new BoundingBox(x, y, width, height);
    }

    public void increaseSpeed(int increment){
        verticalVelocity += increment;
    }
}
