package com.mobile.maxmoneynewapplication.Activity.SummaryTabs.ForeignInvoice;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class ForeignInvoiceDetails extends AppCompatActivity

{
    String orderId = "";
    String sessionMax = "";
    TextView textView_title_invoice,
            textView_summary_title,
            textView_order_summary_foreign,
            textView_item_foreign_title,
            textView_service_title_foreignfee,
            textView_gst_summary_foreign,
            textView_total_summary_title,
            textView_summary_payment_info_title,
            textView_summary_transactionId_title,
            textView_transactionDate_title_summary,
            textView_summary_payment_title,
            textView_paid_summary_title,
            textView_status_summary_title,
            textView_description_title_summary;


    TextView
            textView_date,
            textView_foreign_summary_view,
            textView_item_foreignOrder,
            textView_fee_foreign,
            textView_gst_fee,
            textView_total_summary,
            textView_summary_transactionId,
            textView_summary_transactionDate,
            textView_summary_payment,
            textView_paid_summary,
            textView_beneficiaryCountry_summary,
            textView_description_summary;

    private PreferenceManagerLogin session;
    String send_title, send_total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_foreign_invoice);
        Typeface font2 = Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Summary Order Invoice");
        SS.setSpan(new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);

        //set back button action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent next = getIntent();
        orderId = next.getStringExtra("orderId");
        session = new PreferenceManagerLogin(getApplicationContext());
        declareAllViews();
        Toast.makeText(getApplicationContext(), "order id =" + orderId, Toast.LENGTH_SHORT).show();

        fontStyle();
        loginAGENT();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                getAmirPower(orderId);
            }
        }, 2000);


    }

    private void declareAllViews() {
        //title
        textView_title_invoice = findViewById(R.id.textView_title_Invoice);
        textView_summary_title = findViewById(R.id.textView_summary_title);
        textView_order_summary_foreign = findViewById(R.id.textView_order_summary_foreign);
        textView_item_foreign_title = findViewById(R.id.textView_item_foreign_title);
        textView_service_title_foreignfee = findViewById(R.id.textView_service_title_foreignfee);
        textView_gst_summary_foreign = findViewById(R.id.textView_gst_summary_foreign);
        textView_total_summary_title = findViewById(R.id.textView_total_summary_title);
        textView_summary_payment_info_title = findViewById(R.id.textView_summary_paymentInfo_title);
        textView_summary_transactionId_title = findViewById(R.id.textView_summary_transactionId_title);
        textView_transactionDate_title_summary = findViewById(R.id.textView_transactionDate_title_summary);
        textView_summary_payment_title = findViewById(R.id.textView_summary_payment_title);
        textView_paid_summary_title = findViewById(R.id.textView_paid_summary_title);
        textView_status_summary_title = findViewById(R.id.textView_status_summary_title);
        textView_description_title_summary = findViewById(R.id.textView_description_title_summary);
        //figures
        textView_date = findViewById(R.id.textView_date_summary);
        textView_foreign_summary_view = findViewById(R.id.textView_foreign_summary_order);
        textView_item_foreignOrder = findViewById(R.id.textView_item_foreignOrder);
        textView_fee_foreign = findViewById(R.id.textView_fee_foreign);
        textView_gst_fee = findViewById(R.id.textView_gst_fee);
        textView_total_summary = findViewById(R.id.textView_total_summary);
        textView_summary_transactionId = findViewById(R.id.textView_summary_transactionId);
        textView_summary_transactionDate = findViewById(R.id.textView_summary_transactionDate);
        textView_summary_payment = findViewById(R.id.textView_summary_payment);
        textView_paid_summary = findViewById(R.id.textView_paid_summary);
        textView_beneficiaryCountry_summary = findViewById(R.id.textView_beneficiaryCountry_summary);
        textView_description_summary = findViewById(R.id.textView_description_summary);


    }

    private void fontStyle() {
        textView_title_invoice.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_order_summary_foreign.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_item_foreign_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_service_title_foreignfee.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_total_summary_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_payment_info_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_transactionId_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionDate_title_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_payment_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_paid_summary_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_status_summary_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_description_title_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_date.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_foreign_summary_view.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_item_foreignOrder.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fee_foreign.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_gst_fee.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_total_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_transactionId.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_transactionDate.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionDate_title_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary_payment.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_paid_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_beneficiaryCountry_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_description_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getAmirPower(String orderId) {
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/"+orderId+"",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject receiptsOBJ = new JSONObject(obj1.getString("receipts"));
                            JSONObject firstOBJ = new JSONObject(receiptsOBJ.getString("1"));


                            textView_date.setText(createTimeStamp(Long.parseLong(obj1.getString("created"))));
                            textView_foreign_summary_view.setText(createTimeStamp(Long.parseLong(obj1.getString("orderDate"))));


                            JSONArray itemARRAY = obj1.getJSONArray("items");
                            Log.d("objITEM1",itemARRAY.toString());
                            for(int i =0; i < itemARRAY.length(); i++){
                                JSONObject objITEM = itemARRAY.getJSONObject(i);
                                Log.d("objITEM",objITEM.toString());
                                textView_item_foreignOrder.setText(objITEM.getString("ccy").toUpperCase()+" "+objITEM.getString("value")
                                        +" @ "+objITEM.getString("rate")+" = "
                                        +firstOBJ.getString("txnCurrency")+" "
                                        +firstOBJ.getString("transactionAmount"));

                                Log.d("tag", objITEM.toString(4));                                send_title = objITEM.getString("ccy").toUpperCase()+" "+objITEM.getString("value")
                                        +" @ "+objITEM.getString("rate");

                                send_total = firstOBJ.getString("txnCurrency")+" "+firstOBJ.getString("transactionAmount");
                            }
                            textView_fee_foreign.setText("MYR "+obj1.getString("fee"));
                            textView_gst_fee.setText("MYR "+obj1.getString("tax"));
                            textView_total_summary.setText(firstOBJ.getString("txnCurrency")+" "+obj1.getString("total"));

                            textView_summary_transactionId.setText(firstOBJ.getString("transactionId"));
                            textView_summary_transactionDate.setText(createTimeStamp(Long.parseLong(firstOBJ.getString("updated"))));
                            textView_summary_payment.setText(firstOBJ.getString("txnCurrency")+" "+firstOBJ.getString("transactionAmount"));
                            textView_paid_summary.setText(firstOBJ.getString("bankName"));
                            textView_beneficiaryCountry_summary.setText(firstOBJ.getString("status"));
                            textView_description_summary.setText(firstOBJ.getString("statusDesc"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",sessionMax);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void loginAGENT(){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/sessions/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.has("code")){
                            }
                            if(objectResponse.has("session")){
                                sessionMax = objectResponse.getString("session");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username","sa@maxmoney.com");
                params.put("password","MaxMoney@2016");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public CharSequence createTimeStamp(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(d);
    }
}
