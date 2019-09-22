package com.study.graphics;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class SpriteSheet {

    private BufferedImage   sheet;
    private int             spriteCount;
    private int             scale;
    private int             spriteInWidht;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;
        this.spriteInWidht = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index) {
        index = index % spriteCount;
        int x = index % spriteInWidht * scale;
        int y = index / spriteInWidht * scale;
        sheet = sheet.getSubimage(x, y, scale, scale);

        return sheet;
    }
}
