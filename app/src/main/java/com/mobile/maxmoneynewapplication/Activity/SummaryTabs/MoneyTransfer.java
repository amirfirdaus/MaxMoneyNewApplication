package com.mobile.maxmoneynewapplication.Activity.SummaryTabs;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.mobile.maxmoneynewapplication.Utils.SummaryMoneyPagingListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoneyTransfer extends Fragment implements AbsListView.OnScrollListener{


    ListView listMoney;
    private ArrayList<Map<String, String>> dataMoney = null;
    private SummaryMoneyPagingListAdapter mAdapterMoney;
    private int page = 0;
    private int currentPagination = 1;
    private int total = 100; // assume that we have 100 data.
    private boolean isLoading = false;
    PreferenceManagerLogin session;
    Spinner spinner_statusMoney;
    String spinner_select_money;
    TextView textView_chooseStatusMoney;
    ImageView imageViewMoney;
    ProgressBar progressBarMoney;

    public MoneyTransfer() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money_transfer3, container, false);

        session = new PreferenceManagerLogin(getContext());
        listMoney = v.findViewById(R.id.listViewMoney);
        imageViewMoney = v.findViewById(R.id.imageViewMoney);
        progressBarMoney = v.findViewById(R.id.progressBarMoney);
        spinner_statusMoney = v.findViewById(R.id.spinner_status_money);
        textView_chooseStatusMoney = v.findViewById(R.id.textView_chooseStatusMoney);
        textView_chooseStatusMoney.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Avenir-Roman-12.ttf"));
        progressBarMoney.setVisibility(View.INVISIBLE);
        imageViewMoney.setVisibility(View.INVISIBLE);
        dataMoney = new ArrayList<>();
        mAdapterMoney = new SummaryMoneyPagingListAdapter(getContext(), dataMoney);
        listMoney.setAdapter(mAdapterMoney);
        listMoney.setOnScrollListener(this);
        spinner_statusMoney.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_statusMoney.getSelectedItem().toString().equals("Submitted")){
                    spinner_select_money = "Submitted,Processing,Remitted";
                }
                if(spinner_statusMoney.getSelectedItem().toString().equals("Ready For PickUp")){
                    spinner_select_money = "Ready+for+pickup,Expired";
                }
                if(spinner_statusMoney.getSelectedItem().toString().equals("Completed")){
                    spinner_select_money = "Completed";
                }
                getTotalMoney();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        return v;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount >0 && listMoney != null) {
            int lastPosition = listMoney.getLastVisiblePosition();
            if (lastPosition + 1 == totalItemCount && dataMoney.size() < total)
            {
                currentPagination++;

            }
        }
    }
    private void getTotalMoney()
    {
        StringRequest stringRequest = new StringRequest(GET,BasedURL.ROOT_URL_API+"v1/orders/current?product=omor&page=1&status="+
                spinner_select_money, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj1 = new JSONObject(response);
                    total = Integer.parseInt(obj1.getString("total"));
                    dataMoney = new ArrayList<Map<String, String>>();
                    Toast.makeText(getActivity(),"total ="+total,Toast.LENGTH_SHORT).show();
                    if(total == 0){
                        mAdapterMoney.refreshData(null);
                        Toast.makeText(getActivity(),"No Data",Toast.LENGTH_SHORT).show();
                    }else{
                        getDetailsMoney();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection !!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void getDetailsMoney()
    {
        progressBarMoney.setVisibility(View.INVISIBLE);
        imageViewMoney.setVisibility(View.INVISIBLE);

        if(!isLoading)
        {
            StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current?product=omor&page="+currentPagination+"&status="+spinner_select_money,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBarMoney.setVisibility(View.INVISIBLE);
                            imageViewMoney.setVisibility(View.INVISIBLE);
                            try {
                                JSONObject obj1 = new JSONObject(response);
                                JSONArray arrayORDER = new JSONArray(obj1.getString("orders"));


                                mAdapterMoney.refreshData(null);
                                for(int i = 0; i< arrayORDER.length(); i++){
                                    JSONObject orderOBJ = arrayORDER.getJSONObject(i);
                                    Map<String, String> addData = new HashMap<String, String>();
                                    addData.put("msg1", orderOBJ.getString("id"));
                                    addData.put("msg2", orderOBJ.getString("created"));
                                    addData.put("msg3", orderOBJ.getString("total"));
                                    dataMoney.add(addData);
                                }

                                isLoading = false;
                                mAdapterMoney.refreshData(dataMoney);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBarMoney.setVisibility(View.INVISIBLE);
                            imageViewMoney.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext().getApplicationContext(), "Please check Internet Connection !!", Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> user = session.getUserDetails();
                    String token = user.get(PreferenceManagerLogin.KEY_TOKEN);
                    HashMap<String, String> headers = new HashMap();
                    headers.put("api-key",token);
                    Log.d("key",headers.toString());
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
            requestQueue.add(stringRequest);

        }
    }

}
