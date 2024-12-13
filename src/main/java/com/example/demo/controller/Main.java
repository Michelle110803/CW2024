package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The {@code Main} class is the entry point for the Sky Battle game application.
 * It initializes the JavaFX application, sets up the primary stage, and launches the game through the {@link Controller}.
 *
 * This class extends {@link Application} to utilize JavaFX's application lifecycle.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * Starts the JavaFX application by setting up the stage and launching the game.
	 *
	 * @param stage the primary {@link Stage} for the JavaFX application.
	 *
	 * @throws ClassNotFoundException       if the specified class cannot be found.
	 * @throws NoSuchMethodException        if the constructor cannot be found.
	 * @throws SecurityException            if access to the constructor is restricted.
	 * @throws InstantiationException       if the class cannot be instantiated.
	 * @throws IllegalAccessException       if the constructor is inaccessible.
	 * @throws IllegalArgumentException     if arguments for the constructor are invalid.
	 * @throws InvocationTargetException    if the constructor invocation fails.
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}

	/**
	 * The main method launches the JavaFX application.
	 *
	 * @param args command-line arguments (not used).
	 */
	public static void main(String[] args) {
		launch();
	}
}
