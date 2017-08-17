package com.com.alex.siriwaveview;

import android.graphics.Path;

/**
 * Created by Alnour on 17/08/17.
 */
public class Sine {
    private float frequency;
    private float amplitude;
    private int waveColor;
    private Path path;

    public Sine(float frequency, float amplitude, int waveColor, float phase) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.waveColor = waveColor;
        path = new Path();
        this.phase = phase;
    }

    public float getPhase() {
        return phase;
    }

    public float phase;

    public float getFrequency() {
        return frequency;
    }

    public float getAmplitude() {
        return amplitude;
    }

    public int getWaveColor() {
        return waveColor;
    }

    public Path getPath() {
        return path;
    }


    public void setPhase(float phase) {
        this.phase = phase;
    }
}
