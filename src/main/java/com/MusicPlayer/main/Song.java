package com.MusicPlayer.main;

import javax.swing.*;
import java.io.Serializable;

public class Song implements Serializable {
    private String name;
    private String absPath;
    private ImageIcon likedImg;
    private boolean isLiked;

    public Song(String name, String absPath) {
        this.name = name;
        this.absPath = absPath;
        this.isLiked = false;
        this.likedImg = new ImageIcon(getClass().getClassLoader().getResource("img/like.png"));
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.absPath;
    }

    public ImageIcon getLikedImg() {
        return this.likedImg;
    }

    public boolean liked() {
        return this.isLiked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String absPath) {
        this.absPath = absPath;
    }

    public void setLikeImg(String imgPath) {
        this.likedImg = new ImageIcon(getClass().getClassLoader().getResource(imgPath));
    }

    public void setLike(boolean isLiked) {
        this.isLiked = isLiked;

        if(this.isLiked) {
            setLikeImg("img/likeB.png");
        } else {
            setLikeImg("img/like.png");
        }
    }

    public int getHash() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
