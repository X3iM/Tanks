package com.study.game.level;

import com.study.display.Display;
import com.study.game.Game;
import com.study.game.Player;
import com.study.graphics.Sprite;
import com.study.graphics.SpriteSheet;
import com.study.graphics.TextureAtlas;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Water implements Runnable {

    private enum WaterAnimation {
        WATER_FIRST(34 * Level.TILE_SCALE, 6 * Level.TILE_SCALE, Level.TILE_SCALE, Level.TILE_SCALE),
        WATER_SECOND(32 * Level.TILE_SCALE, 6 * Level.TILE_SCALE, Level.TILE_SCALE, Level.TILE_SCALE);

        private int x, y, h, w;

        WaterAnimation(int x, int y, int h, int w) {
            this.x = x;
            this.y = y;
            this.h = h;
            this.w = w;
        }

        public BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }

    private final long  timeOfWaterAnimation = 800L;
    private final float scale                = 2;
    private int x;
    private int y;
    private long time;
    private WaterAnimation waterAnimation;
    private Map<WaterAnimation, Sprite> spriteMap;

    public Water(int x, int y) {
        this.x = y * Level.SCALED_TILE_SIZE;
        this.y = x * Level.SCALED_TILE_SIZE;
        time = new Date().getTime();
        waterAnimation = WaterAnimation.WATER_FIRST;

        TextureAtlas atlas = new TextureAtlas("texture_atlas.png");
        spriteMap = new HashMap<WaterAnimation, Sprite>();
        for (WaterAnimation h: WaterAnimation.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), Player.SPRITES_PER_HEADING, Player.SPRITE_SCALE / 2);
            Sprite sprite = new Sprite(sheet, scale);
            spriteMap.put(h, sprite);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (Game.running) {
            render();
            if (new Date().getTime() - time > timeOfWaterAnimation)
                update();
        }
    }

    private void update() {
        if (waterAnimation == WaterAnimation.WATER_FIRST)
            waterAnimation = WaterAnimation.WATER_SECOND;
    }

    private void render() {
        spriteMap.get(waterAnimation).render(Display.getGraphics(), x, y);
    }
}
