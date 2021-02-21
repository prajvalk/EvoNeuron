package org.prajvalk.openwork.graphics;

import java.awt.image.BufferedImage;

public class OpenWorkTexture {

    protected BufferedImage image;

    public OpenWorkTexture(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public OpenWorkTexture(BufferedImage img) {
        image = img;
    }

    public OpenWorkColor getColor(int x, int y) {
        return new OpenWorkColor(image.getRGB(x, y));
    }

    public void setColor(OpenWorkColor color, int x, int y) {
        image.setRGB(x, y, color.returnColorCode());
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getHeight() { return image.getHeight(); }

    public int getWidth() { return  image.getWidth(); }

}
