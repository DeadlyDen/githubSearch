package com.githubsearch.utils;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.List;

/**
 * Created by kravdi on 12/20/17.
 */

public class TextInputValidator {

    public  static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean validateFields(List<EditText> fields, List<TextInputLayout> textInputLayouts, List<String> massages){
        boolean isValid = false;
        for (EditText editText : fields) {
            int cIndex = fields.indexOf(editText);
            if (editText.getText().length() != 0) {
                textInputLayouts.get(cIndex).setError(null);
                isValid = true;
            } else {
                textInputLayouts.get(cIndex).setError(massages.get(cIndex));
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static boolean validateFields(EditText editText, TextInputLayout textInputLayout, String errorMsg ){
        if(editText.getText().length() == 0){
            textInputLayout.setError(errorMsg);
            return false;
        } else {
            textInputLayout.setError(null);
            return true;
        }
    }
}
