package com.study.Utils;

import com.study.game.level.Level;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Utils {

    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        newImage.getGraphics().drawImage(image, 0, 0, width, height, null);

        return  newImage;
    }

    public static Integer[][] levelParse(String file) {

        Integer[][] res = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file)))) {

            String line = null;
            List<Integer[]> levelLine = new ArrayList<Integer[]>();
            while ((line = reader.readLine()) != null) {
                //String[] tokens = line.split(" ");
                levelLine.add(strToIntArray(line.split(" ")));
            }
            res = new Integer[levelLine.size()][levelLine.get(0).length];

            for (int i = 0; i < levelLine.size(); i++) {
                res[i] = levelLine.get(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    public static final Integer[] strToIntArray(String[] array) {
        Integer[] res = new Integer[array.length];

        for (int i = 0; i < array.length; i++) {
            res[i] = Integer.parseInt(array[i]);
        }

        return  res;
    }

}
