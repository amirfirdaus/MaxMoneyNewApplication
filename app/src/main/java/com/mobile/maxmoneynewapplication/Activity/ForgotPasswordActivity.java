package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Common.TypeFaceClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.NukeSSLCerts;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView textView_title;
    Button button_forget;
    EditText editText_email;
    ImageView imageView_icon_menu;
    StandardProgressDialog standardProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        standardProgressDialog = new StandardProgressDialog(this.getWindow().getContext());

        textView_title = findViewById(R.id.textView_title);
        editText_email = findViewById(R.id.editText_email);
        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        button_forget = findViewById(R.id.button_forget);

        TypeFaceClass.setTypeFaceTextView(textView_title,getApplicationContext());

        button_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardProgressDialog.show();
                if(editText_email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter Email ",Toast.LENGTH_SHORT).show();
                    standardProgressDialog.dismiss();
                }else{
                    NukeSSLCerts.nuke();
                    StringRequest stringRequest = new StringRequest(POST, BasedURL.ROOT_URL_API +"v1/users/reset/request",
                            new com.android.volley.Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    standardProgressDialog.dismiss();

                                    Toast.makeText(getApplicationContext(),"Password reset email have been sent to your email : "+editText_email.getText().toString(),Toast.LENGTH_SHORT).show();
                                    Intent next = new Intent(getApplicationContext(),LoginActivity.class);
                                    startActivity(next);
                                }
                            },
                            new com.android.volley.Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    standardProgressDialog.dismiss();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username",editText_email.getText().toString().trim());
                            params.put("url","https://www.maxmoney.com/my/reset-password");
                            return params;
                        };
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
