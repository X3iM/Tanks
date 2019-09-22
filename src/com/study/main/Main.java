package com.study.main;

import com.study.display.Display;
import com.study.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Main {

    public static void main(String[] args) {

        Game tanks = new Game();
        tanks.start();

        /*Display.create(800, 600, "Tanks", 0xff00ff00, 3);

        Timer timer = new Timer(1000/60, new AbstractAction() {
            public void actionPerformed (ActionEvent e) {
                //Display.render();
                Display.clear();
                Display.render();

                Display.swapBuffer();
            }
        });

        timer.setRepeats(true);
        timer.start();
         */
    }
}