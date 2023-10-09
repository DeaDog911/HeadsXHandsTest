package org.deadog.models;


public class Monster extends Creature {
    public Monster(int attack, int defense) {
        super(attack, defense);
        System.out.printf("Создан Монстр. Атака: %d, Защита %d \n", attack, defense);
    }
}
