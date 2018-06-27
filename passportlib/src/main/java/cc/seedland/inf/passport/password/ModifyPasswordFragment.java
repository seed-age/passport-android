package cc.seedland.inf.passport.password;

import android.view.View;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.base.PassportFragment;
import cc.seedland.inf.passport.template.ModifyPasswordViewAgent;
import cc.seedland.inf.passport.widget.PasswordOEditText;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 17:21
 * 描述 ：
 **/
public class ModifyPasswordFragment extends PassportFragment<ModifyPasswordViewAgent, ModifyPasswordPresenter> implements View.OnClickListener  {

    private PasswordOEditText originEdt;
    private PasswordOEditText currentEdt;
    private PasswordOEditText confirmEdt;

    @Override
    protected void initViews(View v) {
        super.initViews(v);

        originEdt = v.findViewById(R.id.password_modify_origin_edt);
        currentEdt = v.findViewById(R.id.password_modify_current_edt);
        confirmEdt = v.findViewById(R.id.password_modify_confirm_edt);
        v.findViewById(R.id.password_modify_perform_btn).setOnClickListener(this);
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
