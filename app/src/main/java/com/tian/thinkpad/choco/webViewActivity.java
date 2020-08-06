package com.tian.thinkpad.choco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webViewActivity extends AppCompatActivity {

    private WebView mWebview;

    private static int account=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebview= (WebView) findViewById(R.id.wv_web);
        //使用JAvascript语言
        mWebview.getSettings().setJavaScriptEnabled(true);
        //使用app打开网页
        mWebview.setWebViewClient(new MyWebViewClient());
        //设置网页组件功能
        mWebview.setWebChromeClient(new MyWebChromeClient());
       //  mWebview.loadUrl("http://psy.yangtzeu.edu.cn/psym/SelfHelp/ArticleList.aspx");
         //设置链接
        Intent i=getIntent();
        String url=i.getStringExtra("a");
        mWebview.loadUrl(url);
    }
    class MyWebViewClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url) {

            //view.loadUrl(request.getUrl().toString());
           // return true;
            if(url == null) return false;

            try {
                if(url.startsWith("weixin://") //微信
                        || url.startsWith("alipays://") //支付宝
                        || url.startsWith("mailto://") //邮件
                        || url.startsWith("tel://")//电话
                        || url.startsWith("dianping://")//大众点评
                    //其他自定义的scheme
                ) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
            } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
            }

            //处理http和https开头的url
            view.loadUrl(url);
            return true;
        }
        //在进入页面前的操作，这里为弹出alert框口。
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if(account++ == 1){
                    mWebview.loadUrl("javascript:alert('为您友情链接载入菜谱')");
            }}
            //页面结束后发生的操作
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
       //更改页面标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }
//设置后退键为返回上一步的操作
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode==KeyEvent.KEYCODE_BACK &&mWebview.canGoBack())
            {
                mWebview.goBack();
                return true;
            }
         return super.onKeyDown(keyCode, event);
        }
}
