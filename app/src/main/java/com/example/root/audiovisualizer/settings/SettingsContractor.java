package com.example.root.audiovisualizer.settings;

import com.example.root.audiovisualizer.base.BasePresenter;
import com.example.root.audiovisualizer.base.BaseView;
import com.example.root.audiovisualizer.models.SettingsModel;

/**
 * Created by root on 17/08/17.
 */

public interface SettingsContractor {
    interface Presenter extends BasePresenter{

        void saveSettings();
        void getSettings();
    }
    interface View extends BaseView<Presenter>{
        void applySettings(SettingsModel settingsModel);
        SettingsModel readLastSettings();
        void returnToMain();

        void showMessage(String message);
    }
}
