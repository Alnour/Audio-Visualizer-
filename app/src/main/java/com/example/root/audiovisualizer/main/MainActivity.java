package com.example.root.audiovisualizer.main;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.com.alex.siriwaveview.Sine;
import com.example.root.audiovisualizer.Factories.PresenterFactory;
import com.example.root.audiovisualizer.R;
import com.example.root.audiovisualizer.databinding.ActivityMainBinding;
import com.example.root.audiovisualizer.models.FreqAmp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContractor.View{


    private ActivityMainBinding binding;
    private MainContractor.Presenter presenter;
    private ArrayList<Sine> sines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initializeFreq();
        binding.siriWaveView.setSines(sines);
        setPresenter(PresenterFactory.getMainPresenter(this));
        binding.startStop.setOnClickListener(view -> {
            presenter.startStop();
        });
    }

    private void initializeFreq() {
        sines = new ArrayList<>();
        sines.add(new Sine(1.0f, 10.0f, getResources().getColor(R.color.colorPrimary), 0.5f));
        sines.add(new Sine(2.0f, 5.0f, getResources().getColor(android.R.color.holo_red_dark), 0.5f));
        sines.add(new Sine(10.0f, 0.5f, getResources().getColor(android.R.color.holo_green_dark), 0.5f));
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setPresenter(MainContractor.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showNewData(List<FreqAmp> data) {
        if(data.size() != sines.size())
            throw new RuntimeException("data.length != sines.length");
        for(int i = 0 ; i < data.size(); i++) {
            sines.get(i).setAmplitude((float) data.get(i).getAmplitude());
            sines.get(i).setFrequency((float) data.get(i).getFrequency());
        }
        binding.siriWaveView.setSines(sines);
    }
}
