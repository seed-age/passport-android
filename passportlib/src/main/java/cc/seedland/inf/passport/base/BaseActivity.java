package cc.seedland.inf.passport.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

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

    private TextView titleTxv;

    /**
     * 创建Presenter实例
     * @return Presenter实例
     */
    protected abstract P createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.template_toolbar);
        initToolbar();
        initContent();

        presenter = createPresenter();
        if(presenter == null) {
            throw new NullPointerException("Presenter should not be null, please create an instance first!");
        }
        presenter.attach(this);

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.template_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        titleTxv = findViewById(R.id.template_title_txv);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private void initContent() {
        ViewStub contentStub = findViewById(R.id.template_content_stub);
        @LayoutRes final int layout = getLayoutResource();
        if(layout == 0) {
            throw new IllegalArgumentException("supply a valid layout resource please");
        }else {
            contentStub.setLayoutResource(layout);
            contentStub.inflate();
        }
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setTitle(String title) {
        titleTxv.setText(title);
    }

    protected void setToolbarDivider(boolean show) {
        Toolbar toolbar = findViewById(R.id.template_toolbar);
        LinearLayoutCompat parent = (LinearLayoutCompat) toolbar.getParent();
        parent.setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_NONE);

    }

    /**
     * 实际的布局资源
     * @return
     */
    protected abstract  @LayoutRes int getLayoutResource();

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

    @Override
    public void cancel(Bundle args, String raw) {
        Intent data = new Intent();
        data.putExtra(Constant.EXTRA_KEY_RESULT, args);
        data.putExtra(Constant.EXTRA_KEY_RAW_RESULT, raw);
        setResult(RESULT_CANCELED, data);
        finish();
    }
}
