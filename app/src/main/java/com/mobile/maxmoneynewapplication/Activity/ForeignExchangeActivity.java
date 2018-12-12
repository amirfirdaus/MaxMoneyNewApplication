package com.mobile.maxmoneynewapplication.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.ForeignExchangeDetails;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.ForeignMapsActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.ForeignExchangeMapsLatest;
import com.mobile.maxmoneynewapplication.Model.CountryFirstModel;
import com.mobile.maxmoneynewapplication.Model.CountrySecondModel;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter2;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ForeignExchangeActivity extends AppCompatActivity  implements View.OnClickListener,OnMapReadyCallback {
    boolean status_ontype_send = true;
    boolean status_ontype_receive = false;
    TextView textView_gets,textView_convert_myr,textView_minimum_denomination;
    EditText editText_send,editText_recieve;
    int country_code;
    double country_unit;
    double country_tt;
    Spinner spinner,spinner_2;
    private SpinnerAdapter spinnerAdapter;
    private SpinnerAdapter2 spinnerAdapter2;
    private List<CountryFirstModel> countryFirstModelList = new ArrayList<>();
    private List<CountrySecondModel> countrySecondModelList = new ArrayList<>();
    PreferenceManagerLogin session;
    Boolean statusClickEdittext = true;
    TextView t9_key_1,t9_key_2,t9_key_3,t9_key_4,t9_key_5,t9_key_6,t9_key_7,t9_key_8,t9_key_9,t9_key_0,t9_key_backspace,t9_key_clear;
    Button button_transfer;
    Menu menus;
    private int cartCount = 0;
    LinkedList<String> malaysiaValue = new LinkedList<>();
    LinkedList<String> foreignValue = new LinkedList<>();
    String spinnerTitle = "";
    String denomination = "";
    Double denomination_calculation_myr;

    String myr_value1 ="", myr_value2 = "",myr_value3 ="",myr_value4 ="", myr_value5 ="";
    String foreign_value1 ="",foreign_value2 = "",foreign_value3 ="",foreign_value4 ="", foreign_value5 ="";

    double total_pay = 0.0;

    DecimalFormat formatTotalPay = new DecimalFormat("#,###,###.##");

    ImageView imageView_icon_menu;
    private GoogleMap mMap;
    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_exchange);

        NukeSSLCerts.nuke();

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        malaysiaValue.clear();
        foreignValue.clear();

        session = new PreferenceManagerLogin(getApplicationContext());
        spinner = findViewById(R.id.spinner_1);
        spinner_2 = findViewById(R.id.spinner_2);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), countryFirstModelList);
        spinnerAdapter2 = new SpinnerAdapter2(getApplicationContext(), countrySecondModelList);

        editText_send = findViewById(R.id.editText_send);
        editText_recieve = findViewById(R.id.editText_recieve);

        textView_gets = findViewById(R.id.textView_gets);
        textView_convert_myr = findViewById(R.id.textView_convert_myr);
        textView_minimum_denomination = findViewById(R.id.textView_minimum_denomination);

        foreignValue = new LinkedList<>();
        malaysiaValue = new LinkedList<>();
        button_transfer = findViewById(R.id.button_transfer);

      /*  t9_key_1 = v.findViewById(R.id.t9_key_1);
        t9_key_2 = v.findViewById(R.id.t9_key_2);
        t9_key_3 = v.findViewById(R.id.t9_key_3);
        t9_key_4 = v.findViewById(R.id.t9_key_4);
        t9_key_5 = v.findViewById(R.id.t9_key_5);
        t9_key_6 = v.findViewById(R.id.t9_key_6);
        t9_key_7 = v.findViewById(R.id.t9_key_7);
        t9_key_8 = v.findViewById(R.id.t9_key_8);
        t9_key_9 = v.findViewById(R.id.t9_key_9);
        t9_key_0 = v.findViewById(R.id.t9_key_0);
        t9_key_backspace = v.findViewById(R.id.t9_key_backspace);
        t9_key_clear = v.findViewById(R.id.t9_key_clear);

        t9_key_1.setOnClickListener(this);
        t9_key_2.setOnClickListener(this);
        t9_key_3.setOnClickListener(this);
        t9_key_4.setOnClickListener(this);
        t9_key_5.setOnClickListener(this);
        t9_key_6.setOnClickListener(this);
        t9_key_7.setOnClickListener(this);
        t9_key_8.setOnClickListener(this);
        t9_key_9.setOnClickListener(this);
        t9_key_0.setOnClickListener(this);
        t9_key_backspace.setOnClickListener(this);
        t9_key_clear.setOnClickListener(this);



        editText_send.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                statusClickEdittext = true;
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        editText_recieve.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                statusClickEdittext = false;
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
*/
        setSpinnerFirst();
        setSpinnerSecond();

        editText_send.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    editText_send.setFocusable(true);
                }
            }
        });

        editText_recieve.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    editText_recieve.setFocusable(false);
                }
            }
        });

        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent next = new Intent(ForeignExchangeActivity.this,ForeignMapsActivity.class);

                startActivity(next);
               /* if(editText_recieve.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Value",Toast.LENGTH_SHORT).show();
                }else{
                    if(cartCount < 5){

                        if(Double.parseDouble(editText_recieve.getText().toString()) >= Double.parseDouble(denomination)){
                            cartCount = cartCount + 1;

                            Double denominator = Double.parseDouble(denomination);
                            DecimalFormat df = new DecimalFormat("#");
                            Double foreign_value_denominator = roundOff((Double.parseDouble(df.format(Double.parseDouble(editText_recieve.getText().toString()))) / denominator),0) * denominator;

                            int foreign_value_denominator_new = Integer.parseInt(df.format(foreign_value_denominator));

                            DecimalFormat df2 = new DecimalFormat("#.##");
                            Double malaysia_value_denominator = Double.parseDouble(df2.format(foreign_value_denominator / denomination_calculation_myr));

                            total_pay = total_pay + malaysia_value_denominator;
                            malaysiaValue.add("MYR "+String.valueOf(df2.format(malaysia_value_denominator)));
                            foreignValue.add(spinnerTitle+" "+String.valueOf(foreign_value_denominator_new));

                            openDialog();


                        }else{
                            Toast.makeText(getApplicationContext(),"Please enter value above minimum denomination",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        openDialog();
                        Toast.makeText(getApplicationContext(),"You can add up to 5 currencies in your cart",Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),MenuActivity.class);
                next.putExtra("current","foreign");
                startActivity(next);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    public double roundOff(double valueToRound, int numberOfDecimalPlaces){
        double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multipicationFactor;
        return Math.round(interestedInZeroDPs) / multipicationFactor;
    }

    private int removeDecimal(double decimal){
        String newDecimals = "";
        String decimals = String.valueOf(decimal);
        for(int i =0; i<decimals.length(); i++){
            char aa = decimals.charAt(i);

            if(aa == '.'){
                newDecimals = decimals.substring(0,i);
            }
        }
        return Integer.parseInt(newDecimals);
    }

    private void setSpinnerFirst() {
        countryFirstModelList.add(new CountryFirstModel(R.drawable.malaysiaflag, "MYR"));
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


    }

    private void setSpinnerSecond() {

        countrySecondModelList.add(new CountrySecondModel(R.drawable.thailandflag, "THB"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.taiwanflag, "TWD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.vietnamflag, "VND"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.koreaflag, "KRW"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.japanflag, "JPY"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.hongkongflag, "HKD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.britishflag, "GBP"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.usaflagg, "USD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.australiaflag, "AUD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.indonesiaflag, "IDR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.indiaflag, "INR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.singaporeflag, "SGD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.europe, "EUR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.chinaflag, "CNY"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.philiflag, "PHP"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.saudiflag, "SAR"));

        spinner_2.setAdapter(spinnerAdapter2);
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country_code = adapterView.getSelectedItemPosition();
                editText_recieve.setText("");
                editText_send.setText("");
                getCurrentcyTT();
                titleSpinnerPosition();

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


    }

    private void titleSpinnerPosition() {
        if(spinner_2.getSelectedItemPosition() == 0){
            spinnerTitle = "THB";
        }
        if(spinner_2.getSelectedItemPosition() == 1){
            spinnerTitle = "TWD";
        }
        if(spinner_2.getSelectedItemPosition() == 2){
            spinnerTitle = "VND";
        }
        if(spinner_2.getSelectedItemPosition() == 3){
            spinnerTitle = "KRW";
        }
        if(spinner_2.getSelectedItemPosition() == 4){
            spinnerTitle = "JPY";
        }
        if(spinner_2.getSelectedItemPosition() == 5){
            spinnerTitle = "HKD";
        }
        if(spinner_2.getSelectedItemPosition() == 6){
            spinnerTitle = "GBP";
        }
        if(spinner_2.getSelectedItemPosition() == 7){
            spinnerTitle = "USD";
        }
        if(spinner_2.getSelectedItemPosition() == 8){
            spinnerTitle = "AUD";
        }
        if(spinner_2.getSelectedItemPosition() == 9){
            spinnerTitle = "IDR";
        }
        if(spinner_2.getSelectedItemPosition() == 10){
            spinnerTitle = "INR";
        }
        if(spinner_2.getSelectedItemPosition() == 11){
            spinnerTitle = "SGD";
        }
        if(spinner_2.getSelectedItemPosition() == 12){
            spinnerTitle = "EUR";
        }
        if(spinner_2.getSelectedItemPosition() == 13){
            spinnerTitle = "CNY";
        }
        if(spinner_2.getSelectedItemPosition() == 14){
            spinnerTitle = "PHP";
        }
        if(spinner_2.getSelectedItemPosition() == 15){
            spinnerTitle = "SAR";
        }
    }

    private void getCurrentcyTT() {
        editText_send.requestFocus();
        String URL = BasedURL.ROOT_URL_API + "v1/boards/moos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            final DecimalFormat df = new DecimalFormat("#.####");

                            double total;
                            double total2 = 0;

                            if(country_code == 0){  //THA
                                country_tt = obj.getDouble("thb_sell");
                                country_unit = obj.getDouble("thb_unit");
                                denomination = obj.getString("thb_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("THB "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("THB "+obj.getString("thb_unit")+" = MYR "+obj.getString("thb_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" THB");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 1){  //TAIWAN
                                country_tt = obj.getDouble("twd_sell");
                                country_unit = obj.getDouble("twd_unit");
                                denomination = obj.getString("twd_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("TWD "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("TWD "+obj.getString("twd_unit")+" = MYR "+obj.getString("twd_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" TWD");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 2){  //VIETNAM
                                country_tt = obj.getDouble("vnd_sell");
                                country_unit = obj.getDouble("vnd_unit");
                                denomination = obj.getString("vnd_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("VND "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("VND "+obj.getString("vnd_unit")+" = MYR "+obj.getString("vnd_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" VND");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 3){  //KOREA
                                country_tt = obj.getDouble("krw_sell");
                                country_unit = obj.getDouble("krw_unit");
                                denomination = obj.getString("krw_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("KRW "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("KRW "+obj.getString("krw_unit")+" = MYR "+obj.getString("krw_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" KRW");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 4){  //JAPAN
                                country_tt = obj.getDouble("jpy_sell");
                                country_unit = obj.getDouble("jpy_unit");
                                denomination = obj.getString("jpy_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("JPY "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("JPY "+obj.getString("jpy_unit")+" = MYR "+obj.getString("jpy_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" JPY");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 5){  //HONGKONG
                                country_tt = obj.getDouble("hkd_sell");
                                country_unit = obj.getDouble("hkd_unit");
                                denomination = obj.getString("hkd_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("HKD "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("HKD "+obj.getString("hkd_unit")+" = MYR "+obj.getString("hkd_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" HKD");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 6){  //GREAT BRITAIN
                                country_tt = obj.getDouble("gbp_sell");
                                country_unit = obj.getDouble("gbp_unit");
                                denomination = obj.getString("gbp_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("GBP "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("GBP "+obj.getString("gbp_unit")+" = MYR "+obj.getString("gbp_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" GBP");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 7){  //USA
                                country_tt = obj.getDouble("usd_sell");
                                country_unit = obj.getDouble("usd_unit");
                                denomination = obj.getString("usd_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("USD "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("USD "+obj.getString("usd_unit")+" = MYR "+obj.getString("usd_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" USD");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 8){  //AUSTRALIA
                                country_tt = obj.getDouble("aud_sell");
                                country_unit = obj.getDouble("aud_unit");
                                denomination = obj.getString("aud_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("AUD "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("AUD "+obj.getString("aud_unit")+" = MYR "+obj.getString("aud_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" AUD");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 9){  //INDONESIA
                                country_tt = obj.getDouble("idr_sell");
                                country_unit = obj.getDouble("idr_unit");
                                denomination = obj.getString("idr_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("IDR "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("IDR "+obj.getString("idr_unit")+" = MYR "+obj.getString("idr_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" IDR");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 10){  //INDIA
                                country_tt = obj.getDouble("inr_sell");
                                country_unit = obj.getDouble("inr_unit");
                                denomination = obj.getString("inr_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("INR "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("INR "+obj.getString("inr_unit")+" = MYR "+obj.getString("inr_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" INR");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 11){  //SINGAPORE
                                country_tt = obj.getDouble("sgd_sell");
                                country_unit = obj.getDouble("sgd_unit");
                                denomination = obj.getString("sgd_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("SGD "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("SGD "+obj.getString("sgd_unit")+" = MYR "+obj.getString("sgd_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" SGD");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 12){  //EUROPE
                                country_tt = obj.getDouble("eur_sell");
                                country_unit = obj.getDouble("eur_unit");
                                denomination = obj.getString("eur_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("EUR "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("EUR "+obj.getString("eur_unit")+" = MYR "+obj.getString("eur_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" EUR");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 13){  //CHINESE
                                country_tt = obj.getDouble("cny_sell");
                                country_unit = obj.getDouble("cny_unit");
                                denomination = obj.getString("cny_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("CNY "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("CNY "+obj.getString("cny_unit")+" = MYR "+obj.getString("cny_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" CNY");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 14){  //PHILIPI
                                country_tt = obj.getDouble("php_sell");
                                country_unit = obj.getDouble("php_unit");
                                denomination = obj.getString("php_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("PHP "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("PHP "+obj.getString("php_unit")+" = MYR "+obj.getString("php_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" PHP");
                                total2 = total;
                                sendValue(df, total2);
                            }
                            if(country_code == 15){  //SAUDI ARABIA
                                country_tt = obj.getDouble("sar_sell");
                                country_unit = obj.getDouble("sar_unit");
                                denomination = obj.getString("sar_denominator");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("SAR "+total);
                                denomination_calculation_myr = total;
                                textView_gets.setText("SAR "+obj.getString("sar_unit")+" = MYR "+obj.getString("sar_sell"));
                                textView_minimum_denomination.setText("Minimum denomination = "+denomination+" SAR");
                                total2 = total;
                                sendValue(df, total2);
                            }



                            final double finalTotal = total2;
                            editText_send.addTextChangedListener(new TextWatcher() {
                                public void afterTextChanged(Editable s) {}
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    if(status_ontype_send){
                                        if (s == null || s.toString().isEmpty()) {
                                            editText_recieve.setText("");
                                        } else {
                                            double totalValue = Double.valueOf(df.format( Double.parseDouble(s.toString()) * finalTotal));

                                            editText_recieve.setText(String.valueOf(df.format(totalValue)));
                                            /*editText_recieve.setText(totalValue+"");*/
                                        }
                                    }
                                }
                            });


                            editText_send.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (!hasFocus) {
                                        status_ontype_send = false;
                                        editText_send.setFocusable(false);
                                        editText_recieve.addTextChangedListener(new TextWatcher() {
                                            public void afterTextChanged(Editable s) {}
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                if(!status_ontype_send){
                                                    if (s == null || s.toString().isEmpty()) {
                                                        editText_send.setText("");
                                                    } else {
                                                        double totalValue = Double.valueOf(df.format( Double.parseDouble(s.toString()) / finalTotal));
                                                        editText_send.setText(df.format(totalValue)+"");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            editText_recieve.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    editText_send.setFocusableInTouchMode(true);
                                    if (!hasFocus) {
                                        status_ontype_send = true;
                                    }else if(hasFocus){
                                        status_ontype_send = false;
                                        editText_recieve.addTextChangedListener(new TextWatcher() {
                                            public void afterTextChanged(Editable s) {}
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                if(!status_ontype_send){
                                                    if (s == null || s.toString().isEmpty()) {
                                                        editText_send.setText("");
                                                    } else {
                                                        double totalValue = Double.valueOf(df.format( Double.parseDouble(s.toString()) / finalTotal));
                                                        editText_send.setText(df.format(totalValue)+"");
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext().getApplicationContext(),"Please check Internet Connection !!",Toast.LENGTH_LONG).show();
                    }
                }){
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

    private double calculation_receive_function(DecimalFormat df) {
        double totalRecieve;
        double calculation_receive = country_unit * country_tt;
        totalRecieve = Double.valueOf(df.format(calculation_receive));
        return totalRecieve;
    }

    private double calculation_send_function(DecimalFormat df) {
        double total;
        double calculation_send = country_unit / country_tt;
        total = Double.valueOf(df.format(calculation_send));
        return total;
    }

    private void sendValue(DecimalFormat df, double total2) {
        if(!editText_send.getText().toString().isEmpty()){
            double totalValue = Double.valueOf(df.format( Double.parseDouble(editText_send.getText().toString()) * total2));
            editText_recieve.setText(totalValue+"");
        }
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {

            case R.id.t9_key_1:
                if(statusClickEdittext){editText_send.append("1");}else { editText_recieve.append("1");}

                break;

            case R.id.t9_key_2:
                if(statusClickEdittext){editText_send.append("2");}else { editText_recieve.append("2");}
                break;

            case R.id.t9_key_3:
                if(statusClickEdittext){editText_send.append("3");}else { editText_recieve.append("3");}
                break;
            case R.id.t9_key_4:
                if(statusClickEdittext){editText_send.append("4");}else { editText_recieve.append("4");}
                break;

            case R.id.t9_key_5:
                if(statusClickEdittext){editText_send.append("5");}else { editText_recieve.append("5");}
                break;

            case R.id.t9_key_6:
                if(statusClickEdittext){editText_send.append("6");}else { editText_recieve.append("6");}
                break;
            case R.id.t9_key_7:
                if(statusClickEdittext){editText_send.append("7");}else { editText_recieve.append("7");}
                break;

            case R.id.t9_key_8:
                if(statusClickEdittext){editText_send.append("8");}else { editText_recieve.append("8");}
                break;

            case R.id.t9_key_9:
                if(statusClickEdittext){editText_send.append("9");}else { editText_recieve.append("9");}
                break;
            case R.id.t9_key_0:
                if(statusClickEdittext){editText_send.append("0");}else { editText_recieve.append("0");}
                break;

            case R.id.t9_key_clear:
                if(statusClickEdittext){editText_send.append(".");}else { editText_recieve.append(".");}
                break;
            case R.id.t9_key_backspace:
                if(statusClickEdittext){
                    String passwordStr = editText_send.getText().toString();
                    if (passwordStr.length() > 0) {
                        String newPasswordStr = new StringBuilder(passwordStr)
                                .deleteCharAt(passwordStr.length() - 1).toString();
                        editText_send.setText(newPasswordStr);
                    }
                }else{
                    String passwordStr = editText_recieve.getText().toString();
                    if (passwordStr.length() > 0) {
                        String newPasswordStr = new StringBuilder(passwordStr)
                                .deleteCharAt(passwordStr.length() - 1).toString();
                        editText_recieve.setText(newPasswordStr);
                    }
                }
                break;

            default:
                break;
        }*/
    }

    private void openDialog(){


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForeignExchangeActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_preview_currentcy, null);
        mBuilder.setView(mView);
        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();

        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });


        final TextView textView_foreign1 = mView.findViewById(R.id.textView_foreign);
        final TextView textView_foreign2 = mView.findViewById(R.id.textView_foreign2);
        final TextView textView_foreign3 = mView.findViewById(R.id.textView_foreign3);
        final TextView textView_foreign4 = mView.findViewById(R.id.textView_foreign4);
        final TextView textView_foreign5 = mView.findViewById(R.id.textView_foreign5);

        final TextView textView_malaysia1 = mView.findViewById(R.id.textView_malaysia);
        final TextView textView_malaysia2 = mView.findViewById(R.id.textView_malaysia2);
        final TextView textView_malaysia3 = mView.findViewById(R.id.textView_malaysia3);
        final TextView textView_malaysia4 = mView.findViewById(R.id.textView_malaysia4);
        final TextView textView_malaysia5 = mView.findViewById(R.id.textView_malaysia5);
        final TextView textView_total_currency = mView.findViewById(R.id.textView_total_currency);

        final LinearLayout linearLayout_1 = mView.findViewById(R.id.linear_1);
        final LinearLayout linearLayout_2 = mView.findViewById(R.id.linear_2);
        final LinearLayout linearLayout_3 = mView.findViewById(R.id.linear_3);
        final LinearLayout linearLayout_4 = mView.findViewById(R.id.linear_4);
        final LinearLayout linearLayout_5 = mView.findViewById(R.id.linear_5);

        ImageView delete_1 = mView.findViewById(R.id.imageViewCart1);
        ImageView delete_2 = mView.findViewById(R.id.imageViewCart2);
        ImageView delete_3 = mView.findViewById(R.id.imageViewCart3);
        ImageView delete_4 = mView.findViewById(R.id.imageViewCart4);
        ImageView delete_5 = mView.findViewById(R.id.imageViewCart5);

        final Button button_next = mView.findViewById(R.id.button_next);
        final Button button_addmore = mView.findViewById(R.id.button_addmore);

        button_addmore.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        button_next.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));

        textView_total_currency.setText("MYR "+String.valueOf(formatTotalPay.format(total_pay)));

        linearLayout_1.setVisibility(View.GONE);
        linearLayout_2.setVisibility(View.GONE);
        linearLayout_3.setVisibility(View.GONE);
        linearLayout_4.setVisibility(View.GONE);
        linearLayout_5.setVisibility(View.GONE);


        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(ForeignExchangeActivity.this,ForeignExchangeMapsLatest.class);

               /* next.putExtra("myr_value",malaysiaValue);
                next.putExtra("foreign_value",foreignValue);*/

                startActivity(next);
            }
        });

        button_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        if(foreignValue.size() == 1){
            foreign_value1 = foreignValue.get(0);

            String currentcy_temp = foreign_value1.substring(0,3);
            String foreign_value1_temp = foreign_value1.substring(4);

            Long yaw = Long.parseLong(foreign_value1_temp);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString = formatter.format(yaw);

            //setting text after format to EditText
            textView_foreign1.setText(currentcy_temp+" "+formattedString);
        }
        if(foreignValue.size() == 2) {
            foreign_value2 = foreignValue.get(1);

            String currentcy_temp_1 = foreign_value1.substring(0,3);
            String foreign_value1_temp = foreign_value1.substring(4);

            String currentcy_temp_2 = foreign_value2.substring(0,3);
            String foreign_value2_temp = foreign_value2.substring(4);

            Long first = Long.parseLong(foreign_value1_temp);
            Long second = Long.parseLong(foreign_value2_temp);
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");
            String formattedString1 = formatter.format(first);
            String formattedString2 = formatter.format(second);

            //setting text after format to EditText
            textView_foreign1.setText(currentcy_temp_1+" "+formattedString1);
            textView_foreign2.setText(currentcy_temp_2+" "+formattedString2);
        }
        if(foreignValue.size() == 3) {
            foreign_value3 = foreignValue.get(2);

            String currentcy_temp_1 = foreign_value1.substring(0,3);
            String foreign_value1_temp = foreign_value1.substring(4);

            String currentcy_temp_2 = foreign_value2.substring(0,3);
            String foreign_value2_temp = foreign_value2.substring(4);

            String currentcy_temp_3 = foreign_value3.substring(0,3);
            String foreign_value3_temp = foreign_value3.substring(4);

            Long first = Long.parseLong(foreign_value1_temp);
            Long second = Long.parseLong(foreign_value2_temp);
            Long third = Long.parseLong(foreign_value3_temp);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");

            String formattedString1 = formatter.format(first);
            String formattedString2 = formatter.format(second);
            String formattedString3 = formatter.format(third);

            //setting text after format to EditText
            textView_foreign1.setText(currentcy_temp_1+" "+formattedString1);
            textView_foreign2.setText(currentcy_temp_2+" "+formattedString2);
            textView_foreign3.setText(currentcy_temp_3+" "+formattedString3);
        }
        if(foreignValue.size() == 4){
            foreign_value4 = foreignValue.get(3);

            String currentcy_temp_1 = foreign_value1.substring(0,3);
            String foreign_value1_temp = foreign_value1.substring(4);

            String currentcy_temp_2 = foreign_value2.substring(0,3);
            String foreign_value2_temp = foreign_value2.substring(4);

            String currentcy_temp_3 = foreign_value3.substring(0,3);
            String foreign_value3_temp = foreign_value3.substring(4);

            String currentcy_temp_4 = foreign_value4.substring(0,3);
            String foreign_value4_temp = foreign_value4.substring(4);

            Long first = Long.parseLong(foreign_value1_temp);
            Long second = Long.parseLong(foreign_value2_temp);
            Long third = Long.parseLong(foreign_value3_temp);
            Long fourth = Long.parseLong(foreign_value4_temp);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");

            String formattedString1 = formatter.format(first);
            String formattedString2 = formatter.format(second);
            String formattedString3 = formatter.format(third);
            String formattedString4 = formatter.format(fourth);

            //setting text after format to EditText
            textView_foreign1.setText(currentcy_temp_1+" "+formattedString1);
            textView_foreign2.setText(currentcy_temp_2+" "+formattedString2);
            textView_foreign3.setText(currentcy_temp_3+" "+formattedString3);
            textView_foreign4.setText(currentcy_temp_4+" "+formattedString4);
        }
        if(foreignValue.size() == 5){
            foreign_value5 = foreignValue.get(4);

            String currentcy_temp_1 = foreign_value1.substring(0,3);
            String foreign_value1_temp = foreign_value1.substring(4);

            String currentcy_temp_2 = foreign_value2.substring(0,3);
            String foreign_value2_temp = foreign_value2.substring(4);

            String currentcy_temp_3 = foreign_value3.substring(0,3);
            String foreign_value3_temp = foreign_value3.substring(4);

            String currentcy_temp_4 = foreign_value4.substring(0,3);
            String foreign_value4_temp = foreign_value4.substring(4);

            String currentcy_temp_5 = foreign_value5.substring(0,3);
            String foreign_value5_temp = foreign_value5.substring(4);

            Long first = Long.parseLong(foreign_value1_temp);
            Long second = Long.parseLong(foreign_value2_temp);
            Long third = Long.parseLong(foreign_value3_temp);
            Long fourth = Long.parseLong(foreign_value4_temp);
            Long fifth = Long.parseLong(foreign_value5_temp);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###");

            String formattedString1 = formatter.format(first);
            String formattedString2 = formatter.format(second);
            String formattedString3 = formatter.format(third);
            String formattedString4 = formatter.format(fourth);
            String formattedString5 = formatter.format(fifth);

            //setting text after format to EditText
            textView_foreign1.setText(currentcy_temp_1+" "+formattedString1);
            textView_foreign2.setText(currentcy_temp_2+" "+formattedString2);
            textView_foreign3.setText(currentcy_temp_3+" "+formattedString3);
            textView_foreign4.setText(currentcy_temp_4+" "+formattedString4);
            textView_foreign5.setText(currentcy_temp_5+" "+formattedString5);
        }


        if(malaysiaValue.size() == 1){
            linearLayout_1.setVisibility(View.VISIBLE);
            myr_value1 = malaysiaValue.get(0);
            textView_malaysia1.setText(malaysiaValue.get(0));
        }
        if(malaysiaValue.size() == 2){
            linearLayout_1.setVisibility(View.VISIBLE);
            linearLayout_2.setVisibility(View.VISIBLE);
            myr_value2 = malaysiaValue.get(1);
            textView_malaysia1.setText(malaysiaValue.get(0));
            textView_malaysia2.setText(malaysiaValue.get(1));
        }
        if(malaysiaValue.size() == 3){
            linearLayout_1.setVisibility(View.VISIBLE);
            linearLayout_2.setVisibility(View.VISIBLE);
            linearLayout_3.setVisibility(View.VISIBLE);
            myr_value3 = malaysiaValue.get(2);
            textView_malaysia1.setText(malaysiaValue.get(0));
            textView_malaysia2.setText(malaysiaValue.get(1));
            textView_malaysia3.setText(malaysiaValue.get(2));
        }
        if(malaysiaValue.size() == 4){
            textView_malaysia1.setText(malaysiaValue.get(0));
            textView_malaysia2.setText(malaysiaValue.get(1));
            textView_malaysia3.setText(malaysiaValue.get(2));
            textView_malaysia4.setText(malaysiaValue.get(3));
            linearLayout_1.setVisibility(View.VISIBLE);
            linearLayout_2.setVisibility(View.VISIBLE);
            linearLayout_3.setVisibility(View.VISIBLE);
            linearLayout_4.setVisibility(View.VISIBLE);
            myr_value4 = malaysiaValue.get(3);
        }
        if(malaysiaValue.size() == 5){
            textView_malaysia1.setText(malaysiaValue.get(0));
            textView_malaysia2.setText(malaysiaValue.get(1));
            textView_malaysia3.setText(malaysiaValue.get(2));
            textView_malaysia4.setText(malaysiaValue.get(3));
            textView_malaysia5.setText(malaysiaValue.get(4));
            linearLayout_1.setVisibility(View.VISIBLE);
            linearLayout_2.setVisibility(View.VISIBLE);
            linearLayout_3.setVisibility(View.VISIBLE);
            linearLayout_4.setVisibility(View.VISIBLE);
            linearLayout_5.setVisibility(View.VISIBLE);
            myr_value5 = malaysiaValue.get(4);
        }

        delete_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String substring = "";

