package com.mobile.maxmoneynewapplication.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mobile.maxmoneynewapplication.Activity.AddEditBeneficiary;
import com.mobile.maxmoneynewapplication.Activity.LoginActivity;
import com.mobile.maxmoneynewapplication.Activity.MyBeneficiary;
import com.mobile.maxmoneynewapplication.Common.BasedURL;
import com.mobile.maxmoneynewapplication.Model.BenificiariesClass;
import com.mobile.maxmoneynewapplication.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.DELETE;

public class BeneficiariesMyProfileAdapter extends RecyclerView.Adapter<BeneficiariesMyProfileAdapter.ProductViewHolder> {

    private PreferenceManagerLogin session;
    private Context mCtx;
    private List<BenificiariesClass> beneficiaryList;

    public BeneficiariesMyProfileAdapter(Context mCtx, List<BenificiariesClass> beneficiaryList) {
        this.mCtx = mCtx;
        this.beneficiaryList = beneficiaryList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_list_my_beneficiary, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        String beneficiary_id = "";
        BenificiariesClass beneficiary = beneficiaryList.get(position);

        beneficiary_id = beneficiary.getId();
        holder.textView_fullname.setText(beneficiary.getFullName());
        holder.textView_country.setText(beneficiary.getCountry());
        holder.textView_relationship.setText(beneficiary.getRelationShip());

        final String finalBeneficiary_id = beneficiary_id;
        holder.image_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, AddEditBeneficiary.class);
                intent.putExtra("beneficiary_id", finalBeneficiary_id);
                mCtx.startActivity(intent);
            }
        });

        final String finalBeneficiary_id1 = beneficiary_id;
        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog diaBox = AskOption(finalBeneficiary_id1);
                diaBox.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return beneficiaryList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView textView_fullname,textView_country,textView_relationship;
        ImageView image_edit,imageView_delete;

        public ProductViewHolder(View itemView) {
            super(itemView);

            session = new PreferenceManagerLogin(mCtx);

            textView_fullname = itemView.findViewById(R.id.textView_fullname);
            textView_country = itemView.findViewById(R.id.textView_country);
            textView_relationship= itemView.findViewById(R.id.textView_relationship);
            image_edit = itemView.findViewById(R.id.image_edit);
            imageView_delete = itemView.findViewById(R.id.imageView_delete);

            textView_fullname.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "Avenir-Roman-12.ttf"));
            textView_country.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "Avenir-Roman-12.ttf"));
            textView_relationship.setTypeface(Typeface.createFromAsset(mCtx.getAssets(), "Avenir-Roman-12.ttf"));

        }
    }

    private AlertDialog AskOption(final String beneficiary_id) {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(mCtx)
                //set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure want to delete this beneficiaries?")
                /*.setIcon(R.drawable.icondeletebeneficiary)*/

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int whichButton) {
                        deleteBeneficiary(beneficiary_id);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Intent next = new Intent(mCtx,MyBeneficiary.class);
                                mCtx.startActivity(next);
                            }
                        }, 1000);
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }


    private void deleteBeneficiary(String id){
        StringRequest stringRequest = new StringRequest(DELETE, BasedURL.ROOT_URL_API +"v1/users/current/beneficiaries/"+id,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mCtx, "successfully delete", Toast.LENGTH_LONG).show();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, "Please check Internet Connection !!", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
    }

}