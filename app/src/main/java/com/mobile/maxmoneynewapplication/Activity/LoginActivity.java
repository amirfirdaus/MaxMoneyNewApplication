package com.mobile.maxmoneynewapplication.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Api.LoginAPI;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.InternetConnection;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.Common.Validation;
import com.mobile.maxmoneynewapplication.Firebase.SharedPrefManager;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;
import static com.mobile.maxmoneynewapplication.Common.BasedURL.ROOT_URL_API_LOGIN;

public class LoginActivity extends AppCompatActivity {
    TextView textView_signIn,textView_email,textView_password,textView_copyRight,textView_forgotPassword,textView_signup;
    EditText editText_email,editText_password;
    Button button_login;
    PreferenceManagerLogin session;
    StandardProgressDialog pDialog;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //check internet connection
        checkInternetConnection();

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.RECORD_AUDIO},1);

        session = new PreferenceManagerLogin(getApplicationContext());
        pDialog = new StandardProgressDialog(this.getWindow().getContext());

        textView_signIn = findViewById(R.id.textView_signIn);
        textView_email = findViewById(R.id.textView_email);
        textView_password = findViewById(R.id.textView_password);
        textView_copyRight = findViewById(R.id.textView_copyRight);
        textView_forgotPassword = findViewById(R.id.textView_forgotPassword);
        textView_signup = findViewById(R.id.textView_signup);

        editText_email = findViewById(R.id.editText_email);
        editText_password = findViewById(R.id.editText_password);

        button_login = findViewById(R.id.button_login);
   /*     editText_email.setText("sa@maxmoney.com");
        editText_password.setText("MaxMoney@2016");
*/
        //setfont
        setFontTextView(textView_signIn,textView_email,textView_password,textView_copyRight,textView_forgotPassword,textView_signup);
        setFontEditText(editText_email,editText_password);
        setFontButton(button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.show();
                login();
            }
        });

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(next);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });

        textView_forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),ForgotPasswordActivity.class);
                startActivity(next);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    private void setFontButton(Button button_login) {
        TypeFaceClass.setTypeFaceButton(button_login,getApplicationContext());
    }

    private void setFontEditText(EditText editText_email, EditText editText_password) {
        TypeFaceClass.setTypeFaceEditText(editText_email,getApplicationContext());
        TypeFaceClass.setTypeFaceEditText(editText_password,getApplicationContext());
    }

    private void setFontTextView(TextView textView_signIn, TextView textView_email, TextView textView_password, TextView textView_copyRight, TextView textView_forgotPassword
            ,TextView textView_signup) {
        TypeFaceClass.setTypeFaceTextView(textView_signIn,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_email,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_password,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_copyRight,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_forgotPassword,getApplicationContext());
        TypeFaceClass.setTypeFaceTextView(textView_signup,getApplicationContext());
    }

    private void login(){
        if (editText_email.getText().toString() == null || Validation.isEmailValid(editText_email.getText().toString()) == false) {
            pDialog.dismiss();
            editText_email.setError("Invalid Email Address");
        }else if(editText_password.getText().toString() == null || editText_password.length() < 3){
            pDialog.dismiss();
            editText_password.setError("Invalid Password");
        }else{
                   editText_email.setError(null);editText_email.clearFocus();
                   editText_password.setError(null);editText_password.clearFocus();

                   RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ROOT_URL_API_LOGIN).build();
                   LoginAPI api = adapter.create(LoginAPI.class);
                   api.loginUser(
                           editText_email.getText().toString(),
                           editText_password.getText().toString(),
                           new Callback<Response>() {
                               @Override
                               public void success(Response result, Response response) {

                                   BufferedReader reader = null;
                                   String output = "";
                                   try {
                                       reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                                       output = reader.readLine();
                                   } catch (IOException e) {
                                       e.printStackTrace();
                                   }
                                   try {
                                       JSONObject obj = new JSONObject(output);
                                       if(obj.has("message")){
                                           pDialog.dismiss();
                                           Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                                       }else if(obj.has("session")){
                                           pDialog.dismiss();
                                           registerPushNotification(obj.getString("session"));
                                           insertLogin();
                                           session.createLoginSession(obj.getString("session"),editText_email.getText().toString(),editText_password.getText().toString());
                                           Intent next = new Intent(getApplicationContext(),MenuActivity.class);
                                           startActivity(next);
                                       }

                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }
                               @Override
                               public void failure(RetrofitError error) {
                                   pDialog.dismiss();
                                   Toast.makeText(getApplicationContext(),"Authentication failed",Toast.LENGTH_SHORT).show();
                               }
                           }
                   );
        }
    }

    private void registerPushNotification(final String sessions) {
        final String token_device= SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/RegisterDevice.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",editText_email.getText().toString());
                params.put("status","0");
                params.put("token",token_device);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())  moveTaskToBack(true);
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }

    private void checkInternetConnection() {
        if (InternetConnection.isInternetAvailable(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (InternetConnection.isInternetActiveWithPing() == InternetConnection.STATUS_CONNECTED) {
                        performNetworkingOperations();
                    } else {
                        if (InternetConnection.isInternetActiveWithInetAddress()) {
                            performNetworkingOperations();
                        } else {
                            displayConnectionMessage();
                        }
                    }
                }
            }).start();

        } else {
            displayConnectionMessage();
        }
    }

    private void performNetworkingOperations() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               /* Toast.makeText(LoginActivity.this, "Internet is Available", Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void displayConnectionMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InternetConnection.displayInternetConnectionMessage(LoginActivity.this);
            }
        });
    }

    private void insertLogin() {
        final String token_device= SharedPrefManager.getInstance(getApplicationContext()).getDeviceToken();
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/login-attempt.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",editText_email.getText().toString());
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

}
