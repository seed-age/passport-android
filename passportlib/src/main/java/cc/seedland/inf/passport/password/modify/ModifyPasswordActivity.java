package cc.seedland.inf.passport.password.modify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.BaseActivity;

/**
 * Created by xuchunlei on 2017/11/16.
 */

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenterImpl> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password_modify);

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
    protected ModifyPasswordPresenterImpl createPresenter() {
        return new ModifyPasswordPresenterImpl();
    }
}
