package com.example.m335_dylans_danielas_laniw;

import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FilterDialog {

    /**
     *
     * @param buttonGroup
     * @return
     */
    public String onClosed(RadioGroup buttonGroup){
        if(buttonGroup.getCheckedRadioButtonId() != -1){
//            Log.e("CheckedRadioButton", String.valueOf(buttonGroup.getCheckedRadioButtonId()));
            RadioButton button = buttonGroup.findViewById(buttonGroup.getCheckedRadioButtonId());
            button.setChecked(true);
            return String.valueOf(button.getId());
        } else {
//            Log.e("nothing checked", "ss");
        }
        return "";
    }

}
