package cc.seedland.inf.passport.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import cc.seedland.inf.corework.mvp.BaseFragment;
import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.template.TemplateFactory;
import cc.seedland.inf.passport.util.CompatUtil;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.util.ToastUtil;
import cc.seedland.inf.passport.widget.LoadingDialog;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/22 09:58
 * 描述 ：
 **/
public abstract class PassportFragment<A extends IViewAgent, P extends BasePresenter> extends BaseFragment<P> implements IPassportView {

    protected A agent = TemplateFactory.getTemplate().createAgent(getClass().getName());
    private LoadingDialog loadingDlg;

    @Override
    protected int getLayoutId() {
        return agent.layout();
    }

    @Override
    protected void initViews(View v) {
        agent.initViews(v);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isHidden()) {
            agent.onShow();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            agent.onShow();
        }
    }

    @Override
    public void showToast(String message) {
        super.showToast(message);
        if(isAdded()) {
            ToastUtil.show(message, Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void showTip(String tip) {
        if(isAdded()) {
            ToastUtil.show(tip, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void showLoading() {
        if(isAdded()) {
            hideLoading(); // 关闭已经打开的对话框
            loadingDlg = new LoadingDialog(getContext());
            loadingDlg.show();
        }
    }

    @Override
    public void hideLoading() {
        if(loadingDlg != null && loadingDlg.isShowing()) {
            loadingDlg.dismiss();
            loadingDlg = null;
        }
    }

    @Override
    public void close(Bundle args, String raw) {
        if(isAdded()) {
            Intent data = new Intent();
            data.putExtra(Constant.EXTRA_KEY_RESULT, args);
            data.putExtra(Constant.EXTRA_KEY_RAW_RESULT, raw);
            getActivity().setResult(Activity.RESULT_OK, data);
            getActivity().finish();
        }
    }

    @Override
    public void showError(int code, String msg) {
        // TODO: 2018/6/14 unused here
    }

}
