package com.example.innosynergy;

import com.example.innosynergy.views.HandTransition;
import com.example.innosynergy.views.MouseActions;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.io.InputStream;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) {
        // Construction des éléments de l'horloge analogique
        final Circle face = new Circle(100, 100, 100);
        final Line hourHand = new Line(0, 0, 0, -50);
        hourHand.setTranslateX(100);
        hourHand.setTranslateY(100);
        hourHand.setId("hourHand");

        final Line minuteHand = new Line(0, 0, 0, -75);
        minuteHand.setTranslateX(100);
        minuteHand.setTranslateY(100);
        minuteHand.setId("minuteHand");

        final Line secondHand = new Line(0, 15, 0, -88);
        secondHand.setTranslateX(100);
        secondHand.setTranslateY(100);
        secondHand.setId("secondHand");

        face.setFill(Color.TRANSPARENT);
        final Group analogueClock = new Group(face, hourHand, minuteHand, secondHand);

        HandTransition.create(hourHand, minuteHand, secondHand);

        stage.initStyle(StageStyle.TRANSPARENT);

        // Mise en page de la scène
        final VBox layout = new VBox();
        layout.getChildren().addAll(analogueClock);
        layout.setAlignment(Pos.CENTER);
        layout.setMinSize(300, 300);

        final Scene scene = new Scene(layout, Color.TRANSPARENT);

        // Chargement du fichier CSS avec vérification
        URL cssUrl = Main.class.getResource("/images/clock/clock.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("⚠️ ERREUR : Fichier clock.css introuvable !");
        }

        stage.setScene(scene);

        // Ajout des actions souris (drag & drop et double-clic)
        MouseActions mouseActions = new MouseActions();
        mouseActions.init(stage, scene, layout);

        // Chargement de l'icône avec vérification
        InputStream iconStream = Main.class.getResourceAsStream("/images/clock/icon.png");
        if (iconStream != null) {
            stage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("⚠️ ERREUR : Icône icon.png introuvable !");
        }

        // Affichage de la scène
        stage.show();
    }
}
