package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText username, phone;
    Button getpass;
    TextView restorepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        username=findViewById(R.id.usernameforgot);

        phone=findViewById(R.id.phonenum);

        getpass=findViewById(R.id.getPass);
        restorepass=findViewById(R.id.restoredpass);

        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!username.getText().toString().equals("") && !phone.getText().toString().equals("")){


                    StringRequest request=new StringRequest(
                            Request.Method.POST,
                            "http://"+IPServer.getIP_server()+"/smdass3/password.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);
                                        if(obj.getInt("code")==1)
                                        {
                                            restorepass.setText("Your Password is: "+response);
                                            restorepass.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(),"Your Password is: "+response,Toast.LENGTH_LONG).show();
                                           // finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    ForgetPasswordActivity.this,
                                                    obj.get("msg").toString()
                                                    ,Toast.LENGTH_LONG
                                            ).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(
                                                ForgetPasswordActivity.this,
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
                                            ForgetPasswordActivity.this,
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

                            params.put("Username",username.getText().toString());
                            params.put("PhoneNum",phone.getText().toString());
                            //params.put("RecieverName",un);
                            //params.put("CurrTime",time2);
                            // params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };


                    RequestQueue queue= Volley.newRequestQueue(ForgetPasswordActivity.this);
                    queue.add(request);







                }

                else{
                    Toast.makeText(getApplicationContext(),"Please first complete the fields",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}