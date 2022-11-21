package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        rv=findViewById(R.id.msgs_list_RV);




        String username=getIntent().getStringExtra("Username");
        String password=getIntent().getStringExtra("Password");
        RecyclerView.LayoutManager lm=new LinearLayoutManager(Contacts_Activity.this);
        ls=new ArrayList<>();
        adapter=new MyAdapter(Contacts_Activity.this,ls,username);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);

        Log.i("Username",username);
        Log.i("Password",password);







        StringRequest request=new StringRequest(Request.Method.POST, "http://192.168.10.9/smdass3/get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);

                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("users");

                                for (int i=0; i<contacts.length();i++)
                                {

                                    JSONObject contact=contacts.getJSONObject(i);
                                    ls.add(new MyContact(contact.getString("Username")));
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
                params.put("Password",password);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(Contacts_Activity.this);
        queue.add(request);
    }
}