package com.mobile.maxmoneynewapplication.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    TextView textView_title,textView_accType,textView_fullname,textView_email,textView_mobile,textView_idType,
            textView_idNo,textView_nationality,textView_dateofbirth,textView_dateofexpiry,textView_address,
            textView_state,textView_postol,textView_city;

    TextView textView_dateofexpiry2,textView_dateofbirth2;

    Spinner spinner_accType,spinner_idType,spinner_state;

    SearchableSpinner spinner_nationality;

    EditText editText_fullname,editText_email,editText_mobile,editText_idNo,editText_address,editText_postolcode,editText_city;

    Button register;

    private DatePickerDialog.OnDateSetListener dateExpiryListerner;

    private DatePickerDialog.OnDateSetListener dateBirthListerner;

    private StandardProgressDialog mProgressDialog;

    ArrayAdapter<String> spinnerNationalityAdapter;

    String country_name = "",country_nationality = "";

    LinearLayout linear_expiry;

    PreferenceManagerLogin session;

    LinearLayout linear_business;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);//testing

        mProgressDialog = new StandardProgressDialog(this.getWindow().getContext());
        session = new PreferenceManagerLogin(getApplicationContext());

        textView_title = findViewById(R.id.textView_title);
        spinner_nationality = findViewById(R.id.spinner_nationality);
        linear_expiry = findViewById(R.id.linear_expiry);
        textViewTitleDeclaration();
        editTextDeclaration();
        spinnerDeclaration();
        spinnerNationality();

        linear_business = findViewById(R.id.linear_business);
        linear_business.setVisibility(View.GONE);
        textView_dateofexpiry2 = findViewById(R.id.textView_dateofexpiry2);
        textView_dateofbirth2 = findViewById(R.id.textView_dateofbirth2);

        editText_mobile.setText("+60");

        //spinner account type select
        spinner_accType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinner = parent.getItemAtPosition(position).toString();
                if(spinner.equals("Business")){
                    linear_business.setVisibility(View.VISIBLE);
                }else{
                    linear_business.setVisibility(View.GONE);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //date picker
        textView_dateofexpiry2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateExpiryListerner,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateExpiryListerner  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String newDay = "";
                String newMonth = "";
                if(day < 10){
                    newDay = "0" + day;
                }else {
                    newDay = String.valueOf(day);
                }
                if(month < 10){
                    newMonth = "0" + month;
                }else {
                    newMonth = String.valueOf(month);
                }
                String date = newDay + "-" + newMonth + "-" + year;
                textView_dateofexpiry2.setText(date);
            }
        };

        textView_dateofbirth2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateBirthListerner,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateBirthListerner  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String newDay = "";
                String newMonth = "";
                if(day < 10){
                    newDay = "0" + day;
                }else {
                    newDay = String.valueOf(day);
                }
                if(month < 10){
                    newMonth = "0" + month;
                }else {
                    newMonth = String.valueOf(month);
                }
                String date = newDay + "-" + newMonth + "-" + year;
                textView_dateofbirth2.setText(date);
            }
        };
        //end date picker

        register = findViewById(R.id.button_register);

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                mProgressDialog.show();

                if(editText_fullname.getText().toString().equals("") || editText_email.getText().toString().equals("") ||
                        editText_mobile.getText().toString().equals("") || editText_idNo.getText().toString().equals("") ||
                        editText_address.getText().toString().equals("") || editText_postolcode.getText().toString().equals("") ||
                        editText_city.getText().toString().equals("")){
                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Please fill the form !!",Toast.LENGTH_SHORT).show();
                }else{

                    String accType = spinner_accType.getSelectedItem().toString();
                    String fullname = editText_fullname.getText().toString();
                    String email = editText_email.getText().toString();
                    String mobile = editText_mobile.getText().toString();
                    String idType = spinner_idType.getSelectedItem().toString();
                    String idNo = editText_idNo.getText().toString();
                    String country = country_name;
                    String dob = textView_dateofbirth2.getText().toString();
                    String dateExpiry = textView_dateofexpiry2.getText().toString();
                    String address = editText_address.getText().toString();
                    String state = spinner_state.getSelectedItem().toString();
                    String postalCode = editText_postolcode.getText().toString();
                    String city = editText_city.getText().toString();
                    String registeredThrough = "APPS";
                    String type = "";
                    String nationality = country_nationality;
                    if(accType.equals("Personal")){
                        type = "Individual";
                    }else{
                        type = "SME";
                    }

                    if(state.equals("KUALA LUMPUR")){state = "KUALA_LUMPUR";}
                    else if(state.equals("NEGERI SEMBILAN")){state = "NEGERI_SEMBILAN";}

                    register(idType,idNo,fullname,email,mobile,address,city,state,postalCode,country,nationality,type,dob,registeredThrough);
                }
            }
        });

    }

    private void setDateOfBirth(Bundle arguments) {
        String dateTemp = arguments.getString("dob");
        String DD = dateTemp.substring(0,2);
        String MM = dateTemp.substring(3,5);
        String YY = dateTemp.substring(6,8);
        String fullDate = DD+"-"+MM+"-"+YY;
        Date actualDate = null;
        SimpleDateFormat yy = new SimpleDateFormat( "dd-MM-yy" );
        SimpleDateFormat yyyy = new SimpleDateFormat( "dd-MM-yyyy" );
        try {
            actualDate = yy.parse( fullDate );
        }
        catch ( ParseException pe ) {
            System.out.println( pe.toString() );
        }
        String date_birthday = yyyy.format( actualDate );
        textView_dateofbirth2.setText(date_birthday);
    }

    private void setPassportDateExpiry(Bundle arguments) {
        String dateTemp2 = arguments.getString("expiry");
        String DD2 = dateTemp2.substring(0,2);
        String MM2 = dateTemp2.substring(3,5);
        String YY2 = dateTemp2.substring(6,8);
        String fullDate2 = DD2+"-"+MM2+"-"+YY2;
        Date actualDate2 = null;
        SimpleDateFormat yy2 = new SimpleDateFormat( "dd-MM-yy" );
        SimpleDateFormat yyyy2 = new SimpleDateFormat( "dd-MM-yyyy" );
        try {
            actualDate2 = yy2.parse( fullDate2 );
        }
        catch ( ParseException pe ) {
            System.out.println( pe.toString() );
        }
        String date_expire = yyyy2.format( actualDate2 );
        textView_dateofexpiry2.setText(date_expire);
    }

    private void spinnerNationality() {
        try {
            JSONObject obj = new JSONObject(readJSONFromAsset());
            JSONArray array = obj.getJSONArray("country");

            ArrayList<String> list = new ArrayList<String>();
            for(int i=0; i<array.length(); i++) {
                JSONObject yeah = array.getJSONObject(i);
                list.add(yeah.getString("name"));
            }
            spinnerNationalityAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
            spinner_nationality.setTitle("Choose Country");
            spinner_nationality.setAdapter(spinnerNationalityAdapter);


            spinner_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String spinner = parent.getItemAtPosition(position).toString();
                    try {
                        JSONObject obj = new JSONObject(readJSONFromAsset());
                        JSONArray array = obj.getJSONArray("country");
                        for(int i = 0; i < array.length();i++){
                            JSONObject yeah = array.getJSONObject(i);
                            if(spinner.equals(yeah.getString("name"))){
                                country_name = yeah.getString("name");
                                country_nationality = yeah.getString("nationality");
                                if(country_name.equals("Malaysia")){
                                    linear_expiry.setVisibility(View.GONE);
                                }else {
                                    linear_expiry.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void spinnerSetCountry(final String country) {
        try {
            JSONObject obj = new JSONObject(readJSONFromAsset());
            JSONArray array = obj.getJSONArray("country");

            ArrayList<String> list = new ArrayList<String>();
            for(int i=0; i<array.length(); i++) {
                JSONObject yeah = array.getJSONObject(i);
                list.add(yeah.getString("name"));
            }
            spinnerNationalityAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
            spinner_nationality.setTitle("Choose Country");
            spinner_nationality.setAdapter(spinnerNationalityAdapter);


            spinner_nationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String spinner = parent.getItemAtPosition(position).toString();
                    try {
                        JSONObject obj = new JSONObject(readJSONFromAsset());
                        JSONArray array = obj.getJSONArray("country");
                        for(int i = 0; i < array.length();i++){
                            JSONObject yeah = array.getJSONObject(i);
                            String obj_code = yeah.getString("code");
                            String get_nationality = country;
                            if(get_nationality.equals(obj_code)){
                                country_name = yeah.getString("name");
                                country_nationality = yeah.getString("nationality");
                                spinner_nationality.setSelection(spinnerNationalityAdapter.getPosition(country_name));
                                if(country_name.equals("Malaysia")){
                                    linear_expiry.setVisibility(View.GONE);
                                }else {
                                    linear_expiry.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void spinnerDeclaration() {
        spinner_accType = findViewById(R.id.spinner_accType);
        spinner_idType = findViewById(R.id.spinner_idType);
        spinner_state = findViewById(R.id.spinner_state);
    }

    private void editTextDeclaration() {
        editText_fullname = findViewById(R.id.editText_fullname);
        editText_email = findViewById(R.id.editText_email);
        editText_mobile = findViewById(R.id.editText_mobile);
        editText_idNo = findViewById(R.id.editText_idNo);
        editText_address = findViewById(R.id.editText_address);
        editText_postolcode = findViewById(R.id.editText_postolcode);
        editText_city = findViewById(R.id.editText_city);
    }

    private void textViewTitleDeclaration() {
        textView_title = findViewById(R.id.textView_title);
        textView_accType = findViewById(R.id.textView_accType);
        textView_fullname = findViewById(R.id.textView_fullname);
        textView_email = findViewById(R.id.textView_email);
        textView_mobile = findViewById(R.id.textView_mobile);
        textView_idType = findViewById(R.id.textView_idType);
        textView_idNo = findViewById(R.id.textView_idNo);
        textView_nationality = findViewById(R.id.textView_nationality);
        textView_dateofbirth = findViewById(R.id.textView_dateofbirth);
        textView_dateofexpiry = findViewById(R.id.textView_dateofexpiry);
        textView_address = findViewById(R.id.textView_address);
        textView_state = findViewById(R.id.textView_state);
        textView_postol = findViewById(R.id.textView_postol);
        textView_city = findViewById(R.id.textView_city);


        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_accType,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_fullname,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_email,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_mobile,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_idType,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_idNo,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_nationality,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_dateofbirth,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_dateofexpiry,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_address,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_state,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_postol,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_city,getApplicationContext());
    }

    public void register(final String idType, final String idNo, final String customerName, final String email, final String mobile, final String address, final String city,
                         final String state, final String postalCode, final String country, final String nationality, final String type, final String dob, final String registeredThrough){
        HashMap<String, String> user = session.getUserDetails();
        final String email_online = user.get(PreferenceManagerLogin.KEY_EMAIL);

        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/customers",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject objectResponse = new JSONObject(response);
                            if(objectResponse.has("message")){
                                mProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),objectResponse.getString("message"),Toast.LENGTH_SHORT).show();
                            }else{
                                mProgressDialog.dismiss();
                                Intent next = new Intent(getApplicationContext(), CddActivity.class);
                                next.putExtra("name",customerName);
                                next.putExtra("idNo",idNo);
                                next.putExtra("idType",idType);
                                next.putExtra("email",email);
                                next.putExtra("mobile",mobile);
                                startActivity(next);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        parseVolleyError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idType",idType);
                params.put("idNo",idNo);
                params.put("customerName",customerName);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("address",address);
                params.put("city",city);
                params.put("state",state);
                params.put("postalCode",postalCode);
                params.put("country",country);
                params.put("nationality",nationality);
                params.put("type",type);
                params.put("dob",dob);
                params.put("registeredThrough",registeredThrough);
                if(!country.equals("Malaysia")){
                    params.put("idExpiryDate","31-12-2100");
                }

                Log.d("toString",params.toString());
                return params;
            };
        };

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            JSONArray errors = data.getJSONArray("errors");
            JSONObject jsonMessage = errors.getJSONObject(0);
            String message = jsonMessage.getString("message");
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
        } catch (UnsupportedEncodingException errorr) {
        }
    }

}
