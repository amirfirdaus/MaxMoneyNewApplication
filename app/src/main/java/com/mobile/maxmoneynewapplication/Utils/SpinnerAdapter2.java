package com.mobile.maxmoneynewapplication.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.maxmoneynewapplication.R;
import com.mobile.maxmoneynewapplication.Model.CountrySecondModel;

import java.util.List;

public class SpinnerAdapter2 extends BaseAdapter {

    private Context context;
    private List<CountrySecondModel> countrySecondModelList;

    public SpinnerAdapter2(Context context, List<CountrySecondModel> countrySecondModelList) {
        this.context = context;
        this.countrySecondModelList = countrySecondModelList;
    }

    @Override
    public int getCount() {
        return countrySecondModelList.size();
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
        view = layoutInflater.inflate(R.layout.list_spinner_country_2, null);

        ImageView imageView = view.findViewById(R.id.ivFlag2);
        TextView titleTextView = view.findViewById(R.id.ivCountry2);

        CountrySecondModel countrySecondModelModelModel = countrySecondModelList.get(i);

        imageView.setImageResource(countrySecondModelModelModel.getImage());
        titleTextView.setText(countrySecondModelModelModel.getTitle());
        return view;
    }
}