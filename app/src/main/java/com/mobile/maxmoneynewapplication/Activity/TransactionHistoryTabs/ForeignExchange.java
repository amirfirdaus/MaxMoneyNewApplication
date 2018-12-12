package com.mobile.maxmoneynewapplication.Activity.TransactionHistoryTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Utils.PreferenceManagerLogin;
import com.mobile.maxmoneynewapplication.Utils.TransactionHistoryForeignAdapter;

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
public class ForeignExchange extends Fragment  implements AbsListView.OnScrollListener{
    private PreferenceManagerLogin session;
    ListView list;
    private ArrayList<Map<String, String>> data = null;
    private TransactionHistoryForeignAdapter mPLAdapter;
    private int page = 0;
    private int currentPagination = 1;
    private int total = 100; // assume that we have 100 data.
    private boolean isLoading = false;
    ProgressBar progressBar;
    ImageView imageView; // new ll

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_foreign_exchange2, container, false);

        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);
        list = v.findViewById(R.id.listView);
        session = new PreferenceManagerLogin(getContext().getApplicationContext());

        imageView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        data = new ArrayList<Map<String, String>>(); // data
        mPLAdapter = new TransactionHistoryForeignAdapter(getContext(), data);
        list.setAdapter(mPLAdapter);
        list.setOnScrollListener(this);

        getTotal();
        return v;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount >0 && list != null) {
            int lastPosition = list.getLastVisiblePosition();
            if (lastPosition + 1 == totalItemCount && data.size() < total) {
                currentPagination++;
                getHistory();
            }
        }
    }

    private void getTotal() {
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current?product=omoe&status=Payment%20failed&page=1",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            total = Integer.parseInt(obj1.getString("total"));
                            Log.d("total data omoe", String.valueOf(total));
                            getHistory();

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

    private void getHistory() {
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current?product=omoe&status=Payment%20failed&page="+currentPagination,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.INVISIBLE);

                            JSONObject obj1 = new JSONObject(response);
                            JSONArray array = obj1.getJSONArray("orders");

                            for(int i = 0; i< array.length(); i++){
                                JSONObject product = array.getJSONObject(i);
                                JSONObject receiptsOBJ = new JSONObject(product.getString("receipts"));
                                JSONObject twoOBJ = new JSONObject(receiptsOBJ.getString("1"));

                                Map<String, String> addData = new HashMap<String, String>();
                                addData.put("msg1", product.getString("orderDate"));
                                addData.put("msg2", twoOBJ.getString("txnCurrency") +" "+twoOBJ.getString("transactionAmount"));
                                addData.put("msg3",  product.getString("status").toUpperCase());
                                addData.put("msg4", product.getString("id"));

                                data.add(addData);
                            }
                            isLoading = false;
                            mPLAdapter.refreshData(data);

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
}
