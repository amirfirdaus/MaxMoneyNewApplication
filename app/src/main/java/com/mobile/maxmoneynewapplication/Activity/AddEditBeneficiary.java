package com.mobile.maxmoneynewapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class AddEditBeneficiary extends AppCompatActivity {

    public static String status_refresh = "";
    private Context mCtx;
    String beneficiary_id;
    String getBeneficiary_id_one;
    RadioGroup radio_group;
    LinearLayout linear_bankdeposit;
    TextView textView_choosePayment,textView_fullname,textView_email,textView_address,textView_country,textView_phoneno,
            textView_purpose,textView_relationship,textView_bankInfoTitle,textView_bankname,textView_bankBranch,textView_accNumber;
    EditText editText_fullname,editText_email,editText_address,editText_phoneNo,editText_bankbranch,editText_accNumber;
    SearchableSpinner spinner_countryi,spinner_purpose,spinner_relationship,spinner_bankname;
    Button button_addBeneficiary;
    MediaType countriesJSON;
    ArrayAdapter<String> spinnerCountriesAdapter;
    ArrayAdapter<String> spinnerBankAdapter;
    PreferenceManagerLogin session;
    String codeBank;
    int totalBANK =0;
    String BANKNAME ="";
    String cashOrBank = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_beneficiary);

        Intent intent = getIntent();
        beneficiary_id = intent.getStringExtra("beneficiary_id");

        DeclarationAll();
        ChangeFontType();
        LoadAllSPinner();
        newOrOld();
        linear_bankdeposit.setVisibility(LinearLayout.GONE);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio_group.findViewById(checkedId);
                int index = radio_group.indexOfChild(radioButton);
                switch (index) {
                    case 0: // cash pickup
                        cashOrBank = "cash";
                        linear_bankdeposit.setVisibility(LinearLayout.GONE);
                        break;
                    case 1: // bank deposit
                        cashOrBank = "bank";
                        linear_bankdeposit.setVisibility(LinearLayout.VISIBLE);
                        break;
                }
            }
        });

        button_addBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!beneficiary_id.equals("new")){ //edit beneficiary
                    editNewBeneficiary();
                }
                if(beneficiary_id.equals("new")){ //add beneficiary
                    createNewBeneficiary();
                }
            }
        });
    }

    private void newOrOld() {
        if(!beneficiary_id.equals("new")){
            //set font action bar
            Typeface font2 = Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf");
            SpannableStringBuilder SS = new SpannableStringBuilder("Edit Beneficiaries Id "+beneficiary_id);
            SS.setSpan (new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            getSupportActionBar().setTitle(SS);
            button_addBeneficiary.setText("EDIT BENEFICIARIES");
            getData();
        } else if(beneficiary_id.equals("new")){
            Typeface font2 = Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf");
            SpannableStringBuilder SS = new SpannableStringBuilder("Add New Beneficiaries");
            SS.setSpan (new CustomTypefaceSpan("", font2), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            button_addBeneficiary.setText("ADD BENEFICIARIES");
            getSupportActionBar().setTitle(SS);
        }

        //set back button action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void DeclarationAll() {
        countriesJSON = MediaType.parse("application/json; charset=utf-8");
        session = new PreferenceManagerLogin(getApplicationContext());


        linear_bankdeposit = findViewById(R.id.linear_bankdeposit);
        radio_group = findViewById(R.id.radio_group);

        textView_choosePayment = findViewById(R.id.textView_choosePayment);
        textView_fullname = findViewById(R.id.textView_fullname);
        textView_email = findViewById(R.id.textView_email);
        textView_address = findViewById(R.id.textView_address);
        textView_country = findViewById(R.id.textView_country);
        textView_phoneno = findViewById(R.id.textView_phoneno);
        textView_purpose = findViewById(R.id.textView_purpose);
        textView_relationship = findViewById(R.id.textView_relationship);
        textView_bankInfoTitle = findViewById(R.id.textView_bankInfoTitle);
        textView_bankname = findViewById(R.id.textView_bankname);
        textView_bankBranch = findViewById(R.id.textView_bankBranch);
        textView_accNumber = findViewById(R.id.textView_accNumber);

        editText_fullname = findViewById(R.id.editText_fullname);
        editText_email = findViewById(R.id.editText_email);
        editText_address = findViewById(R.id.editText_address);
        editText_phoneNo = findViewById(R.id.editText_phoneNo);
        editText_bankbranch = findViewById(R.id.editText_bankbranch);
        editText_accNumber = findViewById(R.id.editText_accNumber);
        spinner_countryi = findViewById(R.id.spinner_countryi);
        spinner_purpose = findViewById(R.id.spinner_purpose);
        spinner_relationship = findViewById(R.id.spinner_relationship);
        spinner_bankname = findViewById(R.id.spinner_bankname);

        button_addBeneficiary = findViewById(R.id.button_addBeneficiary);
    }

    private void LoadAllSPinner() {
        spinner_purpose.setTitle("Choose Purpose");
        spinner_relationship.setTitle("Choose Relationship");

        try {
            JSONObject obj = new JSONObject(readJSONFromAsset());
            JSONArray array = obj.getJSONArray("country");
            ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject yeah = array.getJSONObject(i);
                list.add(yeah.getString("name"));
            }
            spinnerCountriesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview_to_spinner, list);
            spinner_countryi.setTitle("Choose Country");
            spinner_countryi.setAdapter(spinnerCountriesAdapter);
            spinner_countryi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                    ((TextView) parent.getChildAt(0)).setTextSize(14);
                    getBanks(spinner_countryi.getSelectedItem().toString());
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }

            });

        }catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void intentGo(){
        Intent next = new Intent(getApplicationContext(),MyBeneficiary.class);
        startActivity(next);
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("countries.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void ChangeFontType() {
        textView_choosePayment.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_fullname.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_email.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_address.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_country.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_phoneno.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_purpose.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_relationship.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_bankInfoTitle.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_bankname.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_bankBranch.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_accNumber.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        button_addBeneficiary .setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));


    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().toLowerCase().equalsIgnoreCase(myString.toLowerCase())){
                return i;
            }
        }

        return 0;
    }

    public void getBanks(final String country){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/locations?country="+country+"&type=BANK",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject totalOBJ = new JSONObject(response);
                            int totalSize = totalOBJ.getInt("total");
                            if(totalSize != 0){
                                getDetailsBanks(country,totalSize,"");
                            }else {
                                Toast.makeText(getApplicationContext(),"No Banks for "+country,Toast.LENGTH_SHORT).show();

                                ArrayList<String> list = new ArrayList<String>();
                                spinnerBankAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview_to_spinner, list);
                                spinner_bankname.setTitle("No Bank Found");
                                spinner_bankname.setAdapter(spinnerBankAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"No Banks for this country", Toast.LENGTH_LONG).show();
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

    public void getDetailsBanks(final String country, final int total, final String bankName){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/locations?country="+country+"&type=BANK&size="+total,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject totalOBJ = new JSONObject(response);
                            JSONArray array = totalOBJ.getJSONArray("locations");
                            ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject yeah = array.getJSONObject(i);
                                list.add(yeah.getString("name"));
                            }
                            spinnerBankAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_textview_to_spinner, list);
                            spinner_bankname.setTitle("Choose Bank");
                            spinner_bankname.setAdapter(spinnerBankAdapter);
                            if(!BANKNAME.equals("")){
                                spinner_bankname.setSelection(getIndex(spinner_bankname, BANKNAME));
                            }
                            spinner_bankname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if(parent.getChildAt(0).toString() !=null) {
                                        ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
                                        ((TextView) parent.getChildAt(0)).setTextSize(14);
                                        getDetailsBankss(country, total, spinner_bankname.getSelectedItem().toString());
                                    }

                                }
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
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

    public void getDetailsBankss(final String country, int total, final String bankName){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/locations?country="+country+"&type=BANK&size="+total,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject totalOBJ = new JSONObject(response);
                            JSONArray array = totalOBJ.getJSONArray("locations");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject yeah = array.getJSONObject(i);
                                if(bankName.equals(yeah.getString("name"))){
                                    codeBank = yeah.getString("code");
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

    public void getData(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/beneficiaries/"+beneficiary_id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject benenificiaryOBJ = new JSONObject(response);
                            if(beneficiary_id.equals(benenificiaryOBJ.optString("id"))){
                                editText_fullname.setText(benenificiaryOBJ.optString("name"));
                                editText_email.setText(benenificiaryOBJ.optString("email"));
                                editText_address.setText(benenificiaryOBJ.optString("address"));
                                editText_phoneNo.setText(benenificiaryOBJ.optString("mobile"));
                                spinner_countryi.setSelection(getIndex(spinner_countryi, benenificiaryOBJ.getString("country")));
                                spinner_relationship.setSelection(getIndex(spinner_relationship, benenificiaryOBJ.getString("relationship")));


                                if(benenificiaryOBJ.has("bankAccounts")){
                                    JSONObject bankAccOBJ = new JSONObject(benenificiaryOBJ.getString("bankAccounts"));
                                    JSONObject firstOBJ = new JSONObject(bankAccOBJ.getString("1"));
                                    radio_group.check(R.id.radio_bank);

                                    BANKNAME = firstOBJ.getString("name");
                                    getBanks(benenificiaryOBJ.getString("country"));



                                    editText_bankbranch.setText(firstOBJ.getString("branch"));
                                    editText_accNumber.setText(firstOBJ.getString("acctNo"));
                                }else{
                                    radio_group.check(R.id.radio_cash);
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),"Something error",Toast.LENGTH_SHORT).show();
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

    public void createNewBeneficiary(){
        NukeSSLCerts.nuke();
        if(!editText_fullname.getText().toString().isEmpty() && !editText_email.getText().toString().isEmpty()
                && !editText_address.getText().toString().isEmpty()&& !editText_phoneNo.getText().toString().isEmpty()){

            StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.isEmpty()){
                                Toast.makeText(getApplicationContext(),"something error",Toast.LENGTH_SHORT).show();
                            }else {
                                try {
                                    JSONObject beneficiaryID = new JSONObject(response);
                                    beneficiary_id = beneficiaryID.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(cashOrBank.equals("bank")){
                                    AddBankAcc();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            intentGo();
                                        }
                                    }, 2000);
                                }

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
                    params.put("name",editText_fullname.getText().toString().trim());
                    params.put("address",editText_address.getText().toString().trim());
                    params.put("country",spinner_countryi.getSelectedItem().toString().trim());
                    params.put("relationship",spinner_relationship.getSelectedItem().toString().trim());
                    params.put("mobile",editText_phoneNo.getText().toString().trim());
                    params.put("email",editText_email.getText().toString().trim());
                    params.put("orderPurpose",spinner_purpose.getSelectedItem().toString().trim());
                    return params;
                };
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);


        }else{
            Toast.makeText(getApplicationContext(),"Some text field are blank",Toast.LENGTH_SHORT).show();
        }

    }

    public void AddBankAcc(){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries/"+beneficiary_id+"/accounts",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.isEmpty()){
                            Toast.makeText(getApplicationContext(),"something error add accounts",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    intentGo();

                                }
                            }, 2000);
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
                params.put("acctNo",editText_accNumber.getText().toString().trim());
                params.put("acctName",editText_fullname.getText().toString().trim());
                params.put("name",spinner_bankname.getSelectedItem().toString().trim());
                params.put("branch",editText_bankbranch.getText().toString().trim());
                params.put("location",codeBank.toString());
                params.put("country",spinner_countryi.getSelectedItem().toString().trim());
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void editNewBeneficiary(){
        NukeSSLCerts.nuke();
        if(!editText_fullname.getText().toString().isEmpty() && !editText_email.getText().toString().isEmpty()
                && !editText_address.getText().toString().isEmpty()&& !editText_phoneNo.getText().toString().isEmpty()){

            StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries/"+beneficiary_id,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.isEmpty()){
                                Toast.makeText(getApplicationContext(),"something error",Toast.LENGTH_SHORT).show();
                            }else {
                                if(cashOrBank.equals("bank")){
                                    AddBankAcc();
                                }else {
                                    Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            intentGo();
                                        }
                                    }, 2000);
                                }
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
                    params.put("name",editText_fullname.getText().toString().trim());
                    params.put("address",editText_address.getText().toString().trim());
                    params.put("country",spinner_countryi.getSelectedItem().toString().trim());
                    params.put("relationship",spinner_relationship.getSelectedItem().toString().trim());
                    params.put("mobile",editText_phoneNo.getText().toString().trim());
                    params.put("email",editText_email.getText().toString().trim());
                    params.put("orderPurpose",spinner_purpose.getSelectedItem().toString().trim());
                    return params;
                };
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }else{
            Toast.makeText(getApplicationContext(),"Some text field are blank",Toast.LENGTH_SHORT).show();
        }
    }

}
