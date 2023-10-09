package org.deadog.models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.deadog.config.Config;
import org.deadog.exceptions.InvalidCreatureArgumentException;

import java.util.Random;

public abstract class Creature {
    private final int attack; // от 1 до 30
    private final int defense; // от 1 до 30
    public static final int maxHealth = Config.getInstance().N; // от 1 до N
    private int health = maxHealth;
    private boolean isAlive = true;

    private final ImageView imageView = new ImageView();
    private Image idleImage, attackImage;

    public Image getIdleImage() {
        return idleImage;
    }

    public void setIdleImage(Image idleImage) {
        this.idleImage = idleImage;
    }

    public Image getAttackImage() {
        return attackImage;
    }

    public void setAttackImage(Image attackImage) {
        this.attackImage = attackImage;
    }
    private final int[] damageRange = {
            Config.getInstance().M,
            Config.getInstance().N
    };

    public Creature(int attack, int defense) {
        if (attack >= 1 && attack <= 30 && defense >= 1 && defense <= 30) {
            this.attack = attack;
            this.defense = defense;
        } else {
            this.attack = -1;
            this.defense = -1;
            throw new InvalidCreatureArgumentException();
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public int[] getDamageRange() {
        return damageRange;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            isAlive = false;
            dead();
            System.out.println("Существо погибло");
        }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean hit(Creature defender) {
        System.out.printf("Начало атаки. \nАтака Существа 1: %d, Защита Существа 2: %d \n", this.attack, defender.getDefense());
        int modifier = this.attack - defender.getDefense() + 1;
        System.out.println("Модификатор атаки: " + modifier);
        boolean success = false;
        for (int i = 0; i < modifier; i++) {
            int randomNum = new Random().nextInt(1, 7);
            if (randomNum >= 5) {
                success = true;
                break;
            }
        }
        if (success) {
            System.out.println("Атака прошла успешна");
            int damage = new Random().nextInt(damageRange[0], damageRange[1] + 1);
            defender.takeDamage(damage);
            System.out.println("Существо 2 получило урон " + damage);
        }else {
            System.out.println("Атака провалилась");
        }
        return success;
    }

    private void dead() {
        this.imageView.setImage(null);
    }
}
