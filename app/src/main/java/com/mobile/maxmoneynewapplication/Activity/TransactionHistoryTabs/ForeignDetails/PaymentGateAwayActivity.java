package com.mobile.maxmoneynewapplication.Activity.TransactionHistoryTabs.ForeignDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.mobile.maxmoneynewapplication.Activity.TransactionHistory;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Activity.MainActivity;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;

public class PaymentGateAwayActivity extends AppCompatActivity {
    WebView webView;
    String order_id,token;
    private MenuItem item;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.done_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


     @Override
     public boolean onOptionsItemSelected(MenuItem item)

     {
         switch (item.getItemId())
         {
             case R.id.done:
                 Intent next = new Intent(getApplicationContext(), TransactionHistory.class);
                 startActivity(next);
                 default:
                     return super.onOptionsItemSelected(item);

         }
     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gate_away);
        //set font action bar
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Make Payment");
        SS.setSpan (new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //set back button action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);

        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        token = intent.getStringExtra("token");


        Log.d("PaymentGateAwayActivity",order_id);
        Log.d("PaymentGateAwayActivity",token);

        webView = findViewById(R.id.webView_web);


        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://www.maxmoney.com/my/app/android/secure-payment?oid="+order_id+"&token="+token);


        }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
