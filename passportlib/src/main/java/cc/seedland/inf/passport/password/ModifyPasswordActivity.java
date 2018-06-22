package cc.seedland.inf.passport.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportActivity;
import cc.seedland.inf.passport.widget.PasswordEditText;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class ModifyPasswordActivity extends PassportActivity<ModifyPasswordPresenter> implements View.OnClickListener {

    private PasswordEditText originEdt;
    private PasswordEditText currentEdt;
    private PasswordEditText confirmEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        originEdt = findViewById(R.id.password_modify_origin_edt);
        currentEdt = findViewById(R.id.password_modify_current_edt);
        confirmEdt = findViewById(R.id.password_modify_confirm_edt);
        findViewById(R.id.password_modify_perform_btn).setOnClickListener(this);

        setTitle(getString(R.string.password_modify_title));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_password_modify;
    }

    @Override
    protected ModifyPasswordPresenter createPresenter() {
        return new ModifyPasswordPresenter(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.password_modify_perform_btn) {
            String origin = originEdt.getText().toString();
            String current = currentEdt.getText().toString();
            String confirm = confirmEdt.getText().toString();
            presenter.performModify(origin, current, confirm);
        }
    }
}
