package org.deadog.animations;

import javafx.geometry.Rectangle2D;
import org.deadog.models.Creature;


public class PlayerAttackAnimation extends AttackAnimation {
    public PlayerAttackAnimation(Creature creature) {
        super(creature, 0, 0, 50, 50, 6,
                new Rectangle2D(10, 20, 25, 40));
    }
}
