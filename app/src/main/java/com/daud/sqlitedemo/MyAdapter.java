package com.daud.sqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.preference.DialogPreference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Context context;
    private List<ModelClass> List;

    public MyAdapter(Context context, java.util.List<ModelClass> List) {
        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.idVh.setText("ID: " + List.get(position).getId());
        holder.nameVh.setText("NAME: " + List.get(position).getName());
        holder.phoneVh.setText("PHONE: " + List.get(position).getPhone());
        holder.ageVh.setText("AGE: " + List.get(position).getAge());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context,DisplayActivity.class);
            intent.putExtra("Activity","update");
            intent.putExtra("ID",""+List.get(position).getId());
            intent.putExtra("NAME",List.get(position).getName());
            intent.putExtra("PHONE",List.get(position).getPhone());
            intent.putExtra("AGE",List.get(position).getAge());
            context.startActivity(intent);
            ((MainActivity)context).finish();
        });

        holder.itemView.setOnLongClickListener(view -> {
            DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setCancelable(false);
            dialog.setTitle("Delete !");
            dialog.setMessage("You Want To Delete "+List.get(position).getName()+" ?");

            dialog.setNegativeButton("No",(dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            dialog.setPositiveButton("Yes",(dialogInterface, i) -> {
                dataBaseHelper.deleteData(List.get(position).getId());
                Toast.makeText(context, "Delete "+List.get(position).getName()+" Successfull", Toast.LENGTH_SHORT).show();
                List.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,List.size());
                if(List.isEmpty()){
                    Intent intent = new Intent(context,MainActivity.class);
                    context.startActivity(intent);
                    ((MainActivity)context).finish();
                }
            });
            dialog.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView idVh,nameVh,phoneVh,ageVh;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idVh = itemView.findViewById(R.id.idVh);
            nameVh = itemView.findViewById(R.id.nameVh);
            phoneVh = itemView.findViewById(R.id.phoneVh);
            ageVh = itemView.findViewById(R.id.ageVh);
        }
    }
}