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
import com.mobile.maxmoneynewapplication.Utils.SummaryForeignPagingListAdapter;

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
public class ForeignExchange extends Fragment implements AbsListView.OnScrollListener{

    ListView list;
    private ArrayList<Map<String, String>> data = null;
    private SummaryForeignPagingListAdapter mAdapterForeign;
    private int page = 0;
    private int currentPagination = 1;
    private int total = 100; // assume that we have 300 data.
    private boolean isLoading = false;
    PreferenceManagerLogin session;
    Spinner spinner_status;
    String spinner_select;
    TextView textView_chooseStatus;
    ImageView imageView;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foreign_exchange3, container, false);
        session = new PreferenceManagerLogin(getContext());
        list = v.findViewById(R.id.listView);
        imageView = v.findViewById(R.id.imageView);
        progressBar = v.findViewById(R.id.progressBar);
        spinner_status = v.findViewById(R.id.spinner_status);
        textView_chooseStatus = v.findViewById(R.id.textView_chooseStatus);
        textView_chooseStatus.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "Avenir-Roman-12.ttf"));
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.INVISIBLE);
        data = new ArrayList<Map<String, String>>(); // data
        mAdapterForeign = new SummaryForeignPagingListAdapter(getContext(), data);
        list.setAdapter(mAdapterForeign);
        list.setOnScrollListener(this);

        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner_status.getSelectedItem().toString().equals("Submitted")){
                    spinner_select = "Submitted,Processing,Remitted";
                }
                if(spinner_status.getSelectedItem().toString().equals("Ready For PickUp")){
                    spinner_select = "Pending+payment+failed";
                }
                if(spinner_status.getSelectedItem().toString().equals("Completed")){
                    spinner_select = "Completed";
                }
                getTotal();
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
        if (totalItemCount >0 && list != null) {
            int lastPosition = list.getLastVisiblePosition();
            if (lastPosition + 1 == totalItemCount && data.size() < total) {
                currentPagination++;
                getDetails();
            }
        }
    }

    private void getTotal() {
        StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current?product=omoe&page=1&status="+spinner_select,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            total = Integer.parseInt(obj1.getString("total"));
                            data = new ArrayList<Map<String, String>>();
                            Toast.makeText(getActivity(),"total ="+total,Toast.LENGTH_SHORT).show();
                            if(total > 0){
                                getDetails();

                            }else{
                                mAdapterForeign.refreshData(null);
                                Toast.makeText(getActivity(),"No Data",Toast.LENGTH_SHORT).show();

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

    private void getDetails(){
        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);
        if (!isLoading) {
            StringRequest stringRequest = new StringRequest(GET, BasedURL.ROOT_URL_API +"v1/orders/current?product=omoe&page="+currentPagination+"&status="+spinner_select,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.INVISIBLE);
                            try {
                                JSONObject obj1 = new JSONObject(response);
                                JSONArray arrayORDER = new JSONArray(obj1.getString("orders"));


                                mAdapterForeign.refreshData(null);
                                for(int i = 0; i< arrayORDER.length(); i++){
                                    JSONObject orderOBJ = arrayORDER.getJSONObject(i);
                                    Map<String, String> addData = new HashMap<String, String>();
                                    addData.put("msg1", orderOBJ.getString("id"));
                                    addData.put("msg2", orderOBJ.getString("created"));
                                    addData.put("msg3", orderOBJ.getString("total"));
                                    data.add(addData);
                                }

                                isLoading = false;
                                mAdapterForeign.refreshData(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.INVISIBLE);
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

