package com.example.innosynergy.views;


import javafx.animation.*;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.time.LocalDateTime;

public class HandTransition {

    public static void create(Line hourHand, Line minuteHand, Line secondHand) {
        // determine the starting time.
        LocalDateTime localDateTime = LocalDateTime.now();

        final double seedSecondDegrees = localDateTime.getSecond() * (360.0 / 60);
        final double seedMinuteDegrees =
                (localDateTime.getMinute() + seedSecondDegrees / 360.0) * (360.0 / 60);
        final double seedHourDegrees =
                (localDateTime.getHour() + seedMinuteDegrees / 360.0) * (360.0 / 12);

        // define rotations to map the analogueClock to the current time.
        final Rotate hourRotate = new Rotate(seedHourDegrees);
        final Rotate minuteRotate = new Rotate(seedMinuteDegrees);
        final Rotate secondRotate = new Rotate(seedSecondDegrees);
        hourHand.getTransforms().add(hourRotate);
        minuteHand.getTransforms().add(minuteRotate);
        secondHand.getTransforms().add(secondRotate);

        // the hour hand rotates twice a day.
        final Timeline hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hourRotate.angleProperty(),
                                360 + seedHourDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // the minute hand rotates once an hour.
        final Timeline minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                minuteRotate.angleProperty(),
                                360 + seedMinuteDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // move second hand rotates once a minute.
        final Timeline secondTime = new Timeline(
                new KeyFrame(
                        Duration.seconds(60),
                        new KeyValue(
                                secondRotate.angleProperty(),
                                360 + seedSecondDegrees,
                                Interpolator.LINEAR
                        )
                )
        );

        // animation of time never ends.
        hourTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.setCycleCount(Animation.INDEFINITE);
        secondTime.setCycleCount(Animation.INDEFINITE);

        secondTime.play();
        minuteTime.play();
        hourTime.play();
    }
}
