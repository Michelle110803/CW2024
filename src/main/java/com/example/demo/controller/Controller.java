package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.SoundManager;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import com.example.demo.Levels.LevelParent;
import com.example.demo.menus.MenuParent;

/**
 * The {@code Controller} class manages the transitions between menus and levels in the game.
 * It uses reflection to dynamically instantiate menus and levels based on their class names.
 * The class also acts as an {@link Observer} to respond to notifications from observed objects.
 *
 * @author michellealessandra
 * @version 1.0
 */
public class Controller implements Observer {

	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;
	private LevelParent currentLevel;
	private SoundManager soundManager;

	/**
	 * Constructs a {@code Controller} with the specified stage.
	 *
	 * @param stage the main {@link Stage} used for the game.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
		stage.setUserData(this);
		soundManager = new SoundManager();
	}

	/**
	 * Launches the game by showing the main stage and transitioning to the home menu.
	 *
	 * @throws ClassNotFoundException    if the class for the menu cannot be found.
	 * @throws NoSuchMethodException     if the constructor for the menu class is not found.
	 * @throws SecurityException         if there is a security violation.
	 * @throws InstantiationException    if the menu class cannot be instantiated.
	 * @throws IllegalAccessException    if the menu class or its constructor is not accessible.
	 * @throws IllegalArgumentException  if the arguments for the menu constructor are invalid.
	 * @throws InvocationTargetException if the constructor invocation fails.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.show();
		goToMenu(HOME_MENU);
	}

	/**
	 * Transitions to a specified menu using the given class name.
	 *
	 * @param menuClassName the fully qualified name of the menu class.
	 * @throws ClassNotFoundException    if the class for the menu cannot be found.
	 * @throws NoSuchMethodException     if the constructor for the menu class is not found.
	 * @throws SecurityException         if there is a security violation.
	 * @throws InstantiationException    if the menu class cannot be instantiated.
	 * @throws IllegalAccessException    if the menu class or its constructor is not accessible.
	 * @throws IllegalArgumentException  if the arguments for the menu constructor are invalid.
	 * @throws InvocationTargetException if the constructor invocation fails.
	 */
	private void goToMenu(String menuClassName) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("Going to menu " + menuClassName);

		Class<?> myClass = Class.forName(menuClassName);
		Constructor<?> constructor;
		MenuParent myMenu;

		if (menuClassName.equals("com.example.demo.menus.PauseMenu")) {
			constructor = myClass.getConstructor(Stage.class, double.class, double.class, LevelParent.class);
			myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight(), currentLevel);
		} else {
			constructor = myClass.getConstructor(Stage.class, double.class, double.class);
			myMenu = (MenuParent) constructor.newInstance(stage, stage.getWidth(), stage.getHeight());
		}

		myMenu.addObserver(this);

		Scene scene = myMenu.initializeScene();
		stage.setScene(scene);
	}

	/**
	 * Transitions to a specified level using the given class name.
	 *
	 * @param className the fully qualified name of the level class.
	 * @throws ClassNotFoundException    if the class for the level cannot be found.
	 * @throws NoSuchMethodException     if the constructor for the level class is not found.
	 * @throws SecurityException         if there is a security violation.
	 * @throws InstantiationException    if the level class cannot be instantiated.
	 * @throws IllegalAccessException    if the level class or its constructor is not accessible.
	 * @throws IllegalArgumentException  if the arguments for the level constructor are invalid.
	 * @throws InvocationTargetException if the constructor invocation fails.
	 */
	public void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);

		Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
		myLevel.addObserver(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();
	}

	/**
	 * Responds to notifications from observed objects to handle transitions.
	 *
	 * @param observable the observable object.
	 * @param arg        the argument passed by the observable.
	 */
	@Override
	public void update(Observable observable, Object arg) {
		if (arg instanceof String) {
			String notification = (String) arg;
			System.out.println("Controller notified to transition to: " + notification);
			try {
				if (notification.startsWith("com.example.demo.Level")) {
					goToLevel(notification);
				} else if (notification.startsWith("com.example.demo.menus")) {
					goToMenu(notification);
				} else {
					System.err.println("Unexpected notification type: " + notification);
				}
			} catch (Exception e) {
				handleError("Error handling update notification: " + notification, e);
			}
		} else {
			System.err.println("Unexpected update notification received: " + arg);
		}
	}

	/**
	 * Handles errors during transitions and displays an alert to the user.
	 *
	 * @param message a message describing the error.
	 * @param error   the exception that caused the error.
	 */
	private void handleError(String message, Throwable error) {
		System.err.println(message);
		error.printStackTrace();
		showAlert("Level Transition Error", message + "\nDetails: " + error.getMessage());
	}

	/**
	 * Displays an error alert with the specified title and content.
	 *
	 * @param title   the title of the alert.
	 * @param content the content of the alert.
	 */
	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.show();
	}

	/**
	 * Retrieves the {@link SoundManager} instance used by the controller.
	 *
	 * @return the {@link SoundManager} instance.
	 */
	public SoundManager getSoundManager() {
		return soundManager;
	}
}
