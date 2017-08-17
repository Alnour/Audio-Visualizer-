package com.example.root.audiovisualizer.main;

import com.example.root.audiovisualizer.base.BasePresenter;
import com.example.root.audiovisualizer.base.BaseView;
import com.example.root.audiovisualizer.models.FreqAmp;

import java.util.List;

/**
 * Created by root on 17/08/17.
 */

public interface MainContractor {
    interface Presenter extends BasePresenter{
        void initRecordingService();
        void openSettings();

        void startStop();
    }

    interface View extends BaseView<Presenter>{
        void showNewData(List<FreqAmp> data);
    }
}
