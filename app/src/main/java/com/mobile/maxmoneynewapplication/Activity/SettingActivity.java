package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class SettingActivity extends AppCompatActivity {
    TextView textView_title;
    ImageView imageView_icon_menu;
    PreferenceManagerLogin sessions;
    Switch switch_notification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sessions = new PreferenceManagerLogin(getApplicationContext());
        textView_title = findViewById(R.id.textView_title);
        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        switch_notification = findViewById(R.id.switch_notification);

        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());
        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), MenuActivity.class);
                i.putExtra("current","settings");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        getDevicesStatus();

        switch_notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    updateStatusNotification("1");
                } else {
                    updateStatusNotification("0");
                }
            }
        });
    }

    private void updateStatusNotification(final String s) {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/updateDeviceStatus.php",
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
                HashMap<String, String> user = sessions.getUserDetails();
                String email = user.get(PreferenceManagerLogin.KEY_EMAIL);
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("status",s);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getDevicesStatus() {
        StringRequest stringRequest = new StringRequest(POST, "https://www.maxmoney.com/my/app/android/GetRegisteredDevices.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            if(object.getString("status").equals("0")){
                                switch_notification.setChecked(false);
                            }else{
                                switch_notification.setChecked(true);
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
                HashMap<String, String> user = sessions.getUserDetails();
                String email = user.get(PreferenceManagerLogin.KEY_EMAIL);
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",email);
                return params;
            };
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
