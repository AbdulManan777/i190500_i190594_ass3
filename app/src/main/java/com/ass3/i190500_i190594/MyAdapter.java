package com.ass3.i190500_i190594;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context c;
    List<MyContact> ls;
    String sendname;

    public MyAdapter(Context c, List<MyContact> ls, String SenderName) {
        this.c = c;
        this.ls = ls;
        this.sendname=SenderName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.contacts_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(ls.get(position).getUsername());
      //  holder.phno.setText(ls.get(position).getPhno());
       // holder.address.setText(ls.get(position).getAddress());
        String username=  holder.name.getText().toString();


        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(c,ChatBox_Activity.class);
                i.putExtra("Username",username);
                i.putExtra("SenderName",sendname);
                c.startActivity(i);


            }
        });





    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,pass,phone;
        LinearLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            row=itemView.findViewById(R.id.roww);
            name=itemView.findViewById(R.id.username);
            //phno=itemView.findViewById(R.id.phno);
            //address=itemView.findViewById(R.id.address);
        }
    }
}

