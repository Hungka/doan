package com.example.tranmanhhung.myplacearound;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by TranManhHung on 27-Mar-16.
 */
public class AlertDialogInfor extends DialogFragment {

    public Context mContext;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Danh Sach");
        alertDialogBuilder.setMessage("Are you sure?");
        return super.onCreateDialog(savedInstanceState);


    }
}
