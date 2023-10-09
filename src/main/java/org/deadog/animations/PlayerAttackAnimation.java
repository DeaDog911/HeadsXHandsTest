package org.deadog.animations;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.deadog.models.Creature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class PlayerAttackAnimation extends AttackAnimation {
    public PlayerAttackAnimation(Creature creature) {
        super(creature, 0, 0, 50, 50, 6,
                new Rectangle2D(10, 20, 25, 40));
    }
}
