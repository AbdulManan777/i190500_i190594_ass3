package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Signup_Activity extends AppCompatActivity {
    EditText username,password,phonenum;
    Button signup;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username=findViewById(R.id.username_signup);
        password=findViewById(R.id.password_signup);
        phonenum=findViewById(R.id.Phone_signup);

        signup=findViewById(R.id.sign_up);
        signin=findViewById(R.id.sign_up_BT2);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup_Activity.this,SignIn_Activity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!username.getText().toString().equals("")&& !password.getText().toString().equals("") && !phonenum.getText().toString().equals("")){
                    StringRequest request=new StringRequest(
                            Request.Method.POST,
                            "http://192.168.10.9/smdass3/insert.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);
                                        if(obj.getInt("code")==1)
                                        {
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(
                                                    Signup_Activity.this,
                                                    obj.get("msg").toString()
                                                    ,Toast.LENGTH_LONG
                                            ).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(
                                                Signup_Activity.this,
                                                "Incorrect JSON"
                                                ,Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(
                                            Signup_Activity.this,
                                            error.toString().trim()
                                            , Toast.LENGTH_LONG
                                    ).show();
                                }
                            })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Log.i("Username",username.getText().toString());
                            Log.i("Password",password.getText().toString());
                            Log.i("PhoneNum",phonenum.getText().toString());
                            Map<String, String> params=new HashMap<>();
                            params.put("Username",username.getText().toString());
                            params.put("Password",password.getText().toString());
                            params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };
                    RequestQueue queue= Volley.newRequestQueue(Signup_Activity.this);
                    queue.add(request);

                }

                else{

                    Toast.makeText(getApplicationContext(),"Please enter all the fields to contniue",Toast.LENGTH_LONG).show();
                }


            }

        });








    }
}