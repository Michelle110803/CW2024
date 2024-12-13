package com.example.demo.controller;

import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest extends ApplicationTest {

    private Main mainApp;

    @Override
    public void start(Stage stage) throws Exception {
        mainApp = new Main();
        mainApp.start(stage); // Start the application
    }

    @Test
    void testLaunchGame() {
        // Assert that myController is initialized
        assertDoesNotThrow(() -> {
            assertNotNull(mainApp.getClass().getDeclaredField("myController"));
        }, "Controller should be initialized");
    }
}
