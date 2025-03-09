package com.example.innosynergy.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int WIDTH = 160;
    private static final int HEIGHT = 60;

    public static BufferedImage generateCaptchaImage(String captchaText) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, WIDTH, HEIGHT);

        // Add random lines for noise
        Random random = new Random();
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Text
        g2d.setFont(new Font("Arial", Font.BOLD, 40));
        for (int i = 0; i < captchaText.length(); i++) {
            g2d.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2d.drawString(String.valueOf(captchaText.charAt(i)), 20 + i * 20, 45);
        }

        // Add random dots for noise
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g2d.drawRect(x, y, 1, 1);
        }

        g2d.dispose();
        return image;
    }

    public static String generateCaptchaText() {
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            captchaText.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return captchaText.toString();
    }
}