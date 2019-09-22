package com.study.game;

import com.study.Input.Input;
import com.study.game.level.Bullet;
import com.study.game.level.Level;
import com.study.graphics.Sprite;
import com.study.graphics.SpriteSheet;
import com.study.graphics.TextureAtlas;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {

    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADING = 1;

    public enum Heading{
        NORTH(0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 1 * SPRITE_SCALE, 1 * SPRITE_SCALE);

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

    private Heading                 heading;
    private Map<Heading, Sprite>    spriteMap;
    protected static float          scale;
    public static float             speed;
    private boolean                 isBulletAlive;
    private long                    time;

    public Player(float x, float y, float scale, float speed, TextureAtlas atlas) {
        super(EntityType.Player, x, y);

        heading = Heading.NORTH;
        spriteMap = new HashMap<Heading, Sprite>();
        Player.scale = scale;
        Player.speed = speed;

        for (Heading h: Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }

    @Override
    public void update(Input input) {
        float newX = getPlayerX();
        float newY = getPlayerY();

        if (input.getKey(KeyEvent.VK_UP)) {
            newY -= speed;
            setPlayerX(Math.round(newX / Level.SCALED_TILE_SIZE) * Level.SCALED_TILE_SIZE);
            newX = getPlayerX();
            heading = Heading.NORTH;
        } else if (input.getKey(KeyEvent.VK_RIGHT)) {
            newX += speed;
            setPlayerY(Math.round(newY / Level.SCALED_TILE_SIZE) * Level.SCALED_TILE_SIZE);
            newY = getPlayerY();
            heading = Heading.EAST;
        } else if (input.getKey(KeyEvent.VK_DOWN)) {
            newY += speed;
            setPlayerX((Math.round(newX / Level.SCALED_TILE_SIZE)) * Level.SCALED_TILE_SIZE);
            newX = getPlayerX();
            heading = Heading.SOUTH;
        } else if (input.getKey(KeyEvent.VK_LEFT)) {
            newX -= speed;
            setPlayerY(Math.round(newY / Level.SCALED_TILE_SIZE) * Level.SCALED_TILE_SIZE);
            newY = getPlayerY();
            heading = Heading.WEST;
        }
        if (input.getKey(KeyEvent.VK_SPACE) && !isBulletAlive) {
            time  = new Date().getTime();
            isBulletAlive = true;
            Bullet bullet = new Bullet(EntityType.Player, heading, getPlayerX(), getPlayerY());
            bullet.start();
        }
        if (new Date().getTime() - time > 850)
            isBulletAlive = false;

        if (newX < 0) {
            newX = 0;
        } else if (newX >= Game.WEIGHT - SPRITE_SCALE * scale) {
            newX = Game.WEIGHT - SPRITE_SCALE * scale;
        }

        if (newY < 0) {
            newY = 0;
        } else if (newY >= Game.HEIGHT - SPRITE_SCALE * scale) {
            newY = Game.HEIGHT - SPRITE_SCALE * scale;
        }

        switch (heading) {
            case NORTH:
                if (canMove(newX, newY, newX + (SPRITE_SCALE * scale / 2), newY, newX + (SPRITE_SCALE * scale), newY)) {
                    setPlayerX(newX);
                    setPlayerY(newY);
                }
                break;
            case SOUTH:
                if (canMove(newX, newY + (SPRITE_SCALE * scale), newX + (SPRITE_SCALE * scale / 2),
                        newY + (SPRITE_SCALE * scale), newX + (SPRITE_SCALE * scale), newY + (SPRITE_SCALE * scale))) {
                    setPlayerX(newX);
                    setPlayerY(newY);
                }
                break;
            case EAST:
                if (canMove(newX + (SPRITE_SCALE * scale), newY, newX + (SPRITE_SCALE * scale),
                        newY + (SPRITE_SCALE * scale / 2), newX + (SPRITE_SCALE * scale), newY + (SPRITE_SCALE * scale))) {
                    setPlayerX(newX);
                    setPlayerY(newY);
                }
                break;
            case WEST:
                if (canMove(newX, newY, newX, newY + (SPRITE_SCALE * scale / 2), newX, newY + (SPRITE_SCALE * scale))) {
                    setPlayerX(newX);
                    setPlayerY(newY);
                }
                break;
        }
    }

    @Override
    public void render(Graphics2D g) {
        spriteMap.get(heading).render(g, getPlayerX(), getPlayerY());
    }
}