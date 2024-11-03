package com.MusicPlayer.SliderUIs;

import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;

public class CustomSliderUI extends MetalSliderUI {
    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Custom track paint (if needed), or leave as default if you want a minimal look
        g2.setColor(Color.GRAY);
        g2.fillRect(trackRect.x, (trackRect.y + trackRect.height / 2) - 1, trackRect.width, trackRect.height / 4);

        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(trackRect.x, trackRect.y + (trackRect.height / 2) - 1, thumbRect.x - trackRect.x, 4);
    }

    @Override
    public void paintThumb(Graphics g) {
        g.setColor(Color.decode("#9792E3"));
        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }
}
