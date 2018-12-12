package com.mobile.maxmoneynewapplication.Activity.TransactionHistoryTabs.ForeignDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
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

public class ForeignDetails extends AppCompatActivity {
    String orderId;
    TextView textView_orderInfo_title,
            textView_createAt_title,
            textView_orderAt_title,
            textView_itemOrder_title,
            textView_service_title,
            textView_gst_title,
            textView_total_view,
            textView_paymentInfo_title,
            textView_transactionId_title,
            textView_transactionDate_title,
            textView_payment_title,
            textView_paid_title,
            textView_status_title,
            textView_description_title;

    TextView textView_createAt,
            textView_orderAt,
            textView_itemOrder,
            textView_service,
            textView_gst,
            textView_total,
            textView_transactionId,
            textView_transactionDate,
            textView_payment,
            textView_paid,
            textView_status,
            textView_description;

    private PreferenceManagerLogin session;
    Button button_payment;

    String send_title,send_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_details);
        //set font action bar

        Typeface font2 = Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf");
        SpannableStringBuilder SS = new SpannableStringBuilder("Order Invoice");
        SS.setSpan (new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(SS);
        //set back button action bar

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");



        session = new PreferenceManagerLogin(getApplicationContext());
        declarerationTextView();
        button_payment = findViewById(R.id.button_payment);

        button_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                Intent next = new Intent(getApplicationContext(),PaymentGateAwayActivity.class);
                next.putExtra("order_id",orderId);
                next.putExtra("token",token);
                startActivity(next);
            }
        });
        ChangeFontType();
      getDetails(orderId);

    }

    private void declarerationTextView() {
        //textview

        textView_createAt = findViewById(R.id.textView_createAt);
        textView_orderAt = findViewById(R.id.textView_orderAt);
        textView_itemOrder = findViewById(R.id.textView_itemOrder);
        textView_service = findViewById(R.id.textView_service);
        textView_gst = findViewById(R.id.textView_gst);
        textView_total = findViewById(R.id.textView_total);
        textView_transactionId = findViewById(R.id.textView_transactionId);
        textView_transactionDate = findViewById(R.id.textView_transactionDate);
        textView_payment = findViewById(R.id.textView_payment);
        textView_paid = findViewById(R.id.textView_paid);
        textView_status = findViewById(R.id.textView_beneficiaryCountry);
        textView_description = findViewById(R.id.textView_description);
        //textview title
        textView_orderInfo_title = findViewById(R.id.textView_title_orderInformation);
        textView_createAt_title = findViewById(R.id.textView_createAt_title);
        textView_orderAt_title = findViewById(R.id.textView_orderAt_title);
        textView_itemOrder_title = findViewById(R.id.textView_itemOrder_title);
        textView_service_title = findViewById(R.id.textView_service_title);
        textView_gst_title = findViewById(R.id.textView_gst_title);
        textView_total_view = findViewById(R.id.textView_total_title);
        textView_paymentInfo_title = findViewById(R.id.textView_paymentInfo_title);
        textView_transactionId_title = findViewById(R.id.textView_transactionId_title);
        textView_transactionDate_title = findViewById(R.id.textView_transactionDate_title);
        textView_payment_title = findViewById(R.id.textView_payment_title);
        textView_paid_title = findViewById(R.id.textView_paid_title);
        textView_status_title = findViewById(R.id.textView_status_title);
        textView_description_title = findViewById(R.id.textView_description_title);
    }

    private void ChangeFontType() {
        button_payment.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_createAt.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_orderAt.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_itemOrder.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_service.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_gst.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_total.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionId.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionDate.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_payment.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_paid.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_status.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_description.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_orderInfo_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_createAt_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_orderAt_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_itemOrder_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_service_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_gst_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_total_view.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_paymentInfo_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionId_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transactionDate_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_payment_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_paid_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_status_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_description_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));

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

    public void getDetails(String orderId){

        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current/"+orderId+"",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject receiptsOBJ = new JSONObject(obj1.getString("receipts"));
                            JSONObject firstOBJ = new JSONObject(receiptsOBJ.getString("1"));


                            textView_createAt.setText(createDate(Long.parseLong(obj1.getString("created"))));
                            textView_orderAt.setText(createDate(Long.parseLong(obj1.getString("orderDate"))));


                            JSONArray itemARRAY = obj1.getJSONArray("items");

                            Log.d("objITEM1",itemARRAY.toString());

                            for(int i =0; i < itemARRAY.length(); i++){
                                JSONObject objITEM = itemARRAY.getJSONObject(i);

                                Log.d("objITEM",objITEM.toString());

                                textView_itemOrder.setText(objITEM.getString("ccy").toUpperCase()+" "+objITEM.getString("value")
                                        +" @ "+objITEM.getString("rate")+" = "
                                        +firstOBJ.getString("txnCurrency")+" "
                                        +firstOBJ.getString("transactionAmount"));

                                send_title = objITEM.getString("ccy").toUpperCase()+" "+objITEM.getString("value")
                                        +" @ "+objITEM.getString("rate");

                                send_total = firstOBJ.getString("txnCurrency")+" "+firstOBJ.getString("transactionAmount");
                            }
                            textView_service.setText("MYR "+obj1.getString("fee"));
                            textView_gst.setText("MYR "+obj1.getString("tax"));
                            textView_total.setText(firstOBJ.getString("txnCurrency")+" "+obj1.getString("total"));

                            textView_transactionId.setText(firstOBJ.getString("transactionId"));
                            textView_transactionDate.setText(createDate(Long.parseLong(firstOBJ.getString("updated"))));
                            textView_payment.setText(firstOBJ.getString("txnCurrency")+" "+firstOBJ.getString("transactionAmount"));
                            textView_paid.setText(firstOBJ.getString("bankName"));
                            textView_status.setText(firstOBJ.getString("status"));
                            textView_description.setText(firstOBJ.getString("statusDesc"));

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
                headers.put("api-key",token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public CharSequence createDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(d);
    }
}
