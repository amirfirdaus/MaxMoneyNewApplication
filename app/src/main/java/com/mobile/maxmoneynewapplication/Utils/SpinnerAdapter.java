package com.mobile.maxmoneynewapplication.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.maxmoneynewapplication.Model.CountryFirstModel;
import com.mobile.maxmoneynewapplication.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<CountryFirstModel> countryFirstModelList;

    public SpinnerAdapter(Context context, List<CountryFirstModel> countryFirstModelList) {
        this.context = context;
        this.countryFirstModelList = countryFirstModelList;
    }

    @Override
    public int getCount() {
        return countryFirstModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.list_spinner_country, null);

        ImageView imageView = view.findViewById(R.id.ivFlag);
        TextView titleTextView = view.findViewById(R.id.ivCountry);

        CountryFirstModel countryFirstModelModel = countryFirstModelList.get(i);

        imageView.setImageResource(countryFirstModelModel.getImage());
        titleTextView.setText(countryFirstModelModel.getTitle());
        return view;
    }
}