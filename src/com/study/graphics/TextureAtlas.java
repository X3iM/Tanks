package com.study.graphics;

import com.study.Utils.ResourceLoader;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class TextureAtlas {

    private BufferedImage image;

    public TextureAtlas(String imageName) {
        image = ResourceLoader.loadImage(imageName);
        /*
        WritableRaster writableRaster = image.getRaster();
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int[] pixel = writableRaster.getPixel(i, j, new int[4]);
                if (pixel[0] <= 1 && pixel[1] <= 1 && pixel[2] <= 1) {
                    pixel[3] = 0;
                    //writableRaster.setPixel(i, j, pixel);
                }
            }
        }*/

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int pixel = image.getRGB(j, i);
                if ((pixel & 0x00FFFFFF) < 10) {
                    image.setRGB(j, i, (pixel & 0x00FFFFFF));
                }
            }
        }
    }

    public BufferedImage cut(int x, int y, int w, int h) {
        return image.getSubimage(x, y, w, h);
    }

}
