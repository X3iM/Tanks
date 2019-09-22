package com.study.game.level;
import com.study.Input.Input;
import com.study.display.Display;
import com.study.game.*;
import com.study.graphics.Sprite;
import com.study.graphics.SpriteSheet;
import com.study.graphics.TextureAtlas;

import java.awt.image.BufferedImage;
import java.lang.Runnable;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Bullet implements Runnable {

    public enum Heading{
        NORTH(20 * Player.SPRITE_SCALE, 6 * Player.SPRITE_SCALE + 4, Player.SPRITE_SCALE / 2, Player.SPRITE_SCALE / 2),
        EAST(21 * Player.SPRITE_SCALE + Player.SPRITE_SCALE / 2, 6 * Player.SPRITE_SCALE + 4, Player.SPRITE_SCALE / 2, Player.SPRITE_SCALE / 2),
        SOUTH(21 * Player.SPRITE_SCALE, 6 * Player.SPRITE_SCALE + 4, Player.SPRITE_SCALE / 2, Player.SPRITE_SCALE / 2),
        WEST(20 * Player.SPRITE_SCALE + Player.SPRITE_SCALE / 2, 6 * Player.SPRITE_SCALE + 4, Player.SPRITE_SCALE / 2, Player.SPRITE_SCALE / 2);

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

    private final float              scale = 2;
    private static final float       speed = Player.speed / 5000;
    private Heading                  heading;
    private Map<Heading, Sprite>     spriteMap;
    private float                    x;
    private float                    y;
    private static Integer[][]       tileMap;

    public Bullet(EntityType type, Player.Heading heading1, float x, float y) {
        this.x = x;
        this.y = y;
        tileMap = Level.getTileMap();
        if (type == EntityType.Player) {
            if (heading1 == Player.Heading.NORTH) {
                this.x += Player.SPRITE_SCALE / scale - scale;
                heading = Heading.NORTH;
            } else if (heading1 == Player.Heading.EAST) {
                this.y += Player.SPRITE_SCALE / scale;
                this.x += Player.SPRITE_SCALE;
                heading = Heading.EAST;
            } else if (heading1 == Player.Heading.WEST) {
                this.y += Player.SPRITE_SCALE / scale;
                heading = Heading.WEST;
            } else {
                this.y += Player.SPRITE_SCALE;
                this.x += Player.SPRITE_SCALE / scale - scale;
                heading = Heading.SOUTH;
            }
        }
        /*else {
            if (heading1 == Entity.Heading.NORTH) {
                this.x += Player.SPRITE_SCALE / scale - scale;
                heading = Heading.NORTH;
            } else if (heading1 == Entity.Heading.EAST) {
                this.y += Player.SPRITE_SCALE / scale;
                this.x += Player.SPRITE_SCALE;
                heading = Heading.EAST;
            } else if (heading1 == Entity.Heading.WEST) {
                this.y += Player.SPRITE_SCALE / scale;
                heading = Heading.WEST;
            } else {
                this.y += Player.SPRITE_SCALE;
                this.x += Player.SPRITE_SCALE / scale - scale;
                heading = Heading.SOUTH;
            }
        }
         */
        TextureAtlas atlas = new TextureAtlas("texture_atlas.png");
        spriteMap = new HashMap<Heading, Sprite>();
        for (Heading h: Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), Player.SPRITES_PER_HEADING, Player.SPRITE_SCALE / 2);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }

    @Override
    public void run() {
        while (true) {
            render(Display.getGraphics());
            update(Game.getInput());
            if (!canMove())
                break;
        }
    }

    public synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void update(Input input) {
        switch (heading) {
            case EAST: x += speed; break;
            case WEST: x -= speed; break;
            case NORTH: y -= speed; break;
            case SOUTH: y += speed; break;
        }
    }

    private void render(Graphics2D g) {
        spriteMap.get(heading).render(g, x, y);
    }

    private boolean canMove() {
        float floatCoordX = x/Player.SPRITE_SCALE;
        float floatCoordY = y/Player.SPRITE_SCALE;
        int newX = (int)floatCoordX;
        int newY = (int)floatCoordY;

        if (x < 0 || x > Game.WEIGHT - Level.SCALED_TILE_SIZE)
            return false;

        if (y < 0 || y > Game.HEIGHT - Level.SCALED_TILE_SIZE)
            return false;

        int cnt = 0;
        if (tileMap[newY][newX] == 1) {
            tileMap[newY][newX] = 0;
            cnt++;
        }

        floatCoordX -= newX;
        floatCoordY -= newY;
        if (floatCoordX >= 0.5f) {
            switch (heading) {
                case EAST:
                    if (tileMap[newY+1][newX+1] == 1) {
                        tileMap[newY + 1][newX + 1] = 0;
                        return false;
                    }
                case WEST:
                    if (tileMap[newY+1][newX] == 1) {
                        tileMap[newY + 1][newX] = 0;
                        return false;
                    }
            }
        } else if (floatCoordY >= 0.5f) {
            switch (heading) {
                case SOUTH:
                    if (tileMap[newY+1][newX+1] == 1) {
                        tileMap[newY+1][newX+1] = 0;
                        tileMap[newY+1][newX] = 0;
                        return false;
                    }
                case NORTH:
                    if (tileMap[newY][newX+1] == 1) {
                        tileMap[newY][newX + 1] = 0;
                        return false;
                    }
            }
        }

        if (tileMap[newY][newX] == 2)
            return false;

        if (cnt > 0)
            return false;

        return true;
    }
}
