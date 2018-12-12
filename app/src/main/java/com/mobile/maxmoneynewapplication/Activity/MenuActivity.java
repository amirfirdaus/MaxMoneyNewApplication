package com.mobile.maxmoneynewapplication.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.ForeignExchangeDetailsNew;
import com.mobile.maxmoneynewapplication.Activity.ForeignExchangeDetailss.ForeignMapsActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.ForeignExchangeMapsLatest;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.POST;

public class MenuActivity extends AppCompatActivity {
    TextView textView_title,textView_welcome,textView_verified;
    ImageView imageView_back,imageView_logout,imageView_profile_image,imageView_beneficiary,imageView_ratelist,imageView_transaction,
            imageView_summary,imageView_myprofile,imageView_foreign,imageView_money,imageView_verified,imageView_settings;
    String current,URL_IMAGE = "https://www.maxmoney.com/my/app/android/getProfilePicture.php?";;
    PreferenceManagerLogin session;
    Button button_pid;
    AlertDialog.Builder mBuilder;
    AlertDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        session = new PreferenceManagerLogin(getApplicationContext());

        if(session.checkLogin()){
            finish();
        }else{

            mBuilder = new AlertDialog.Builder(MenuActivity.this, R.style.CustomDialog);
            final View mView = getLayoutInflater().inflate(R.layout.dialog_custom_loading, null);
            mBuilder.setView(mView);
            mDialog = mBuilder.create();

          /*  Intent get = getIntent();

            current = get.getStringExtra("current");*/
            textView_title = findViewById(R.id.textView_title);
            textView_welcome = findViewById(R.id.textView_welcome);
            textView_verified = findViewById(R.id.textView_verified);
            imageView_verified = findViewById(R.id.imageView_verified);

            /* imageView_back = findViewById(R.id.imageView_back);*/
            imageView_logout = findViewById(R.id.imageView_logout);
            imageView_profile_image = findViewById(R.id.imageView_profile_image);
            button_pid = findViewById(R.id.button_pid);

            imageView_beneficiary = findViewById(R.id.imageView_beneficiary); // PAGE BENEFICIARY
            imageView_ratelist = findViewById(R.id.imageView_ratelist); //PAGE RATES LIST
            imageView_transaction = findViewById(R.id.imageView_transaction); //PAGE TRANSACTION HISTORY
            imageView_summary = findViewById(R.id.imageView_summary); //PAGE SUMMARY
            imageView_myprofile = findViewById(R.id.imageView_myprofile); //PAGE PROFILE
            imageView_foreign = findViewById(R.id.imageView_foreign); // FOREIGN EXCHANGE
            imageView_money = findViewById(R.id.imageView_money); //MONEY TRANSFER
            imageView_settings = findViewById(R.id.imageView_settings); //settings

            TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
            textView_welcome.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-Roman-12.ttf"));

/*        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.equals("MainActivity")){
                    Intent next = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(next);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("MyBeneficiary")){
                    Intent next = new Intent(getApplicationContext(),MyBeneficiary.class);
                    startActivity(next);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("Transaction")){
                    Intent nexts = new Intent(getApplicationContext(),TransactionHistory.class);
                    startActivity(nexts);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("summary")){
                    Intent nexts = new Intent(getApplicationContext(),SummaryActivity.class);
                    startActivity(nexts);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("profile")){
                    Intent nexts = new Intent(getApplicationContext(),MyProfile.class);
                    startActivity(nexts);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("foreign")){
                    Intent nexts = new Intent(getApplicationContext(),ForeignExchangeActivity.class);
                    startActivity(nexts);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }
                if(current.equals("money")){
                    Intent nexts = new Intent(getApplicationContext(),MoneyTransferActivity.class);
                    startActivity(nexts);
                    overridePendingTransition(R.anim.enter, R.anim.exit);
                }

            }
        });*/

            imageView_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutDialog();
                }
            });
            imageView_beneficiary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),MyBeneficiary.class);
                            startActivity(next);
                        }
                    }, 1000);


                }
            });
            imageView_ratelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(next);
                        }
                    }, 1000);

                }
            });
            imageView_transaction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),TransactionHistory.class);
                            startActivity(next);
                        }
                    }, 1000);

                }
            });
            imageView_summary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),SummaryActivity.class);
                            startActivity(next);
                        }
                    }, 1000);

                }
            });
            imageView_myprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),MyProfile.class);
                            startActivity(next);
                        }
                    }, 1000);

                }
            });
            imageView_foreign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),ForeignExchangeDetailsNew.class);
                            startActivity(next);
                        }
                    }, 1000);

                }
            });
            imageView_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),MoneyTransferActivity.class);
                            startActivity(next);
                        }
                    }, 1000);
                }
            });
            imageView_settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDialog.show();
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return keyCode == KeyEvent.KEYCODE_BACK;
                        }
                    });

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                            Intent next = new Intent(getApplicationContext(),SettingActivity.class);
                            startActivity(next);
                        }
                    }, 1000);
                }
            });

            getImage();
            getNameAndEmail();
            checkFirstTimeLogin();
        }
    }

    private void checkFirstTimeLogin() {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/getLogin.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            for(int i = 0; i < arr.length(); i++){
                                JSONObject test = arr.getJSONObject(i);

                                String attempt = test.getString("attempt");
                                if(attempt.equals("0")){
                                    openDialogPassword();
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
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                final String email = user.get(PreferenceManagerLogin.KEY_EMAIL);
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
    }

    public void logoutDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Log Out Activity")
                .setMessage("Are you sure you want to log out ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        killSession();
                        session.logoutUser();
                        Intent next = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(next);

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void killSession() {
        HashMap<String, String> user = session.getUserDetails();
        final String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
        StringRequest stringRequest = new StringRequest(DELETE, BasedURL.ROOT_URL_API +"v1/sessions/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("")){

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
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",token);
                return headers;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getImage(){
        HashMap<String, String> user = session.getUserDetails();
        String token = user.get(PreferenceManagerLogin.KEY_TOKEN);

        StringRequest stringRequest = new StringRequest(GET, URL_IMAGE+"user_id="+token,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject OBJOBJ = new JSONObject(response);
                            JSONArray result = new JSONArray(OBJOBJ.getString("result"));
                            for(int i = 0; i<result.length();i++){
                                JSONObject beneficiaryOBJ = result.getJSONObject(i);
                                if(Build.VERSION.SDK_INT <= 22){
                                    String imageUrl = "http://www.maxmoney.com/my/images/profile-picture/"+beneficiaryOBJ.getString("url");
                                    Picasso.get().load(imageUrl).into(imageView_profile_image);
                                }else{
                                    String imageUrl = "https://www.maxmoney.com/my/images/profile-picture/"+beneficiaryOBJ.getString("url");
                                    Picasso.get().load(imageUrl).into(imageView_profile_image);
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getNameAndEmail(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current",
                new com.android.volley.Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userOBJ = new JSONObject(response);
                            textView_welcome.setText("Welcome , "+userOBJ.getString("fullName").toUpperCase());
                            button_pid.setText("PID : "+userOBJ.getString("idNo"));
                            if(userOBJ.getString("status").equals("active")){
                                textView_verified.setText("Account Verified");
                                imageView_verified.setImageDrawable(getDrawable(R.drawable.icon_verified));
                            }else{
                                textView_verified.setText("Account Not Verified");
                                imageView_verified.setImageDrawable(getDrawable(R.drawable.icon_notverified
                                ));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext().getApplicationContext(),"Session Timeout",Toast.LENGTH_LONG).show();
                        Intent next = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(next);
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

    private void openDialogPassword(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MenuActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        mBuilder.setView(mView);
        final AlertDialog mDialog = mBuilder.create();
        mDialog.show();
        mDialog.setCanceledOnTouchOutside(false);

        TextView textView = mView.findViewById(R.id.textView);
        final EditText editText_first = mView.findViewById(R.id.editText_first);
        final EditText editText_second = mView.findViewById(R.id.editText_second);
        Button button_submit = mView.findViewById(R.id.button_submit);

        TypeFaceClass.setTypeFaceTextView(textView,mView.getContext());
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_first.getText().toString().equals("") || editText_second.getText().toString().equals("")){
                    editText_first.setError("Please fill");
                    editText_second.setError("Please fill");
                }else if(!editText_second.getText().toString().equals(editText_first.getText().toString())){
                    editText_second.setError("password not match");
                }else{
                    changePassword(editText_first.getText().toString(),editText_second.getText().toString());
                    mDialog.dismiss();
                }
            }
        });

    }

    private void changePassword(final String password, final String confim){
        StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/users/current/reset",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Password Change",Toast.LENGTH_SHORT).show();
                        updateAttempt();
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
                params.put("password",password);
                params.put("confirmPassword",confim);
                return params;
            };
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

    private void updateAttempt() {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/update-attempt.php",
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
                HashMap<String, String> user = session.getUserDetails();
                final String email = user.get(PreferenceManagerLogin.KEY_EMAIL);
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
