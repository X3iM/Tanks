package com.study.game;

import com.study.Input.Input;
import com.study.game.level.Bullet;
import com.study.graphics.Sprite;
import com.study.graphics.SpriteSheet;
import com.study.graphics.TextureAtlas;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Enemy extends Entity implements Runnable {

    public enum Heading{
        NORTH(8 * Player.SPRITE_SCALE, 0 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE),
        EAST(14 * Player.SPRITE_SCALE, 0 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE),
        SOUTH(12 * Player.SPRITE_SCALE, 0 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE),
        WEST(10 * Player.SPRITE_SCALE, 0 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE, 1 * Player.SPRITE_SCALE);

        private int x, y, h, w;

        Heading(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }

        public BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }

    @Override
    public void run() {

    }

    private Heading                 heading;
    public static float             speed;
    private float                   scale;
    private Map<Heading, Sprite>    spriteMap;

    public Enemy(float x, float y, TextureAtlas atlas) {
        super(EntityType.Bullet, x, y);

        heading = Heading.SOUTH;
        speed = Player.speed;
        scale = Player.scale;
        spriteMap = new HashMap<Heading, Sprite>();
        for (Heading h: Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), Player.SPRITES_PER_HEADING, Player.SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }

    @Override
    public void update(Input input) {
        //if (enemyX == Player.playerX) {
            //Bullet bullet = new Bullet(EntityType.Bullet, heading, enemyX, enemyY);
        //}
        //else if (enemyY == Player.playerY) {
            //Bullet bullet = new Bullet(EntityType.Bullet, heading, enemyX, enemyY);
        //}
        float newX = getEnemyX();
        float newY = getEnemyY();

        /*if ((int)playerX > (int)enemyX) {
            enemyX += speed;
            heading = Heading.EAST;
        } else if((int)playerX < (int)enemyX) {
            enemyX -= speed;
            heading = Heading.WEST;
        } else if ((int)playerY > (int)enemyY) {
            enemyY += speed;
            heading = Heading.SOUTH;
        } else {
            enemyY -= speed;
            heading = Heading.NORTH;
        }*/
        /*switch (heading) {
            case NORTH:
                if (canMove(enemyX, enemyY, enemyX + (Player.SPRITE_SCALE * scale / 2), enemyY, enemyX + (Player.SPRITE_SCALE * scale), enemyY)) {
                    enemyX = newX;
                    enemyY = newY;
                }
                break;
            case SOUTH:
                if (canMove(enemyX, enemyY + (Player.SPRITE_SCALE * scale), enemyX + (Player.SPRITE_SCALE * scale / 2),
                        enemyY + (Player.SPRITE_SCALE * scale), enemyX + (Player.SPRITE_SCALE * scale), enemyY + (Player.SPRITE_SCALE * scale))) {
                    playerX = newX;
                    playerY = newY;
                }
                break;
            case EAST:
                if (canMove(enemyX + (Player.SPRITE_SCALE * scale), enemyY, enemyX + (Player.SPRITE_SCALE * scale),
                        enemyY + (Player.SPRITE_SCALE * scale / 2), enemyX + (Player.SPRITE_SCALE * scale), enemyY + (Player.SPRITE_SCALE * scale))) {
                    playerX = newX;
                    playerY = newY;
                }
                break;
            case WEST:
                if (canMove(enemyX, newY, enemyX, enemyY + (Player.SPRITE_SCALE * scale / 2), enemyX, enemyX + (Player.SPRITE_SCALE * scale))) {
                    playerX = newX;
                    playerY = newY;
                }
                break;
        }
         */
    }

    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, getEnemyX(), getEnemyY());
    }

}
