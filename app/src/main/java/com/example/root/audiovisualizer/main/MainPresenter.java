package com.example.root.audiovisualizer.main;

import com.example.root.audiovisualizer.models.FreqAmp;

import java.util.List;

/**
 * Created by root on 17/08/17.
 */

public class MainPresenter implements MainContractor.Presenter{
    MainContractor.View view;



    RecordingService recordingService;


    public MainPresenter(MainContractor.View view) {
        this.view = view;
        initRecordingService();
    }

    @Override
    public void initRecordingService() {
        recordingService = new RecordingService((List<FreqAmp> data) -> view.showNewData(data));
    }

    @Override
    public void openSettings() {

    }

    @Override
    public void startStop() {
        if(recordingService.isContinueRecording())
            recordingService.start();
        else
            recordingService.end();
    }
}
