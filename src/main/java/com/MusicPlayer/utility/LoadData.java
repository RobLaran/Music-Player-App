package com.MusicPlayer.utility;

import java.io.*;
import java.util.ArrayList;

public class LoadData {
    public static ArrayList<Object> load(File loadFile) {
        try {
            File file = loadFile;

            if(file.exists()) {
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);

                return (ArrayList<Object>) objectStream.readObject();
            }
        } catch(FileNotFoundException fileExc) {
            fileExc.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
