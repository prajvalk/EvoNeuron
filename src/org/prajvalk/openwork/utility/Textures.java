package org.prajvalk.openwork.utility;

import org.prajvalk.openwork.exceptions.OpenWorkTextureLoadingIOException;
import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Textures {

    public static void paint(OpenWorkColor color, OpenWorkTexture texture) {
        paint(0, 0, color, texture);
    }

    public static void paint(OpenWorkColor color, OpenWorkTexture... textures) {
        paint(0, 0, color, textures);
    }

    public static void paint(int offsetX, int offsetY, OpenWorkColor color, OpenWorkTexture... textures) {
        for(OpenWorkTexture texture:textures) {
            for(int i=offsetX;i<texture.getWidth();i++) {
                for(int j=offsetY;j<texture.getHeight();j++) {
                    texture.setColor(color, i, j);
                }
            }
        }
    }

    public static void paint(int offsetX, int offsetY, int finalX, int finalY,OpenWorkColor color, OpenWorkTexture... textures) {
        for(OpenWorkTexture texture:textures) {
            for(int i=offsetX;i<finalX;i++) {
                for(int j=offsetY;j<finalY;j++) {
                    texture.setColor(color, i, j);
                }
            }
        }
    }

    public static OpenWorkTexture load(String filename) throws OpenWorkTextureLoadingIOException {
        try {
            return new OpenWorkTexture(ImageIO.read(new File(filename)));
        } catch (IOException ioException) {
            throw new OpenWorkTextureLoadingIOException(ioException.getMessage());
        }
    }

    public static OpenWorkTexture load(URL url) throws OpenWorkTextureLoadingIOException {
        try {
            return new OpenWorkTexture(ImageIO.read(url));
        } catch (IOException ioException) {
            throw new OpenWorkTextureLoadingIOException(ioException.getMessage());
        }
    }

}
