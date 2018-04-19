package com.example.maxpower.bluetoothapp;

import java.io.File;

public class AudioUtil {
    public static File getSoundFile(String fileName) {
        File soundFile = new File(fileName);
        if(!soundFile.exists() || !soundFile.isFile())
            throw new IllegalArgumentException("Not a file: " + soundFile);
        return soundFile;
    }
}
