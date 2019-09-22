package com.study.game;

import com.study.Input.Input;
import com.study.game.level.Level;
import com.study.game.level.Tile;
import com.study.game.level.TileType;

import java.awt.*;

public abstract class Entity {
    private static float playerX;
    private static float playerY;
    private float enemyX;
    private float enemyY;

    protected Entity(EntityType type, float x, float y) {
        if (type == EntityType.Bullet) {
            this.enemyX = x;
            this.enemyY = y;
        } else {
            Entity.playerX = x;
            Entity.playerY = y;
        }
    }

    public abstract void update(Input input);

    public abstract void render(Graphics2D g);

    protected boolean canMove(float newX, float newY, float centerX, float centerY, float bottomX, float bottomY) {
        int tileX = (int) (newX / Level.SCALED_TILE_SIZE);
        int tileY = (int) (newY / Level.SCALED_TILE_SIZE);
        int tileCenterX = (int) (centerX / Level.SCALED_TILE_SIZE);
        int tileCenterY = (int) (centerY / Level.SCALED_TILE_SIZE);
        int tileBottomX = bottomX % Level.SCALED_TILE_SIZE == 0 ? tileCenterX
                : (int) (bottomX / Level.SCALED_TILE_SIZE);
        int tileBottomY = bottomY % Level.SCALED_TILE_SIZE == 0 ? tileCenterY
                : (int) (bottomY / Level.SCALED_TILE_SIZE);

        Integer[][] tileMap = Level.getTileMap();

        if (Integer.max(tileY, tileBottomY) >= tileMap.length || Integer.max(tileX, tileBottomX) >= tileMap[0].length
                || isImpassableTile(tileMap[tileY][tileX], tileMap[tileCenterY][tileCenterX],
                tileMap[tileBottomY][tileBottomX])) {
            return false;
        }
        return true;
    }

    protected boolean isImpassableTile(Integer... tileNum) {
        for (int i = 0; i < tileNum.length; i++)
            if (tileNum[i] == TileType.BRICK.numeric() || tileNum[i] == TileType.METAL.numeric()
                    || tileNum[i] == TileType.DOWN_LEFT_EAGLE.numeric()
                    || tileNum[i] == TileType.DOWN_RIGHT_EAGLE.numeric()
                    || tileNum[i] == TileType.UP_LEFT_EAGLE.numeric() || tileNum[i] == TileType.UP_RIGHT_EAGLE.numeric()
                    || tileNum[i] == TileType.DOWN_LEFT_DEAD_EAGLE.numeric()
                    || tileNum[i] == TileType.DOWN_RIGHT_DEAD_EAGLE.numeric()
                    || tileNum[i] == TileType.UP_LEFT_DEAD_EAGLE.numeric()
                    || tileNum[i] == TileType.UP_RIGHT_DEAD_EAGLE.numeric() || tileNum[i] == TileType.WATER_FIRST.numeric()
                    || tileNum[i] == TileType.WATER_SECOND.numeric()) {
                return true;
            }
        return false;
    }

    public static float getPlayerX() {
        return playerX;
    }

    public static void setPlayerX(float playerX) {
        Entity.playerX = playerX;
    }

    public static float getPlayerY() {
        return playerY;
    }

    public static void setPlayerY(float playerY) {
        Entity.playerY = playerY;
    }

    public float getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(float enemyX) {
        this.enemyX = enemyX;
    }

    public float getEnemyY() {
        return enemyY;
    }

    public void setEnemyY(float enemyY) {
        this.enemyY = enemyY;
    }
}