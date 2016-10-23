package com.fhzc.app.android.android.ui.activity.fuhuaservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.base.platform.utils.android.ToastTool;
import com.fhzc.app.android.R;
import com.fhzc.app.android.android.base.BaseActivity;
import com.fhzc.app.android.android.ui.view.widget.CommonToolBar;
import com.fhzc.app.android.android.ui.view.widget.DJXWebView;
import com.fhzc.app.android.configer.UrlRes;
import com.fhzc.app.android.configer.constants.Constant;
import com.fhzc.app.android.configer.enums.HttpConfig;
import com.fhzc.app.android.controller.EventManager;
import com.fhzc.app.android.controller.ReportController;
import com.fhzc.app.android.db.UserPreference;
import com.fhzc.app.android.event.CancelCollectEvent;
import com.fhzc.app.android.models.ReportModel;
import com.fhzc.app.android.utils.android.IntentTools;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.CommonUtil;
import com.fhzc.app.android.utils.im.ImageLoader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 私行报告详情
 * Created by lenovo on 2016/7/11.
 */
public class FhReportDetailActivity extends BaseActivity implements View.OnClickListener, CommonToolBar.ClickRightListener {


    @Bind(R.id.reportDetailToolbar)
    CommonToolBar reportDetailToolbar;
    @Bind(R.id.signUpTextView)
    TextView signUpTextView;
    @Bind(R.id.bottomLayout)
    RelativeLayout bottomLayout;
    @Bind(R.id.webDetailLoading)
    ProgressBar webDetailLoading;
//    @Bind(R.id.reportwebview)
//    DJXWebView reportwebview;
    @Bind(R.id.reportwebview)
     WebView reportwebview;

    @Bind(R.id.reportTextView)
    TextView reportTextView;
    //@Bind(R.id.repostTimeText)
    TextView repostTimeText;
    @Bind(R.id.reportContextText)
    TextView reportContextText;
    @Bind(R.id.reportImageView)
    ImageView reportImageView;
    @Bind(R.id.textiamgeLayout)
    LinearLayout textiamgeLayout;

    @Bind(R.id.showimage)
    TextView showimage;
    @Bind(R.id.prdinaryLayout)
    LinearLayout prdinaryLayout;
//    @Bind(R.id.webview)
//    WebView webview;
    @Bind(R.id.ScrollViewLayoutsd)
    ScrollView ScrollViewLayoutsd;
    private int position;
    private int reportId;
    private ReportModel reportModel;
    private Handler mHandler;
    private Thread t;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        ButterKnife.bind(this);
        if (!UserPreference.isCustomer()) {
            bottomLayout.setVisibility(View.VISIBLE);
        } else {
            bottomLayout.setVisibility(View.GONE);
        }
//        initWebView(reportwebview);

