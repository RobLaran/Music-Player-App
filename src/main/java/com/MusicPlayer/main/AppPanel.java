package com.MusicPlayer.main;

import com.MusicPlayer.SliderUIs.CustomSliderUI;
import com.MusicPlayer.SliderUIs.OvalSliderUI;
import com.MusicPlayer.utility.LoadData;
import com.MusicPlayer.utility.SaveData;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppPanel extends JPanel {
    final int SCREEN_WIDTH = 680;
    final int SCREEN_HEIGHT = 510;

    public JLabel recordImage;

    public JPanel playlistPanel;
    public JLabel songName;

    public JSlider slider;
    public JLabel currentPosition;
    public JLabel duration;

    public JLabel likeButton;
    public ImageIcon likeIcon;
    public JLabel playBtn;
    public JLabel prevBtn;

    public JLabel nextBtn;
    public JSlider volumeSlider;
    public JLabel volumeImg;

    public JList<String> playlist;
    public DefaultListModel<String> playlistModel;
    public JScrollPane scrollPane;

    public JPanel buttonPanel;
    public JButton addBtn;
    public JButton removeBtn;

    SpringLayout layout = new SpringLayout();
    Listener listener;

    MusicPlayer musicPlayer;

    public AppPanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#48435C"));
        this.setLayout(layout);

        musicPlayer = new MusicPlayer(this);
        listener = new Listener(this);

        addComponents();
        addOns();
        load();
    }

    public void addComponents() {
        playlistPanel();

        recordImage = new JLabel();
        ImageIcon player = new ImageIcon(getClass().getClassLoader().getResource("img/record.png"));
        recordImage.setIcon(player);
        layout.putConstraint(SpringLayout.NORTH, recordImage, 65, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, recordImage, 50, SpringLayout.WEST, this);

        slider = new JSlider(0, musicPlayer != null ? musicPlayer.getDuration() : 0, 0);
        slider.setPreferredSize(new Dimension(SCREEN_WIDTH - 200, 30));
        slider.setUI(new CustomSliderUI());
        slider.setOpaque(false);
        slider.addChangeListener(listener);
        layout.putConstraint(SpringLayout.SOUTH, slider, -100, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, slider, 100, SpringLayout.WEST, this);

        songName = new JLabel();
        songName.setHorizontalAlignment(SwingConstants.CENTER);
        songName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 32));
        songName.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        songName.setPreferredSize(new Dimension(SCREEN_WIDTH - 200, 38));
        layout.putConstraint(SpringLayout.SOUTH, songName, 0, SpringLayout.NORTH, slider);
        layout.putConstraint(SpringLayout.WEST, songName, 0, SpringLayout.WEST, slider);

        currentPosition = new JLabel("0:00");
        currentPosition.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        currentPosition.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        layout.putConstraint(SpringLayout.NORTH, currentPosition, 0, SpringLayout.SOUTH, slider);
        layout.putConstraint(SpringLayout.WEST, currentPosition, 5, SpringLayout.WEST, slider);

        duration = new JLabel("0:00");
        duration.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        duration.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        layout.putConstraint(SpringLayout.NORTH, duration, 0, SpringLayout.SOUTH, slider);
        layout.putConstraint(SpringLayout.EAST, duration, -5, SpringLayout.EAST, slider);

        likeButton = new JLabel();
        likeIcon = new ImageIcon(getClass().getClassLoader().getResource("img/like.png"));
        likeButton.setIcon(likeIcon);
        likeButton.setPreferredSize(new Dimension(40,40));
        likeButton.setName("like");
        likeButton.addMouseListener(listener);
        layout.putConstraint(SpringLayout.NORTH, likeButton, -10, SpringLayout.NORTH, slider);
        layout.putConstraint(SpringLayout.WEST, likeButton, 3, SpringLayout.EAST, slider);

        playBtn = new JLabel();
        ImageIcon playImg = new ImageIcon(getClass().getClassLoader().getResource("img/pause.png"));
        playBtn.setIcon(playImg);
        playBtn.setName("pause/play");
        playBtn.addMouseListener(listener);
        layout.putConstraint(SpringLayout.SOUTH, playBtn, -60, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, playBtn, 219, SpringLayout.WEST, slider);

        prevBtn = new JLabel();
        ImageIcon prevImg = new ImageIcon(getClass().getClassLoader().getResource("img/prev.png"));
        prevBtn.setIcon(prevImg);
        prevBtn.setName("prev");
        prevBtn.addMouseListener(listener);
        layout.putConstraint(SpringLayout.NORTH, prevBtn, 1, SpringLayout.NORTH, playBtn);
        layout.putConstraint(SpringLayout.EAST, prevBtn, -2, SpringLayout.WEST, playBtn);

        nextBtn = new JLabel();
        ImageIcon nextImg = new ImageIcon(getClass().getClassLoader().getResource("img/next.png"));
        nextBtn.setIcon(nextImg);
        nextBtn.setName("next");
        nextBtn.addMouseListener(listener);
        layout.putConstraint(SpringLayout.NORTH, nextBtn, 1, SpringLayout.NORTH, playBtn);
        layout.putConstraint(SpringLayout.WEST, nextBtn, 2, SpringLayout.EAST, playBtn);

        volumeSlider = new JSlider(JSlider.VERTICAL,-50,6,-20);
        volumeSlider.setPreferredSize(new Dimension(10, 70));
        volumeSlider.setUI(new OvalSliderUI());
        volumeSlider.setOpaque(false);
        volumeSlider.addChangeListener(listener);
        layout.putConstraint(SpringLayout.NORTH, volumeSlider, -75, SpringLayout.NORTH, slider);
        layout.putConstraint(SpringLayout.EAST, volumeSlider, -15, SpringLayout.WEST, slider);

        volumeImg = new JLabel();
        ImageIcon volIcon = new ImageIcon(getClass().getClassLoader().getResource("img/volume.png"));
        volumeImg.setIcon(volIcon);
        layout.putConstraint(SpringLayout.NORTH, volumeImg, -10, SpringLayout.NORTH, slider);
        layout.putConstraint(SpringLayout.EAST, volumeImg, -3, SpringLayout.WEST, slider);

        this.add(recordImage);
        this.add(songName);
        this.add(slider);
        this.add(currentPosition);
        this.add(duration);
        this.add(likeButton);
        this.add(volumeImg);
        this.add(playBtn);
        this.add(prevBtn);
        this.add(nextBtn);
        this.add(volumeSlider);
    }

    public void playlistPanel() {
        playlistPanel = new JPanel();
        playlistPanel.setPreferredSize(new Dimension(280,260));
        playlistPanel.setBackground(Color.decode("#5A5766"));
        playlistPanel.setLayout(new BorderLayout());
        playlistPanel.addMouseListener(listener);

        layout.putConstraint(SpringLayout.NORTH, playlistPanel, 50, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, playlistPanel, -50, SpringLayout.EAST, this);

        playlistModel = new DefaultListModel<>();
        playlist = new JList<>(playlistModel);
        playlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playlist.setSelectionBackground(Color.decode("#61E786"));
        playlist.setSelectionForeground(Color.decode("#EDFFEC"));
        playlist.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        playlist.setBackground(Color.decode(CommonConstants.SECONDARY_COLOR));
        playlist.addMouseListener(listener);
        scrollPane = new JScrollPane(playlist);

        playlistPanel.add(scrollPane, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode(CommonConstants.BTN_PANEL));

        addBtn = new JButton("Add");
        addBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        addBtn.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        addBtn.setBackground(Color.decode(CommonConstants.PRIMARY_COLOR));
        addBtn.setFocusable(false);
        addBtn.setPreferredSize(new Dimension(90, 24));
        addBtn.setActionCommand("add");
        addBtn.addActionListener(listener);

        removeBtn = new JButton("Remove");
        removeBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        removeBtn.setForeground(Color.decode(CommonConstants.FONT_COLOR));
        removeBtn.setBackground(Color.decode(CommonConstants.PRIMARY_COLOR));
        removeBtn.setFocusable(false);
        removeBtn.setPreferredSize(new Dimension(90, 24));
        removeBtn.setActionCommand("remove");
        removeBtn.addActionListener(listener);

        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);

        playlistPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(playlistPanel);
    }

    public void setCurrentPosition(int position) {
        int mins = position / 60;
        int secs = position % 60;

        currentPosition.setText(String.format("%02d:%02d", mins, secs));
    }

    public void setDuration(int songDuration) {
        int mins = songDuration / 60;
        int secs = songDuration % 60;

        this.duration.setText(String.format("%02d:%02d", mins, secs));
    }

    public void addOns() {
        JLabel crowds = new JLabel();
        ImageIcon crwdIcon = new ImageIcon(getClass().getClassLoader().getResource("img/crowd4.png"));
        crowds.setIcon(crwdIcon);

        layout.putConstraint(SpringLayout.SOUTH, crowds, 0, SpringLayout.SOUTH, this);

        this.add(crowds);
    }

    public void resetLike() {
        likeIcon = new ImageIcon(getClass().getClassLoader().getResource("img/like.png"));
        likeButton.setIcon(likeIcon);
    }

    public void save() {
        try {
            Path dirPath = Paths.get("data");
            Files.createDirectories(dirPath);
            Path filePath = Paths.get("data/songs.ser");

            if(!Files.exists(filePath)) {
                Files.createFile(filePath);
                System.out.println("Created the data/songs.ser file");
            }

            SaveData.save(musicPlayer.likedSongs, "data/songs.ser");
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    public void load() {
        File file = new File("data/songs.ser");

        if(file.exists()) {
            ArrayList<Object> loadedData = LoadData.load(file);

            if(musicPlayer.likedSongs != null) {
                for(Object data : loadedData) {
                    Song song = (Song) data;

                    musicPlayer.likedSongs.add(song);

                    playlistModel.addElement(song.getName());
                    musicPlayer.playlist.add(song);

                    if(!musicPlayer.likedSongs.isEmpty()) {
                        likeButton.setIcon(musicPlayer.getSong().getLikedImg());
                    }

                    musicPlayer.start();
                }
            }
        }
    }
 }


