package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatBox_Activity extends AppCompatActivity {
    TextView Username;
    EditText messageEdit;
    ImageView sendMessage;
    RecyclerView rv;
    MessagesAdapter adapter;
    List<MyMessage> ls;
    String senderName,un;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        Username=findViewById(R.id.user_Name);
        messageEdit=findViewById(R.id.msg);
        sendMessage=findViewById(R.id.submit_msg);

        rv=findViewById(R.id.messageRV);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(ChatBox_Activity.this);
        ls=new ArrayList<>();
        adapter=new MessagesAdapter(ChatBox_Activity.this,ls);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);


        un=getIntent().getStringExtra("Username");
        senderName=getIntent().getStringExtra("SenderName");





        Username.setText(un);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!messageEdit.getText().toString().equals("")) {
                    DateFormat df = new SimpleDateFormat("HH:mm aaa"); // Format time
                    String time2 = df.format(Calendar.getInstance().getTime());

                    StringRequest request = new StringRequest(
                            Request.Method.POST,
                            "http://"+IPServer.getIP_server()+"/smdass3/Message.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        if (obj.getInt("code") == 1) {
                                            Toast.makeText(getApplicationContext(), "Message Successfully Sent", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(
                                                    ChatBox_Activity.this,
                                                    obj.get("msg").toString()
                                                    , Toast.LENGTH_LONG
                                            ).show();
                                        }
                                        messageEdit.setText("");
                                    } catch (JSONException e) {
                                        e.printStackTrace();

                                        Toast.makeText(
                                                ChatBox_Activity.this,
                                                response
                                                , Toast.LENGTH_LONG
                                        ).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(
                                            ChatBox_Activity.this,
                                            error.toString().trim()
                                            , Toast.LENGTH_LONG
                                    ).show();
                                }
                            }) {

                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("Message", messageEdit.getText().toString());
                            params.put("SenderName", senderName);
                            params.put("RecieverName", un);
                            params.put("CurrTime", time2);
                            // params.put("PhoneNum",phonenum.getText().toString());

                            return params;
                        }
                    };


                    RequestQueue queue = Volley.newRequestQueue(ChatBox_Activity.this);
                    queue.add(request);

                }

                else{

                    Toast.makeText(getApplicationContext(),"Please enter the message first to send",Toast.LENGTH_LONG).show();
                }






            }
        });







    }

    @Override
    protected void onResume() {
        super.onResume();



        StringRequest request=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/getMessageSenderRecv.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);

                            if(obj.getInt("code")==1)
                            {
                                JSONArray contacts=obj.getJSONArray("messages");

                                for (int i=0; i<contacts.length();i++)
                                {

                                    JSONObject contact=contacts.getJSONObject(i);
                                    ls.add(new MyMessage(contact.getString("Message"),contact.getString("CurrTime"),senderName));

                                    adapter.notifyDataSetChanged();
                                }

                            }
                            else{
                                Toast.makeText(ChatBox_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ChatBox_Activity.this,"No Messages to Load",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChatBox_Activity.this,"Error Connecting Server",Toast.LENGTH_LONG).show();
                    }
                }

        )
        {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                //Log.i("Username",username);
                //Log.i("Password",password);
                params.put("SenderName", senderName);
                params.put("RecieverName", un);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(ChatBox_Activity.this);
        queue.add(request);


    }
}