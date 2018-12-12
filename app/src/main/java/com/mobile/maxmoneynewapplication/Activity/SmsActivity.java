package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.CameraTwo.ImageComparisonActivity;
import com.mobile.maxmoneynewapplication.Activity.CameraTwo.VideoRecording.MainActivity;
import com.mobile.maxmoneynewapplication.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.android.volley.Request.Method.GET;

public class SmsActivity extends AppCompatActivity {

    EditText editText_v1, editText_v2, editText_v3, editText_v4;
    TextView textView_1, textView_2, textView_3, textView_4;
    Button submit;
    ImageView imageView_back, imageView_cancel;
    Intent intent_back, intent_cancel, intent_next;

    String idNo="",email="",bitmap1="",code="",mobile="";
    AlertDialog.Builder mBuilder;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        Random random = new Random();
        code =  String.format("%04d", random.nextInt(10000));
        Log.d("SMSACTIVITY","SMS : "+code);

        mBuilder = new AlertDialog.Builder(SmsActivity.this, R.style.CustomDialog);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_custom_loading, null);
        mBuilder.setView(mView);
        mDialog = mBuilder.create();


        Intent getIntent = getIntent();
        idNo = getIntent.getStringExtra("idNo");
        email = getIntent.getStringExtra("email");
        bitmap1 = getIntent.getStringExtra("image_1");
        mobile = getIntent.getStringExtra("mobile");
        //declare
        editText_v1 = findViewById(R.id.editText_v1);
        editText_v2 = findViewById(R.id.editText_v2);
        editText_v3 = findViewById(R.id.editText_v3);
        editText_v4 = findViewById(R.id.editText_v4);


        textView_2 = findViewById(R.id.textView_digit);
        textView_3 = findViewById(R.id.textView_register);
        textView_4 = findViewById(R.id.textView_below);

        submit = findViewById(R.id.button_submit);

        //verification of code 4 number
        editText_v1.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    editText_v2.requestFocus();
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        editText_v2.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    editText_v3.requestFocus();
                } else {
                    editText_v1.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        editText_v3.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    editText_v4.requestFocus();
                } else {
                    editText_v2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        editText_v4.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    editText_v3.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        //set type font for all text in activities
        ChangeFontType();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code_sms = editText_v1.getText().toString()+editText_v2.getText().toString()+editText_v3.getText().toString()+editText_v4.getText().toString();
                mDialog.show();
                if(code_sms.equals(code)){
                    mDialog.dismiss();
                    intent_next = new Intent(getBaseContext(),MainActivity.class);
                    intent_next.putExtra("idNo",idNo);
                    intent_next.putExtra("bitmap1",bitmap1);
                    intent_next.putExtra("email",email);
                    startActivity(intent_next);
                }else {
                    Toast.makeText(getApplicationContext(),"TAC IS NOT VALID",Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }


            }
        });
        getSMSVerification();
    }

    private void ChangeFontType() {
        textView_2.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_3.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        textView_4.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        editText_v1.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        editText_v2.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        editText_v3.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        editText_v4.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
        submit.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    public void getSMSVerification(){
        mobile = mobile.replace("+","");
        mobile.trim();
        String URL_SMS = "http://cloudsms.trio-mobile.com/index.php/api/bulk_mt?api_key=NUC13010100005773831150aed14a175a573dd172c2d0127e&action=send&to="+mobile+"&msg=MAX%20MONEY%20TAC%20:%20"+code+"&sender_id=CLOUDSMS&content_type=1&mode=shortcode";
        StringRequest stringRequest = new StringRequest(GET, URL_SMS,
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
                HashMap<String, String> headers = new HashMap();
                return headers;
            }
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
