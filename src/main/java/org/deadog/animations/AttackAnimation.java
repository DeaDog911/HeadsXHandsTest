package org.deadog.animations;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;
import org.deadog.models.Creature;

public class AttackAnimation extends Transition {
    private final int OFFSET_X;
    private final int OFFSET_Y;
    private final int WIDTH;
    private final int HEIGHT;
    private final int COLUMNS;
    private final Creature creature;

    public AttackAnimation(Creature creature, int OFFSET_X, int OFFSET_Y, int WIDTH, int HEIGHT, int COLUMNS, Rectangle2D idleRectangle) {
        this.OFFSET_X = OFFSET_X;
        this.OFFSET_Y = OFFSET_Y;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.COLUMNS = COLUMNS;
        this.creature = creature;

        creature.getImageView().setImage(creature.getAttackImage());
        setCycleDuration(Duration.millis(1000));
        setOnFinished(actionEvent -> {
            creature.getImageView().setImage(creature.getIdleImage());
            creature.getImageView().setViewport(idleRectangle);
        });
    }

    @Override
    protected void interpolate(double v) {
        final int index = Math.min((int) Math.floor(v * COLUMNS), COLUMNS - 1);
        int x = (index % COLUMNS) * WIDTH + OFFSET_X;
        int y = (index / COLUMNS) * HEIGHT + OFFSET_Y;
        this.creature.getImageView().setViewport(new Rectangle2D(x, y, WIDTH, HEIGHT));
    }
}
