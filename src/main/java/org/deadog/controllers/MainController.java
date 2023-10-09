package org.deadog.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.deadog.animations.MonsterAttackAnimation;
import org.deadog.animations.PlayerAttackAnimation;
import org.deadog.exceptions.InvalidCreatureArgumentException;
import org.deadog.models.Monster;
import org.deadog.models.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainController {
    @FXML
    private GridPane grid;

    private Player player;
    private List<Monster> monsters;
    @FXML
    private void mouseClicked(MouseEvent e) {
        Node clickedNode = e.getPickResult().getIntersectedNode();
        if (clickedNode != grid) {
            int colIndex = GridPane.getColumnIndex(clickedNode);
            int rowIndex = GridPane.getRowIndex(clickedNode);
            int playerColumnIndex = GridPane.getColumnIndex(player.getImageView());
            int playerRowIndex = GridPane.getRowIndex(player.getImageView());
            boolean isAttacking = false;
            for (Monster monster : monsters) {
                if (monster.isAlive()) {
                    if (GridPane.getColumnIndex(monster.getImageView()) == colIndex && GridPane.getRowIndex(monster.getImageView()) == rowIndex) {
                        isAttacking = true;
                        if (Math.abs(playerColumnIndex - colIndex) <= 1 && Math.abs(playerRowIndex - rowIndex) <= 1) {
                            attack(player, monster);
                        }
                    }
                }
            }
            if (!isAttacking) {
                try {
                    grid.add(player.getImageView(), colIndex, rowIndex);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }

    private void attack(Player player, Monster monster) {
        new PlayerAttackAnimation(player).play();
        player.hit(monster);
        if (monster.isAlive()) {
            new MonsterAttackAnimation(monster).play();
            monster.hit(player);
        }
    }

    public void initialize() throws FileNotFoundException {
        int numCols = 15;
        int numRows = 12;
        createGrid(numCols, numRows);

        Image playerIdleImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/player.png")).getPath()));
        Image monsterIdleImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/monster_idle.png")).getPath()));
        Image playerAttackImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/player_attack.png")).getPath()));
        Image monsterAttackImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/monster_attack.png")).getPath()));

        int monstersCount = 1;
        try {
            player = new Player(15, 20);
            player.setIdleImage(playerIdleImg);
            player.setAttackImage(playerAttackImg);
            monsters = new LinkedList<>();

            for (int i = 0; i < monstersCount; i++) {
                int attack = new Random().nextInt(10, 20);
                int defence = new Random().nextInt(10, 20);
                Monster monster = new Monster(attack, defence);
                monster.setIdleImage(monsterIdleImg);
                monster.setAttackImage(monsterAttackImg);
                monsters.add(monster);
            }
        } catch (InvalidCreatureArgumentException e) {
            System.err.println(e.getMessage());
            throw e;
        }

        initializePlayerImageView(player);
        initializeMonstersImageView(monsters, numCols, numRows);
    }

    private void initializePlayerImageView(Player player) {
        ImageView playerImageView = player.getImageView();
        playerImageView.setImage(player.getIdleImage());
        playerImageView.setViewport(new Rectangle2D(10, 20, 25, 40));
        playerImageView.setScaleX(2);
        playerImageView.setScaleY(2);
        grid.add(playerImageView, 0, 0);
    }

    private void initializeMonstersImageView(List<Monster> monsters, int numCols, int numRows) {
        Image monsterIdleImage = monsters.get(0).getIdleImage();
        for (int i = 0; i < monsters.size(); i++) {
            ImageView monsterImageView = monsters.get(i).getImageView();
            monsterImageView.setImage(monsterIdleImage);
            monsterImageView.setViewport(new Rectangle2D(55, 45, 50, 60));
            monsterImageView.setScaleX(-1.5);
            monsterImageView.setScaleY(1.5);
            int colIndex = new Random().nextInt(1, numCols);
            int rowIndex = new Random().nextInt(1, numRows);
            grid.add(monsterImageView, colIndex, rowIndex);
        }
    }

    private void createGrid(int numCols, int numRows) {
        for (int i = 0 ; i < numCols ; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(Priority.SOMETIMES);
            grid.getColumnConstraints().add(colConstraints);
        }

        for (int i = 0 ; i < numRows ; i++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(rowConstraints);
        }

        for (int i = 0 ; i < numCols ; i++) {
            for (int j = 0; j < numRows; j++) {
                addPane(i, j);
            }
        }
    }

    private void addPane(int colIndex, int rowIndex) {
        Pane pane = new Pane();
        grid.add(pane, colIndex, rowIndex);
    }
}
