package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Contacts_Activity extends AppCompatActivity {

    RecyclerView rv;
   // ImageView add;
    MyAdapter adapter;
    List<MyContact> ls;
    ImageView profile,profile_dp;
    String url2;
    EditText et;
    Button searchButton;
    LinearLayout ll;
    TextView usern1;
    ImageView userImg;
    DrawerLayout dwr;
    TextView username4,password4,phone4;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rv=findViewById(R.id.msgs_list_RV);
        profile=findViewById(R.id.profielpicture);
        et=findViewById(R.id.search);
        phone4=findViewById(R.id.phone4);
        searchButton=findViewById(R.id.searchButton);
        ll=findViewById(R.id.lluser);
        password4=findViewById(R.id.password4);
        dwr = findViewById(R.id.drwr);
        usern1=findViewById(R.id.username3);
        userImg=findViewById(R.id.dpImg3);
        profile_dp=findViewById(R.id.profile_dp);
        username4=findViewById(R.id.user_name4);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dwr.isDrawerOpen(Gravity.LEFT))
                    dwr.closeDrawer(Gravity.LEFT);
                else
                    dwr.openDrawer(Gravity.LEFT);
            }
        });




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!et.getText().equals("")){

                    ll.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);



                    StringRequest request=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/searchContact.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj=new JSONObject(response);

                                        if(obj.getInt("code")==1)
                                        {
                                            JSONArray contacts=obj.getJSONArray("pictures");

                                            for (int i=0; i<contacts.length();i++)
                                            {

                                                JSONObject contact=contacts.getJSONObject(i);
                                                String usern=contact.getString("Username");
                                                String image=contact.getString("image");
                                                String url="http://"+IPServer.getIP_server()+"/smdass3/images/"+image;
                                                usern1.setText(usern);
                                                Glide.with(Contacts_Activity.this).load(url).into(userImg);




                                                //MyContact m=new MyContact(usern,url);
                                                //ls.add(m);
                                                //adapter.notifyDataSetChanged();
                                            }

                                        }
                                        else{
                                            Toast.makeText(Contacts_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(Contacts_Activity.this,response,Toast.LENGTH_LONG).show();
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Contacts_Activity.this,"Error Connecting Server",Toast.LENGTH_LONG).show();
                                }
                            }

                    )
                    {

                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params=new HashMap<>();
                            Log.i("Username",et.getText().toString());

                            params.put("Username",et.getText().toString());
                            // params.put("Password",password);
                            // params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };


                    RequestQueue queue= Volley.newRequestQueue(Contacts_Activity.this);
                    queue.add(request);



                }

                else{
                    Toast.makeText(getApplicationContext(),"Firstly enter the text to search",Toast.LENGTH_LONG).show();
                }

            }
        });





        String username=getIntent().getStringExtra("Username");
        String password=getIntent().getStringExtra("Password");
        username4.setText(username);
        password4.setText(password);



        //Getting Current user phone Number
        StringRequest request3=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/getPhone.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);

                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("phonenumber");

                                for (int i=0; i<contacts.length();i++)
                                {

                                    JSONObject contact=contacts.getJSONObject(i);
                                    phone=contact.getString("PhoneNumber");
                                    //String image=contact.getString("image");
                                    //url2="http://"+IPServer.getIP_server()+"/smdass3/images/"+image;


                                    //MyContact m=new MyContact(usern,url);
                                    //ls.add(m);
                                    //adapter.notifyDataSetChanged();
                                }

                            }
                            else{
                                Toast.makeText(Contacts_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                           phone4.setText(phone);

                        } catch (JSONException e) {
                            Toast.makeText(Contacts_Activity.this,response,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Contacts_Activity.this,"Error Connecting Server",Toast.LENGTH_LONG).show();
                    }
                }

        )
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.i("Username",username);
                Log.i("Password",password);
                params.put("Username",username);
                params.put("Password",password);
                // params.put("Password",password);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue3= Volley.newRequestQueue(Contacts_Activity.this);
        queue3.add(request3);









        RecyclerView.LayoutManager lm=new LinearLayoutManager(Contacts_Activity.this);
        ls=new ArrayList<>();
        adapter=new MyAdapter(Contacts_Activity.this,ls,username);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);

        Log.i("Username",username);
        Log.i("Password",password);


        //Getting Current User Dp
        StringRequest request2=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/getImageOnly.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);

                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("pictures");

                                for (int i=0; i<contacts.length();i++)
                                {

                                    JSONObject contact=contacts.getJSONObject(i);
                                    //String usern=contact.getString("Username");
                                    String image=contact.getString("image");
                                     url2="http://"+IPServer.getIP_server()+"/smdass3/images/"+image;

                                    //MyContact m=new MyContact(usern,url);
                                    //ls.add(m);
                                    //adapter.notifyDataSetChanged();
                                }

                            }
                            else{
                                Toast.makeText(Contacts_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                            Glide.with(Contacts_Activity.this).load(url2).into(profile);
                            Glide.with(Contacts_Activity.this).load(url2).into(profile_dp);

                        } catch (JSONException e) {
                            Toast.makeText(Contacts_Activity.this,response,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Contacts_Activity.this,"Error Connecting Server",Toast.LENGTH_LONG).show();
                    }
                }

        )
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.i("Username",username);
                Log.i("Password",password);
                params.put("Username",username);
                // params.put("Password",password);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue2= Volley.newRequestQueue(Contacts_Activity.this);
        queue2.add(request2);















        //Getting COntacts with thier Profile pics
        StringRequest request=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/getUserImg.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);

                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("pictures");

                                for (int i=0; i<contacts.length();i++)
                                {

                                    JSONObject contact=contacts.getJSONObject(i);
                                    String usern=contact.getString("Username");
                                    String image=contact.getString("image");
                                    String url="http://"+IPServer.getIP_server()+"/smdass3/images/"+image;

                                    MyContact m=new MyContact(usern,url);
                                    ls.add(m);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                            else{
                                Toast.makeText(Contacts_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(Contacts_Activity.this,response,Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Contacts_Activity.this,"Error Connecting Server",Toast.LENGTH_LONG).show();
                    }
                }

                )
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                Log.i("Username",username);
                Log.i("Password",password);
                params.put("Username",username);
               // params.put("Password",password);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(Contacts_Activity.this);
        queue.add(request);
    }


}