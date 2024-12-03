package com.example.demo;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;

import java.net.URL;

public class Obstacle extends ActiveActorDestructible {

    private static final String IMAGE_NAME = "Obstacle.png";
    private static final int IMAGE_HEIGHT = 100;
    private static final int VERTICAL_VELOCITY = 2;

    public Obstacle(double initialXPos, double initialYPos){
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition(){
        moveVertically(VERTICAL_VELOCITY);
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
}
