package com.example.root.audiovisualizer.models;

/**
 * Created by root on 17/08/17.
 */

public class SettingsModel {
    public static final String KEY = "settings";
    int firstColor = android.R.color.holo_red_dark;
    int secondColor = android.R.color.holo_green_dark;
    int thirdColor = android.R.color.holo_blue_dark;

    boolean showFirst = true;
    boolean showSecond = true;
    boolean showThird = true;


    public void setThirdColor(int thirdColor) {
        this.thirdColor = thirdColor;
    }

    public void setFirstColor(int firstColor) {
        this.firstColor = firstColor;
    }

    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
    }

    public void setShowFirst(boolean showFirst) {
        this.showFirst = showFirst;
    }

    public void setShowSecond(boolean showSecond) {
        this.showSecond = showSecond;
    }

    public void setShowThird(boolean showThird) {
        this.showThird = showThird;
    }


    public boolean isShowThird() {
        return showThird;
    }

    public int getFirstColor() {
        return firstColor;
    }

    public int getSecondColor() {
        return secondColor;
    }

    public int getThirdColor() {
        return thirdColor;
    }

    public boolean isShowFirst() {
        return showFirst;
    }

    public boolean isShowSecond() {
        return showSecond;
    }



}
