package com.example.innosynergy.views;


import com.example.innosynergy.Main;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import java.util.prefs.*;

public class MouseActions {

    // Retrieve the user preference node for the package com.example.clock
    private static final Preferences prefs =
            Preferences.userNodeForPackage(com.example.innosynergy.views.MouseActions.class);

    // Preference key name
    private static final String PREF_NAME = "clock_face";

    private AtomicReference<Integer> number = new AtomicReference<>(0);

    public void init(Stage stage, Scene scene, VBox layout) {

        Image clockImg1 =
                new Image(Main.class.getResource("/images/clock/1.png").toString(), 200, 200, true, true);
        BackgroundImage backgroundImage1 = new BackgroundImage(clockImg1,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Image clockImg2 =
                new Image(Main.class.getResource("/images/clock/2.png").toString(), 200, 200, true, true);
        BackgroundImage backgroundImage2 = new BackgroundImage(clockImg2,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        Image clockImg3 =
                new Image(Main.class.getResource("/images/clock/3.png").toString(), 200, 200, true, true);
        BackgroundImage backgroundImage3 = new BackgroundImage(clockImg3,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        extracted(layout, backgroundImage1, backgroundImage2, backgroundImage3);

        // allow the clock background to be used to drag the clock around.
        final Delta dragDelta = new Delta();
        layout.setOnMousePressed(mouseEvent -> {
            // record a delta distance for the drag and drop operation.
            dragDelta.x = stage.getX() - mouseEvent.getScreenX();
            dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            scene.setCursor(Cursor.MOVE);

            // double click will change the clock look
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {

                    number.getAndSet(number.get() + 1);

                    saveUserSettings(number.get());
                    extracted(layout, backgroundImage1, backgroundImage2, backgroundImage3);
                }
            }
        });
        layout.setOnMouseReleased(mouseEvent -> scene.setCursor(Cursor.HAND));
        layout.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + dragDelta.x);
            stage.setY(mouseEvent.getScreenY() + dragDelta.y);
        });
    }

    private void extracted(VBox layout,
                           BackgroundImage backgroundImage1,
                           BackgroundImage backgroundImage2,
                           BackgroundImage backgroundImage3) {
        Integer clockFace = getUserSettings();
        switch (clockFace) {
            case 1 -> layout.setBackground(new Background(backgroundImage1));
            case 2 -> layout.setBackground(new Background(backgroundImage2));
            case 3 -> layout.setBackground(new Background(backgroundImage3));
            default -> number.getAndSet(0);
        }
    }


    private void saveUserSettings(Integer number) {
        // Set the value of the preference
        prefs.put(PREF_NAME, number.toString());
    }

    private Integer getUserSettings() {
        // Get the value of the preference;
        // default value is returned if the preference does not exist
        String defaultValue = "1";
        String propertyValue = prefs.get(PREF_NAME, defaultValue);
        return Integer.valueOf(propertyValue);
    }

    // records relative x and y co-ordinates.
    static class Delta {
        double x, y;
    }
}
