package com.gautam.upcomingmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.gautam.upcomingmovies.R;

/**
 * Created by Gautam on 04/12/2017.
 */

public class CommonCode {
    private Context mContext;

    public CommonCode(Context context) {
        mContext = context;
    }

    public boolean checkInternetConnectivity() {
        boolean check = false;
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            check = true;
        }
        return check;
    }

    public void ShowDialog(String msg) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
        builder.setMessage(msg);
        builder.setPositiveButton(mContext.getString(R.string.ok), null);
        builder.show();
    }
}
