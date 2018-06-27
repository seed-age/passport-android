package cc.seedland.inf.passport.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import cc.seedland.inf.passport.R;

/**
 * Created by xuchunlei on 2017/11/17.
 */

public class PasswordOEditText extends LinearLayoutCompat {

    private EditText edit;
    private CheckBox check;

    public PasswordOEditText(Context context) {
        this(context, null);
    }

    public PasswordOEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordOEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setFocusable(true);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        ContextThemeWrapper wrapper = new ContextThemeWrapper(context, R.style.PasswordEdit);
        edit = new EditText(wrapper);
        edit.setBackground(null);
        edit.setPadding(0, 0, 0, 0);
        check = new CheckBox(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PasswordOEditText);
        if(a.hasValue(R.styleable.PasswordOEditText_hint)) {
            final String hint = a.getString(R.styleable.PasswordOEditText_hint);
            edit.setHint(hint);
        }
        if(a.hasValue(R.styleable.PasswordOEditText_button)) {
            final Drawable buttonDrawable = a.getDrawable(R.styleable.PasswordOEditText_button);
            check.setButtonDrawable(buttonDrawable);
        }
        a.recycle();

        LayoutParams paramsEdit = new LayoutParams(0, LayoutParams.WRAP_CONTENT);
        paramsEdit.gravity = Gravity.BOTTOM;
        paramsEdit.weight = 1;
        addView(edit, paramsEdit);

        LayoutParams paramsCheck = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        paramsCheck.gravity = Gravity.BOTTOM;
        addView(check, paramsCheck);

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    public Editable getText() {
        return edit.getText();
    }
}
