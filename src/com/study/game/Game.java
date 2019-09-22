package com.study.game;

import com.study.Input.Input;
import com.study.Utils.Time;
import com.study.display.Display;
import com.study.game.level.Bullet;
import com.study.game.level.Level;
import com.study.graphics.TextureAtlas;

import java.awt.*;

public class Game implements Runnable {

    public static final int     WEIGHT          = 800;
    public static final int     HEIGHT          = 595;
    public static final String  TITLE           = "Tanks";
    public static final int     CLEAR_COLOR     = 0xff000000;
    public static final int     NUM_BUFFERS     = 3;

    public static final float   UPDATE_RATE     = 60.0f;
    public static final float   UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long    IDLE_TIME       = 1;

    public static final String  ATLAS_FILE_NAME = "texture_atlas.png";

    public static boolean       running;
    private Thread              gameThread;
    private static Graphics2D   graphics;
    private static Input        input;
    private TextureAtlas        atlas;
    private Player              player;
    private Level               level;
    private Enemy               enemy;

    public Game() {
        running = false;
        Display.create(WEIGHT, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);
        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        player = new Player(300, 300, 2, 2.5f, atlas);
        level = new Level(atlas);
        enemy = new Enemy(100, 100, atlas);
        //sheet = new SpriteSheet(atlas.cut(8 * 16, 5 * 16, 16 * 2, 16), 1, 16);
        //sprite = new Sprite(sheet, 3);
    }

    public synchronized void start() {

        if (running)
            return;

        running = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public synchronized void stop() {

        if (!running)
            return;

        running = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cleanUp();
    }

    private void update() {
        player.update(input);
        level.update();
        enemy.update(input);
        /*
        if (input.getKey(KeyEvent.VK_UP))
            y -= speed;
        if (input.getKey(KeyEvent.VK_DOWN))
            y += speed;
        if (input.getKey(KeyEvent.VK_LEFT))
            x -= speed;
        if (input.getKey(KeyEvent.VK_RIGHT))
            x += speed;
         */
    }

    private void render() {
        Display.clear();
        level.render(graphics);
        player.render(graphics);
        level.renderGrass(graphics);
        enemy.render(graphics);
        //graphics.setColor(Color.WHITE);
        //graphics.fillOval((int) (x + Math.sin(delta) * 200), (int)y, (int)radius*2, (int)radius*2);
        //sprite.render(graphics, x, y);
        Display.swapBuffer();
    }

    public void run() {
        int fps = 0;
        int upd = 0;
        int updl = 0;

        long cnt = 0;

        float delta = 0;

        long lastTime = Time.get();
        while (running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            cnt += elapsedTime;

            boolean render = false;
            delta += (elapsedTime / UPDATE_INTERVAL);

            while (delta > 1) {
                update();
                upd++;
                delta--;

                if (render) {
                    updl++;
                } else {
                    render = true;
                }
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (cnt >= Time.SECOND) {
                Display.setTitle(TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                updl = 0;
                fps = 0;
                cnt = 0;
            }

        }
    }

    private void cleanUp() {
        Display.destroy();
    }

    public static Input getInput() {
        return input;
    }
}