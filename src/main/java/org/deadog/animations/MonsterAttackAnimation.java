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

public class MonsterAttackAnimation extends AttackAnimation {
    public MonsterAttackAnimation(Creature creature) {
        super(creature, 0, 0, 150, 150, 8,
                new Rectangle2D(55, 45, 50, 60));
    }
}
