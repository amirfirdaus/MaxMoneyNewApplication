package com.mobile.maxmoneynewapplication.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mobile.maxmoneynewapplication.Activity.TransactionHistoryTabs.ForeignExchange;
import com.mobile.maxmoneynewapplication.Activity.TransactionHistoryTabs.MoneyTransfer;

public class PagerAdapterTransactionHistory extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterTransactionHistory(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ForeignExchange foreignFragment = new ForeignExchange();
                return foreignFragment;
            case 1:
                MoneyTransfer moneyFragment = new MoneyTransfer();
                return moneyFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}