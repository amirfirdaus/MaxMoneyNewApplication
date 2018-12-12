package com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeActivity;
import com.mobile.maxmoneynewapplication.Activity.MenuActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.ForeignExchangeMapsLatest;
import com.mobile.maxmoneynewapplication.Model.CountryFirstModel;
import com.mobile.maxmoneynewapplication.Model.CountrySecondModel;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PlaceArrayAdapter;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter2;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class ForeignExchangeDetailsNew extends FragmentActivity implements OnMapReadyCallback ,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    String session_put;
    private GoogleMap mMap;
    TextView textView_titlefirst,textView_title,textView_convert_myr,textView_gets,textView_minimum_denomination;
    LinearLayout linear_details;
    EditText editText_send,editText_recieve;
    Spinner spinner_1,spinner_2;
    Button button_transfer,button_order;
    private List<CountryFirstModel> countryFirstModelList = new ArrayList<>();
    private List<CountrySecondModel> countrySecondModelList = new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;
    private SpinnerAdapter2 spinnerAdapter2;
    int country_code;
    String spinnerTitle = "",denomination = "";;
    double country_unit,country_tt,denomination_calculation_myr,total_pay = 0.0;;
    PreferenceManagerLogin session;
    boolean status_ontype_send = true;
    private int cartCount = 0;
    LinkedList<String> malaysiaValue;
    LinkedList<String> foreignValue;
    DecimalFormat formatTotalPay = new DecimalFormat("#,###,###.##");

    String myr_value1 ="", myr_value2 = "",myr_value3 ="",myr_value4 ="", myr_value5 ="";
    String foreign_value1 ="",foreign_value2 = "",foreign_value3 ="",foreign_value4 ="", foreign_value5 ="";
    ScrollView sv;

    private Boolean mLocationPermissionsGranted = false;


    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    double totalCampur = 0.0;
    //vars

    EditText editText_currentDate,editText_address1;
    SearchableSpinner spinner_time;
    Boolean flag = true;
    double LatSearch;
    double LatiSearch;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private AutoCompleteTextView mAutocompleteTextView;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final String TAG = "MainActivity";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    LinearLayout linear_after_continue,linear_delivery,linear_payment;
    Button button_continue,button_continue2,button_continue3;
    RadioGroup radio_group;
    Time today;

    String days = "";
    String months = "";
    String years = "";
    String dayss = "";
    String monthss = "";
    String yearss = "";
    String deliveryFee = "";
    String longitute = "";
    String longitute1 = "";
    String latitude = "";
    String fpxSubtype = "";
    String bankCode = "";
    String bankNames = "";

    ArrayAdapter<String> spinnerBankAdapter;
    SimpleAdapter simpleAdapter;
    Spinner spinner_bank,spinner_orderPurpose;
    LinearLayout linear_cart1,linear_cart2,linear_cart3,linear_cart4,linear_cart5,linear_order;
    TextView textView_kiri_1,textView_kiri_2,textView_kiri_3,textView_kiri_4,textView_kiri_5;
    TextView textView_kanan_1,textView_kanan_2,textView_kanan_3,textView_kanan_4,textView_kanan_5;
    TextView textView_totall,textView_delivery;
    double totals = 0.0;

    int totalCart = 0;
    String order_id = "";
    WebView webView;
    long dateTime;
    LinkedList<String> myr_value_items;
    LinkedList<String> foreign_value_items;
    ImageView imageView_icon_menu;
    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_exchange_details_new);

        long time= System.currentTimeMillis();
        dateTime = time;

        session = new PreferenceManagerLogin(getApplicationContext());
        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        getLocationPermission();
        foreignValue = new LinkedList<>();
        malaysiaValue = new LinkedList<>();
        //declare
        textView_titlefirst = findViewById(R.id.textView_titlefirst);
        textView_title = findViewById(R.id.textView_title);

        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_titlefirst,getApplicationContext());

        linear_details = findViewById(R.id.linear_details);
        linear_details.setVisibility(View.INVISIBLE);

        //first linear
        editText_send = findViewById(R.id.editText_send);
        editText_recieve  = findViewById(R.id.editText_recieve);
        textView_convert_myr = findViewById(R.id.textView_convert_myr);
        textView_gets = findViewById(R.id.textView_gets);
        textView_minimum_denomination = findViewById(R.id.textView_minimum_denomination);
        spinner_1= findViewById(R.id.spinner_1);
        spinner_2 = findViewById(R.id.spinner_2);
        button_transfer = findViewById(R.id.button_transfer);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), countryFirstModelList);
        spinnerAdapter2 = new SpinnerAdapter2(getApplicationContext(), countrySecondModelList);

        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), MenuActivity.class);
                i.putExtra("current","foreign");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        setSpinnerFirst();
        setSpinnerSecond();

        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_recieve.getText().toString().equals("")){
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
                }
            }
        });

        sv = findViewById(R.id.scrollView);
        //first linear


        editText_address1 = findViewById(R.id.editText_address1);

        linear_cart1 = findViewById(R.id.linear_cart1);
        linear_cart2 = findViewById(R.id.linear_cart2);
        linear_cart3 = findViewById(R.id.linear_cart3);
        linear_cart4 = findViewById(R.id.linear_cart4);
        linear_cart5 = findViewById(R.id.linear_cart5);
        linear_order = findViewById(R.id.linear_order);

        textView_kanan_1 = findViewById(R.id.textView_kanan_1);
        textView_kanan_2 = findViewById(R.id.textView_kanan_2);
        textView_kanan_3 = findViewById(R.id.textView_kanan_3);
        textView_kanan_4 = findViewById(R.id.textView_kanan_4);
        textView_kanan_5 = findViewById(R.id.textView_kanan_5);

        textView_kiri_1 = findViewById(R.id.textView_kiri_1);
        textView_kiri_2 = findViewById(R.id.textView_kiri_2);
        textView_kiri_3 = findViewById(R.id.textView_kiri_3);
        textView_kiri_4 = findViewById(R.id.textView_kiri_4);
        textView_kiri_5 = findViewById(R.id.textView_kiri_5);
        textView_totall = findViewById(R.id.textView_totall);
        textView_delivery = findViewById(R.id.textView_delivery);


        linear_cart1.setVisibility(View.GONE);
        linear_cart2.setVisibility(View.GONE);
        linear_cart3.setVisibility(View.GONE);
        linear_cart4.setVisibility(View.GONE);
        linear_cart5.setVisibility(View.GONE);
        linear_order.setVisibility(View.GONE);

        spinner_time = findViewById(R.id.spinner_time);
        spinner_orderPurpose = findViewById(R.id.spinner_order);
        editText_currentDate = findViewById(R.id.editText_currentDate);
        TextWatcher tww = ValidationBirthday();
        editText_currentDate.addTextChangedListener(tww);

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);

        days = formattedDate.substring(0,2);
        months = formattedDate.substring(3,5);
        years = formattedDate.substring(6,10);


        spinner_bank = findViewById(R.id.spinner_bank);
        radio_group = findViewById(R.id.radio_group);
        linear_payment = findViewById(R.id.linear_payment);
        linear_after_continue = findViewById(R.id.linear_after_continue);
        linear_delivery = findViewById(R.id.linear_delivery);
        button_continue = findViewById(R.id.button_continue);
        button_continue2 = findViewById(R.id.button_continue2);
        button_continue3 = findViewById(R.id.button_continue3);
        button_order = findViewById(R.id.button_order);
        button_continue2.setVisibility(View.GONE);
        linear_after_continue.setVisibility(View.INVISIBLE);
        linear_payment.setVisibility(View.INVISIBLE);

        linear_delivery.setVisibility(View.GONE);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);

        mGoogleApiClient = new GoogleApiClient.Builder(ForeignExchangeDetailsNew.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        getLocationPermission();


        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_details.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        linear_details.getHeight(),  // fromYDelta
                        0);                // toYDelta
                animate.setDuration(700);
                animate.setFillAfter(true);
                linear_details.startAnimation(animate);
                sv.scrollTo(0, 900);
                button_order.setVisibility(View.GONE);
                orderPurpose(order_id);
            }
        });

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*loginAGENT();*/


                linear_after_continue.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        linear_after_continue.getHeight(),  // fromYDelta
                        0);                // toYDelta
                animate.setDuration(500);
                animate.setFillAfter(true);
                linear_after_continue.startAnimation(animate);
                button_continue.setVisibility(View.GONE);
                sv.scrollTo(0, 2500);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        riderFeeDetails();
                        putOrder();
                        button_continue2.setVisibility(View.VISIBLE);
                    }
                }, 2000);

                button_continue.setVisibility(View.GONE);

            }
        });






        button_continue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBank();

                button_continue2.setVisibility(View.GONE);
                linear_payment.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        linear_after_continue.getHeight(),  // fromYDelta
                        0);                // toYDelta
                animate.setDuration(500);
                animate.setFillAfter(true);
                linear_payment.startAnimation(animate);
                sv.scrollTo(0, 3500);
            }
        });


        button_continue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardProgressDialog.show();
                submitOrder();
            }
        });

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio_group.findViewById(checkedId);
                int index = radio_group.indexOfChild(radioButton);
                switch (index) {
                    case 0: // speed delivery
                        linear_delivery.setVisibility(View.GONE);
                        break;
                    case 1: // time delivery
                        linear_delivery.setVisibility(View.VISIBLE);
                        break;
                }
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

    //first linear
    private void setSpinnerFirst() {
        countryFirstModelList.add(new CountryFirstModel(R.drawable.malaysiaflag, "MYR"));
        spinner_1.setAdapter(spinnerAdapter);
        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public double roundOff(double valueToRound, int numberOfDecimalPlaces){
        double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multipicationFactor;
        return Math.round(interestedInZeroDPs) / multipicationFactor;
    }

    private void openDialog(){


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ForeignExchangeDetailsNew.this);
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
                mDialog.dismiss();
                linear_order.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,                 // fromXDelta
                        0,                 // toXDelta
                        linear_details.getHeight(),  // fromYDelta
                        0);                // toYDelta
                animate.setDuration(700);
                animate.setFillAfter(true);
                linear_order.startAnimation(animate);
                sv.scrollTo(0, 900);


                if(malaysiaValue.size() == 1){
                    linear_cart1.setVisibility(View.VISIBLE);
                }
                if(malaysiaValue.size() == 2){
                    linear_cart1.setVisibility(View.VISIBLE);
                    linear_cart2.setVisibility(View.VISIBLE);
                }
                if(malaysiaValue.size() == 3){
                    linear_cart1.setVisibility(View.VISIBLE);
                    linear_cart2.setVisibility(View.VISIBLE);
                    linear_cart3.setVisibility(View.VISIBLE);
                }
                if(malaysiaValue.size() == 4){
                    linear_cart1.setVisibility(View.VISIBLE);
                    linear_cart2.setVisibility(View.VISIBLE);
                    linear_cart3.setVisibility(View.VISIBLE);
                    linear_cart4.setVisibility(View.VISIBLE);
                }
                if(malaysiaValue.size() == 5){
                    linear_cart1.setVisibility(View.VISIBLE);
                    linear_cart2.setVisibility(View.VISIBLE);
                    linear_cart3.setVisibility(View.VISIBLE);
                    linear_cart4.setVisibility(View.VISIBLE);
                    linear_cart5.setVisibility(View.VISIBLE);
                }

                int size = foreignValue.size();
                for(int i = 0; i < size; i++){
                    if(i == 0){
                        postOrderFirstTime(foreignValue.get(0),malaysiaValue.get(0));
                    }else{
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        final Handler handler = new Handler();
                        final int finalI = i;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                postOrder2345time(foreignValue.get(finalI),malaysiaValue.get(finalI));
                            }
                        }, 3000);

                    }
                }
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
    //END first linear


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (mLocationPermissionsGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            // Add a marker in Sydney, Australia, and move the camera.
            LatLng chennai = new LatLng(3.1390, 101.6869);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chennai, 15));
            mMap.addMarker(new MarkerOptions().position(chennai).title("Kuala Lumpur"));
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        mMap.clear();

        if(!title.equals("My Location")){
            UiSettings uiSettings = mMap.getUiSettings();
            uiSettings.setZoomGesturesEnabled(true);
            uiSettings.setZoomControlsEnabled(true);
            LatLng current = new LatLng(latLng.latitude, latLng.longitude);

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(current)
                            .tilt(60)
                            .zoom(17)
                            .build()));
            mMap.addMarker(new MarkerOptions().position(current).title(title));
        }
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(ForeignExchangeDetailsNew.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(ForeignExchangeDetailsNew.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            flag = false;

            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(TAG, "Fetching details for ID: " + item.placeId);


            Places.GeoDataApi.getPlaceById(mGoogleApiClient, placeId)
                    .setResultCallback(new ResultCallback<PlaceBuffer>() {
                        @Override
                        public void onResult(PlaceBuffer places) {
                            if (places.getStatus().isSuccess() && places.getCount() > 0) {
                                final Place myPlace = places.get(0);
                                Log.i(TAG, "Place found: " + myPlace.getName());
                                LatLng latlangObj = myPlace.getLatLng();
                                moveCamera(latlangObj,15,myPlace.getName().toString());
                                LatiSearch = latlangObj.latitude;
                                LatSearch = latlangObj.longitude;

                                Log.e(TAG, String.valueOf(LatiSearch));
                                Log.e(TAG, String.valueOf(LatSearch));
                            } else {
                                Log.e(TAG, "Place not found");
                            }
                            places.release();
                        }
                    });
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();
        }
    };


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(TAG, "Google Places API connection suspended.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

    }

    @NonNull
    private TextWatcher ValidationBirthday() {
        return new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");
                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;
                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));
                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);
                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;


                        if(mon < Integer.parseInt(months)){
                            mon = Integer.parseInt(months);
                            if(day < Integer.parseInt(days)){
                                day = Integer.parseInt(days);
                            }
                        }

                        if(mon == Integer.parseInt(months)){
                            if(day < Integer.parseInt(days)){
                                day = Integer.parseInt(days);
                            }
                        }

                        if(year < Integer.parseInt(years)){
                            year = Integer.parseInt(years);
                        }

                        clean = String.format("%02d%02d%02d", day, mon, year);
                        dayss = String.valueOf(day);
                        monthss = String.valueOf(mon);
                        yearss = String.valueOf(year);
                    }
                    clean = String.format("%s-%s-%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));
                    sel = sel < 0 ? 0 : sel;
                    current = clean;


                    editText_currentDate.setText(current);
                    editText_currentDate.setSelection(sel < current.length() ? sel : current.length());

                    updateTIMING();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }

    public void updateTIMING(){
        today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(date);

        days = formattedDate.substring(0,2);
        months = formattedDate.substring(3,5);
        years = formattedDate.substring(6,10);


        if(months.equals("01") || months.equals("02") || months.equals("03") || months.equals("04") || months.equals("05") || months.equals("06")
                || months.equals("07") || months.equals("08") || months.equals("09")){
            months = months.replace("0","");
        }
        spinner_time.setTitle("Select Time");
        Log.d("days",days);
        Log.d("dayss",dayss);
        Log.d("months",months);
        Log.d("monthss",monthss);
        Log.d("years",years);
        Log.d("yearss",yearss);

        if(days.equals(dayss) && months.equals(monthss) && years.equals(yearss)){
            if(today.hour >= 10 && today.hour < 11){
                String colors[] = {"13.00","14.00","15.00","16.00","17.00","18.00","19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }else if(today.hour >= 11 && today.hour < 12){
                String colors[] = {"14.00","15.00","16.00","17.00","18.00","19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }else if(today.hour >= 12 && today.hour < 13){
                String colors[] = {"15.00","16.00","17.00","18.00","19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }else if(today.hour >= 13 && today.hour < 14){
                String colors[] = {"17.00","18.00","19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }else if(today.hour >= 14 && today.hour < 15){
                String colors[] = {"18.00","19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);

            }else if(today.hour >= 15 && today.hour < 16){
                String colors[] = {"19.00","20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);

            }else if(today.hour >= 16 && today.hour < 17){
                String colors[] = {"20.00"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }else{
                String colors[] = {""};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner_time.setAdapter(spinnerArrayAdapter);
            }
        }else {
            String colors[] = {"13.00","14.00","15.00","16.00","17.00","18.00","19.00","20.00"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
            spinner_time.setAdapter(spinnerArrayAdapter);
        }
    }


    // FUNCTION ORDER

    private void postOrderFirstTime(String foreignY, String myrY){

        final String ccy ;
        final String from;

        ccy = foreignY.substring(0,4).trim().toLowerCase();
        from = myrY.substring(4).trim();

        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.has("id")){
                                order_id = objectResponse.getString("id");
                                Log.d("order_id first",order_id);

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
                params.put("ccy",ccy);
                params.put("from",from);
                params.put("product","omoe");
                params.put("round","true");
                params.put("channelType","APP");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void orderPurpose(final String order_id) {
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id+"/cdd",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {JSONObject jsonObject = new JSONObject(response);
                        } catch (JSONException ignored) {
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError) {
                        }
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
                params.put("orderPurpose",spinner_orderPurpose.getSelectedItem().toString());
                Log.d("params",params.toString());
                return params;
            };

            @Override
            public Priority getPriority() {
                return Priority.IMMEDIATE;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void postOrder2345time(String foreignY, String myrY){

        final String ccy ;
        final String from;

        ccy = foreignY.substring(0,4).trim().toLowerCase();
        from = myrY.substring(4).trim();

        NukeSSLCerts.nuke();

        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/current/"+order_id+"/items",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.has("id")){
                                order_id = objectResponse.getString("id");
                                totalCart = totalCart + 1;
                                Log.d("order_id ="+totalCart,order_id);
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
                params.put("ccy",ccy);
                params.put("from",from);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void getOrder(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject objectResponse = new JSONObject(response);

                            if(objectResponse.has("orders")){
                                JSONArray orderArray = new JSONArray(objectResponse.getString("orders"));
                                for(int i =0; i < orderArray.length(); i++){
                                    JSONObject orderOBJ = orderArray.getJSONObject(i);
                                    if(orderOBJ.getString("id").equals(order_id)){

                                        JSONArray itemARR = new JSONArray(orderOBJ.getString("items"));
                                        for(int ii = 0; ii < itemARR.length(); ii++){
                                            JSONObject itemOBJ = itemARR.getJSONObject(ii);
                                            if(ii == 0){
                                                textView_kiri_1.setText(itemOBJ.getString("ccy").toUpperCase()+" "+
                                                        itemOBJ.getString("value")+" @ "+
                                                        itemOBJ.getString("rate"));
                                                textView_kanan_1.setText("MYR "+itemOBJ.getString("total"));
                                                totals = totals + itemOBJ.getDouble("total");
                                                linear_cart1.setVisibility(View.VISIBLE);
                                            }else if(ii == 1){
                                                textView_kiri_2.setText(itemOBJ.getString("ccy").toUpperCase()+" "+
                                                        itemOBJ.getString("value")+" @ "+
                                                        itemOBJ.getString("rate"));
                                                textView_kanan_2.setText("MYR "+itemOBJ.getString("total"));
                                                totals = totals + itemOBJ.getDouble("total");
                                                linear_cart1.setVisibility(View.VISIBLE);
                                                linear_cart2.setVisibility(View.VISIBLE);
                                            }else if(ii == 2){
                                                textView_kiri_3.setText(itemOBJ.getString("ccy").toUpperCase()+" "+
                                                        itemOBJ.getString("value")+" @ "+
                                                        itemOBJ.getString("rate"));
                                                textView_kanan_3.setText("MYR "+itemOBJ.getString("total"));
                                                totals = totals + itemOBJ.getDouble("total");
                                                linear_cart1.setVisibility(View.VISIBLE);
                                                linear_cart2.setVisibility(View.VISIBLE);
                                                linear_cart3.setVisibility(View.VISIBLE);
                                            }else if(ii == 3){
                                                textView_kiri_4.setText(itemOBJ.getString("ccy").toUpperCase()+" "+
                                                        itemOBJ.getString("value")+" @ "+
                                                        itemOBJ.getString("rate"));
                                                textView_kanan_4.setText("MYR "+itemOBJ.getString("total"));
                                                totals = totals + itemOBJ.getDouble("total");
                                                linear_cart1.setVisibility(View.VISIBLE);
                                                linear_cart2.setVisibility(View.VISIBLE);
                                                linear_cart3.setVisibility(View.VISIBLE);
                                                linear_cart4.setVisibility(View.VISIBLE);
                                            }else if(ii == 4){
                                                textView_kiri_5.setText(itemOBJ.getString("ccy").toUpperCase()+" "+
                                                        itemOBJ.getString("value")+" @ "+
                                                        itemOBJ.getString("rate"));
                                                textView_kanan_5.setText("MYR "+itemOBJ.getString("total"));
                                                totals = totals + itemOBJ.getDouble("total");
                                                linear_cart1.setVisibility(View.VISIBLE);
                                                linear_cart2.setVisibility(View.VISIBLE);
                                                linear_cart3.setVisibility(View.VISIBLE);
                                                linear_cart4.setVisibility(View.VISIBLE);
                                                linear_cart5.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }



                                }
                            }


                            totalCampur = Double.parseDouble(deliveryFee) + totals;
                            textView_delivery.setText("MYR "+deliveryFee);
                            textView_totall.setText("MYR "+String.valueOf(totalCampur));


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

    private void riderFeeDetails(){
        StringRequest stringRequest = new StringRequest(POST, "http://zeptoapi.com/api/rest/calculator/address",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rider fee details",response.toString());
                        try {
                            JSONObject jsonRESPONSE = new JSONObject(response);
                            if(jsonRESPONSE.has("result")){
                                JSONArray jsonArray = new JSONArray(jsonRESPONSE.getString("result"));
                                for(int i =0; i<jsonArray.length(); i++){
                                    JSONObject yaw = jsonArray.getJSONObject(i);
                                    deliveryFee = yaw.getString("price_myr");
                                    longitute = yaw.getString("delivery_latlng");
                                }
                            }

                            for(int i = 0; i <longitute.length(); i++){
                                char aa = longitute.charAt(i);
                                if(aa == ','){
                                    longitute1 = longitute.substring(0,i);
                                    latitude = longitute.substring(i+1);
                                }
                            }
                            getOrder();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    updateRiderCollectionDetails();
                                }
                            }, 2000);


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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token","b3989a06f67e4aef1a4c1e96ff2fb7c0");
                params.put("app_id","164757831422");
                params.put("pickup","Jalan Raja Chulan, Bukit Ceylon, 50200 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur");
                params.put("delivery",editText_address1.getText().toString()+","+mAutocompleteTextView.getText().toString());
                params.put("vehicle","1");
                params.put("schedule",String.valueOf(dateTime));
                params.put("country","MY");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void updateRiderCollectionDetails(){
        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/orders/"+order_id+"/update-collection-details",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MASUK","MASUKK");
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
                params.put("location",editText_address1.getText().toString()+","+mAutocompleteTextView.getText().toString());
                params.put("deliveryDate", String.valueOf(dateTime));
                params.put("longitude",latitude);
                params.put("latitude",longitute1);
                params.put("deliveryStatus","pending");
                params.put("deliveryFees",deliveryFee);

                return params;
            };
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
                                        Log.d("bank",pay.toString());

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
                                                map.put("hide", "CIMB");
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
                                simpleAdapter = new SimpleAdapter(getApplicationContext(),data,R.layout.custom_textview_to_spinnerr,new String[] {"thumbnail","hide"},new int[] {R.id.imageView,R.id.hide});


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
                        }, 3500);


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
        NukeSSLCerts.nuke();
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);

        String httpss = "https://paymence.io/app/curl.php?id="+order_id+"&paymentType=fpx&paymentSubType="+bankCode+"&bankName="+bankNames+"&Total="+totalCampur+"&api_key="+token;
        StringRequest stringRequest = new StringRequest(POST, httpss,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        standardProgressDialog.dismiss();
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
                        standardProgressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("error",error.toString());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            };
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void putOrder(){
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
                params.put("location","ws001");

                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {}

}
