package com.mobile.maxmoneynewapplication.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobile.maxmoneynewapplication.Activity.SummaryTabs.ForeignInvoice.ForeignInvoiceDetails;
import com.mobile.maxmoneynewapplication.Activity.SummaryTabs.MoneyInvoice.MoneyInvoiceDetails;
import com.mobile.maxmoneynewapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class SummaryMoneyPagingListAdapter extends BaseAdapter {

    private ArrayList<Map<String, String>> data = null;
    private Context context = null;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView tv1;
        TextView tv2;
        TextView tv3;
        ImageView tv4;
    }

    public SummaryMoneyPagingListAdapter(Context context, ArrayList<Map<String, String>> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return (data == null) ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_summary_money_list, null);
            holder = new ViewHolder();
            holder.tv1 = view.findViewById(R.id.textView_orderid);
            holder.tv2 = view.findViewById(R.id.textView_date);
            holder.tv3 = view.findViewById(R.id.textView_total);
            holder.tv4 = view.findViewById(R.id.imageView_seeMore);


            holder.tv1.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-Roman-12.ttf"));
            holder.tv2.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-Roman-12.ttf"));
            holder.tv3.setTypeface(Typeface.createFromAsset(context.getAssets(), "Avenir-Roman-12.ttf"));
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv1.setText(data.get(position).get("msg1"));
        holder.tv2.setText(createDate(Long.parseLong(data.get(position).get("msg2"))));
        holder.tv3.setText("RM "+data.get(position).get("msg3"));


        final String SummaryForeignInvoice  = data.get(position).get("msg4");
        holder.tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ForeignInvoiceDetails.class);
                intent.putExtra("orderId", SummaryForeignInvoice);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public void refreshData(ArrayList<Map<String, String>> newData) {
        this.data = newData;
        this.notifyDataSetChanged();
    }

    public CharSequence createDate(long timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        Date d = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(d);
    }
}