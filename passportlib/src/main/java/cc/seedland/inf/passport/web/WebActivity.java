package cc.seedland.inf.passport.web;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import cc.seedland.inf.corework.mvp.BaseActivity;
import cc.seedland.inf.passport.R;
import cc.seedland.inf.passport.stat.PassportStatAgent;
import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.TemplateFactory;
import cc.seedland.inf.passport.widget.ProgressStripView;

/**
 * <pre>
 * 作者：徐春蕾
 * 联系方式：xuchunlei@seedland.cc / QQ:22003950
 * 时间：2018/03/15
 * 描述：
 * </pre>
 */

public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View{

    public static final String EXTRA_KEY_TITLE = "title";
    public static final String EXTRA_KEY_URL = "url";

    protected ITemplate template = TemplateFactory.getTemplate();

    private WebView web;
    private TextView titleV;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(template.createTheme());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initViews() {
        super.initViews();

        template.initView(this);

        web = findViewById(R.id.web_wbv_content);

        /* ------------ web settings - begin -------------*/
        WebSettings settings = web.getSettings();

        // 支持Javascript交互
        settings.setJavaScriptEnabled(true);

        // 自适应屏幕
        settings.setUseWideViewPort(true);          // 将图片调整到适合WebView的大小
        settings.setLoadWithOverviewMode(true);     // 缩放至屏幕大小

        // 支持缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);      // 设置内置的缩放控件
        settings.setDisplayZoomControls(false);     // 隐藏原生的缩放控件

        // 其他
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        settings.setAllowFileAccess(true);                                // 设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true);          // 支持通过JavaScript打开新窗口
        settings.setLoadsImagesAutomatically(true);                       // 支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");                     // 设置编码格式

        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        String cacheDir = getFilesDir().getAbsolutePath() + "/Seed";
        settings.setAppCachePath(cacheDir);

        /* ------------ web settings - end ------------ */

        titleV = findViewById(R.id.title_txv);
        final String defTitle = getIntent().getStringExtra(EXTRA_KEY_TITLE);
        final ProgressStripView progress = findViewById(R.id.web_psv_progress);
        web.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 在WebView内部打开，而不是调用浏览器
                view.loadUrl(url);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
//                presenter.handleError(errorResponse.getStatusCode(), errorResponse.getReasonPhrase());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                presenter.loadTitle(view.getTitle(), defTitle);
                progress.hideGently();
            }
        });

        // 页面内容
        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progress.setProgress(newProgress);
            }
        });


        url = getIntent().getStringExtra(EXTRA_KEY_URL);
        presenter.loadPage(url);
    }

    @Override
    protected WebPresenter createPresenter() {
        return new WebPresenter(this);
    }

    @Override
    public void showPage(String url) {
        web.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PassportStatAgent.get().onHtmlPage(url);
    }

    @Override
    public void switchTitle(String title) {
        titleV.setText(title);
    }

    @Override
    public void close(String cause) {
        showToast(cause);
        if(web.canGoBack()) {
            web.goBack();
        }else {
            finish();
        }
    }

    @Override
    public void showError(int code, String msg) {

    }
}