        WebSettings webSettings = reportwebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setBlockNetworkLoads(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
//        reportwebview.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5MTI0OTMwNg==&mid=2651969862&idx=2&sn=9a830b3ac15450125e916dd99c642b84&chksm=bd5d1fb38a2a96a55df720dbbfb67cbe0b0eedc30a1ab919169dd06639caf7199b79b6e4192c&mpshare=1&scene=1&srcid=1011yWMLnVwg0ORVK0OzZRms&pass_ticket=7MQskFQzRpPs6awsCuMmxCXa10h6pVGW0NNk0MsN6p86PQbaqZjVflE37vZNztk6#rd");
        reportwebview.setWebViewClient(new webViewClient ());
        mHandler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 0x101) {
                    showimage.setText((Spanned) msg.obj);
                }
                return false;
            }
        });
    }
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
    private void initWebView(DJXWebView mWebView) {

//        WebSettings webSettings = webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setBuiltInZoomControls(true);
////        webview.loadUrl("https://mp.weixin.qq.com/s?__biz=MjM5MTI0OTMwNg==&mid=2651969837&idx=1&sn=ae1f55943848f67f736edab11ccc6e19&chksm=bd5d1fd88a2a96ce53f8273bae039a3de642614797aab9d2319ff6074fadad8157e717cd78ba&mpshare=1&scene=1&srcid=10095We5f6huRiey7TLziPVY&pass_ticket=%2FggrqfMh2NxN7j50imcEsZg1ethvGBNcihevRc9zWUxw5s7u%2Fnz5aatXYZPac1d5#rd");
//        webview.setWebViewClient(new webViewClient ());
//        ScrollViewLayoutsd.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                webview.onTouchEvent(event);
//                return false;
//            }
//        });
        webDetailLoading.setVisibility(View.INVISIBLE);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.getSettings().setUseWideViewPort(false);
//        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setDomStorageEnabled(true);
//        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.getSettings().setDatabaseEnabled(true);
//        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setBlockNetworkImage(true);
//        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
//        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
//        mWebView.getSettings().setAppCachePath(appCachePath);
        mWebView.getSettings().setAllowFileAccess(true);

//        mWebView.getSettings().setAppCacheEnabled(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setDatabasePath(CommonUtil.getWebStorageDirectory(this));
        }


        mWebView.setWebChromeClient(new DJXWebView.DJXWebChromeClient(mWebView) {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webDetailLoading.setSecondaryProgress(newProgress);
                if (newProgress == 100) {
                    webDetailLoading.setVisibility(View.INVISIBLE);
                } else {
                    webDetailLoading.setVisibility(View.VISIBLE);
                }
            }


            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                com.base.platform.utils.android.Logger.e("webview receive error");
                webDetailLoading.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webDetailLoading.setVisibility(View.VISIBLE);
                view.loadUrl("javascript:if(window.MCSBridge == undefined) {window.MCSBridge={};}");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webDetailLoading.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void initEvent() {
        reportDetailToolbar.setClickRightListener(this);
        signUpTextView.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        position = getIntent().getIntExtra("position", -1);
        reportId = getIntent().getIntExtra("reportId", -1);
        if (reportId == -1)
            return;
        ReportController.getInstance().focusStatus(this, reportId);
        ReportController.getInstance().reportDetail(this, reportId);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.report_detail_activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpTextView:
                IntentTools.startClientList(FhReportDetailActivity.this,"report",reportId);
                break;
        }
    }

    @Override
    public void onData(Serializable result, int flag, boolean fromNet, Object o) {
        if (flag == HttpConfig.focusStatus.getType()) {
            int status = (int) result;
            reportDetailToolbar.setCollect(status != 0);
//            if (status == 0) {
//                reportDetailToolbar.setCollect(false);
//            } else {
//                reportDetailToolbar.setCollect(true);
//            }
        }
        if (flag == HttpConfig.reportDetail.getType()){
            reportModel = (ReportModel)result;
            Logger.e("result"+reportModel.getUrl());
            if(reportModel.getDesc().equals("")){
                Logger.e("resulwewqret"+reportModel.getUrl());
                showimage.setVisibility(View.GONE);
//                webview.setVisibility(View.GONE);
                prdinaryLayout.setVisibility(View.VISIBLE);
                if(reportModel.getUrl().contains("http://")||reportModel.getUrl().contains("https://")){
//                    webview.setVisibility(View.VISIBLE);
                    Logger.e("result"+reportModel.getUrl());
                    reportwebview.setVisibility(View.VISIBLE);
                    textiamgeLayout.setVisibility(View.GONE);
//                    reportwebview.loadUrl(reportModel.getUrl().replace("<p>","").replace("</p>",""));
                    reportwebview.loadUrl(reportModel.getUrl());
                }else{
                    Logger.e("result"+reportModel.getSummary());
                    reportwebview.setVisibility(View.GONE);
                    textiamgeLayout.setVisibility(View.VISIBLE);
                    reportTextView.setText(reportModel.getName());
                    ImageLoader.getInstance(this, R.drawable.default_error_long).displayImage(reportModel.getCover(), reportImageView);
                    reportContextText.setText(reportModel.getSummary());
                }
            }else{
                webDetailLoading.setVisibility(View.GONE);
                reportwebview.setVisibility(View.GONE);
                prdinaryLayout.setVisibility(View.GONE);
                showimage.setVisibility(View.VISIBLE);
                try{
//                    Spanned spanned = Html.fromHtml(reportModel.getDesc(), new MyImageGetter(this,
//                            showimage), null);
//                    showimage.setText(spanned);
//                    showimage.setMovementMethod(LinkMovementMethod.getInstance());

                    t=new Thread(new Runnable() {
                        Message msg = Message.obtain();
                            @Override
                            public void run() {
                                Html.ImageGetter imageGetter=new Html.ImageGetter() {
                                    @Override
                                    public Drawable getDrawable(String source) {
                                        WindowManager wm = FhReportDetailActivity.this.getWindowManager();
                                        int width = wm.getDefaultDisplay().getWidth();
                                        int height = wm.getDefaultDisplay().getHeight();
                                        Drawable drawable =getImageFromNetwork(source);
                                        drawable.setBounds(0,0,width,drawable.getIntrinsicHeight()*4);
                                        return drawable;
                                    }
                                };
                                Spanned spanned= Html.fromHtml(reportModel.getDesc(), imageGetter, null);
                                msg.what = 0x101;
                                msg.obj = spanned;
                                mHandler.sendMessage(msg);
                        }
                    });
                    t.start();
                }catch (Exception e){
                    Logger.e("e.getmessasge"+e.getMessage());
                }
            }
        }
        if (flag == HttpConfig.focus.getType()) {
            if (reportDetailToolbar.isCollect()) {
                reportDetailToolbar.setCollect(false);
                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_REPORT, position, false, null));
                ToastTool.show("取消关注");
            } else {
                reportDetailToolbar.setCollect(true);

                EventManager.getInstance().post(new CancelCollectEvent(Constant.COLLECTTYPE_ACTIVITY, position, true, reportModel));
                ToastTool.show("已关注");
            }
        }
    }
    Drawable getImageFromNetwork(String imageUrl) {
        URL myFileUrl = null;
        Drawable drawable = null;
        try {
            if(imageUrl.contains("/opt/fhzc/system")){
                myFileUrl = new URL(UrlRes.getInstance().getPictureUrl()+imageUrl);
            }else{
                myFileUrl = new URL(imageUrl);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            drawable = Drawable.createFromStream(is, null);
            if(is!=null){
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }
    @Override
    public void OnClickRight(View view) {
        if (reportId == -1) {
            return;
        }
        if (reportDetailToolbar.isCollect()) {
            ReportController.getInstance().cancelFocusReport(this, reportId);
        } else {
            ReportController.getInstance().focusReport(this, reportId);
        }
    }
    public class MyImageGetter implements Html.ImageGetter {

        private Context context;
        private TextView tv;

        public MyImageGetter(Context context, TextView tv) {
            this.context = context;
            this.tv = tv;
        }

        @Override
        public Drawable getDrawable(String source) {
            // TODO Auto-generated method stub
            // 将source进行MD5加密并保存至本地
            String imageName = source;
            String sdcardPath = Environment.getExternalStorageDirectory()
                    .toString(); // 获取SDCARD的路径
            // 获取图片后缀名
            String[] ss = source.split("\\.");
            String ext = ss[ss.length - 1];

            // 最终图片保持的地址
            String savePath = sdcardPath + "/" + context.getPackageName() + "/"
                    + imageName + "." + ext;

            File file = new File(savePath);
            if (file.exists()) {
                WindowManager wm = FhReportDetailActivity.this.getWindowManager();

                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                // 如果文件已经存在，直接返回
                Drawable drawable = Drawable.createFromPath(savePath);
                drawable.setBounds(0, 0, width,
                        drawable.getIntrinsicHeight()*5);
                return drawable;
            }

            // 不存在文件时返回默认图片，并异步加载网络图片
            Resources res = context.getResources();
            URLDrawable drawable = new URLDrawable(
                    res.getDrawable(R.drawable.default_pictures));
            new ImageAsync(drawable).execute(savePath, source);
            return drawable;

        }

        private class ImageAsync extends AsyncTask<String, Integer, Drawable> {

            private URLDrawable drawable;

            public ImageAsync(URLDrawable drawable) {
                this.drawable = drawable;
            }

            @Override
            protected Drawable doInBackground(String... params) {
                // TODO Auto-generated method stub
                String savePath = params[0];
                String url = params[1];
                url= UrlRes.getInstance().getPictureUrl()+url;
                InputStream in = null;
                try {
                    // 获取网络图片
                    HttpGet http = new HttpGet(url);
                    HttpClient client = new DefaultHttpClient();
                    HttpResponse response = (HttpResponse) client.execute(http);
                    BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(
                            response.getEntity());
                    in = bufferedHttpEntity.getContent();

                } catch (Exception e) {
                    try {
                        if (in != null)
                            in.close();
                    } catch (Exception e2) {
                        // TODO: handle exception
                    }
                }

                if (in == null)
                    return drawable;

                try {
                    File file = new File(savePath);
                    String basePath = file.getParent();
                    File basePathFile = new File(basePath);
                    if (!basePathFile.exists()) {
                        basePathFile.mkdirs();
                    }
                    file.createNewFile();
                    FileOutputStream fileout = new FileOutputStream(file);
                    byte[] buffer = new byte[4 * 1024];
                    while (in.read(buffer) != -1) {
                        fileout.write(buffer);
                    }
                    fileout.flush();

                    Drawable mDrawable = Drawable.createFromPath(savePath);
                    return mDrawable;
                } catch (Exception e) {
                    // TODO: handle exception
                }
                return drawable;
            }

            @Override
            protected void onPostExecute(Drawable result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                if (result != null) {
                    drawable.setDrawable(result);
                    tv.setText(tv.getText()); // 通过这里的重新设置 TextView 的文字来更新UI
                }
            }

        }

        public class URLDrawable extends BitmapDrawable {

            private Drawable drawable;

            public URLDrawable(Drawable defaultDraw) {
                setDrawable(defaultDraw);
            }

            private void setDrawable(Drawable nDrawable) {
                drawable = nDrawable;
                WindowManager wm = FhReportDetailActivity.this.getWindowManager();

                int width = wm.getDefaultDisplay().getWidth();
                int height = wm.getDefaultDisplay().getHeight();
                drawable.setBounds(0, 0, width,
                        drawable.getIntrinsicHeight()*5);
                setBounds(0, 0, width,
                        drawable.getIntrinsicHeight()*5);
            }

            @Override
            public void draw(Canvas canvas) {
                // TODO Auto-generated method stub
                drawable.draw(canvas);
            }

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && reportwebview.canGoBack()) {
            reportwebview.goBack();
            return true;
        }
        finish();
        return false;
    }

}
