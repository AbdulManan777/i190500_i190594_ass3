package com.ass3.i190500_i190594;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    Context c;
    List<MyMessage> ls;

    public MessagesAdapter(Context c, List<MyMessage> ls) {
        this.c = c;
        this.ls = ls;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.text_message_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.message.setText(ls.get(position).getMessage());



        holder.time1.setText(ls.get(position).getTime1());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialog cdd=new CustomDialog((Activity) c,holder.message.getText().toString());
                cdd.show();

            }
        });




    }


    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message,time1;
        LinearLayout row;
       // ImageView dpImg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.message_row);
            message=itemView.findViewById(R.id.messageText);
            time1=itemView.findViewById(R.id.time1);

            //phno=itemView.findViewById(R.id.phno);
            //address=itemView.findViewById(R.id.address);
        }
    }


}