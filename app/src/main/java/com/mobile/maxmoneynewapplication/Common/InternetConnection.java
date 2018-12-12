package com.mobile.maxmoneynewapplication.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.net.InetAddress;

public class InternetConnection {
    public static final int STATUS_CONNECTED = 0 ;

    public static boolean isInternetAvailable(Context ctx){
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static int isInternetActiveWithPing() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = process.waitFor();
            return exitValue;
        } catch (Exception ex) {
            return -1;
        }
    }

    public static boolean isInternetActiveWithInetAddress() {
        try {
            InetAddress inetAddress = InetAddress.getByName("www.google.com");
            return inetAddress != null && !inetAddress.toString().equals("");
        } catch (Exception ex) {
            return false;
        }
    }

    public static void displayInternetConnectionMessage(Context ctx){
        Toast.makeText(ctx, "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }
}
