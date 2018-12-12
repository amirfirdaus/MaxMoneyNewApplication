package com.mobile.maxmoneynewapplication.Activity.MainActivityTabs;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.LoginActivity;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

public class ForeignExchange extends Fragment {
    SearchableSpinner spinner_branch;
    int totalBranch = 0;
    PreferenceManagerLogin session;
    ArrayAdapter<String> spinnerBranchAdapter;
    RadioGroup radio_group;
    LinearLayout linear_dropdown,linear_rates;
    String branch_code = "";


    TextView textView_unit,textView_buy,textView_sell;
    TextView currentcy_usd,unit_usd,sell_usd,buy_usd;  //usd
    TextView currentcy_british,unit_british,sell_british,buy_british;  //british
    TextView currentcy_singapore,unit_singapore,sell_singapore,buy_singapore;  //singapore
    TextView currentcy_japan,unit_japan,sell_japan,buy_japan;  //japan
    TextView currentcy_hkg,unit_hkg,sell_hkg,buy_hkg;  //HONGKONG
    TextView currentcy_australia,unit_australia,sell_australia,buy_australia;  //Australia
    TextView currentcy_thailand,unit_thailand,sell_thailand,buy_thailand;  //Thailand
    TextView currentcy_indonesia,unit_indonesia,sell_indonesia,buy_indonesia;  //INDONESIA
    TextView currentcy_india,unit_india,sell_india,buy_india;  //INDIA
    TextView currentcy_vietnam,unit_vietnam,sell_vietnam,buy_vietnam;  //VIETNAM
    TextView currentcy_philipi,unit_philipi,sell_philipi,buy_philipi;  //PHILIPI
    TextView currentcy_new,unit_new,sell_new,buy_new;  //NEW ZEALAND
    TextView currentcy_china,unit_china,sell_china,buy_china;  //china
    TextView currentcy_taiwan,unit_taiwan,sell_taiwan,buy_taiwan;  //taiwan
    TextView currentcy_swiss,unit_swiss,sell_swiss,buy_swiss;  //swiss
    TextView currentcy_canada,unit_canada,sell_canada,buy_canada;  //canada
    TextView currentcy_korea,unit_korea,sell_korea,buy_korea;  //korea
    TextView currentcy_saudi,unit_saudi,sell_saudi,buy_saudi;  //saudi
    TextView currentcy_euro,unit_euro,sell_euro,buy_euro;  //euro
    TextView currentcy_uae,unit_uae,sell_uae,buy_uae;  //uae
    TextView currentcy_qatar,unit_qatar,sell_qatar,buy_qatar;  //qatar
    TextView currentcy_sweden,unit_sweden,sell_sweden,buy_sweden;  //sweden
    TextView currentcy_srilanka,unit_srilanka,sell_srilanka,buy_srilanka;  //srilanka
    TextView currentcy_norway,unit_norway,sell_norway,buy_norway;  //norway
    TextView currentcy_denmark,unit_denmark,sell_denmark,buy_denmark;  //denmark
    TextView currentcy_southafrika,unit_southafrika,sell_southafrika,buy_southafrika;  //southafrika
    TextView currentcy_turkey,unit_turkey,sell_turkey,buy_turkey;  //turkey
    TextView currentcy_brunei,unit_brunei,sell_brunei,buy_brunei;  //brunei
    TextView currentcy_nepal,unit_nepal,sell_nepal,buy_nepal;  //nepal
    TextView currentcy_pakistan,unit_pakistan,sell_pakistan,buy_pakistan;  //pakistan
    TextView currentcy_bangladesh,unit_bangladesh,sell_bangladesh,buy_bangladesh;  //bangladesh

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foreign_exchange, container, false);

        session = new PreferenceManagerLogin(getContext());


        textView_unit = v.findViewById(R.id.textView_unit);
        textView_buy = v.findViewById(R.id.textView_buy);
        textView_sell = v.findViewById(R.id.textView_sell);

        spinner_branch = v.findViewById(R.id.spinner_branch);

        linear_dropdown = v.findViewById(R.id.linear_dropdown);
        linear_rates = v.findViewById(R.id.linear_rates);
        linear_rates.setVisibility(View.GONE);

        AllDeclareration(v);
        ChangeFontType();
        radio_group = v.findViewById(R.id.radio_group);

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio_group.findViewById(checkedId);
                int index = radio_group.indexOfChild(radioButton);
                switch (index) {
                    case 0: // counter rates
                        linear_dropdown.setVisibility(LinearLayout.VISIBLE);
                        linear_rates.setVisibility(View.VISIBLE);
                        getTotalBranch();
                        break;
                    case 1: // online rates
                        linear_dropdown.setVisibility(LinearLayout.GONE);
                        linear_rates.setVisibility(View.VISIBLE);
                        getForeignExchangeOnline();
                        break;
                }
            }
        });
        linear_dropdown.setVisibility(LinearLayout.GONE);
        linear_rates.setVisibility(View.VISIBLE);
        getForeignExchangeOnline();
        return v;
    }

    private void AllDeclareration(View v) {
        declareUSD(v);declareBritish(v);declareSingapore(v);declareJapan(v);
        declareHKG(v);declareAus(v);declareThai(v);declareIndon(v);
        declareIndia(v);declareVietnam(v);declarePhilipi(v);declareNew(v);
        declareChina(v);declareTaiwan(v);declareSwiss(v);declareCanada(v);
        declareKorea(v);declareSaudi(v);declareEuro(v);declareUae(v);
        declareQatar(v);declareSweden(v);declareSriLanka(v);declareNorway(v);
        declareDenmark(v);declareAfrika(v);declareTurkey(v);declareBrunei(v);
        declareNepal(v);declarePakistan(v);declareBangladesh(v);
    }
    private void declareBangladesh(View v) {
        currentcy_bangladesh   =  v.findViewById(R.id.currentcy_bangladesh);unit_bangladesh = v.findViewById(R.id.unit_bangladesh);
        sell_bangladesh= v.findViewById(R.id.sell_bangladesh);buy_bangladesh = v.findViewById(R.id.buy_bangladesh);
    }private void declarePakistan(View v) {
        currentcy_pakistan   =  v.findViewById(R.id.currentcy_pakistan);unit_pakistan = v.findViewById(R.id.unit_pakistan);
        sell_pakistan= v.findViewById(R.id.sell_pakistan);buy_pakistan = v.findViewById(R.id.buy_pakistan);
    }
    private void declareNepal(View v) {
        currentcy_nepal   =  v.findViewById(R.id.currentcy_nepal);unit_nepal = v.findViewById(R.id.unit_nepal);
        sell_nepal= v.findViewById(R.id.sell_nepal);buy_nepal = v.findViewById(R.id.buy_nepal);
    }private void declareBrunei(View v) {
        currentcy_brunei =  v.findViewById(R.id.currentcy_brunei);unit_brunei = v.findViewById(R.id.unit_brunei);
        sell_brunei= v.findViewById(R.id.sell_brunei);buy_brunei = v.findViewById(R.id.buy_brunei);
    }
    private void declareTurkey(View v) {
        currentcy_turkey  =  v.findViewById(R.id.currentcy_turkey);unit_turkey = v.findViewById(R.id.unit_turkey);
        sell_turkey= v.findViewById(R.id.sell_turkey);buy_turkey = v.findViewById(R.id.buy_turkey);
    }private void declareAfrika(View v) {
        currentcy_southafrika   =  v.findViewById(R.id.currentcy_southafrika);unit_southafrika = v.findViewById(R.id.unit_southafrika);
        sell_southafrika= v.findViewById(R.id.sell_southafrika);buy_southafrika = v.findViewById(R.id.buy_southafrika);
    }
    private void declareDenmark(View v) {
        currentcy_denmark =  v.findViewById(R.id.currentcy_denmark);unit_denmark = v.findViewById(R.id.unit_denmark);
        sell_denmark = v.findViewById(R.id.sell_denmark);buy_denmark = v.findViewById(R.id.buy_denmark);
    }private void declareNorway(View v) {
        currentcy_norway  =  v.findViewById(R.id.currentcy_norway);unit_norway = v.findViewById(R.id.unit_norway);
        sell_norway= v.findViewById(R.id.sell_norway);buy_norway = v.findViewById(R.id.buy_norway);
    }
    private void declareSriLanka(View v) {
        currentcy_srilanka   =  v.findViewById(R.id.currentcy_srilanka);unit_srilanka = v.findViewById(R.id.unit_srilanka);
        sell_srilanka= v.findViewById(R.id.sell_srilanka);buy_srilanka = v.findViewById(R.id.buy_srilanka);
    }private void declareSweden(View v) {
        currentcy_sweden  =  v.findViewById(R.id.currentcy_sweden);unit_sweden = v.findViewById(R.id.unit_sweden);
        sell_sweden= v.findViewById(R.id.sell_sweden);buy_sweden = v.findViewById(R.id.buy_sweden);
    }
    private void declareQatar(View v) {
        currentcy_qatar   =  v.findViewById(R.id.currentcy_qatar);unit_qatar = v.findViewById(R.id.unit_qatar);
        sell_qatar= v.findViewById(R.id.sell_qatar);buy_qatar = v.findViewById(R.id.buy_qatar);
    }private void declareUae(View v) {
        currentcy_uae   =  v.findViewById(R.id.currentcy_uae);unit_uae = v.findViewById(R.id.unit_uae);
        sell_uae= v.findViewById(R.id.sell_uae);buy_uae = v.findViewById(R.id.buy_uae);
    }
    private void declareEuro(View v) {
        currentcy_euro   =  v.findViewById(R.id.currentcy_euro);unit_euro = v.findViewById(R.id.unit_euro);
        sell_euro= v.findViewById(R.id.sell_euro);buy_euro = v.findViewById(R.id.buy_euro);
    }private void declareSaudi(View v) {
        currentcy_saudi   =  v.findViewById(R.id.currentcy_saudi);unit_saudi = v.findViewById(R.id.unit_saudi);
        sell_saudi = v.findViewById(R.id.sell_saudi);buy_saudi = v.findViewById(R.id.buy_saudi);
    }
    private void declareKorea(View v) {
        currentcy_korea   =  v.findViewById(R.id.currentcy_korea);unit_korea = v.findViewById(R.id.unit_korea);
        sell_korea = v.findViewById(R.id.sell_korea);buy_korea = v.findViewById(R.id.buy_korea);
    }private void declareCanada(View v) {
        currentcy_canada   =  v.findViewById(R.id.currentcy_canada);unit_canada = v.findViewById(R.id.unit_canada);
        sell_canada = v.findViewById(R.id.sell_canada);buy_canada = v.findViewById(R.id.buy_canada);
    }
    private void declareSwiss(View v) {
        currentcy_swiss   =  v.findViewById(R.id.currentcy_swiss);unit_swiss = v.findViewById(R.id.unit_swiss);
        sell_swiss = v.findViewById(R.id.sell_swiss);buy_swiss = v.findViewById(R.id.buy_swiss);
    }private void declareTaiwan(View v) {
        currentcy_taiwan   =  v.findViewById(R.id.currentcy_taiwan);unit_taiwan = v.findViewById(R.id.unit_taiwan);
        sell_taiwan = v.findViewById(R.id.sell_taiwan);buy_taiwan = v.findViewById(R.id.buy_taiwan);
    }
    private void declareChina(View v) {
        currentcy_china   =  v.findViewById(R.id.currentcy_china);unit_china = v.findViewById(R.id.unit_china);
        sell_china = v.findViewById(R.id.sell_china);buy_china = v.findViewById(R.id.buy_china);
    }private void declareNew(View v) {
        currentcy_new   =  v.findViewById(R.id.currentcy_new);unit_new = v.findViewById(R.id.unit_new);
        sell_new = v.findViewById(R.id.sell_new);buy_new = v.findViewById(R.id.buy_new);
    }
    private void declarePhilipi(View v) {
        currentcy_philipi   =  v.findViewById(R.id.currentcy_philipi);unit_philipi = v.findViewById(R.id.unit_philipi);
        sell_philipi = v.findViewById(R.id.sell_philipi);buy_philipi= v.findViewById(R.id.buy_philipi);
    }private void declareVietnam(View v) {
        currentcy_vietnam   =  v.findViewById(R.id.currentcy_vietnam);unit_vietnam = v.findViewById(R.id.unit_vietnam);
        sell_vietnam = v.findViewById(R.id.sell_vietnam);buy_vietnam = v.findViewById(R.id.buy_vietnam);
    }
    private void declareIndia(View v) {
        currentcy_india   =  v.findViewById(R.id.currentcy_india);unit_india = v.findViewById(R.id.unit_india);
        sell_india = v.findViewById(R.id.sell_india);buy_india = v.findViewById(R.id.buy_india);
    }private void declareIndon(View v) {
        currentcy_indonesia = v.findViewById(R.id.currentcy_indonesia);unit_indonesia = v.findViewById(R.id.unit_indonesia);
        sell_indonesia = v.findViewById(R.id.sell_indonesia);buy_indonesia = v.findViewById(R.id.buy_indonesia);
    }
    private void declareThai(View v) {
        currentcy_thailand  =  v.findViewById(R.id.currentcy_thailand);unit_thailand = v.findViewById(R.id.unit_thailand);
        sell_thailand = v.findViewById(R.id.sell_thailand);buy_thailand = v.findViewById(R.id.buy_thailand);
    }private void declareAus(View v) {
        currentcy_australia =  v.findViewById(R.id.currentcy_australia);unit_australia = v.findViewById(R.id.unit_australia);
        sell_australia = v.findViewById(R.id.sell_australia);buy_australia = v.findViewById(R.id.buy_australia);
    }
    private void declareHKG(View v) {
        currentcy_hkg =  v.findViewById(R.id.currentcy_hongkong);unit_hkg = v.findViewById(R.id.unit_hongkong);
        sell_hkg = v.findViewById(R.id.sell_hongkong);buy_hkg = v.findViewById(R.id.buy_hongkong);
    }private void declareJapan(View v) {
        currentcy_japan = v.findViewById(R.id.currentcy_japan);unit_japan = v.findViewById(R.id.unit_japan);
        sell_japan = v.findViewById(R.id.sell_japan);buy_japan = v.findViewById(R.id.buy_japan);
    }
    private void declareSingapore(View v) {
        currentcy_singapore = v.findViewById(R.id.currentcy_singapore);unit_singapore = v.findViewById(R.id.unit_singapore);
        sell_singapore = v.findViewById(R.id.sell_singapore);buy_singapore = v.findViewById(R.id.buy_singapore);
    }private void declareBritish(View v) {
        currentcy_british  =  v.findViewById(R.id.currentcy_british);unit_british = v.findViewById(R.id.unit_british);
        sell_british = v.findViewById(R.id.sell_british);buy_british = v.findViewById(R.id.buy_british);
    }
    private void declareUSD(View v) {
        currentcy_usd =  v.findViewById(R.id.currentcy_usd);buy_usd = v.findViewById(R.id.buy_usd);
        sell_usd = v.findViewById(R.id.sell_usd);unit_usd = v.findViewById(R.id.unit_usd);
    }

    private void ChangeFontType() {
        textView_unit.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Roman-12.ttf"));
        textView_buy.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Roman-12.ttf"));
        textView_sell.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Roman-12.ttf"));

        currentcy_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        buy_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        sell_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_usd.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_british.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        unit_british.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_british.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_british.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        unit_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_singapore.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_japan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "Avenir-Oblique-11.ttf"));
        unit_japan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_japan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_japan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_hkg.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_australia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_thailand.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_indonesia.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_india.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_vietnam.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_philipi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_philipi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_philipi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_philipi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_new.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_new.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_new.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_new.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_china.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_taiwan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_taiwan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_taiwan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_taiwan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_swiss.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_swiss.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_swiss.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_swiss.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_canada.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_canada.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_canada.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_canada.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_korea.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_korea.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_korea.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_korea.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_saudi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_saudi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_saudi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_saudi.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_euro.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_euro.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_euro.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_euro.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));


        currentcy_uae.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_uae.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_uae.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_uae.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_qatar.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_qatar.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_qatar.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_qatar.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_sweden.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_sweden.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_sweden.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_sweden.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_srilanka.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_norway.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_norway.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_norway.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_norway.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_denmark.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_denmark.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_denmark.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_denmark.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_southafrika.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_southafrika.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_southafrika.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_southafrika.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_turkey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_turkey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_turkey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_turkey.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_brunei.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_brunei.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_brunei.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_brunei.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_nepal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        buy_pakistan.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        currentcy_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        unit_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));
        sell_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

        buy_bangladesh.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"Avenir-Oblique-11.ttf"));

    }

    public void getTotalBranch(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/locations?status=active&country=Malaysia&type=BRANCH",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            totalBranch = obj.getInt("total");

                            if(totalBranch != 0){
                                getBranchSpinner();
                            }else{
                                Toast.makeText(getActivity(),"No Branch",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Session Timeout", Toast.LENGTH_LONG).show();
                        Intent next = new Intent(getActivity(), LoginActivity.class);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    public void getBranchSpinner(){
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/locations?status=active&country=Malaysia&type=BRANCH&size="+totalBranch,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            final JSONArray locationARR = new JSONArray(obj.getString("locations"));

                            final ArrayList<String> list = new ArrayList<String>();
                            for (int i = 0; i < locationARR.length(); i++) {
                                JSONObject locationOBJ = locationARR.getJSONObject(i);
                                list.add(locationOBJ.getString("name"));
                            }
                            spinnerBranchAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_layout_list_foreign, list);
                            spinner_branch.setTitle("Choose Branch");
                            spinner_branch.setAdapter(spinnerBranchAdapter);
                            spinner_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if(parent.getChildAt(0).toString() !=null) {
                                        ((TextView) parent.getChildAt(0)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Avenir-Roman-12.ttf"));
                                        ((TextView) parent.getChildAt(0)).setTextSize(14);
                                    }

                                    for (int i = 0; i < locationARR.length(); i++) {
                                        try {
                                            JSONObject locationOBJ = locationARR.getJSONObject(i);
                                            if(spinner_branch.getSelectedItem().toString().equals(locationOBJ.getString("name"))){
                                                branch_code = locationOBJ.getString("code");
                                                getForeignExchange();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> user = session.getUserDetails();
                String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key",token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void getForeignExchange() {
        String URL = BasedURL.ROOT_URL_API + "v1/boards/"+branch_code;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            currentcy_usd.setText(obj.getString("usd_name"));
                            unit_usd.setText(obj.getString("usd_unit"));
                            buy_usd.setText(obj.getString("usd_buy"));
                            sell_usd.setText(obj.getString("usd_sell"));

                            currentcy_british.setText(obj.getString("gbp_name"));
                            unit_british.setText(obj.getString("gbp_unit"));
                            buy_british.setText(obj.getString("gbp_buy"));
                            sell_british.setText(obj.getString("gbp_sell"));

                            currentcy_singapore.setText(obj.getString("sgd_name"));
                            unit_singapore.setText(obj.getString("sgd_unit"));
                            buy_singapore.setText(obj.getString("sgd_buy"));
                            sell_singapore.setText(obj.getString("sgd_sell"));

                            currentcy_japan.setText(obj.getString("jpy_name"));
                            unit_japan.setText(obj.getString("jpy_unit"));
                            buy_japan.setText(obj.getString("jpy_buy"));
                            sell_japan.setText(obj.getString("jpy_sell"));

                            currentcy_hkg.setText(obj.getString("hkd_name"));
                            unit_hkg.setText(obj.getString("hkd_unit"));
                            buy_hkg.setText(obj.getString("hkd_buy"));
                            sell_hkg.setText(obj.getString("hkd_sell"));

                            /*currentcy_australia.setText(obj.getString("aud_name"));*/
                            unit_australia.setText(obj.getString("aud_unit"));
                            sell_australia.setText(obj.getString("aud_buy"));
                            buy_australia.setText(obj.getString("aud_sell"));

                            currentcy_thailand.setText(obj.getString("thb_name"));
                            unit_thailand.setText(obj.getString("thb_unit"));
                            sell_thailand.setText(obj.getString("thb_sell"));
                            buy_thailand.setText(obj.getString("thb_buy"));

                            currentcy_indonesia.setText(obj.getString("idr_name"));
                            unit_indonesia.setText(obj.getString("idr_unit"));
                            sell_indonesia.setText(obj.getString("idr_sell"));
                            buy_indonesia.setText(obj.getString("idr_buy"));

                            currentcy_india.setText(obj.getString("inr_name"));
                            unit_india.setText(obj.getString("inr_unit"));
                            sell_india.setText(obj.getString("inr_sell"));
                            buy_india.setText(obj.getString("inr_buy"));

                            currentcy_vietnam.setText(obj.getString("vnd_name"));
                            unit_vietnam.setText(obj.getString("vnd_unit"));
                            sell_vietnam.setText(obj.getString("vnd_sell"));
                            buy_vietnam.setText(obj.getString("vnd_buy"));

                            currentcy_philipi.setText(obj.getString("php_name"));
                            unit_philipi.setText(obj.getString("php_unit"));
                            sell_philipi.setText(obj.getString("php_sell"));
                            buy_philipi.setText(obj.getString("php_buy"));

                            currentcy_new.setText(obj.getString("nzd_name"));
                            unit_new.setText(obj.getString("nzd_unit"));
                            sell_new.setText(obj.getString("nzd_sell"));
                            buy_new.setText(obj.getString("nzd_buy"));

                            currentcy_china.setText(obj.getString("cny_name"));
                            unit_china.setText(obj.getString("cny_unit"));
                            sell_china.setText(obj.getString("cny_sell"));
                            buy_china.setText(obj.getString("cny_buy"));

                            currentcy_taiwan.setText(obj.getString("twd_name"));
                            unit_taiwan.setText(obj.getString("twd_unit"));
                            sell_taiwan.setText(obj.getString("twd_sell"));
                            buy_taiwan.setText(obj.getString("twd_buy"));

                            currentcy_swiss.setText(obj.getString("chf_name"));
                            unit_swiss.setText(obj.getString("chf_unit"));
                            sell_swiss.setText(obj.getString("chf_sell"));
                            buy_swiss.setText(obj.getString("chf_buy"));

                            currentcy_canada.setText(obj.getString("cad_name"));
                            unit_canada.setText(obj.getString("cad_unit"));
                            sell_canada.setText(obj.getString("cad_sell"));
                            buy_canada.setText(obj.getString("cad_buy"));

                            currentcy_korea.setText(obj.getString("krw_name"));
                            unit_korea.setText(obj.getString("krw_unit"));
                            sell_korea.setText(obj.getString("krw_sell"));
                            buy_korea.setText(obj.getString("krw_buy"));

                            currentcy_saudi.setText(obj.getString("sar_name"));
                            unit_saudi.setText(obj.getString("sar_unit"));
                            sell_saudi.setText(obj.getString("sar_sell"));
                            buy_saudi.setText(obj.getString("sar_buy"));

                            currentcy_uae.setText(obj.getString("aed_name"));
                            unit_uae.setText(obj.getString("aed_unit"));
                            sell_uae.setText(obj.getString("aed_sell"));
                            buy_uae.setText(obj.getString("aed_buy"));

                            currentcy_qatar.setText(obj.getString("qat_name"));
                            unit_qatar.setText(obj.getString("qat_unit"));
                            sell_qatar.setText(obj.getString("qat_sell"));
                            buy_qatar.setText(obj.getString("qat_buy"));

                            currentcy_sweden.setText(obj.getString("sek_name"));
                            unit_sweden.setText(obj.getString("sek_unit"));
                            sell_sweden.setText(obj.getString("sek_sell"));
                            buy_sweden.setText(obj.getString("sek_buy"));

                            currentcy_srilanka.setText(obj.getString("srl_name"));
                            unit_srilanka.setText(obj.getString("srl_unit"));
                            sell_srilanka.setText(obj.getString("srl_sell"));
                            buy_srilanka.setText(obj.getString("srl_buy"));

                            currentcy_norway.setText(obj.getString("nok_name"));
                            unit_norway.setText(obj.getString("nok_unit"));
                            sell_norway.setText(obj.getString("nok_sell"));
                            buy_norway.setText(obj.getString("nok_buy"));

                            currentcy_denmark.setText(obj.getString("dkk_name"));
                            unit_denmark.setText(obj.getString("dkk_unit"));
                            sell_denmark.setText(obj.getString("dkk_sell"));
                            buy_denmark.setText(obj.getString("dkk_buy"));

                            currentcy_southafrika.setText(obj.getString("zar_name"));
                            unit_southafrika.setText(obj.getString("zar_unit"));
                            sell_southafrika.setText(obj.getString("zar_sell"));
                            buy_southafrika.setText(obj.getString("zar_buy"));

                            currentcy_turkey.setText(obj.getString("thb_name"));
                            unit_turkey.setText(obj.getString("thb_unit"));
                            sell_turkey.setText(obj.getString("thb_sell"));
                            buy_turkey.setText(obj.getString("thb_buy"));

                            currentcy_brunei.setText(obj.getString("bru_name"));
                            unit_brunei.setText(obj.getString("bru_unit"));
                            sell_brunei.setText(obj.getString("bru_sell"));
                            buy_brunei.setText(obj.getString("bru_buy"));

                            currentcy_nepal.setText(obj.getString("npr_name"));
                            unit_nepal.setText(obj.getString("npr_unit"));
                            sell_nepal.setText(obj.getString("npr_sell"));
                            buy_nepal.setText(obj.getString("npr_buy"));

                            currentcy_pakistan.setText(obj.getString("pkr_name"));
                            unit_pakistan.setText(obj.getString("pkr_unit"));
                            sell_pakistan.setText(obj.getString("pkr_sell"));
                            buy_pakistan.setText(obj.getString("pkr_buy"));

                            currentcy_bangladesh.setText(obj.getString("bdt_name"));
                            unit_bangladesh.setText(obj.getString("bdt_unit"));
                            sell_bangladesh.setText(obj.getString("bdt_sell"));
                            buy_bangladesh.setText(obj.getString("bdt_buy"));

                            currentcy_euro.setText(obj.getString("eur_name"));
                            unit_euro.setText(obj.getString("eur_unit"));
                            sell_euro.setText(obj.getString("eur_sell"));
                            buy_euro.setText(obj.getString("eur_buy"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap();
                headers.put("api-key","d7a8b8c03fbcb5ed77cba75892b11c5e2f14d632");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void getForeignExchangeOnline() {
        String URL = BasedURL.ROOT_URL_API + "v1/boards/moos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);

                            currentcy_usd.setText(obj.getString("usd_name"));
                            unit_usd.setText(obj.getString("usd_unit"));
                            buy_usd.setText(obj.getString("usd_buy"));
                            sell_usd.setText(obj.getString("usd_sell"));

                            currentcy_british.setText(obj.getString("gbp_name"));
                            unit_british.setText(obj.getString("gbp_unit"));
                            buy_british.setText(obj.getString("gbp_buy"));
                            sell_british.setText(obj.getString("gbp_sell"));

                            currentcy_singapore.setText(obj.getString("sgd_name"));
                            unit_singapore.setText(obj.getString("sgd_unit"));
                            buy_singapore.setText(obj.getString("sgd_buy"));
                            sell_singapore.setText(obj.getString("sgd_sell"));

                            currentcy_japan.setText(obj.getString("jpy_name"));
                            unit_japan.setText(obj.getString("jpy_unit"));
                            buy_japan.setText(obj.getString("jpy_buy"));
                            sell_japan.setText(obj.getString("jpy_sell"));

                            currentcy_hkg.setText(obj.getString("hkd_name"));
                            unit_hkg.setText(obj.getString("hkd_unit"));
                            buy_hkg.setText(obj.getString("hkd_buy"));
                            sell_hkg.setText(obj.getString("hkd_sell"));

                            /*currentcy_australia.setText(obj.getString("aud_name"));*/
                            unit_australia.setText(obj.getString("aud_unit"));
                            sell_australia.setText(obj.getString("aud_buy"));
                            buy_australia.setText(obj.getString("aud_sell"));

                            currentcy_thailand.setText(obj.getString("thb_name"));
                            unit_thailand.setText(obj.getString("thb_unit"));
                            sell_thailand.setText(obj.getString("thb_sell"));
                            buy_thailand.setText(obj.getString("thb_buy"));

                            currentcy_indonesia.setText(obj.getString("idr_name"));
                            unit_indonesia.setText(obj.getString("idr_unit"));
                            sell_indonesia.setText(obj.getString("idr_sell"));
                            buy_indonesia.setText(obj.getString("idr_buy"));

                            currentcy_india.setText(obj.getString("inr_name"));
                            unit_india.setText(obj.getString("inr_unit"));
                            sell_india.setText(obj.getString("inr_sell"));
                            buy_india.setText(obj.getString("inr_buy"));

                            currentcy_vietnam.setText(obj.getString("vnd_name"));
                            unit_vietnam.setText(obj.getString("vnd_unit"));
                            sell_vietnam.setText(obj.getString("vnd_sell"));
                            buy_vietnam.setText(obj.getString("vnd_buy"));

                            currentcy_philipi.setText(obj.getString("php_name"));
                            unit_philipi.setText(obj.getString("php_unit"));
                            sell_philipi.setText(obj.getString("php_sell"));
                            buy_philipi.setText(obj.getString("php_buy"));

                            currentcy_new.setText(obj.getString("nzd_name"));
                            unit_new.setText(obj.getString("nzd_unit"));
                            sell_new.setText(obj.getString("nzd_sell"));
                            buy_new.setText(obj.getString("nzd_buy"));

                            currentcy_china.setText(obj.getString("cny_name"));
                            unit_china.setText(obj.getString("cny_unit"));
                            sell_china.setText(obj.getString("cny_sell"));
                            buy_china.setText(obj.getString("cny_buy"));

                            currentcy_taiwan.setText(obj.getString("twd_name"));
                            unit_taiwan.setText(obj.getString("twd_unit"));
                            sell_taiwan.setText(obj.getString("twd_sell"));
                            buy_taiwan.setText(obj.getString("twd_buy"));

                            currentcy_swiss.setText(obj.getString("chf_name"));
                            unit_swiss.setText(obj.getString("chf_unit"));
                            sell_swiss.setText(obj.getString("chf_sell"));
                            buy_swiss.setText(obj.getString("chf_buy"));

                            currentcy_canada.setText(obj.getString("cad_name"));
                            unit_canada.setText(obj.getString("cad_unit"));
                            sell_canada.setText(obj.getString("cad_sell"));
                            buy_canada.setText(obj.getString("cad_buy"));

                            currentcy_korea.setText(obj.getString("krw_name"));
                            unit_korea.setText(obj.getString("krw_unit"));
                            sell_korea.setText(obj.getString("krw_sell"));
                            buy_korea.setText(obj.getString("krw_buy"));

                            currentcy_saudi.setText(obj.getString("sar_name"));
                            unit_saudi.setText(obj.getString("sar_unit"));
                            sell_saudi.setText(obj.getString("sar_sell"));
                            buy_saudi.setText(obj.getString("sar_buy"));

                            currentcy_uae.setText(obj.getString("aed_name"));
                            unit_uae.setText(obj.getString("aed_unit"));
                            sell_uae.setText(obj.getString("aed_sell"));
                            buy_uae.setText(obj.getString("aed_buy"));

                            currentcy_qatar.setText(obj.getString("qat_name"));
                            unit_qatar.setText(obj.getString("qat_unit"));
                            sell_qatar.setText(obj.getString("qat_sell"));
                            buy_qatar.setText(obj.getString("qat_buy"));

                            currentcy_sweden.setText(obj.getString("sek_name"));
                            unit_sweden.setText(obj.getString("sek_unit"));
                            sell_sweden.setText(obj.getString("sek_sell"));
                            buy_sweden.setText(obj.getString("sek_buy"));

                            currentcy_srilanka.setText(obj.getString("srl_name"));
                            unit_srilanka.setText(obj.getString("srl_unit"));
                            sell_srilanka.setText(obj.getString("srl_sell"));
                            buy_srilanka.setText(obj.getString("srl_buy"));

                            currentcy_norway.setText(obj.getString("nok_name"));
                            unit_norway.setText(obj.getString("nok_unit"));
                            sell_norway.setText(obj.getString("nok_sell"));
                            buy_norway.setText(obj.getString("nok_buy"));

                            currentcy_denmark.setText(obj.getString("dkk_name"));
                            unit_denmark.setText(obj.getString("dkk_unit"));
                            sell_denmark.setText(obj.getString("dkk_sell"));
                            buy_denmark.setText(obj.getString("dkk_buy"));

                            currentcy_southafrika.setText(obj.getString("zar_name"));
                            unit_southafrika.setText(obj.getString("zar_unit"));
                            sell_southafrika.setText(obj.getString("zar_sell"));
                            buy_southafrika.setText(obj.getString("zar_buy"));

                            currentcy_turkey.setText(obj.getString("thb_name"));
                            unit_turkey.setText(obj.getString("thb_unit"));
                            sell_turkey.setText(obj.getString("thb_sell"));
                            buy_turkey.setText(obj.getString("thb_buy"));

                            currentcy_brunei.setText(obj.getString("bru_name"));
                            unit_brunei.setText(obj.getString("bru_unit"));
                            sell_brunei.setText(obj.getString("bru_sell"));
                            buy_brunei.setText(obj.getString("bru_buy"));

                            currentcy_nepal.setText(obj.getString("npr_name"));
                            unit_nepal.setText(obj.getString("npr_unit"));
                            sell_nepal.setText(obj.getString("npr_sell"));
                            buy_nepal.setText(obj.getString("npr_buy"));

                            currentcy_pakistan.setText(obj.getString("pkr_name"));
                            unit_pakistan.setText(obj.getString("pkr_unit"));
                            sell_pakistan.setText(obj.getString("pkr_sell"));
                            buy_pakistan.setText(obj.getString("pkr_buy"));

                            currentcy_bangladesh.setText(obj.getString("bdt_name"));
                            unit_bangladesh.setText(obj.getString("bdt_unit"));
                            sell_bangladesh.setText(obj.getString("bdt_sell"));
                            buy_bangladesh.setText(obj.getString("bdt_buy"));

                            currentcy_euro.setText(obj.getString("eur_name"));
                            unit_euro.setText(obj.getString("eur_unit"));
                            sell_euro.setText(obj.getString("eur_sell"));
                            buy_euro.setText(obj.getString("eur_buy"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(),"Session Timeout",Toast.LENGTH_LONG).show();
                        Intent next = new Intent(getActivity(),LoginActivity.class);
                        startActivity(next);
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