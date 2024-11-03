package com.MusicPlayer.main;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements MouseListener, ChangeListener, ActionListener {
    AppPanel panel;

    MusicPlayer player;
    boolean selected = false;

    public Listener(AppPanel panel) {
        this.panel = panel;
        this.player = panel.musicPlayer;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double point = e.getLocationOnScreen().getY();

        if(panel.musicPlayer.musicClip != null && point > 500) {
            String name = e.getComponent().getName();

            if(!panel.musicPlayer.playlist.isEmpty()) {
                ImageIcon playPauseImg;

                if(name.equals("pause/play")) {
                    if(!player.musicClip.isOpen()) {
                        player.start();
                    }

                    if(!player.musicClip.isRunning()) {
                        playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/pause.png"));
                        panel.playBtn.setIcon(playPauseImg);

                        if(panel.musicPlayer.currentPosition == panel.musicPlayer.getDuration()) {
                            System.out.println("restart");
                            panel.musicPlayer.restart();
                        }

                        player.play();
                    } else {
                        playPauseImg = new ImageIcon(getClass().getClassLoader().getResource("img/play.png"));
                        panel.playBtn.setIcon(playPauseImg);
                        player.stop();
                    }
                }

                if(name.equals("prev")) {
                    player.prev();
                } else if(name.equals("next")) {
                    player.next();
                }

                if(name.equals("like") && !panel.musicPlayer.getSong().liked()) {
                    System.out.println("like");
                    panel.musicPlayer.getSong().setLike(true);
                    panel.likeButton.setIcon(panel.musicPlayer.getSong().getLikedImg());
                    panel.musicPlayer.likedSongs.add(panel.musicPlayer.getSong());
                } else if(name.equals("like") && panel.musicPlayer.getSong().liked()) {
                    System.out.println("dislike");
                    panel.musicPlayer.getSong().setLike(false);
                    panel.likeButton.setIcon(panel.musicPlayer.getSong().getLikedImg());
                    panel.musicPlayer.likedSongs.remove(panel.musicPlayer.getSong());
                }

            }
        }

        if(selected) {
            panel.playlist.clearSelection();
            selected = false;
        } else {
            selected = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(panel.slider.getValueIsAdjusting() && panel.musicPlayer.musicClip != null) {
            int currentPosition = panel.slider.getValue();
            panel.slider.setValue(currentPosition);
            player.musicClip.setMicrosecondPosition(currentPosition * 1000000L);

            if(!player.musicClip.isOpen()) {
                System.out.println("Adjusting");
                player.start();
            }
        }

        if(panel.volumeSlider.getValueIsAdjusting()) {
            System.out.println(panel.volumeSlider.getValue());;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("add")) {

            JFileChooser fileChoose = new JFileChooser();
            fileChoose.setFileFilter(new FileNameExtensionFilter("Audio Files", "wav"));
            int returnVal = fileChoose.showOpenDialog(panel);

            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String songName = fileChoose.getSelectedFile().getName();
                String songPath = fileChoose.getSelectedFile().getAbsolutePath();
                Song song = new Song(songName, songPath);

                panel.playlistModel.addElement(song.getName());
                panel.musicPlayer.playlist.add(song);

                System.out.println("Name: " + songName);
                System.out.println("Path: " + songPath);


                if(panel.musicPlayer.playlist.size() == 1) {
                    panel.musicPlayer.setupClip(songPath);
                    panel.musicPlayer.start();
                    panel.musicPlayer.play();
                }
            }
        } else if(e.getActionCommand().equals("remove")) {
            if(!panel.musicPlayer.playlist.isEmpty() && !panel.playlist.isSelectionEmpty()) {
                String valueToRemove = panel.playlist.getSelectedValue();
                int valueToRemoveIndex = panel.playlist.getSelectedIndex();


                if(panel.musicPlayer.playlist.size() == 1) {
                    panel.songName.setText("");
                    panel.currentPosition.setText("0:00");
                    panel.duration.setText("0:00");
                    panel.slider.setValue(0);
                    panel.resetLike();
                    try {
                        panel.musicPlayer.close();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                panel.playlistModel.removeElement(valueToRemove);
                panel.musicPlayer.playlist.remove(valueToRemoveIndex);
                panel.musicPlayer.removeSongFromLikedSongs(valueToRemove);
            }
        }
    }
}










