package com.example.root.audiovisualizer.settings;

import com.example.root.audiovisualizer.data.Preferences;
import com.example.root.audiovisualizer.models.SettingsModel;

/**
 * Created by root on 17/08/17.
 */

public class SettingsPresenter implements SettingsContractor.Presenter{
    SettingsContractor.View view;
    public SettingsPresenter(SettingsContractor.View view) {
        this.view = view;
    }

    @Override
    public void saveSettings() {
        SettingsModel model = view.readLastSettings();
        Preferences pref = Preferences.getInstance(view.getContext());
        pref.saveItem(SettingsModel.KEY, model);
        view.showMessage("Saved");
        view.returnToMain();
    }

    @Override
    public void getSettings() {
        Preferences pref = Preferences.getInstance(view.getContext());
        SettingsModel model = pref.getSavedItem(SettingsModel.KEY, SettingsModel.class);
        view.applySettings(model);
    }
}
