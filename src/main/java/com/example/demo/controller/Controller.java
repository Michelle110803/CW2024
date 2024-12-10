package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;


import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;
import com.example.demo.menus.MenuParent;
import com.example.demo.menus.HomeMenu;


public class Controller implements Observer {

	//private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private static final String HOME_MENU = "com.example.demo.menus.HomeMenu";
	private final Stage stage;
	private LevelParent currentLevel;


	public Controller(Stage stage) {
		this.stage = stage;
		stage.setUserData(this);
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToMenu(HOME_MENU);
	}



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


	@Override
	public void update(Observable observable, Object arg) {
		if (arg instanceof String) {
			String notification = (String) arg;
			System.out.println("Controller notified to transition to: " + notification);
			try {
				if (notification.startsWith("com.example.demo.Level")) {
					goToLevel(notification); // Navigate to a level
				} else if (notification.startsWith("com.example.demo.menus")) {
					goToMenu(notification); // Navigate to a menu
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





	private void handleError(String message, Throwable error) {
		System.err.println(message);
		error.printStackTrace();
		showAlert("Level Transition Error", message + "\nDetails: " + error.getMessage());
	}

	private void showAlert(String title, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.show();
	}


}
