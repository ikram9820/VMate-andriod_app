package com.example.vmate;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    public WebView v1 ;
    public SwipeRefreshLayout refreshWebView;
    private SearchView searchView;
    private LinearLayout itemBar;
    private ProgressBar pBar;

    public String surl,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surl=null;

        v1 = (WebView) findViewById(R.id.v1);
        pBar =(ProgressBar)findViewById(R.id.pBar);
        refreshWebView = (SwipeRefreshLayout)findViewById(R.id.s1) ;
        searchView = (SearchView)findViewById(R.id.searchView);
        itemBar =(LinearLayout)findViewById(R.id.bar);

        Toast.makeText(this,"youtube is loading",Toast.LENGTH_SHORT).show();
        setWebView("https://www.youtube.com");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                surl=query;
                if(surl==null)
                    return false;
                setWebView(surl);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                surl=newText;
                if(surl==null)
                    return false;
                return true;

            }
        });


    }//end onCreate()


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        v1.setOnScrollChangeListener(new AbsListView.OnScrollChangeListener(){
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if((scrollY+oldScrollY)>oldScrollY) {
                    itemBar.setVisibility(View.GONE);
                    searchView.setVisibility(View.GONE);
                }
                else if((scrollY+oldScrollY)>scrollY) {
                    itemBar.setVisibility(View.VISIBLE);
                    searchView.setVisibility(View.VISIBLE);
                }
                /*Toast.makeText(MainActivity.this," "+scrollX+" "+ scrollY+" "+ oldScrollX+" "+ oldScrollY,Toast.LENGTH_SHORT).show();*/
            }

        });
        return super.onTouchEvent(event);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void setWebView(String url){

        String search="https://www.google.com/search?q=";
        if(!(url.contains("https://")||url.contains("http://"))) {
            url = search + url;
            Toast.makeText(this, "web page is loading", Toast.LENGTH_SHORT).show();
        }


        v1.getSettings().setJavaScriptEnabled(true);
        v1.setWebViewClient(new WebViewClient());
        v1.loadUrl(url);
        v1.computeScroll();
        v1.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        refreshWebView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                v1.reload();
                refreshWebView.setRefreshing(false);

            }
        });

        v1.getSettings().setBuiltInZoomControls(true);
        v1.getSettings().setUseWideViewPort(true);
        v1.getSettings().setLoadWithOverviewMode(true);

        setProgressBarVisibilty(View.VISIBLE);
        v1.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setProgressBarVisibilty(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setProgressBarVisibilty(View.VISIBLE);
            }
        });


    }

    private void setProgressBarVisibilty(int visibilty){
        if(pBar!=null)
            pBar.setVisibility(visibilty);
    }

    @Override
    public void onBackPressed() {
        if(v1.canGoBack())
            v1.goBack();
        else
        super.onBackPressed();
    }

    public void instaUrl(View view) {
      Toast.makeText(this,"instagram is loading",Toast.LENGTH_SHORT).show();
        setWebView("https://www.instagram.com");
    }

    public void fbUrl(View view) {
        Toast.makeText(this,"facebook is loading",Toast.LENGTH_SHORT).show();
       setWebView("https://www.facebook.com");
    }

    public void twiterUrl(View view) {
        Toast.makeText(this,"twitter is loading",Toast.LENGTH_SHORT).show();
       setWebView("https://mobile.twitter.com/?lang=en");
    }

    public void ytUrl(View view) {
        Toast.makeText(this,"youtube is loading",Toast.LENGTH_SHORT).show();
       setWebView("https://www.youtube.com");
    }
}
