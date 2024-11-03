package com.MusicPlayer.main;

import com.MusicPlayer.utility.SaveData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AppFrame extends JFrame {

    public AppFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Music Player");
        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("img/icon.png"));
        Image icon = imageIcon.getImage();
        this.setIconImage(icon);

        AppPanel panel = new AppPanel();

        Container container = this.getContentPane();

        container.add(panel);
        this.pack();
        this.setLocationRelativeTo((null));
        this.setResizable(false);
        this.setVisible(true);

        // Add the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(!panel.musicPlayer.likedSongs.isEmpty()) {
                System.out.println("saving");
                panel.save();
            } else if(panel.musicPlayer.likedSongs.isEmpty() && new File("data/songs.ser").exists()){
                SaveData.save(new ArrayList<>(),"data/songs.ser");
            }

            System.out.println("Shutdown hook triggered, data saved.");
        }));
    }
}
