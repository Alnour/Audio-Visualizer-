package com.example.root.audiovisualizer.models;

/**
 * Created by root on 17/08/17.
 */
public class FreqAmp {
    double frequency;
    double amplitude;

    public double getFrequency() {
        return frequency;
    }

    public double getAmplitude() {
        return amplitude;
    }

    public FreqAmp(double frequency, double amplitude) {

        this.frequency = frequency;
        this.amplitude = amplitude;
    }
}
