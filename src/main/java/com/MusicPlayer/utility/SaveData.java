package com.MusicPlayer.utility;

import com.MusicPlayer.main.Song;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveData {
    public static void save(ArrayList<Song> list, String filePath) {
        try {
            FileOutputStream fileStream = new FileOutputStream(filePath);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileStream);
            outputStream.writeObject(list);
        } catch(FileNotFoundException fileExc) {
            fileExc.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
