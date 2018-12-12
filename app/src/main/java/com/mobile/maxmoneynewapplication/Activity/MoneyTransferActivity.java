package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.mobile.maxmoneynewapplication.Activity.MoneyTransferDetails.MoneyTransferDetailsActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.Model.CountryFirstModel;
import com.mobile.maxmoneynewapplication.Model.CountrySecondModel;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter;
import com.mobile.maxmoneynewapplication.Utils.SpinnerAdapter2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class MoneyTransferActivity extends AppCompatActivity implements View.OnClickListener {
    boolean status_ontype_send = true;
    boolean status_ontype_receive = false;
    TextView textView_gets,textView_convert_myr;
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
    TextView t9_key_1,t9_key_2,t9_key_3,t9_key_4,t9_key_5,t9_key_6,t9_key_7,t9_key_8,t9_key_9,t9_key_0,t9_key_backspace,t9_key_clear,textView2;
    Button button_transfer;
    String country_choose = "";
    String malaysia_money = "";
    String foreign_money = "";
    String foreign_code = "";
    String malaysia_currency = "";
    TextView textView_title;
    ImageView imageView_icon_menu;
    StandardProgressDialog standardProgressDialog;
    LinearLayout linear_layout1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_transfer);

        NukeSSLCerts.nuke();
        session = new PreferenceManagerLogin(getApplicationContext());
        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        linear_layout1 = findViewById(R.id.linear_layout1);
        linear_layout1.setVisibility(View.INVISIBLE);
        textView2 = findViewById(R.id.textView2);

        spinner = findViewById(R.id.spinner_1);
        spinner_2 = findViewById(R.id.spinner_2);
        spinnerAdapter = new SpinnerAdapter(getApplicationContext(), countryFirstModelList);
        spinnerAdapter2 = new SpinnerAdapter2(getApplicationContext(), countrySecondModelList);
        textView_title = findViewById(R.id.textView_title);
        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),MenuActivity.class);
                next.putExtra("current","money");
                startActivity(next);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
        editText_send = findViewById(R.id.editText_send);
        editText_recieve = findViewById(R.id.editText_recieve);

        textView_gets = findViewById(R.id.textView_gets);
        textView_convert_myr = findViewById(R.id.textView_convert_myr);
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
        t9_key_clear.setOnClickListener(this);*/

        /*editText_send.setOnTouchListener(new View.OnTouchListener() {
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
        });*/

        editText_send.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //   status_ontype_send = false;
                    editText_send.setFocusable(true);

                }
            }
        });



       /* editText_recieve.setOnTouchListener(new View.OnTouchListener() {
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
        editText_recieve.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //   status_ontype_send = false;
                    editText_recieve.setFocusable(false);

                }
            }
        });


        setSpinnerFirst();
        setSpinnerSecond();




       /* field2.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                field1.setText("");
            }
        });*/

        button_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_recieve.getText().toString().equals("") && editText_send.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Value",Toast.LENGTH_SHORT).show();
                }else{
                    checkBeneficiaries();
                }

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
        countrySecondModelList.add(new CountrySecondModel(R.drawable.australiaflag, "AUD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.bangladeshflag, "BDT"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.indiaflag, "INR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.indonesiaflag, "IDR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.nepalflag, "NPR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.pakistanflag, "PKR"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.philiflag, "PHP"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.singaporeflag, "SGD"));
        countrySecondModelList.add(new CountrySecondModel(R.drawable.thailandflag, "THB"));

        spinner_2.setAdapter(spinnerAdapter2);
        spinner_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country_code = adapterView.getSelectedItemPosition();
                editText_recieve.setText("");
                editText_send.setText("");
                getCurrentcyTT();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });


    }

    private void getCurrentcyTT() {
        editText_send.requestFocus();
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
        String URL = BasedURL.ROOT_URL_API + "v1/boards/moos?api-key="+token;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            final DecimalFormat df = new DecimalFormat("#.####");

                            double total;
                            double total2 = 0;

                            if(country_code == 0){  //AUSTRALIA
                                country_tt = obj.getDouble("aud_tt");
                                country_unit = obj.getDouble("aud_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("AUD "+total);
                                textView_gets.setText("AUD "+obj.getString("aud_unit")+" = MYR "+obj.getString("aud_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Australia";
                                foreign_code = "AUD";
                                malaysia_currency = obj.getString("aud_tt");
                            }

                            if(country_code == 1){  //BANGLADESH
                                country_tt = obj.getDouble("bdt_tt");
                                country_unit = obj.getDouble("bdt_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("BDT "+total);
                                textView_gets.setText("BDT "+obj.getString("bdt_unit")+" = MYR "+obj.getString("bdt_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Bangladesh";
                                foreign_code = "BDT";
                                malaysia_currency = obj.getString("bdt_tt");
                            }

                            if(country_code == 2){  //INDIA
                                country_tt = obj.getDouble("inr_tt");
                                country_unit = obj.getDouble("inr_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("INR "+total);
                                textView_gets.setText("INR "+obj.getString("inr_unit")+" = MYR "+obj.getString("inr_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "India";
                                foreign_code = "INR";
                                malaysia_currency = obj.getString("inr_tt");
                            }

                            if(country_code == 3){  //INDONESIA
                                country_tt = obj.getDouble("idr_tt");
                                country_unit = obj.getDouble("idr_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("IDR "+total);
                                textView_gets.setText("IDR "+obj.getString("idr_unit")+" = MYR "+obj.getString("idr_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Indonesia";
                                foreign_code = "IDR";
                                malaysia_currency = obj.getString("idr_tt");
                            }

                            if(country_code == 4){  //NEPAL
                                country_tt = obj.getDouble("npr_tt");
                                country_unit = obj.getDouble("npr_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("NPR "+total);
                                textView_gets.setText("NPR "+obj.getString("npr_unit")+" = MYR "+obj.getString("npr_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Nepal";
                                foreign_code = "NPR";
                                malaysia_currency = obj.getString("npr_tt");
                            }

                            if(country_code == 5){  //PAKISTAN
                                country_tt = obj.getDouble("pkr_tt");
                                country_unit = obj.getDouble("pkr_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("PKR "+total);
                                textView_gets.setText("PKR "+obj.getString("pkr_unit")+" = MYR "+obj.getString("pkr_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Pakistan";
                                foreign_code = "PKR";
                                malaysia_currency = obj.getString("pkr_tt");
                            }

                            if(country_code == 6){  //PHILIP
                                country_tt = obj.getDouble("php_tt");
                                country_unit = obj.getDouble("php_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("PHP "+total);
                                textView_gets.setText("PHP "+obj.getString("php_unit")+" = MYR "+obj.getString("php_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Philippines";
                                foreign_code = "PHP";
                                malaysia_currency = obj.getString("php_tt");
                            }

                            if(country_code == 7){  //SINGAPORE
                                country_tt = obj.getDouble("sgd_tt");
                                country_unit = obj.getDouble("sgd_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("SGD "+total);
                                textView_gets.setText("SGD "+obj.getString("sgd_unit")+" = MYR "+obj.getString("sgd_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Singapore";
                                foreign_code = "SGD";
                                malaysia_currency = obj.getString("sgd_tt");
                            }

                            if(country_code == 8){  //THAILAND
                                country_tt = obj.getDouble("thb_tt");
                                country_unit = obj.getDouble("thb_unit");
                                total = calculation_send_function(df);
                                textView_convert_myr.setText("THB "+total);
                                textView_gets.setText("THB "+obj.getString("thb_unit")+" = MYR "+obj.getString("thb_tt"));
                                total2 = total;
                                sendValue(df, total2);
                                country_choose = "Thailand";
                                foreign_code = "THB";
                                malaysia_currency = obj.getString("thb_tt");
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
                                            editText_recieve.setText(totalValue+"");
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
                                                        editText_send.setText(totalValue+"");
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
                                                        editText_send.setText(totalValue+"");
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
   /*     switch (v.getId()) {

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

    public void checkBeneficiaries(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries?country="+country_choose,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if(jsonArray.length() == 0){
                                linear_layout1.setVisibility(View.VISIBLE);
                                textView2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent next = new Intent(getApplicationContext(),AddEditBeneficiary.class);
                                        next.putExtra("beneficiary_id","new");
                                        startActivity(next);
                                    }
                                });
                            }else {
                                linear_layout1.setVisibility(View.INVISIBLE);
                                malaysia_money = editText_send.getText().toString();
                                foreign_money = editText_recieve.getText().toString();
                                Intent next = new Intent(getApplicationContext(), MoneyTransferDetailsActivity.class);
                                next.putExtra("country",country_choose);
                                next.putExtra("malaysia_money",malaysia_money);
                                next.putExtra("foreign_money",foreign_money);
                                next.putExtra("foreign_code",foreign_code);
                                next.putExtra("malaysia_currency",malaysia_currency);
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
    @Override
    public void onBackPressed() {
    }
}

