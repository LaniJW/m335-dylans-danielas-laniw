package com.example.m335_dylans_danielas_laniw;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FilterDialog {

    public void onClosed(RadioGroup buttonGroup){
        if(buttonGroup.getCheckedRadioButtonId() != -1){
//            Log.e("CheckedRadioButton", String.valueOf(buttonGroup.getCheckedRadioButtonId()));
            RadioButton radioButton = buttonGroup.findViewById(buttonGroup.getCheckedRadioButtonId());
        } else {
//            Log.e("nothing checked", "ss");
        }
    }

}
