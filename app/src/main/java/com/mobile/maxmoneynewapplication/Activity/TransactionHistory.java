package com.mobile.maxmoneynewapplication.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PagerAdapterTransactionHistory;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class TransactionHistory extends AppCompatActivity {
    TabLayout tabLayout;
    TextView textView_welcome,textView_how,textView_title;
    Button button_pid,button_personal;
    public static String month ="All month";
    ImageView profile_image,imageView_icon_menu;
    String URL_IMAGE = "https://www.maxmoney.com/my/app/android/getProfilePicture.php?";
    private PreferenceManagerLogin session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        session = new PreferenceManagerLogin(getApplicationContext());

        textView_title = findViewById(R.id.textView_title);
        textView_welcome = findViewById(R.id.textView_welcome);
        textView_how = findViewById(R.id.textView_how);
        button_pid = findViewById(R.id.button_pid);
        button_personal = findViewById(R.id.button_personal);
        profile_image  = findViewById(R.id.profile_image);
        imageView_icon_menu = findViewById(R.id.imageView_icon_menu);
        ChangeFontType();

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        tabLayout.addTab(tabLayout.newTab().setText("Foreign Exchange"));
        tabLayout.addTab(tabLayout.newTab().setText("Money Transfer"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        setCustomFont();
        getImage();
        getFullName();

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapterTransactionHistory adapter = new PagerAdapterTransactionHistory(TransactionHistory.this.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {viewPager.setCurrentItem(tab.getPosition());}
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        imageView_icon_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(getApplicationContext(), MenuActivity.class);
                i.putExtra("current","Transaction");
                startActivity(i);
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    public void onBackPressed() {}

    private void ChangeFontType() {
        textView_welcome.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_how.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        button_pid.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        button_personal.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
        textView_title.setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
    }

    private void setCustomFont() {
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(), "Avenir-BookOblique-02.ttf"));
                    ((TextView) tabViewChild).setTextSize(8);
                }
            }
        }
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
                                    Picasso.get().load(imageUrl).fit().centerCrop().into(profile_image);
                                }else{
                                    String imageUrl = "https://www.maxmoney.com/my/images/profile-picture/"+beneficiaryOBJ.getString("url");
                                    Picasso.get().load(imageUrl).into(profile_image);
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

    public void getFullName(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/users/current",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userOBJ = new JSONObject(response);
                            textView_welcome.setText("Welcome , "+userOBJ.getString("fullName"));
                            button_pid.setText("ID :- "+userOBJ.getString("idNo"));

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
}
