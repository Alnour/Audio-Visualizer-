package com.example.root.audiovisualizer.base;

import android.content.Context;

/**
 * Created by root on 17/08/17.
 */

public interface BaseView<T>{
    Context getContext();
    void setPresenter(T presenter);
}
