package com.example.root.audiovisualizer.main;

import com.example.root.audiovisualizer.base.BasePresenter;
import com.example.root.audiovisualizer.base.BaseView;
import com.example.root.audiovisualizer.models.FreqAmp;
import com.example.root.audiovisualizer.models.SettingsModel;

import java.util.List;

/**
 * Created by root on 17/08/17.
 */

public interface MainContractor {
    interface Presenter extends BasePresenter{
        void initRecordingService();
        void openSettings();
        void startStop();
        void getSettings();
    }

    interface View extends BaseView<Presenter>{
        void showNewData(List<FreqAmp> data);
        void showSettings();
        void applySettings(SettingsModel settingsModel);
        void updateView();
    }
}
