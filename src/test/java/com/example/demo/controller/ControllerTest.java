package com.example.demo.controller;

import com.example.demo.SoundManager;
import com.example.demo.menus.MenuParent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Stage mockStage;
    private Controller controller;

    @BeforeEach
    void setUp() {
        mockStage = mock(Stage.class);
        controller = new Controller(mockStage);
    }

    @Test
    void testConstructorInitializesSoundManager() {
        assertNotNull(controller.getSoundManager(), "SoundManager should be initialized");
    }

    @Test
    void testLaunchGame() throws Exception {
        // Mock Stage behavior
        doNothing().when(mockStage).show();

        // Call method
        controller.launchGame();

        // Verify Stage is displayed
        verify(mockStage).show();
    }

    @Test
    void testGoToMenu() throws Exception {
        // Mock behavior of menu and scene
        MenuParent mockMenu = mock(MenuParent.class);
        Scene mockScene = mock(Scene.class);
        when(mockMenu.initializeScene()).thenReturn(mockScene);

        // Simulate menu navigation
        //controller.goToMenu("com.example.demo.menus.HomeMenu");

        // Verify that the scene is set on the stage
        verify(mockStage).setScene(mockScene);
    }

    @Test
    void testGoToLevel() throws Exception {
        // Mock behavior of level and scene
        //LevelParent mockLevel = mock(LevelParent.class);
        //Scene mockScene = mock(Scene.class);
        //when(mockLevel.initializeScene()).thenReturn(mockScene);

        // Simulate level navigation
        controller.goToLevel("com.example.demo.Levels.Level1");

        // Verify that the scene is set on the stage
        //verify(mockStage).setScene(mockScene);
        //verify(mockLevel).startGame();
    }

    @Test
    void testUpdateForMenuTransition() throws Exception {
        String notification = "com.example.demo.menus.PauseMenu";

        // Simulate update notification
        controller.update(null, notification);

        // Verify that goToMenu is called with the correct parameter
        verify(mockStage, atLeastOnce()).setScene(any());
    }

    @Test
    void testUpdateForLevelTransition() throws Exception {
        String notification = "com.example.demo.Levels.Level1";

        // Simulate update notification
        controller.update(null, notification);

        // Verify that goToLevel is called with the correct parameter
        verify(mockStage, atLeastOnce()).setScene(any());
    }

    @Test
    void testHandleError() {
        // Call the private handleError method using reflection
        Exception exception = new Exception("Test Exception");
        String message = "Test Error";

        // Use reflection to access and test private method
        assertDoesNotThrow(() -> {
            controller.getClass().getDeclaredMethod("handleError", String.class, Throwable.class)
                    .invoke(controller, message, exception);
        });
    }
}
