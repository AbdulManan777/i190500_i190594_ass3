package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class SignIn_Activity extends AppCompatActivity {

    EditText username,password;
    Button signin;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        username=findViewById(R.id.username_signin);
        password=findViewById(R.id.password_signin);

        signin=findViewById(R.id.sign_in);
        signup=findViewById(R.id.sign_up_BT);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignIn_Activity.this,Signup_Activity.class));
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){

                    StringRequest stringRequest=new StringRequest(Request.Method.POST,
                            "http://"+IP_server.getIP_server()+"/smdass3/login.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if (response.equals("Success")) {

                                        Intent i = new Intent(SignIn_Activity.this, Contacts_Activity.class);
                                        i.putExtra("Username",username.getText().toString());
                                        i.putExtra("Password",password.getText().toString());
                                        startActivity(i);
                                       // finish();

                                    } else if (response.equals("Failure")) {

                                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Unable to Connect to Server",Toast.LENGTH_LONG).show();
                        }
                    })
                    {

                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params=new HashMap<>();
                            params.put("Username",username.getText().toString());
                            params.put("Password",password.getText().toString());
                           // params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };

                    RequestQueue queue= Volley.newRequestQueue(SignIn_Activity.this);
                    queue.add(stringRequest);

                } else {

                    Toast.makeText(getApplicationContext(),"No field should be empty",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}