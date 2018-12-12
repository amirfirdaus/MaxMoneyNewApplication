package com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mobile.maxmoneynewapplication.Activity.MenuActivity;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
import com.mobile.maxmoneynewapplication.R;

public class FpxActivity extends AppCompatActivity {

    WebView webView;
    String fpx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpx);


        Intent intent = getIntent();

        fpx = intent.getStringExtra("fpx");
        webView = findViewById(R.id.webView_web);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);

        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                String message = "SSL Certificate error.";
                switch (error.getPrimaryError()) {
                    case SslError.SSL_UNTRUSTED:
                        message = "The certificate authority is not trusted.";
                        break;
                    case SslError.SSL_EXPIRED:
                        message = "The certificate has expired.";
                        break;
                    case SslError.SSL_IDMISMATCH:
                        message = "The certificate Hostname mismatch.";
                        break;
                    case SslError.SSL_NOTYETVALID:
                        message = "The certificate is not yet valid.";
                        break;
                }
                message += " Do you want to continue anyway?";

                builder.setTitle("SSL Certificate Error");
                builder.setMessage(message);
                builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Here put your code for redirect

                Log.d("url",url);

                if(url.contains("https://www2.pbebank.com/") ||
                        url.contains("https://payment.bankrakyat.com.my") ||
                        url.contains("https://www.bankislam.biz") ||
                        url.contains("https://efpx.muamalat.com.my") ||
                        url.contains("https://rib.affinonline.com") ||
                        url.contains("https://www.hlbepay.com.my") ||
                        url.contains("https://www.cimbclicks.com.my/") /*sampai cimb je mls buat*/ ){

                }else {
                    Intent next = new Intent(getApplicationContext(), MenuActivity.class);
                    next.putExtra("current","nothing");
                    startActivity(next);
                }


                // return true; //Indicates WebView to NOT load the url;
                return false; //Allow WebView to load url
            }
        });
        webView.loadDataWithBaseURL(null, fpx, "text/html", "utf-8", null);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

    }


    @Override
    public void onBackPressed() {}
}

