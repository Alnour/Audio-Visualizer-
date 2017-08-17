package com.example.root.audiovisualizer.main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.com.alex.siriwaveview.Sine;
import com.example.root.audiovisualizer.Factories.PresenterFactory;
import com.example.root.audiovisualizer.R;
import com.example.root.audiovisualizer.databinding.ActivityMainBinding;
import com.example.root.audiovisualizer.models.FreqAmp;
import com.example.root.audiovisualizer.models.SettingsModel;
import com.example.root.audiovisualizer.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContractor.View{


    private static final String[] START_STOP = {"Start", "Stop"};
    private int index = 0;
    private ActivityMainBinding binding;
    private MainContractor.Presenter presenter;
    private ArrayList<Sine> sines;

    @Override
    protected void onResume() {
        presenter.getSettings();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initializeFreq();
        updateView();
        setPresenter(PresenterFactory.getMainPresenter(this));
        setEventListeners();
    }

    @Override
    public void updateView() {
        binding.siriWaveView.setSines(sines);
    }

    private void setEventListeners() {
        binding.startStop.setOnClickListener(view -> {
            presenter.startStop();
            binding.startStop.setText(START_STOP[index%2]);
            index ++;
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
        updateView();
    }

    @Override
    public void showSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void applySettings(SettingsModel settingsModel) {
        if(sines.size() < 3)
            return;
        sines.get(0).setWaveColor(convertColor(settingsModel.getFirstColor()));
        sines.get(1).setWaveColor(convertColor(settingsModel.getSecondColor()));
        sines.get(2).setWaveColor(convertColor(settingsModel.getThirdColor()));

        sines.get(0).setShown(settingsModel.isShowFirst());
        sines.get(1).setShown(settingsModel.isShowSecond());
        sines.get(2).setShown(settingsModel.isShowThird());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                presenter.openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public int convertColor(int colorIndex){
        int resColorResId = R.color.colorPrimary;
        switch (colorIndex){
            case 0:
                resColorResId = android.R.color.holo_red_dark;
                break;
            case 1:
                resColorResId = android.R.color.holo_blue_bright;
                break;
            case 2:
                resColorResId = android.R.color.holo_green_dark;
                break;
        }
        return getResources().getColor(resColorResId);
    }
}
