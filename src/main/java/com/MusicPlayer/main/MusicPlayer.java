package com.MusicPlayer.main;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class MusicPlayer {
    ArrayList<Song> playlist = new ArrayList<>();
    ArrayList<Song> likedSongs;

    File file;
    public Clip musicClip;

    private int duration;
    public int currentPosition;

    ImageIcon playPauseImg;

    public Thread musicThread;

    int i = 0;

    public FloatControl volumeControl;

    AppPanel panel;

    public MusicPlayer(AppPanel panel) {
        this.panel = panel;
        this.likedSongs = new ArrayList<>();
    }

    public void setupClip(String filePath) {
        try {
            file = new File(filePath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(file);
            musicClip = AudioSystem.getClip();

            musicClip.open(audioInput);


            volumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(panel.volumeSlider.getValue());

            duration = (int) (musicClip.getMicrosecondLength() / 1000000) + 1;

            panel.setDuration(duration - 1);
            panel.songName.setText(getSongName());
            panel.slider.setMaximum(duration);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public void restart() {
        musicClip.close();
        setupClip(file.getAbsolutePath());
    }

    public void start() {
        if(musicClip == null)  setupClip(getSongPath());

        System.out.println("Starting");

        playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/pause.png"));
        panel.playBtn.setIcon(playPauseImg);

        currentPosition = 0;

        musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (musicClip != null) {
                    currentPosition = (int) musicClip.getMicrosecondPosition() / 1000000;
                    volumeControl.setValue(panel.volumeSlider.getValue());

//                    System.out.println("Current Position: " + currentPosition + " seconds");
//                    System.out.println("Volume: " + volumeControl.getValue());
//                    System.out.println("Duration: " + duration);
//            System.out.println(panel.slider.getMaximum());
//                    System.out.println(Thread.currentThread());

                    panel.setCurrentPosition(currentPosition);
                    panel.slider.setValue(currentPosition);

                    try {
                        Thread.sleep(1000); // Wait for 1 second
                    } catch (InterruptedException e) {
                        System.out.println(e.getLocalizedMessage());
                    }

                    if(panel.musicPlayer.playlist.size() > 1 && !musicClip.isRunning() && currentPosition >= duration - 1) {
                        System.out.println("next song");
                        panel.musicPlayer.next();
                        playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/pause.png"));
                    }

                    if(musicClip != null) {
                        if (!musicClip.isOpen() || currentPosition == duration - 1) {
                            System.out.println("Music closed");

                            playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/play.png"));
                            panel.playBtn.setIcon(playPauseImg);
                        }

                        System.out.println("Running: " + musicClip.isRunning());
                        if(!musicClip.isRunning()) {
                            playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/play.png"));
                        } else {
                            playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/pause.png"));
                        }

                        panel.playBtn.setIcon(playPauseImg);
                    }

                }
            }
        });

        musicThread.start();
    }

    public void close() throws InterruptedException {
        if(musicClip != null) {
            System.out.println("Closing");
            musicClip.close();
            musicClip = null;
        }
    }

    public void play() {
        if(musicClip != null) {
            System.out.println("Playing");
            panel.likeButton.setIcon(getSong().getLikedImg());
            musicClip.start();
        }
    }

    public void stop() {
        if(musicClip != null) {
            System.out.println("Stops");
            musicClip.stop();
        }
    }

    public void next() {
        if(musicClip != null) {
            System.out.println("Change song");
            musicClip.close();

            i++;
            if(i > playlist.size() - 1) {
                i = 0;
            }

            if(!musicClip.isOpen()) {
                setupClip(getSongPath());
            }
            play();
        }
    }

    public void prev() {
        if(musicClip != null) {
            System.out.println("Change song");
            musicClip.close();

            i--;
            if(i < 0) {
                i = playlist.size() - 1;
            }

            if(!musicClip.isOpen()) {
                setupClip(getSongPath());
            }
            play();
        }
    }

    public int getDuration() {
        int duration = 0;

        if(musicClip != null) {
            duration = (int) (musicClip.getMicrosecondLength() / 1000000);
        }

        return duration;
    }

    public String getSongName() {
        String[] fileArr = file.getName().split("\\.");

        return  fileArr[0];
    }

    public String getSongPath() {
        return playlist.get(i).getPath();
    }

    public Song getSong() {
        return playlist.get(i);
    }

    public void setSong() {

    }

    public void removeSongFromLikedSongs(String name) {
        for(Song song : likedSongs) {
            if(song.getName().equals(name)) {
                likedSongs.remove(song);
                break;
            }
        }
    }

}
