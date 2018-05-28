package com.example.maxpower.bluetoothapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

public class AudioPlay{
    private final String TAG = "AudioPlay";
    private static ArrayList<byte[]> bufferList = new ArrayList<>();
    private static int minSize = AudioTrack.getMinBufferSize(22050, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    private static AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 22050, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize, AudioTrack.MODE_STREAM);
    private int bufferIndexCounter = 0;
    private int x = 0;
    int test = 0;
    public AudioPlay() {
        audioTrack.play();
    }

    public void playMusic(byte[] buffer, int count){
        Log.e(TAG, "Before add bufferlist");
        bufferList.add(0, buffer);
        Log.e(TAG, "after add bufferlist");

            if(!bufferList.isEmpty())
            audioTrack.write(bufferList.get(x), 0, count);
            else
                audioTrack.stop();
            bufferList.remove(0);


        Log.e(TAG, "Buffersize " + bufferList.size());
        bufferIndexCounter++;

    }

    public void thread() {

    }


}
