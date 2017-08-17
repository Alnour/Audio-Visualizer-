package com.com.alex.siriwaveview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.root.audiovisualizer.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.maxLevel;

/**
 * Created by Alex on 6/25/2016.
 * Edited By Alnour Ahmed Khalifa on 17/8/2017 in order to add different sine waves
 *
 */
public class SiriWaveView extends View {

    private float maxSineAmp;

    public void setSines(List<Sine> mSines) {
        this.mSines = mSines;
        setMaxAmplitude(mSines);
    }

    private void setMaxAmplitude(List<Sine> mSines) {
        if(!this.mSines.isEmpty()) {
            maxSineAmp = this.mSines.get(0).getAmplitude();
            for (Sine sine : mSines) {
                if (sine.getAmplitude() > maxSineAmp)
                    maxSineAmp = sine.getAmplitude();
            }
        }
    }

    List<Sine> mSines = new ArrayList<>();

    private float frequency = 1.5f;
    private float IdleAmplitude = 0.00f;
    private int waveNumber = 2;
    private float phaseShift = 0.15f;
    private float initialPhaseOffset = 0.0f;
    private float waveHeight;
    private float waveVerticalPosition = 2;
    private int waveColor;


    ObjectAnimator mAmplitudeAnimator;

    public SiriWaveView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context, null);
    }

    public SiriWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    public SiriWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SiriWaveView);
        frequency = a.getFloat(R.styleable.SiriWaveView_waveFrequency, frequency);
        IdleAmplitude = a.getFloat(R.styleable.SiriWaveView_waveIdleAmplitude, IdleAmplitude);
        phaseShift = a.getFloat(R.styleable.SiriWaveView_wavePhaseShift, phaseShift);
        initialPhaseOffset = a.getFloat(R.styleable.SiriWaveView_waveInitialPhaseOffset, initialPhaseOffset);
        waveHeight = a.getDimension(R.styleable.SiriWaveView_waveHeight, waveHeight);
        waveColor = a.getColor(R.styleable.SiriWaveView_waveColor, waveColor);
        waveVerticalPosition = a.getFloat(R.styleable.SiriWaveView_waveVerticalPosition, waveVerticalPosition);
        waveNumber = a.getInteger(R.styleable.SiriWaveView_waveAmount, waveNumber);
        a.recycle();
        initAnimation();
    }

    private void initAnimation() {
        if (mAmplitudeAnimator == null) {
            mAmplitudeAnimator = ObjectAnimator.ofFloat(this, "amplitude", 1f);
            mAmplitudeAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        }
        if (!mAmplitudeAnimator.isRunning()) {
            mAmplitudeAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(Sine sine : mSines){
            sine.getPaint().setColor(sine.getWaveColor());
            canvas.drawPath(sine.getPath(), sine.getPaint());
            updatePath(sine);
            invalidate();

        }

    }

    private void updatePath(Sine sine) {
        sine.getPath().reset();
        sine.setPhase(sine.getPhase() + phaseShift);

        float halfHeight = getHeight() / waveVerticalPosition;
        float width = getWidth();
        float midWidth = width / 2.0f;

        float maxAmplitude = halfHeight - (halfHeight - waveHeight);
        float amplitude = sine.getAmplitude() * maxAmplitude / maxSineAmp;



        for (int x = 0; x < width; x++) {
            float scaling = (float) (-Math.pow(1 / midWidth * (x - midWidth), 2) + 1);

            float y = (float) (scaling * amplitude  * Math.sin(2 * Math.PI * (x / width) * sine.getFrequency() + sine.getPhase() + initialPhaseOffset) + halfHeight);

            if (x == 0) {
                sine.getPath().moveTo(x, y);
            } else {
                sine.getPath().lineTo(x, y);
            }
        }
    }




    public void stopAnimation() {
        if (mAmplitudeAnimator != null) {
            mAmplitudeAnimator.removeAllListeners();
            mAmplitudeAnimator.end();
            mAmplitudeAnimator.cancel();
        }
    }

    public void startAnimation() {
        if (mAmplitudeAnimator != null) {
            mAmplitudeAnimator.start();
        }
    }

}