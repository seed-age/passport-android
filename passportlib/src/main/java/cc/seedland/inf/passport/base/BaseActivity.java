package cc.seedland.inf.passport.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.util.Constant;
import cc.seedland.inf.passport.widget.LoadingDialog;

/**
 * 活动基类
 * Created by xuchunlei on 2017/11/8.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView{

    protected P presenter;

    private LoadingDialog loadingDlg;

    /**
     * 创建Presenter实例
     * @return Presenter实例
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = createPresenter();
        if(presenter == null) {
            throw new NullPointerException("Presenter should not be null, please create an instance first!");
        }
        presenter.attach(this);

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
}
