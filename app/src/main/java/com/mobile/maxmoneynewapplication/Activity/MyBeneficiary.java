package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Common.CustomTypefaceSpan;
import com.mobile.maxmoneynewapplication.Common.StandardProgressDialog;
import com.mobile.maxmoneynewapplication.Model.BenificiariesClass;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.BeneficiariesMyProfileAdapter;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class MyBeneficiary extends AppCompatActivity {
    ImageView imageView_icon_menu,imageView_add;
    private PreferenceManagerLogin session;
    RecyclerView recyclerView;
    List<BenificiariesClass> beneficiaryClass;
    StandardProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beneficiary);

        session = new PreferenceManagerLogin(getApplicationContext());
        dialog = new StandardProgressDialog(this.getWindow().getContext());

        recyclerView = findViewById(R.id.recylcerView);

        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), MenuActivity.class);
                i.putExtra("current","MyBeneficiary");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        imageView_add = findViewById(R.id.imageView_add);
        imageView_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(MyBeneficiary.this, AddEditBeneficiary.class);
                next.putExtra("beneficiary_id","new");
                startActivity(next);
            }
        });
        dialog.show();
        getBeneficiary();


    }

    private void getBeneficiary(){
        recyclerView.setLayoutManager(new LinearLayoutManager(MyBeneficiary.this));
        beneficiaryClass = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                final JSONObject beneficiaryOBJ = jsonArray.getJSONObject(i);
                                beneficiaryClass.add(new BenificiariesClass(
                                        beneficiaryOBJ.getString("id"),
                                        beneficiaryOBJ.getString("name"),
                                        beneficiaryOBJ.getString("country"),
                                        beneficiaryOBJ.getString("relationship")
                                ));
                                BeneficiariesMyProfileAdapter adapter = new BeneficiariesMyProfileAdapter(MyBeneficiary.this, beneficiaryClass);
                                recyclerView.setAdapter(adapter);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please check Internet Connection !!", Toast.LENGTH_LONG).show();
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

    public void onBackPressed() {

    }

}
