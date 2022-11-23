package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditMessageActivity extends AppCompatActivity {

    TextView Username;
    EditText messageEdit;
    ImageView sendMessage;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);

        Username=findViewById(R.id.user_Name1);
        messageEdit=findViewById(R.id.msg1);

        sendMessage=findViewById(R.id.submit_msg1);
        message=getIntent().getStringExtra("message");

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!messageEdit.getText().toString().equals("")){


                    StringRequest request=new StringRequest(
                            Request.Method.POST,
                            "http://"+IP_server.getIP_server()+"/smdass3/edit.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);
                                        if(obj.getInt("code")==1)
                                        {
                                            Toast.makeText(getApplicationContext(),"Message Updated Successfully",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    EditMessageActivity.this,
                                                    obj.get("msg").toString()
                                                    ,Toast.LENGTH_LONG
                                            ).show();
                                        }
                                        messageEdit.setText("");
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(
                                                EditMessageActivity.this,
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
                                            EditMessageActivity.this,
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
                            params.put("Message2",messageEdit.getText().toString());
                            //params.put("RecieverName",un);
                            //params.put("CurrTime",time2);
                            // params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };


                    RequestQueue queue= Volley.newRequestQueue(EditMessageActivity.this);
                    queue.add(request);







                }
            }
        });
    }

}