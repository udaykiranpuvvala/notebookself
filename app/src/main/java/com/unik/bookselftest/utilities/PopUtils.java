package com.unik.bookselftest.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.unik.bookselftest.R;

import java.util.ArrayList;

public class PopUtils {

    public static Dialog dialog;

    public static void dialogListShowEditText(Context mContext, ArrayList arrayList, String mTitle, final EditText mTextView,
                                              final ReturnValue returnValue) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
        } else {
            dialog = new Dialog(mContext);
        }
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.white);
        dialog.setContentView(R.layout.dialog_popup);
        dialog.setTitle(mTitle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
// TODO Auto-generated method stub
//                Toast.makeText(RegisterActivity.this,
//                        "OnCancelListener",
//                        Toast.LENGTH_LONG).show();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
// TODO Auto-generated method stub
//                Toast.makeText(RegisterActivity.this,
//                        "OnDismissListener",
//                        Toast.LENGTH_LONG).show();
            }
        });

//Prepare ListView in dialog
        ListView dialog_ListView = (ListView) dialog.findViewById(R.id.dialog_listview);
        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, arrayList);
        dialog_ListView.setAdapter(adapter);
        dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
// TODO Auto-generated method stub
//                Toast.makeText(RegisterActivity.this,
//                        parent.getItemAtPosition(position).toString() + " clicked",
//                        Toast.LENGTH_LONG).show();
//                value = parent.getItemAtPosition(position).toString();

                returnValue.returnValues(parent.getItemAtPosition(position).toString(), position);

                mTextView.setText(parent.getItemAtPosition(position).toString());
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}
