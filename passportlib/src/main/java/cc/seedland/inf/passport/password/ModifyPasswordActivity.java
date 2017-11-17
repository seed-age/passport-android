package cc.seedland.inf.passport.password;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;
import cc.seedland.inf.passport.base.IBaseView;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements View.OnClickListener, IBaseView {

    private EditText originEdt;
    private EditText currentEdt;
    private EditText confirmEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password_modify);

        originEdt = findViewById(R.id.password_modify_origin_edt);
        currentEdt = findViewById(R.id.password_modify_current_edt);
        confirmEdt = findViewById(R.id.password_modify_confirm_edt);
        findViewById(R.id.password_modify_perform_btn).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.password_modify_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView titleTxv = findViewById(R.id.password_modify_title_txv);
        titleTxv.setText(R.string.password_modify_title);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected ModifyPasswordPresenter createPresenter() {
        return new ModifyPasswordPresenter();
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
