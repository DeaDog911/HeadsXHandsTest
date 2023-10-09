package org.deadog.models;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Player extends Creature {
    private int healCount = 0;

    public Player(int attack, int defense) {
        super(attack, defense);
        System.out.printf("Создан Игрок. Атака: %d, Защита %d \n", attack, defense);
    }

    public void heal() {
        if (healCount++ < 4) {
            this.setHealth(this.getHealth() + ((int) (0.3 * Creature.maxHealth)) % Creature.maxHealth);
            System.out.println("Игрок исцелился. Здоровье: " + this.getHealth());
        }
        System.out.println("Больше нельзя исцелиться (");
    }

    // Игрок может исцелиться случайным образом, когда его здоровь опускается ниже 30 процентов от максимума
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (this.getHealth() <= Creature.maxHealth * 0.3) {
            int randomCube = new Random().nextInt(1, 7);
            if (randomCube >= 5) {
                heal();
            }else {
                System.out.println("Игроку не удалось исцелиться");
            }
        }
    }
}
