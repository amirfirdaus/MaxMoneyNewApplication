package com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
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
import com.mobile.maxmoneynewapplication.Activity.SplashScreen;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class ForeignMapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    private SpinnerAdapter spinnerAdapter;
    private SpinnerAdapter2 spinnerAdapter2;
    private List<CountryFirstModel> countryFirstModelList = new ArrayList<>();
    private List<CountrySecondModel> countrySecondModelList = new ArrayList<>();
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

    Spinner spinner,spinner_2;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //vars
    private Boolean mLocationPermissionsGranted = false;

    EditText editText_currentDate,editText_address1;
    SearchableSpinner spinner_time;
    Boolean flag = true;
    double LatSearch;
    double LatiSearch;
    private GoogleMap mMap;
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
    Spinner spinner_bank;
    LinearLayout linear_cart1,linear_cart2,linear_cart3,linear_cart4,linear_cart5;
    TextView textView_kiri_1,textView_kiri_2,textView_kiri_3,textView_kiri_4,textView_kiri_5;
    TextView textView_kanan_1,textView_kanan_2,textView_kanan_3,textView_kanan_4,textView_kanan_5;
    TextView textView_totall,textView_delivery;
    double totals = 0.0;

    int totalCart = 0;
    PreferenceManagerLogin session;
    String order_id = "";
    ScrollView sv;
    WebView webView;
    long dateTime;
    LinkedList<String> myr_value_items;
    LinkedList<String> foreign_value_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreign_maps);


        dateTime = System.currentTimeMillis();

        session = new PreferenceManagerLogin(getApplicationContext());

        Intent intent = getIntent();

        if(intent.getExtras()!=null){
            myr_value_items = new LinkedList<>((List<String>) intent.getSerializableExtra("myr_value"));
            foreign_value_items = new LinkedList<>((List<String>) intent.getSerializableExtra("foreign_value"));
        }

        spinner = findViewById(R.id.spinner_1);
        spinner_2 = findViewById(R.id.spinner_2);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), countryFirstModelList);
        spinnerAdapter2 = new SpinnerAdapter2(getApplicationContext(), countrySecondModelList);


        setSpinnerFirst();
        setSpinnerSecond();

        final ScrollView sv = findViewById(R.id.scrollView);


        editText_address1 = findViewById(R.id.editText_address1);

        linear_cart1 = findViewById(R.id.linear_cart1);
        linear_cart2 = findViewById(R.id.linear_cart2);
        linear_cart3 = findViewById(R.id.linear_cart3);
        linear_cart4 = findViewById(R.id.linear_cart4);
        linear_cart5 = findViewById(R.id.linear_cart5);

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

        Animation anim = new AlphaAnimation(0.0f, 1.0f);

        anim.setDuration(1000);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        textView_kanan_1.startAnimation(anim);
        textView_kanan_2.startAnimation(anim);
        textView_kanan_3.startAnimation(anim);
        textView_kanan_4.startAnimation(anim);
        textView_kanan_5.startAnimation(anim);

        textView_kiri_1.startAnimation(anim);
        textView_kiri_2.startAnimation(anim);
        textView_kiri_3.startAnimation(anim);
        textView_kiri_4.startAnimation(anim);
        textView_kiri_5.startAnimation(anim);

        linear_cart1.setVisibility(View.GONE);
        linear_cart2.setVisibility(View.GONE);
        linear_cart3.setVisibility(View.GONE);
        linear_cart4.setVisibility(View.GONE);
        linear_cart5.setVisibility(View.GONE);

        if(myr_value_items!=null){
            if(myr_value_items.size() == 1){
                linear_cart1.setVisibility(View.VISIBLE);
            }
            if(myr_value_items.size() == 2){
                linear_cart1.setVisibility(View.VISIBLE);
                linear_cart2.setVisibility(View.VISIBLE);
            }
            if(myr_value_items.size() == 3){
                linear_cart1.setVisibility(View.VISIBLE);
                linear_cart2.setVisibility(View.VISIBLE);
                linear_cart3.setVisibility(View.VISIBLE);
            }
            if(myr_value_items.size() == 4){
                linear_cart1.setVisibility(View.VISIBLE);
                linear_cart2.setVisibility(View.VISIBLE);
                linear_cart3.setVisibility(View.VISIBLE);
                linear_cart4.setVisibility(View.VISIBLE);
            }
            if(myr_value_items.size() == 5){
                linear_cart1.setVisibility(View.VISIBLE);
                linear_cart2.setVisibility(View.VISIBLE);
                linear_cart3.setVisibility(View.VISIBLE);
                linear_cart4.setVisibility(View.VISIBLE);
                linear_cart5.setVisibility(View.VISIBLE);
            }
        }



        spinner_time = findViewById(R.id.spinner_time);
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
        button_continue2.setVisibility(View.GONE);
        linear_after_continue.setVisibility(View.INVISIBLE);
        linear_payment.setVisibility(View.INVISIBLE);

        linear_delivery.setVisibility(View.GONE);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mAutocompleteTextView = findViewById(R.id.autoCompleteTextView);
        mAutocompleteTextView.setThreshold(3);

        mGoogleApiClient = new GoogleApiClient.Builder(ForeignMapsActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

        getLocationPermission();

        button_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int size = foreign_value_items.size();
                for(int i = 0; i < size; i++){
                    if(i == 0){
                        postOrderFirstTime(foreign_value_items.get(0),myr_value_items.get(0));
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
                                postOrder2345time(foreign_value_items.get(finalI),myr_value_items.get(finalI));
                            }
                        }, 3000);

                    }
                }

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
                sv.scrollTo(0, 700);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        riderFeeDetails();
                        putOrder();
                        button_continue2.setVisibility(View.VISIBLE);
                    }
                }, 3000);

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
                sv.scrollTo(800, sv.getBottom());
            }
        });


        button_continue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
              /*  country_code = adapterView.getSelectedItemPosition();
                editText_recieve.setText("");
                editText_send.setText("");
                getCurrentcyTT();
                titleSpinnerPosition();*/

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


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
        mapFragment.getMapAsync(ForeignMapsActivity.this);
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
            inputManager.hideSoftInputFromWindow(ForeignMapsActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    public void getOrder(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/"+order_id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            JSONArray arrayItems = objectResponse.getJSONArray("items");

                            for (int i = 0; i < arrayItems.length(); i++) {
                                JSONObject yeah = arrayItems.getJSONObject(i);
                                if(i == 0){
                                    textView_kiri_1.setText(yeah.getString("ccy").toUpperCase()+" "+
                                            yeah.getString("value")+" @ "+
                                            yeah.getString("rate"));
                                    textView_kanan_1.setText("MYR "+yeah.getString("total"));
                                    totals = totals + yeah.getDouble("total");

                                    textView_kanan_1.clearAnimation();
                                    textView_kiri_1.clearAnimation();

                                }else if(i == 1){
                                    textView_kiri_2.setText(yeah.getString("ccy").toUpperCase()+" "+
                                            yeah.getString("value")+" @ "+
                                            yeah.getString("rate"));
                                    textView_kanan_2.setText("MYR "+yeah.getString("total"));
                                    totals = totals + yeah.getDouble("total");

                                    textView_kanan_2.clearAnimation();
                                    textView_kiri_2.clearAnimation();

                                }else if(i == 2){
                                    textView_kiri_3.setText(yeah.getString("ccy").toUpperCase()+" "+
                                            yeah.getString("value")+" @ "+
                                            yeah.getString("rate"));
                                    textView_kanan_3.setText("MYR "+yeah.getString("total"));
                                    totals = totals + yeah.getDouble("total");

                                    textView_kanan_3.clearAnimation();
                                    textView_kiri_3.clearAnimation();

                                }else if(i == 3){
                                    textView_kiri_4.setText(yeah.getString("ccy").toUpperCase()+" "+
                                            yeah.getString("value")+" @ "+
                                            yeah.getString("rate"));
                                    textView_kanan_4.setText("MYR "+yeah.getString("total"));
                                    totals = totals + yeah.getDouble("total");

                                    textView_kanan_4.clearAnimation();
                                    textView_kiri_4.clearAnimation();
                                }else if(i == 4){
                                    textView_kiri_5.setText(yeah.getString("ccy").toUpperCase()+" "+
                                            yeah.getString("value")+" @ "+
                                            yeah.getString("rate"));
                                    textView_kanan_5.setText("MYR "+yeah.getString("total"));
                                    totals = totals + yeah.getDouble("total");

                                    textView_kanan_5.clearAnimation();
                                    textView_kiri_5.clearAnimation();
                                }
                            }

                            double totalCampur = 0.0;
                            totalCampur = Double.parseDouble(deliveryFee) + Double.parseDouble(objectResponse.getString("amountRemaining"));
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
                return params;
            };
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

    private void riderFeeDetails(){
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/orders/"+order_id+"/rider-fee-details",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("rider fee details",response.toString());
                        try {
                            JSONObject jsonRESPONSE = new JSONObject(response);
                            deliveryFee = jsonRESPONSE.getString("price_myr");
                            longitute = jsonRESPONSE.getString("delivery_latlng");

                            for(int i = 0; i <longitute.length(); i++){
                                char aa = longitute.charAt(i);
                                if(aa == ','){
                                    longitute1 = longitute.substring(0,i);
                                    latitude = longitute.substring(i+1);
                                }
                            }


                            getOrder();
                            updateRiderCollectionDetails();
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
                params.put("deliveryAddress",editText_address1.getText().toString()+","+mAutocompleteTextView.getText().toString());
                params.put("schedule","2018-09-13 15:44:38");
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
                params.put("deliveryDate",String.valueOf(dateTime));
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

    private void putOrder(){
        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/orders/"+order_id,
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
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);

        String https = "https://paymence.io/app/curl.php?id="+order_id+"&paymentType=fpx&paymentSubType="+bankCode+"&bankName="+bankNames+"&Total="+textView_totall.getText().toString()+"&api_key="+token;
        StringRequest stringRequest = new StringRequest(POST, https,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String htmlData = response.toString();

                        htmlData = htmlData.replaceAll("&lt;", "<");
                        htmlData = htmlData.replaceAll("&gt;", ">");
                        htmlData = htmlData.replaceAll("&quot;", "\"");

                     /*   Intent next = new Intent(getApplicationContext(),FpxActivity.class);
                        next.putExtra("fpx",htmlData.toString());
                        startActivity(next);
*/
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
