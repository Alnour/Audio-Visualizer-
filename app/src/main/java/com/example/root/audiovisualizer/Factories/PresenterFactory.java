package com.example.root.audiovisualizer.Factories;

import com.example.root.audiovisualizer.base.BasePresenter;
import com.example.root.audiovisualizer.main.MainContractor;
import com.example.root.audiovisualizer.main.MainPresenter;
import com.example.root.audiovisualizer.settings.SettingsContractor;
import com.example.root.audiovisualizer.settings.SettingsPresenter;

/**
 * Created by root on 17/08/17.
 */

public class PresenterFactory {
    public static MainContractor.Presenter getMainPresenter(MainContractor.View view){
        return new MainPresenter(view);
    }

    public static SettingsContractor.Presenter getSettingsPresenter(SettingsContractor.View view) {
        return new SettingsPresenter(view);
    }
}
