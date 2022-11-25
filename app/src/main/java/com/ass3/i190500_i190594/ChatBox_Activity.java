package com.ass3.i190500_i190594;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    Button ss;
    TextView img;
    boolean flag=true;
    Bitmap bitmap;
    String encodeImageString;
    byte[] bytearray;
    ImageView imgmessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        Username=findViewById(R.id.user_Name);
        messageEdit=findViewById(R.id.msg);
        sendMessage=findViewById(R.id.submit_msg);
        img=findViewById(R.id.imgselect);
        imgmessage=findViewById(R.id.submit_imsg);

        rv=findViewById(R.id.messageRV);

        RecyclerView.LayoutManager lm=new LinearLayoutManager(ChatBox_Activity.this);
        ls=new ArrayList<>();
        adapter=new MessagesAdapter(ChatBox_Activity.this,ls);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);
        ss=findViewById(R.id.ss);


        un=getIntent().getStringExtra("Username");
        senderName=getIntent().getStringExtra("SenderName");





        Username.setText(un);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Dexter.withActivity(ChatBox_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();










            }
        });

      //  if(bytearray!=null){

        //}



            imgmessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(bytearray==null){

                        Toast.makeText(ChatBox_Activity.this,"Please Select Image first",Toast.LENGTH_LONG).show();
                    }
                    else {
                        uploaddatatodb();
                    }
                }
            });





        getStoragePermission(ChatBox_Activity.this);
        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( ScreenShot(getWindow().getDecorView().getRootView())!=null) {
                   Toast.makeText(getApplicationContext(), "ScreenShot Saved Successfully", Toast.LENGTH_LONG).show();
               }
                else{
                   Toast.makeText(getApplicationContext(), "Some Error Occured", Toast.LENGTH_LONG).show();

                }
            }
        });



        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!messageEdit.getText().toString().equals("")) {
                    DateFormat df = new SimpleDateFormat("LLLL dd, HH:mm aaa"); // Format time
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                //Log.i("Hello","Button is visible");
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                //dp.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                bytearray = stream.toByteArray();
                imgmessage.setVisibility(View.VISIBLE);
                sendMessage.setVisibility(View.GONE);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
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
                                    MyMessage m=new MyMessage(contact.getString("Message"),contact.getString("CurrTime"),senderName);
                                    m.setFlag(true);
                                    ls.add(m);

                                   // ls.add(new MyMessage(contact.getString("Message"),contact.getString("CurrTime"),senderName));

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









        StringRequest request2=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/getImageMessage.php",
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
                                    String time3=contact.getString("CurrTime");
                                    String image=contact.getString("image");
                                    String url="http://"+IPServer.getIP_server()+"/smdass3/ImageMessage/"+image;

                                    MyMessage m=new MyMessage(url,time3,senderName);
                                    m.setFlag(false);
                                    ls.add(m);
                                    adapter.notifyDataSetChanged();
                                }

                            }
                            else{
                                Toast.makeText(ChatBox_Activity.this,obj.get("msg").toString(),Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(ChatBox_Activity.this,response,Toast.LENGTH_LONG).show();
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
             //   Log.i("",username);
               // Log.i("Password",password);
                params.put("SenderName",senderName);
                params.put("RecieverName",un);
                // params.put("Password",password);
                // params.put("PhoneNum",phonenum.getText().toString());

                return params;
            }
        };


        RequestQueue queue2= Volley.newRequestQueue(ChatBox_Activity.this);
        queue.add(request2);


    }

    public void uploaddatatodb(){

        DateFormat df = new SimpleDateFormat("LLLL dd, HH:mm aaa"); // Format time
        String time2 = df.format(Calendar.getInstance().getTime());



        StringRequest request=new StringRequest(Request.Method.POST, "http://"+IPServer.getIP_server()+"/smdass3/AddImg.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

             //   dp.setImageResource(R.drawable.dp_upload);
                bytearray=null;
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
               // Intent i=new Intent(ChatBox_Activity.this,Contacts_Activity.class);
                //i.putExtra("Username",Username);
                //i.putExtra("Password",Password);
                //startActivity(i);
                Log.i("Response:", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                Log.i("Error:",error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();

                map.put("ImageMessage",encodeImageString);
                map.put("SenderName",senderName);
                map.put("RecieverName",un);
                map.put("CurrTime",time2);


                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);




    }

    public static File ScreenShot(View v){

       // Date date=new Date();

        try{
            DateFormat df = new SimpleDateFormat("HH:mm:ss aaa"); // Format time
            String time2 = df.format(Calendar.getInstance().getTime());

            String path= Environment.getExternalStorageDirectory()+"/ScreenShots";
            File folderpath=new File(path);
            if(!folderpath.exists()){

                folderpath.mkdir();

                Log.i("Making Folder","Done");
            }
            String pth=path+ "/" + "-" + time2 + ".jpeg";
            v.setDrawingCacheEnabled(true);
            Bitmap b=Bitmap.createBitmap(v.getDrawingCache());

            v.setDrawingCacheEnabled(false);
            File ssFile=new File(pth);
            FileOutputStream f=new FileOutputStream(ssFile);
            b.compress(Bitmap.CompressFormat.JPEG,100,f);
            f.flush();
            f.close();
            return ssFile;


        }
        catch (FileNotFoundException e){
            Log.i("File: ","Not found Excption");
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return  null;

    }
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static String[] PERMISSION_STORAGE={
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void getStoragePermission(Activity a){

        int permission= ActivityCompat.checkSelfPermission(a,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(a,PERMISSION_STORAGE,REQUEST_EXTERNAL_STORAGE);

        }
    }
}