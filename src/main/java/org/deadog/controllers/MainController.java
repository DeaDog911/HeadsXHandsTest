package org.deadog.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.deadog.animations.MonsterAttackAnimation;
import org.deadog.animations.PlayerAttackAnimation;
import org.deadog.exceptions.InvalidCreatureArgumentException;
import org.deadog.models.Monster;
import org.deadog.models.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MainController {
    @FXML
    private GridPane grid;
    private Player player;
    private List<Monster> monsters;
    private int playerColIndex = 0;
    private int playerRowIndex = 0;
    private final int NUM_COLS = 15;
    private final int NUM_ROWS = 12;
    private long time = new Date().getTime();

    private Image playerIdleImg;
    private Image monsterIdleImg;
    private Image playerAttackImg;
    private Image monsterAttackImg;
    @FXML
    private void mouseClicked(MouseEvent e) {
        if (new Date().getTime() - time >= 1500) {
            Node clickedNode = e.getPickResult().getIntersectedNode();
            if (clickedNode != grid) {
                int colIndex = GridPane.getColumnIndex(clickedNode);
                int rowIndex = GridPane.getRowIndex(clickedNode);
                int playerColumnIndex = GridPane.getColumnIndex(player.getImageView());
                int playerRowIndex = GridPane.getRowIndex(player.getImageView());
                for (Monster monster : monsters) {
                    if (monster.isAlive()) {
                        if (GridPane.getColumnIndex(monster.getImageView()) == colIndex && GridPane.getRowIndex(monster.getImageView()) == rowIndex) {
                            if (Math.abs(playerColumnIndex - colIndex) <= 1 && Math.abs(playerRowIndex - rowIndex) <= 1) {
                                attack(player, monster);
                                time = new Date().getTime();
                            }
                        }
                    }
                }
            }
        }
    }

    private void attack(Player player, Monster monster) {
        int monsterColumnIndex = GridPane.getColumnIndex(monster.getImageView());
        if (playerColIndex > monsterColumnIndex) {
            player.getImageView().setScaleX(-2);
            monster.getImageView().setScaleX(1.5);
        } else {
            player.getImageView().setScaleX(2);
            monster.getImageView().setScaleX(-1.5);
        }
        new PlayerAttackAnimation(player).play();
        player.hit(monster);
        if (monster.isAlive()) {
            new MonsterAttackAnimation(monster).play();
            monster.hit(player);
        }
    }

    public void initialize() throws FileNotFoundException {
        createGrid(NUM_COLS, NUM_ROWS);
        playerIdleImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/player.png")).getPath()));
        monsterIdleImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/monster_idle.png")).getPath()));
        playerAttackImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/player_attack.png")).getPath()));
        monsterAttackImg = new Image(new FileInputStream(Objects.requireNonNull(getClass().getResource("/img/monster_attack.png")).getPath()));
        // Количетво врагов на карте
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
        initializeMonstersImageView(monsters, NUM_COLS, NUM_ROWS);
    }

    private void initializePlayerImageView(Player player) {
        ImageView playerImageView = player.getImageView();
        playerImageView.setImage(playerIdleImg);
        playerImageView.setViewport(new Rectangle2D(10, 20, 25, 40));
        playerImageView.setScaleX(2);
        playerImageView.setScaleY(2);
        playerColIndex = 0;
        playerRowIndex = 0;
        grid.add(playerImageView, playerColIndex, playerRowIndex);
    }

    private void initializeMonstersImageView(List<Monster> monsters, int numCols, int numRows) {
        for (int i = 0; i < monsters.size(); i++) {
            ImageView monsterImageView = monsters.get(i).getImageView();
            monsterImageView.setImage(monsterIdleImg);
            monsterImageView.setViewport(new Rectangle2D(55, 45, 50, 60));
            monsterImageView.setScaleX(-1.5);
            monsterImageView.setScaleY(1.5);
            int colIndex = new Random().nextInt(1, numCols);
            int rowIndex = new Random().nextInt(1, numRows);
            grid.add(monsterImageView, colIndex, rowIndex);
        }
    }

    public void keyPressed(KeyEvent event) {
        try {
            int colIndex = playerColIndex, rowIndex = playerRowIndex;
            switch (event.getCode()) {
                case LEFT -> colIndex = Math.max((playerColIndex-1), 0);
                case UP -> rowIndex = Math.max((playerRowIndex-1), 0);
                case RIGHT -> colIndex = Math.min((playerColIndex+1), NUM_COLS-1);
                case DOWN -> rowIndex = Math.min((playerRowIndex+1), NUM_ROWS-1);
            }
            boolean go = true;
            for (Monster monster : monsters) {
                if (monster.isAlive()) {
                    if (GridPane.getColumnIndex(monster.getImageView()) == colIndex && GridPane.getRowIndex(monster.getImageView()) == rowIndex) {
                        go = false;
                    }
                }
            }
            if (go) {
                playerColIndex = colIndex;
                playerRowIndex = rowIndex;
                grid.add(player.getImageView(), playerColIndex, playerRowIndex);
            }
        } catch (IllegalArgumentException ignored) {}
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
