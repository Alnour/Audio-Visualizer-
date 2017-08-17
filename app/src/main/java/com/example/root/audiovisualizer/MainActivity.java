package com.example.root.audiovisualizer;

import android.databinding.DataBindingUtil;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.com.alex.siriwaveview.Sine;
import com.example.root.audiovisualizer.FFT.FFTbase;
import com.example.root.audiovisualizer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private int sampleRate = 8000; //hz
    private AudioRecord audioRecord;
    boolean continueRecording = true;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        List<Sine> sines = new ArrayList<>();
        sines.add(new Sine(1.5f, 3.0f, R.color.colorPrimary, 0f));
        sines.add(new Sine(0.5f, 6.0f, R.color.colorPrimary, 0f));
        binding.siriWaveView.setSines(sines);
        init();
        new RecordingService().execute();
    }


    void init(){

        int minSize = AudioRecord.getMinBufferSize(sampleRate,AudioFormat.
                        CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, minSize);
    }

    public class RecordingService extends AsyncTask<Void, Void, Void >{


        @Override
        protected Void doInBackground(Void... params) {
            audioRecord.startRecording();
            while (continueRecording){
                Log.i("record", "started recording");
                int readSize = 256;
                short[] buffer = new short[readSize];
                audioRecord.read(buffer, 0, readSize);
                double real[] = new double[buffer.length];
                double img[] = new double[buffer.length];
                for(int i = 0 ; i < buffer.length ; i++){
                    real[i] = buffer[i];
                    img[i] = 0;
                }
                double res[] = FFTbase.fft(real, img, true);
                String rep = "";
                for (double d : res)
                    rep += ", " + d;
                Log.i("record", rep);
            }
            return null;
        }
    }

}
