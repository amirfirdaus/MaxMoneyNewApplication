package com.mobile.maxmoneynewapplication.Activity.MainActivityTabs;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyTransfer extends Fragment {

    TextView textView_buy,textView_sell;
    TextView currentcy_usd,sell_usd,buy_usd;  //usd
    TextView currentcy_singapore,sell_singapore,buy_singapore;  //singapore
    TextView currentcy_hkg,sell_hkg,buy_hkg;  //HONGKONG
    TextView currentcy_australia,sell_australia,buy_australia;  //Australia
    TextView currentcy_thailand,sell_thailand,buy_thailand;  //Thailand
    TextView currentcy_indonesia,sell_indonesia,buy_indonesia;  //INDONESIA
    TextView currentcy_india,sell_india,buy_india;  //INDIA
    TextView currentcy_vietnam,sell_vietnam,buy_vietnam;  //VIETNAM
    TextView currentcy_china,sell_china,buy_china;  //china
    TextView currentcy_srilanka,sell_srilanka,buy_srilanka;  //srilanka
    TextView currentcy_nepal,sell_nepal,buy_nepal;  //nepal
    TextView currentcy_pakistan,sell_pakistan,buy_pakistan;  //pakistan
    TextView currentcy_bangladesh,sell_bangladesh,buy_bangladesh;  //bangladesh
    PreferenceManagerLogin session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_money_transfer, container, false);

        session = new PreferenceManagerLogin(getContext());

        textView_buy = v.findViewById(R.id.textView_buy);
        textView_sell = v.findViewById(R.id.textView_sell);
        AllDeclareration(v);
        ChangeFontType();
        getMoneyTransferRate();
        return v;
    }

    private void AllDeclareration(View v) {
        declareUSD(v);declareSingapore(v);
        declareHKG(v);declareAus(v);declareThai(v);declareIndon(v);
        declareIndia(v);declareVietnam(v);declareSriLanka(v);
        declareNepal(v);declarePakistan(v);declareBangladesh(v);declareChina(v);
    }
    private void declareBangladesh(View v) {
        currentcy_bangladesh   =  v.findViewById(R.id.currentcy_bangladesh);
        sell_bangladesh= v.findViewById(R.id.sell_bangladesh);buy_bangladesh = v.findViewById(R.id.buy_bangladesh);
    }
    private void declarePakistan(View v) {
        currentcy_pakistan   =  v.findViewById(R.id.currentcy_pakistan);
        sell_pakistan= v.findViewById(R.id.sell_pakistan);buy_pakistan = v.findViewById(R.id.buy_pakistan);
    }
    private void declareNepal(View v) {
        currentcy_nepal   =  v.findViewById(R.id.currentcy_nepal);
        sell_nepal= v.findViewById(R.id.sell_nepal);buy_nepal = v.findViewById(R.id.buy_nepal);
    }
    private void declareSriLanka(View v) {
        currentcy_srilanka   =  v.findViewById(R.id.currentcy_srilanka);
        sell_srilanka= v.findViewById(R.id.sell_srilanka);buy_srilanka = v.findViewById(R.id.buy_srilanka);
    }
    private void declareChina(View v) {
        currentcy_china   =  v.findViewById(R.id.currentcy_china);
        sell_china = v.findViewById(R.id.sell_china);buy_china = v.findViewById(R.id.buy_china);
    }
    private void declareVietnam(View v) {
        currentcy_vietnam   =  v.findViewById(R.id.currentcy_vietnam);
        sell_vietnam = v.findViewById(R.id.sell_vietnam);buy_vietnam = v.findViewById(R.id.buy_vietnam);
    }
    private void declareIndia(View v) {
        currentcy_india   =  v.findViewById(R.id.currentcy_india);
        sell_india = v.findViewById(R.id.sell_india);buy_india = v.findViewById(R.id.buy_india);
    }
    private void declareIndon(View v) {
        currentcy_indonesia = v.findViewById(R.id.currentcy_indonesia);
        sell_indonesia = v.findViewById(R.id.sell_indonesia);buy_indonesia = v.findViewById(R.id.buy_indonesia);
    }
    private void declareThai(View v) {
        currentcy_thailand  =  v.findViewById(R.id.currentcy_thailand);
        sell_thailand = v.findViewById(R.id.sell_thailand);buy_thailand = v.findViewById(R.id.buy_thailand);
    }
    private void declareAus(View v) {
        currentcy_australia =  v.findViewById(R.id.currentcy_australia);
        sell_australia = v.findViewById(R.id.sell_australia);buy_australia = v.findViewById(R.id.buy_australia);
    }
    private void declareHKG(View v) {
        currentcy_hkg =  v.findViewById(R.id.currentcy_hongkong);
        sell_hkg = v.findViewById(R.id.sell_hongkong);buy_hkg = v.findViewById(R.id.buy_hongkong);
    }
    private void declareSingapore(View v) {
        currentcy_singapore = v.findViewById(R.id.currentcy_singapore);
        sell_singapore = v.findViewById(R.id.sell_singapore);buy_singapore = v.findViewById(R.id.buy_singapore);
    }
    private void declareUSD(View v) {
        currentcy_usd =  v.findViewById(R.id.currentcy_usd);buy_usd = v.findViewById(R.id.buy_usd);
        sell_usd = v.findViewById(R.id.sell_usd);
    }
    private void ChangeFontType() {
        textView_buy.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Roman-12.ttf"));
        textView_sell.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Roman-12.ttf"));

        currentcy_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        buy_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        sell_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        buy_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

    }

    private void getMoneyTransferRate() {
        String URL = BasedURL.ROOT_URL_API + "v1/boards/moos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            double value= 200.3456;
                            DecimalFormat df = new DecimalFormat("0.0000");

                            currentcy_usd.setText(obj.getString("usd_name"));
                            buy_usd.setText("MYR 1.00");
                            sell_usd.setText("USD "+String.valueOf(df.format(obj.getDouble("usd_unit") / obj.getDouble("usd_tt"))));

                            currentcy_singapore.setText(obj.getString("sgd_name"));
                            buy_singapore.setText("MYR 1.00");
                            sell_singapore.setText("SGD "+String.valueOf(df.format(obj.getDouble("sgd_unit") / obj.getDouble("sgd_tt"))));

                            currentcy_hkg.setText(obj.getString("hkd_name"));
                            buy_hkg.setText("MYR 1.00");
                            sell_hkg.setText("HKD "+String.valueOf(df.format(obj.getDouble("hkd_unit") / obj.getDouble("hkd_tt"))));

                            /*currentcy_australia.setText(obj.getString("aud_name"));*/
                            buy_australia.setText("MYR 1.00");
                            sell_australia.setText("AUD "+String.valueOf(df.format(obj.getDouble("aud_unit") / obj.getDouble("aud_tt"))));

                            currentcy_thailand.setText(obj.getString("thb_name"));
                            buy_thailand.setText("MYR 1.00");
                            sell_thailand.setText("THB "+String.valueOf(df.format(obj.getDouble("thb_unit") / obj.getDouble("thb_tt"))));

                            currentcy_indonesia.setText(obj.getString("idr_name"));
                            buy_indonesia.setText("MYR 1.00");
                            sell_indonesia.setText("IDR "+String.valueOf(df.format(obj.getDouble("idr_unit") / obj.getDouble("idr_tt"))));

                            currentcy_india.setText(obj.getString("inr_name"));
                            buy_india.setText("MYR 1.00");
                            sell_india.setText("INR "+String.valueOf(df.format(obj.getDouble("inr_unit") / obj.getDouble("inr_tt"))));

                            currentcy_vietnam.setText(obj.getString("vnd_name"));
                            buy_vietnam.setText("MYR 1.00");
                            sell_vietnam.setText("VND "+String.valueOf(df.format(obj.getDouble("vnd_unit") / obj.getDouble("vnd_tt"))));

                            currentcy_china.setText(obj.getString("cny_name"));
                            buy_china.setText("MYR 1.00");
                            sell_china.setText("CNY "+String.valueOf(df.format(obj.getDouble("cny_unit") / obj.getDouble("cny_tt"))));


                            currentcy_srilanka.setText(obj.getString("srl_name"));
                            buy_srilanka.setText("MYR 1.00");
                            sell_srilanka.setText("SRL "+String.valueOf(df.format(obj.getDouble("srl_unit") / obj.getDouble("srl_tt"))));

                            currentcy_nepal.setText(obj.getString("npr_name"));
                            buy_nepal.setText("MYR 1.00");
                            sell_nepal.setText("NPR "+String.valueOf(df.format(obj.getDouble("npr_unit") / obj.getDouble("npr_tt"))));

                            currentcy_pakistan.setText(obj.getString("pkr_name"));
                            buy_pakistan.setText("MYR 1.00");
                            sell_pakistan.setText("PKR "+String.valueOf(df.format(obj.getDouble("pkr_unit") / obj.getDouble("pkr_tt"))));

                            currentcy_bangladesh.setText(obj.getString("bdt_name"));
                            buy_bangladesh.setText("MYR 1.00");
                            sell_bangladesh.setText("BDT "+String.valueOf(df.format(obj.getDouble("bdt_unit") / obj.getDouble("bdt_tt"))));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"Session Timeout",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}