//                Log.d("foreignLINKED",foreignValue.get(0));
                //        Log.d("foreignTextView",textView_foreign1.getText().toString());


                for(int i =0; i <foreignValue.size(); i++){
                    if(foreignValue.get(i).equals(textView_foreign1.getText().toString().replace(",","").trim())){
                        substring = malaysiaValue.get(i).substring(4);
                        foreignValue.remove(i);
                        malaysiaValue.remove(i);

                    }
                }
                myr_value1 = "";
                foreign_value1 = "";
                linearLayout_1.setVisibility(View.GONE);
                cartCount --;
                if(cartCount == 0){
                    mDialog.dismiss();
                }
                if(substring != null && !substring .isEmpty()) {
                    double total = Double.parseDouble(substring);
                    total_pay = total_pay - total;
                    textView_total_currency.setText("MYR " + String.valueOf(formatTotalPay.format(total_pay)));
                }
            }
        });

        delete_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String substring = "";
                for(int i =0; i <foreignValue.size(); i++){
                    if(foreignValue.get(i).equals(textView_foreign2.getText().toString().replace(",","").trim())){
                        substring = malaysiaValue.get(i).substring(4);
                        foreignValue.remove(i);
                        malaysiaValue.remove(i);
                    }
                }
                myr_value2 = "";
                foreign_value2 = "";
                linearLayout_2.setVisibility(View.GONE);
                cartCount --;
                if(cartCount == 0){
                    mDialog.dismiss();
                }
                if(substring != null && !substring .isEmpty())
                {
                    double total = Double.parseDouble(substring);
                    total_pay = total_pay - total;
                    textView_total_currency.setText("MYR "+String.valueOf(formatTotalPay.format(total_pay)));

                }
            }
        });

        delete_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String substring = "";
                for(int i =0; i <foreignValue.size(); i++){
                    if(foreignValue.get(i).equals(textView_foreign3.getText().toString().replace(",","").trim())){
                        substring = malaysiaValue.get(i).substring(4);
                        foreignValue.remove(i);
                        malaysiaValue.remove(i);
                    }
                }
                myr_value3 = "";
                foreign_value3 = "";
                linearLayout_3.setVisibility(View.GONE);
                cartCount --;
                if(cartCount == 0){
                    mDialog.dismiss();
                }
                if(substring != null && !substring .isEmpty()) {
                    double total = Double.parseDouble(substring);
                    total_pay = total_pay - total;
                    textView_total_currency.setText("MYR " + String.valueOf(formatTotalPay.format(total_pay)));
                }
            }
        });

        delete_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String substring = "";
                for(int i =0; i <foreignValue.size(); i++){
                    if(foreignValue.get(i).equals(textView_foreign4.getText().toString().replace(",","").trim())){
                        substring = malaysiaValue.get(i).substring(4);
                        foreignValue.remove(i);
                        malaysiaValue.remove(i);
                    }
                }
                myr_value4 = "";
                foreign_value4 = "";
                linearLayout_4.setVisibility(View.GONE);
                cartCount --;
                if(cartCount == 0){
                    mDialog.dismiss();
                }
                if(substring != null && !substring .isEmpty()) {
                    double total = Double.parseDouble(substring);
                    total_pay = total_pay - total;
                    textView_total_currency.setText("MYR " + String.valueOf(formatTotalPay.format(total_pay)));
                }
            }
        });

        delete_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String substring = "";
                for(int i =0; i <foreignValue.size(); i++){
                    if(foreignValue.get(i).equals(textView_foreign5.getText().toString().replace(",","").trim())){
                        substring = malaysiaValue.get(i).substring(4);
                        foreignValue.remove(i);
                        malaysiaValue.remove(i);
                    }
                }
                myr_value5 = "";
                foreign_value5 = "";
                linearLayout_5.setVisibility(View.GONE);
                cartCount --;
                if(cartCount == 0){
                    mDialog.dismiss();
                }
                if(substring != null && !substring .isEmpty()) {
                    double total = Double.parseDouble(substring);
                    total_pay = total_pay - total;
                    textView_total_currency.setText("MYR " + String.valueOf(formatTotalPay.format(total_pay)));
                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

            // Add a marker in Sydney, Australia, and move the camera.
            LatLng chennai = new LatLng(3.1390, 101.6869);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chennai, 15));
            mMap.addMarker(new MarkerOptions().position(chennai).title("Kuala Lumpur"));
    }
}
