package com.example.root.audiovisualizer.main;

import com.example.root.audiovisualizer.callbacks.ResultCallback;
import com.example.root.audiovisualizer.data.Preferences;
import com.example.root.audiovisualizer.models.FreqAmp;
import com.example.root.audiovisualizer.models.SettingsModel;

import java.util.List;

/**
 * Created by root on 17/08/17.
 */

public class MainPresenter implements MainContractor.Presenter{
    MainContractor.View view;



    RecordingService recordingService;
    private ResultCallback<List<FreqAmp>> callback;


    public MainPresenter(MainContractor.View view) {
        this.view = view;
        initRecordingService();
    }

    @Override
    public void initRecordingService() {
        callback = (List<FreqAmp> data) -> view.showNewData(data);
        recordingService = new RecordingService(callback);
    }

    @Override
    public void openSettings() {
        //save state or do any presenter job
        view.showSettings();
    }

    @Override
    public void startStop() {
        if(!recordingService.isContinueRecording())
            recordingService.start();
        else {
            recordingService.end();
            recordingService = new RecordingService(callback);
            recordingService.start();
        }
    }

    @Override
    public void getSettings() {
        SettingsModel settingsModel =
                Preferences.getInstance(view.getContext()).getSavedItem(SettingsModel.KEY, SettingsModel.class);
        view.applySettings(settingsModel);
        view.updateView();
    }
}
