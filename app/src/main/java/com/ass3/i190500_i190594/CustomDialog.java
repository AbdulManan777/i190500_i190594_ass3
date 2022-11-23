package com.ass3.i190500_i190594;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button edit, del;
    String message;

    public CustomDialog(Activity a, String message) {
        super(a);

        this.c = a;
        this.message=message;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_message_custom_dialog);
        edit = (Button) findViewById(R.id.Edit_Button);
        del = (Button) findViewById(R.id.del_Button);
        edit.setOnClickListener(this);
        del.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Edit_Button:
                Intent i=new Intent(c,EditMessageActivity.class);
                i.putExtra("message",message);
                c.startActivity(i);
                break;
            case R.id.del_Button:
                Toast.makeText(c,"Del Clicked",Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        dismiss();
    }
}
