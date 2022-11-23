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
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button edit, del;
    String message;
    String sendername;

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

                StringRequest request=new StringRequest(
                        Request.Method.POST,
                        "http://192.168.10.5/smdass3/deletemessage.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj=new JSONObject(response);
                                    if(obj.getInt("code")==1)
                                    {
                                        Toast.makeText(c,"Message Deleted Successfully",Toast.LENGTH_LONG).show();
                                        //finish();
                                    }
                                    else{
                                        Toast.makeText(
                                                c,
                                                obj.get("msg").toString()
                                                ,Toast.LENGTH_LONG
                                        ).show();
                                    }
                                   // messageEdit.setText("");
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    Toast.makeText(
                                            c,
                                            response
                                            ,Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(
                                        c,
                                        error.toString().trim()
                                        , Toast.LENGTH_LONG
                                ).show();
                            }
                        })
                {

                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params=new HashMap<>();

                        params.put("Message",message);
                        //params.put("Message2",messageEdit.getText().toString());
                        //params.put("RecieverName",un);
                        //params.put("CurrTime",time2);
                        // params.put("PhoneNum",phonenum.getText().toString());

                        return params;
                    }
                };


                RequestQueue queue= Volley.newRequestQueue(c);
                queue.add(request);





                break;
            default:
                break;
        }
        dismiss();
    }
}
