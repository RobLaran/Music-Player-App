package com.MusicPlayer.SliderUIs;

import com.MusicPlayer.main.CommonConstants;

import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;

public class OvalSliderUI extends MetalSliderUI {

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw unfilled oval track
        g2.setColor(Color.GRAY);  // Background color of the track
        g2.fillRect((trackRect.x + trackRect.width / 2) - 2, trackRect.y, trackRect.width / 4, trackRect.height);

        Rectangle trackBounds = trackRect;
        g2.setColor(Color.LIGHT_GRAY);
        int filledHeight = trackBounds.height - (thumbRect.y + thumbRect.height - trackBounds.y); // Calculate filled height
        g2.fillRect(trackBounds.x + (trackBounds.width / 2) - 2, trackBounds.y + trackBounds.height - filledHeight, 4, filledHeight);

    }

    @Override
    public void paintThumb(Graphics g) {
        g.setColor(Color.decode(CommonConstants.FONT_COLOR)); // Color of the thumb
        g.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }
}
