package org.prajvalk.openwork.graphics;

public class OpenWorkColor {

    private int alphaChannel;

    private int redChannel;

    private int greenChannel;

    private int blueChannel;

    public OpenWorkColor(int alpha, int red, int green, int blue) {
        alphaChannel = alpha;
        redChannel = red;
        greenChannel = green;
        blueChannel = blue;
    }

    public OpenWorkColor(int red, int green, int blue) {
        this(255, red, green, blue);
    }

    public OpenWorkColor(double percentAlpha, double percentRed, double percentGreen, double percentBlue) {
        alphaChannel = (int) (percentAlpha * 255);
        redChannel = (int) (percentRed * 255);
        greenChannel = (int) (percentGreen * 255);
        blueChannel = (int) (percentBlue * 255);
    }

    public OpenWorkColor(double percentRed, double percentGreen, double percentBlue) {
        this(100, percentRed, percentGreen, percentBlue);
    }

    public OpenWorkColor(int pixelValue) {
        alphaChannel = (pixelValue >> 24) & 0xff;
        redChannel = (pixelValue >> 16) & 0xff;
        greenChannel = (pixelValue >> 8) & 0xff;
        blueChannel = (pixelValue) & 0xff;
    }

    public int getAlphaChannel() {
        return alphaChannel;
    }

    public int getRedChannel() {
        return redChannel;
    }

    public int getGreenChannel() {
        return greenChannel;
    }

    public int getBlueChannel() {
        return blueChannel;
    }

    public int returnColorCode() {
        return (alphaChannel << 24) | (redChannel << 16) | (greenChannel << 8) | (blueChannel);
    }
}
