package com.example.root.audiovisualizer.Factories;

import com.example.root.audiovisualizer.base.BasePresenter;
import com.example.root.audiovisualizer.main.MainContractor;
import com.example.root.audiovisualizer.main.MainPresenter;

/**
 * Created by root on 17/08/17.
 */

public class PresenterFactory {
    public static MainContractor.Presenter getMainPresenter(MainContractor.View view){
        return new MainPresenter(view);
    }
}
