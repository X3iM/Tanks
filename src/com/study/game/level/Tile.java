package com.study.game.level;

import com.study.Utils.Utils;
import com.study.graphics.Sprite;
import com.study.graphics.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage           image;
    private TileType                type;

    protected Tile(BufferedImage image, int scale, TileType type) {
        this.type = type;
        this.image = Utils.resize(image, image.getWidth()*scale, image.getHeight()*scale);
    }

    protected void render(Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    public TileType type() {
        return type;
    }
}