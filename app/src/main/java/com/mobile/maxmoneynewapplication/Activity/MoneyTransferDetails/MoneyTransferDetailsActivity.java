package com.mobile.maxmoneynewapplication.Activity.MoneyTransferDetails;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.FpxActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class MoneyTransferDetailsActivity extends AppCompatActivity {
    TextView textView_title,textView_fullname,textView_fullname_b,textView_email,textView_email_b,
            textView_transaction,textView_transaction_b,textView_relationship,textView_relationship_b,
            textView_review,textView_summary,textView_kiri_1,textView_kanan_1,textView_service_t,
            textView_service,textView_gst_t,textView_gst,textView_total,textView_totall,textView_title_payment,
            textView_chooseBank,textView_titlee;
    ArrayAdapter<String> spinnerNameAdapter;
    ArrayAdapter<String> spinnerBankAdapter;
    SearchableSpinner spinner_name;
    Spinner spinner_bank;
    PreferenceManagerLogin session;
    String country,foreign_money = "",malaysia_money = "",foreign_code = "",malaysia_currency = "",order_id = "",beneficiary_id = "",location_id = "";
    LinearLayout linear_after_continue,linear_after_continue2;
    Button button_continue,button_continue2,button_continue3;
    RadioGroup radio_group;
    String fpxSubtype = "";
    String bankCode = "";
    String bankNames = "";
    SimpleAdapter simpleAdapter;
    String service_fee = "";
    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer_details);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        Intent intent = getIntent();
        country = intent.getStringExtra("country");
        foreign_money = intent.getStringExtra("foreign_money");
        malaysia_money = intent.getStringExtra("malaysia_money");
        foreign_code = intent.getStringExtra("foreign_code");
        malaysia_currency = intent.getStringExtra("malaysia_currency");

        session = new PreferenceManagerLogin(getApplicationContext());

        textView_titlee = findViewById(R.id.textView_titlee);
        TypeFaceClass.setTypeFaceTextView(textView_titlee,getApplicationContext());

        textView_title = findViewById(R.id.textView_title);
        textView_fullname = findViewById(R.id.textView_fullname);
        textView_fullname_b = findViewById(R.id.textView_fullname_b);
        textView_email = findViewById(R.id.textView_email);
        textView_email_b = findViewById(R.id.textView_email_b);
        textView_transaction = findViewById(R.id.textView_transaction);
        textView_transaction_b = findViewById(R.id.textView_transaction_b);
        textView_relationship = findViewById(R.id.textView_relationship);
        textView_relationship_b = findViewById(R.id.textView_relationship_b);

        textView_review = findViewById(R.id.textView_review);
        textView_summary = findViewById(R.id.textView_summary);
        textView_kiri_1 = findViewById(R.id.textView_kiri_1);
        textView_kanan_1 = findViewById(R.id.textView_kanan_1);
        textView_service_t = findViewById(R.id.textView_service_t);
        textView_service = findViewById(R.id.textView_service);
        textView_gst_t = findViewById(R.id.textView_gst_t);
        textView_gst = findViewById(R.id.textView_gst);
        textView_total = findViewById(R.id.textView_total);
        textView_totall = findViewById(R.id.textView_totall);
        textView_title_payment = findViewById(R.id.textView_title_payment);
        textView_chooseBank = findViewById(R.id.textView_chooseBank);

        spinner_name = findViewById(R.id.spinner_name);
        spinner_bank = findViewById(R.id.spinner_bank);

        linear_after_continue = findViewById(R.id.linear_after_continue);
        linear_after_continue2 = findViewById(R.id.linear_after_continue2);

        button_continue = findViewById(R.id.button_continue);
        button_continue2 = findViewById(R.id.button_continue2);
        button_continue3 = findViewById(R.id.button_continue3);

        radio_group = findViewById(R.id.radio_group);

        linear_after_continue.setVisibility(View.INVISIBLE);
        linear_after_continue2.setVisibility(View.INVISIBLE);


        button_continue2.setVisibility(View.GONE);
        ChangeFontType();
        setSpinnerName();

        spinner_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

                StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries?country="+country,
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    ArrayList<String> list = new ArrayList<String>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject beneficiaryOBJ = jsonArray.getJSONObject(i);
                                        if(spinner_name.getSelectedItem().toString().equals(beneficiaryOBJ.getString("name"))){
                                            beneficiary_id = beneficiaryOBJ.getString("id");
                                            textView_fullname_b.setText(beneficiaryOBJ.getString("name"));
                                            textView_email_b.setText(beneficiaryOBJ.getString("email"));
                                            textView_relationship_b.setText(beneficiaryOBJ.getString("relationship"));

                                            if(beneficiaryOBJ.has("bankAccounts")){
                                                JSONObject account = new JSONObject(beneficiaryOBJ.getString("bankAccounts"));
                                                if(account.has("1")){
                                                    JSONObject oneOBJ = new JSONObject(account.getString("1"));
                                                    textView_transaction_b.setText("BANK DEPOSIT "+oneOBJ.getString("acctNo")+" "
                                                            +oneOBJ.getString("name"));
                                                    location_id = oneOBJ.getString("location");
                                                }
                                            }else {
                                                textView_transaction_b.setText("Cash Pick Up");
                                                getLocationMremmit();
                                            }

                                        }
                                    }

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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServiceFee();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linear_after_continue.setVisibility(View.VISIBLE);
                        TranslateAnimation animate = new TranslateAnimation(
                                0,                 // fromXDelta
                                0,                 // toXDelta
                                linear_after_continue.getHeight(),  // fromYDelta
                                0);                // toYDelta
                        animate.setDuration(500);
                        animate.setFillAfter(true);
                        linear_after_continue.startAnimation(animate);

                        textView_kiri_1.setText(foreign_code+" "+foreign_money+" @ "+malaysia_currency);
                        textView_kanan_1.setText("MYR "+malaysia_money);
                        textView_service.setText("MYR "+service_fee);


                        Double MALAYSIA_PAY = Double.parseDouble(malaysia_money);
                        Double TOTAL = MALAYSIA_PAY + Double.parseDouble(service_fee) + 0.0;
                        textView_totall.setText("MYR "+String.valueOf(TOTAL));

                        button_continue.setVisibility(View.GONE);
                        button_continue2.setVisibility(View.VISIBLE);
                    }
                }, 3000);


            }
        });

        button_continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
                getBeneficiary();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        putOrder();
                    }
                }, 2000);
                radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        View radioButton = radio_group.findViewById(checkedId);
                        int index = radio_group.indexOfChild(radioButton);
                        switch (index) {
                            case 0: // BANK PAYMENT
                                break;
                            case 1: // SCAN & PAY

                                break;
                        }
                    }
                });
            }
        });

        button_continue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardProgressDialog.show();
                submitOrder();
            }
        });

        spinner_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String seleted = spinner_bank.getSelectedItem().toString();
                Log.d("seleted",seleted);
                seleted = seleted.replace("{","");
                seleted = seleted.replace("}","");
                seleted = seleted.replace("hide=","");
                seleted = seleted.replace(",","");
                seleted = seleted.replace("thumbnail=","");

                int index = seleted.indexOf("21");

                char selected_new = seleted.charAt(0);
                Log.d("selected_new",String.valueOf(selected_new));
               /*bankNames = String.valueOf(index);

               seleted.substring(0,index);*/

                if(selected_new == '2'){
                    if(seleted.contains("Public Bank")){
                        int indexx = seleted.indexOf("P");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("maybank2e.net")){
                        int indexx = seleted.indexOf("m");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Bank Rakyat")){
                        int indexx = seleted.indexOf("B");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Alliance Bank")){
                        int indexx = seleted.indexOf("A");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("maybank2u.com")){
                        int indexx = seleted.indexOf("m");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Bank Islam")){
                        int indexx = seleted.indexOf("B");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Bank Muamalat")){
                        int indexx = seleted.indexOf("B");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Kuwait Finance House")){
                        int indexx = seleted.indexOf("K");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("AFFiN")){
                        int indexx = seleted.indexOf("A");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("RHB Bank")){
                        int indexx = seleted.indexOf("R");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("OCBC Bank")){
                        int indexx = seleted.indexOf("O");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("Standard Chartered Bank")){
                        int indexx = seleted.indexOf("S");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("HongLeong Bank")){
                        int indexx = seleted.indexOf("H");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("CIMB")){
                        int indexx = seleted.indexOf("C");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("AmBank")){
                        int indexx = seleted.indexOf("A");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("BSN Bank")){
                        int indexx = seleted.indexOf("B");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                    if(seleted.contains("HSBC Bank")){
                        int indexx = seleted.indexOf("H");
                        bankNames = seleted.substring(indexx).trim();
                        Log.d("banknames",bankNames);
                    }
                }else{
                    bankNames = seleted.substring(0,index).trim();
                    Log.d("banknames",bankNames);
                }




              /*  for(int i =0; i <seleted.length(); i++){
                    char aa = seleted.charAt(i);
                    if(aa == ','){
                        bankNames = seleted.substring(i+1).trim();
                        Log.d("banknames",bankNames);
                    }
                }*/

                StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id+"/payments",
                        new com.android.volley.Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject responseOBJ = new JSONObject(response);
                                    if(responseOBJ.has("paymentTypes")){
                                        JSONArray arrPay = new JSONArray(responseOBJ.getString("paymentTypes"));
                                        for(int i = 0; i<arrPay.length(); i++){
                                            JSONObject yeah = arrPay.getJSONObject(i);
                                            if(yeah.has("paymentSubTypes")){
                                                JSONArray pay = new JSONArray(yeah.getString("paymentSubTypes"));
                                                for(int ii =0; ii<pay.length(); ii++){
                                                    JSONObject yaw = pay.getJSONObject(ii);

                                                    if(yaw.getString("name").equals(bankNames)){
                                                        bankCode = yaw.getString("code");
                                                        fpxSubtype = yaw.getString("name");
                                                        Log.d("aa",bankCode);
                                                    }
                                                }
                                            }
                                        }
                                    }
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
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void getLocationMremmit(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/agents?provider=mremit",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseOBJ = new JSONObject(response);

                            JSONArray agentARR = new JSONArray(responseOBJ.getString("agents"));
                            for (int i =0; i<agentARR.length(); i++){
                                JSONObject agentOBJ = agentARR.getJSONObject(i);
                                if(agentOBJ.getString("country").toLowerCase().equals(country.toLowerCase())){
                                    JSONArray cOBJ = new JSONArray(agentOBJ.getString("C"));
                                    location_id = cOBJ.get(0).toString() + "_c";
                                }
                            }
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

    public void getBank(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id+"/payments",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseOBJ = new JSONObject(response);
                            if(responseOBJ.has("paymentTypes")){
                                ArrayList<String> list = new ArrayList<String>();

                                List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
                                JSONArray arrPay = new JSONArray(responseOBJ.getString("paymentTypes"));

                                for(int i = 0; i<arrPay.length(); i++){
                                    JSONObject yeah = arrPay.getJSONObject(i);
                                    if(yeah.has("paymentSubTypes")){
                                        JSONArray pay = new JSONArray(yeah.getString("paymentSubTypes"));


                                        for(int ii = 0; ii<pay.length(); ii++){
                                            JSONObject yeahs = pay.getJSONObject(ii);
                                            if(yeahs.getString("name").equals("Public Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.publicbank);
                                                map.put("hide", "Public Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("maybank2e.net")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.maybank2e);
                                                map.put("hide", "maybank2e.net");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Bank Rakyat")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.bankrakyat);
                                                map.put("hide", "Bank Rakyat");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Alliance Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.alliancebank);
                                                map.put("hide", "Alliance Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("maybank2u.com")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.maybank2u);
                                                map.put("hide", "maybank2u.com");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Bank Islam")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.bankislam);
                                                map.put("hide", "Bank Islam");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Bank Muamalat")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.bankmuamalat);
                                                map.put("hide", "Bank Muamalat");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Kuwait Finance House")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.kuwaitfinance);
                                                map.put("hide", "Kuwait Finance House");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("AFFiN")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.affin);
                                                map.put("hide", "AFFiN");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("RHB Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.rhb);
                                                map.put("hide", "RHB Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("OCBC Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.ocbc);
                                                map.put("hide", "OCBC Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("Standard Chartered Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.standard);
                                                map.put("hide", "Standard Chartered Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("HongLeong Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.hongleong);
                                                map.put("hide", "HongLeong Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("United Overseas Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.unitedoverseas);
                                                map.put("hide", "United Overseas Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("CIMB")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.cimbclick);
                                                map.put("hide", "CIMB Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("AmBank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.ambank);
                                                map.put("hide", "AmBank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("BSN Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.bsn);
                                                map.put("hide", "BSN Bank");
                                                data.add(map);
                                            }else if(yeahs.getString("name").equals("HSBC Bank")){
                                                Map<String,Object> map = new HashMap<String,Object>();
                                                map.put("thumbnail", R.drawable.hsbc);
                                                map.put("hide", "HSBC Bank");
                                                data.add(map);
                                            }

                                        }

                                    }
                                }
                                simpleAdapter = new SimpleAdapter(getApplicationContext(),data,R.layout.custom_textview_to_spinnerr,new String[] {"hide","thumbnail"},new int[] {R.id.hide,R.id.imageView});


                                spinner_bank.setAdapter(simpleAdapter);
                            }
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

    public void setSpinnerName(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries?country="+country,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject yeah = jsonArray.getJSONObject(i);
                                list.add(yeah.getString("name"));
                            }
                            spinnerNameAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview_to_spinner4r, list);
                            spinner_name.setTitle("Choose Beneficiary");
                            spinner_name.setAdapter(spinnerNameAdapter);
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

    private void ChangeFontType() {
        textView_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fullname.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fullname_b.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_email.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_email_b.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transaction.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_transaction_b.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_relationship.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_relationship_b.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_review.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_summary.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_kiri_1.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_kanan_1.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_service_t.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_service.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_gst_t.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_gst.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_total.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_totall.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_title_payment.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_chooseBank.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
    }

    @Override
    public void onBackPressed() {

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


    public void getServiceFee(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/boards/moos",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String foreignCODE = foreign_code.toLowerCase();
                        String codesForeign = foreignCODE+"_service_fee";
                        try {
                            JSONObject objectRESPONSE = new JSONObject(response);

                            if(objectRESPONSE.has(codesForeign)){
                                service_fee = objectRESPONSE.getString(codesForeign);
                                Log.d("service fee",service_fee);
                            }

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

    //step flow
    public void addOrder(){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseOBJ = new JSONObject(response);
                            if(responseOBJ.has("id")){
                                order_id = responseOBJ.getString("id");
                             //   Toast.makeText(getApplicationContext(),"order_id ="+order_id,Toast.LENGTH_SHORT).show();
                                linear_after_continue2.setVisibility(View.VISIBLE);
                                TranslateAnimation animate = new TranslateAnimation(
                                        0,                 // fromXDelta
                                        0,                 // toXDelta
                                        linear_after_continue.getHeight(),  // fromYDelta
                                        0);                // toYDelta
                                animate.setDuration(500);
                                animate.setFillAfter(true);
                                linear_after_continue2.startAnimation(animate);
                                button_continue2.setVisibility(View.GONE);
                                getBank();
                            }else{
                                Toast.makeText(getApplicationContext(),"something wrong. Please Try again later",Toast.LENGTH_SHORT).show();
                            }
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("product","omor");
                params.put("ccy",foreign_code.toLowerCase().trim());
                params.put("from",malaysia_money.toString());
                params.put("fee","false");
                params.put("channelType","APP");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    } //1step

    public void getBeneficiary(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/beneficiaries/"+beneficiary_id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

    public void putOrder(){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("location",location_id);
                params.put("beneficiary",beneficiary_id);
                params.put("beneficiaryBankAccount","0");
                params.put("beneficiaryRealTime","true");
                Log.d("haa",params.toString());
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void submitOrder(){
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id+"/submit",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                orderFinal();
                            }
                        }, 3000);


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

    public void orderFinal(){
        standardProgressDialog.dismiss();
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);

        String https = "https://paymence.io/app/curl.php?id="+order_id+"&paymentType=fpx&paymentSubType="+bankCode+"&bankName="+bankNames+"&Total=496&api_key="+token;
        Log.d("htts",https);
        StringRequest stringRequest = new StringRequest(POST, https,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String htmlData = response.toString();

                        htmlData = htmlData.replaceAll("&lt;", "<");
                        htmlData = htmlData.replaceAll("&gt;", ">");
                        htmlData = htmlData.replaceAll("&quot;", "\"");

                        Intent next = new Intent(getApplicationContext(),FpxActivity.class);
                        next.putExtra("fpx",htmlData.toString());
                        startActivity(next);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
