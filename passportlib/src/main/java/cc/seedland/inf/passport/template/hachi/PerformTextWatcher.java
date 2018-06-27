package cc.seedland.inf.passport.template.hachi;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.support.annotation.ColorInt;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cc.seedland.inf.passport.R;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/27 14:15
 * 描述 ：
 **/
public class PerformTextWatcher implements TextWatcher {

    private @ColorInt int colorDisable;
    private @ColorInt int colorEnable;
    private Button perform;
    private TextView send;
    private EditText[] edits;

    public PerformTextWatcher(Button perform, EditText... edits) {

        Resources resources = perform.getResources();
        colorDisable = resources.getColor(R.color.gray_deep_hachi);
        colorEnable = resources.getColor(R.color.red_hachi);
        this.perform = perform;
        this.edits = edits;
        perform.setEnabled(false);
        perform.getBackground().setColorFilter(colorDisable, PorterDuff.Mode.SRC_ATOP);
    }

    public PerformTextWatcher(Button perform, TextView send, EditText... edits) {
        this(perform, edits);
        this.send = send;
        send.setEnabled(false);
        send.getBackground().setColorFilter(colorDisable, PorterDuff.Mode.SRC_ATOP);
        send.setTextColor(colorDisable);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkFieldsForEmptyValues();
    }

    private void checkFieldsForEmptyValues(){

        if(isEmpty()){
            if(perform.isEnabled()) {
                perform.setEnabled(false);
                perform.getBackground().setColorFilter(colorDisable, PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            if(!perform.isEnabled()) {
                perform.setEnabled(true);
                perform.getBackground().setColorFilter(colorEnable, PorterDuff.Mode.SRC_ATOP);
            }
        }

        if(send != null) {
            if(canSend()) {
                if(!send.isEnabled()) {
                    send.setEnabled(true);
                    send.getBackground().setColorFilter(colorEnable, PorterDuff.Mode.SRC_ATOP);
                    send.setTextColor(colorEnable);
                }
            }else {
                if(send.isEnabled()) {
                    send.setEnabled(false);
                    send.getBackground().setColorFilter(colorDisable, PorterDuff.Mode.SRC_ATOP);
                    send.setTextColor(colorDisable);
                }
            }
        }

    }

    private boolean isEmpty() {
        for(EditText e : edits) {
            if(TextUtils.isEmpty(e.getText())) {
                return true;
            }
        }
        return false;
    }

    private boolean canSend() {
        if(edits != null && edits.length >= 2) {
            return !TextUtils.isEmpty(edits[0].getText()) && !TextUtils.isEmpty(edits[1].getText());
        }
        return false;
    }
}
