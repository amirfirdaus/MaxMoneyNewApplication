package com.mobile.maxmoneynewapplication.Activity.CameraTwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.MenuActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Activity.LoginActivity;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class ImageComparisonActivity extends AppCompatActivity {

    String bitmap1="",bitmap2="",email="";
    TextView textView_percentage,dialog_info;
    Button dialog_ok;
    AlertDialog.Builder mBuilder;
    AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_comparison);

        mBuilder = new AlertDialog.Builder(ImageComparisonActivity.this, R.style.CustomDialog);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_custom_loading, null);
        mBuilder.setView(mView);
        mDialog = mBuilder.create();
        mDialog.show();

        Intent getIntent = getIntent();
        bitmap1 = getIntent.getStringExtra("bitmap1");
        bitmap2 = getIntent.getStringExtra("bitmap2");
        email = getIntent.getStringExtra("email");

        textView_percentage = findViewById(R.id.textView_percentage);
        dialog_info = findViewById(R.id.dialog_info);
        dialog_ok = findViewById(R.id.dialog_ok);

        dialog_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(next);
            }
        });

        checkIMAGESimilirity();
    }

    private void checkIMAGESimilirity() {
        StringRequest stringRequest = new StringRequest(POST, "https://api-us.faceplusplus.com/facepp/v3/compare",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            mDialog.dismiss();
                            JSONObject testing = new JSONObject(response);
                            Log.d("test",testing.toString());
                            Double percentages = Double.parseDouble(testing.getString("confidence"));
                            if(percentages < 80){
                                textView_percentage.setText(String.valueOf(percentages)+"%");
                                dialog_info.setText("Please Try Again. E-kyc does not match. Please wait for compliance checking !");
                                updateStatus("inactive");
                            }else{
                                textView_percentage.setText(String.valueOf(percentages)+"%");
                                updateStatus("active");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mDialog.dismiss();
                        Log.d("error",error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("api_key","tE3w_7kxW_7WTASu28HvH0ZYHwO3XCW-");
                params.put("api_secret","GLPFMNv5fvoY5Ar4I13qc0p1FMOA1GJW");
                params.put("image_base64_1",bitmap1+"");
                params.put("image_base64_2",bitmap2+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
    }

    public void updateStatus(final String status){
        NukeSSLCerts.nuke();
        StringRequest stringRequest = new StringRequest(PUT, BasedURL.ROOT_URL_API +"v1/users/"+email,
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
                headers.put("api-key","1da1b520367629e86bb0e6556749448fa7c379da");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("status",status);
                params.put("requestSource","agent");
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}
