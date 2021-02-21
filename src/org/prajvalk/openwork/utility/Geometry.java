package org.prajvalk.openwork.utility;

import org.prajvalk.openwork.graphics.OpenWorkColor;
import org.prajvalk.openwork.graphics.OpenWorkTexture;

public final class Geometry {

    public static void drawArc(int centerx, int centery, float radius, float stepping, float partCircle, OpenWorkColor color, OpenWorkTexture... textures) {
        for(OpenWorkTexture texture:textures) {
            for (float angle = 0; angle <= 360 * partCircle; angle += stepping, radius += 0.1f) {
                int px = centerx + (int) (radius * Math.cos(Math.toDegrees(angle)));
                int py = centery + (int) (radius * Math.sin(Math.toDegrees(angle)));
                try {
                    texture.setColor(color, px, py);
                } catch (Exception e) {}
            }
        }
    }
}
