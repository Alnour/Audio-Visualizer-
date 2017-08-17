package com.example.root.audiovisualizer.main;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.AsyncTask;

/**
 * Created by root on 17/08/17.
 */

public class PlayingService extends AsyncTask<Void, Double, Void> {
    final static int SAMPLE_RATE = 8000;
    final static int CHANNELS = 3;
    private AudioTrack audioTrack;

    @Override
    protected Void doInBackground(Void... params) {

        int mBufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        if (mBufferSize == AudioTrack.ERROR || mBufferSize == AudioTrack.ERROR_BAD_VALUE) {
            mBufferSize = SAMPLE_RATE * CHANNELS * 2;
        }

        audioTrack = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                mBufferSize,
                AudioTrack.MODE_STREAM);
        return null;
    }
}
