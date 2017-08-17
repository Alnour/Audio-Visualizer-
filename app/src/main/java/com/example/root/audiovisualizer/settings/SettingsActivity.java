package com.example.root.audiovisualizer.settings;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.root.audiovisualizer.Factories.PresenterFactory;
import com.example.root.audiovisualizer.R;
import com.example.root.audiovisualizer.databinding.ActivitySettingsBinding;
import com.example.root.audiovisualizer.models.SettingsModel;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends AppCompatActivity implements SettingsContractor.View{

    private ActivitySettingsBinding binding;
    SettingsContractor.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        fillOptions();
        setPresenter(PresenterFactory.getSettingsPresenter(this));
        setEventListeners();
    }

    private void fillOptions() {
        List<String> options = new ArrayList<>();
        options.add("Red");
        options.add("Blue");
        options.add("Green");
        fillTextSpinner(options, binding.firstColor);
        fillTextSpinner(options, binding.secondColor);
        fillTextSpinner(options, binding.thirdColor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getSettings();
    }

    private void setEventListeners() {
        binding.save.setOnClickListener(view ->{
            presenter.saveSettings();
        });
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void setPresenter(SettingsContractor.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void applySettings(SettingsModel settingsModel) {
        binding.firstWave.setChecked(settingsModel.isShowFirst());
        binding.secondWave.setChecked(settingsModel.isShowSecond());
        binding.thirdWave.setChecked(settingsModel.isShowThird());

        binding.firstColor.setSelection(settingsModel.getFirstColor());
        binding.secondColor.setSelection(settingsModel.getSecondColor());
        binding.thirdColor.setSelection(settingsModel.getThirdColor());
    }

    @Override
    public SettingsModel readLastSettings() {
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.setShowFirst(binding.firstWave.isChecked());
        settingsModel.setShowSecond(binding.secondWave.isChecked());
        settingsModel.setShowThird(binding.thirdWave.isChecked());

        settingsModel.setFirstColor(binding.firstColor.getSelectedItemPosition());
        settingsModel.setSecondColor(binding.secondColor.getSelectedItemPosition());
        settingsModel.setThirdColor(binding.thirdColor.getSelectedItemPosition());
        return settingsModel;
    }

    @Override
    public void returnToMain() {
        onBackPressed();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public static <T>void fillTextSpinner(List<T> items, Spinner spinner) {
        ArrayAdapter<T> adapter = new ArrayAdapter<>(
                spinner.getContext(),
                android.R.layout.simple_spinner_item,
                items);

        spinner.setAdapter(adapter);
    }

}
