package com.com.alex.siriwaveview;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Alnour on 17/08/17.
 */
public class Sine {
    private float frequency;
    private boolean isShown = true;
    private float amplitude;
    private int waveColor;
    private Path path;
    public float phase;




    public void setShown(boolean shown) {
        isShown = shown;
    }

    public boolean isShown() {
        return isShown;
    }




    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }


    public void setWaveColor(int waveColor) {
        this.waveColor = waveColor;
    }



    public Paint getPaint() {
        return paint;
    }

    private Paint paint;
    public Sine(float frequency, float amplitude, int waveColor, float phase) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.waveColor = waveColor;
        path = new Path();
        this.phase = phase;
        this.paint = getPaint(waveColor);
    }

    public float getPhase() {
        return phase;
    }


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

    private Paint getPaint(int waveColor) {
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(waveColor);
        return mPaint;
    }


    public void setPhase(float phase) {
        this.phase = phase;
    }
}
