package cc.seedland.inf.passport.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import cc.seedland.inf.corework.mvp.BaseActivity;
import cc.seedland.inf.corework.mvp.BasePresenter;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.template.TemplateFactory;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.widget.LoadingDialog;

/**
 * 活动基类
 * Created by xuchunlei on 2017/11/8.
 */

public abstract class PassportOActivity<A extends IViewAgent, P extends BasePresenter> extends BaseActivity<P> implements IPassportView {

    protected A agent;

    private LoadingDialog loadingDlg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(presenter == null) {
            throw new NullPointerException("Presenter should not be null, please create an instance first!");
        }

    }

    @Override
    protected int getLayoutId() {
        return agent.layout();
    }

    @Override
    protected void initViews() {
        agent.initViews(getWindow().getDecorView());
    }

    @Override
    public void showTip(String tip) {
        Toast.makeText(this, tip, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        hideLoading(); // 关闭已经打开的对话框
        loadingDlg = new LoadingDialog(this);
        loadingDlg.show();
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
        Intent data = new Intent();
        data.putExtra(Constant.EXTRA_KEY_RESULT, args);
        data.putExtra(Constant.EXTRA_KEY_RAW_RESULT, raw);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void showError(int code, String msg) {
        // TODO: 2018/6/14 unused here
    }
}
