package com.example.appmsgraph.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;

import com.example.appmsgraph.R;

public class CustomDialogUpdate extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.custom_dialog_update, null))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Appel network for POST data

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
//        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.button_ok_update_dialog);
//        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.button_ok_update_dialog);
//        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
//        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
    }
}
