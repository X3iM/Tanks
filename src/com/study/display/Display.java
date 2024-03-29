package com.study.display;

import com.study.Input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public abstract class Display {

    private static boolean created = false;
    private static JFrame window;
    private static Canvas content;

    private static BufferedImage buffer;
    private static int[] bufferData;
    private static Graphics bufferGraphics;
    private static int clearColor;

    private static BufferStrategy bufferStrategy;

    //private static float delta = 0;

    public static void create(int weight, int height, String title, int _clearColor, int numBuffer) {

        if (created)
            return;

        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new Canvas();

        /*
        //        for static program
        content = new Canvas() {
            public void paint(Graphics g) {
                super.paint(g);
                render(g);
            }
        };
         */

        Dimension size = new Dimension(weight, height);
        content.setPreferredSize(size);

        window.setResizable(false);
        window.getContentPane().add(content);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        buffer = new BufferedImage(weight, height, BufferedImage.TYPE_INT_ARGB);
        bufferData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();
        bufferGraphics = buffer.getGraphics();

        ( (Graphics2D) bufferGraphics ).setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );

        clearColor = _clearColor;

        content.createBufferStrategy(numBuffer);
        bufferStrategy = content.getBufferStrategy();

        created = true;
    }

    public static void clear() {
        Arrays.fill(bufferData, clearColor);
    }

    public static void swapBuffer() {
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(buffer, 0, 0, null);
        bufferStrategy.show();
    }

    public static Graphics2D getGraphics() {
        return (Graphics2D) bufferGraphics;
    }

    public static void destroy() {
        if (!created)
            return;

        window.dispose();
    }

    public static void setTitle(String title) {
        window.setTitle(title);
    }

    public static void addInputListener(Input inputListener) {
        window.add(inputListener);
    }

    /*
    public static void render() {
        bufferGraphics.setColor(new Color(0xff0000ff));
        bufferGraphics.fillOval((int) (350 + (Math.sin(delta) * 200)), 250, 100, 100);

        ( (Graphics2D) bufferGraphics ).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        bufferGraphics.fillOval((int) (500 + (Math.sin(delta) * 200)), 250, 100, 100);
        ( (Graphics2D) bufferGraphics ).setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF );

        //delta += 0.02f;
    }
     */


    /*
    //        for static program
    public static void render() {
        content.repaint();
    }

    private static void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(350, 250, 100, 100);
    }

     */
}