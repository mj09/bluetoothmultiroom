package com.example.maxpower.bluetoothapp;

import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class AudioStream{
    private static String TAG = "AudioStream";
    private File soundFile = new File(MasterActivity.filepath);
    byte audioBuffer[] = new byte[44100];
    byte[] data = new byte[(int) 44100];
    boolean x = true;
    public void streamAudio(OutputStream outputStream) {
        try {
            FileInputStream fi = new FileInputStream(soundFile);
            fi.read(data);
            fi.close();
            int count;
            while(x) {
                outputStream.write(data, 0, data.length);
            }
        } catch (IOException e) {

        }
     /*   try {
            FileInputStream fileInputStream = new FileInputStream(soundFile);

            byte buffer[] = new byte[44100];
            int count;

            while((count = fileInputStream.read(buffer)) != -1) {
                try {
                    audioBuffer = buffer;
                    outputStream.write(buffer, 0, count);
                } catch (IOException e) {
                    Log.e(TAG, "Error when sending data " + e);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }*/
    }

    public byte[] getAudioBuffer() {
        return audioBuffer;
    }

    public void setAudioBuffer(byte buffer[]) {
        audioBuffer = buffer;
    }

    public void newStreamAudio() {
        try {
             FileInputStream fileInputStream = new FileInputStream(soundFile);
             int count;

             while((count = fileInputStream.read(audioBuffer)) != -1) {
                     Log.e(TAG, "audiobuffer: " + audioBuffer);

             }
        }catch (IOException e) {
            Log.e(TAG,e.toString());
        }
    }
}
