package com.example.root.audiovisualizer.main;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;

import com.example.root.audiovisualizer.Utils.FFTbase;
import com.example.root.audiovisualizer.callbacks.ResultCallback;
import com.example.root.audiovisualizer.models.FreqAmp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/08/17.
 */
public class RecordingService extends AsyncTask<Void, Double, Void> {

    //TODO handle parallel access


    public boolean isContinueRecording() {
        return continueRecording;
    }

    private  boolean continueRecording = false;
    private AudioRecord audioRecord;
    private int sampleRate = 8000;
    ResultCallback<List<FreqAmp>> callback;

    public RecordingService(ResultCallback<List<FreqAmp>> callback){
        init();
        this.callback = callback;
    }

    public void start(){
        continueRecording = true;
        execute();
    }

    public void end(){
        continueRecording = false;
        //and then it will end in the next loop
    }

    private void init() {
        int minSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.
                        CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, minSize);
    }

    @Override
    protected void onProgressUpdate(Double... frequencies) {
        super.onProgressUpdate(frequencies);
        if(callback != null)
            callback.onResponse(convertFre2List(frequencies));
    }

    @Override
    protected Void doInBackground(Void... params) {
        audioRecord.startRecording();
        while (continueRecording) {
            int readSize = 256;
            short[] buffer = new short[readSize];
            audioRecord.read(buffer, 0, readSize);
            double real[] = new double[buffer.length];
            double img[] = new double[buffer.length];
            for (int i = 0; i < buffer.length; i++) {
                real[i] = buffer[i];
                img[i] = 0;
            }
            Double[] res = FFTbase.fft(real, img, true);
            this.publishProgress(res);
        }
        return null;
    }

    /**
     * name your highest freq component X
     10% are frequencies from DC to 0.1*X
     draw a normal distribution for mean and phase if you like
     the magnitude of the Sin could be the mean value of all amplitudes in the frequency range
     it's frequency could be the mean frequency or the frequency of its highest magnitude

     * */
    public List<FreqAmp> convertFre2List(Double[] freq){
        double baseBandFreq = 1/sampleRate;
        List<FreqAmp> result = new ArrayList<>();
        int n = freq.length/2;
        Double magnitudes[] = new Double[n];
        for(int i = 0 ; i < n; i += 2){

            magnitudes[i] = Math.sqrt(freq[i] * freq[i] + freq[i+1] * freq[i+1]);
        }
        int tenPercEndIndex = (int) (0.1 * n);
        int next30PercEndIndex = (int) (0.3*n + tenPercEndIndex);
        int next40PercEndIndex = (int) (0.4*n + next30PercEndIndex);
        result.add(getNPercentValues(baseBandFreq, magnitudes, 0, tenPercEndIndex));
        result.add(getNPercentValues(baseBandFreq, magnitudes, tenPercEndIndex, next30PercEndIndex));
        result.add(getNPercentValues(baseBandFreq, magnitudes, next30PercEndIndex, next40PercEndIndex));
        return result;
    }

    private FreqAmp getNPercentValues(double baseBandFreq, Double[] magnitudes, int startIndex, int endIndex) {
        double curMag = 0;
        double magSum = 0;
        double curFreqIndex = startIndex;
        for(int i = startIndex ; i < endIndex ; i++){
            if(magnitudes[i] > curMag){
                curFreqIndex = i;
                curMag = magnitudes[i];
            }
            magSum += magnitudes[i];
        }
        return new FreqAmp(curFreqIndex*baseBandFreq,magSum/endIndex);
    }

}
