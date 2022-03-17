package com.daud.sqlitedemo;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView noDataTv;
    private FloatingActionButton floatingInsertBtn,floatingUpdateBtn;
    private DataBaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private List<ModelClass> List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        List = databaseHelper.getData();
        dataChecker();
        recyclerView.setAdapter(new MyAdapter(MainActivity.this,List));

        floatingInsertBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
            intent.putExtra("Activity","insert");
            intent.putExtra("Context","MainActivity.this");
            startActivity(intent);
            finish();
        });
    }

    /***METHODS***/

    public void dataChecker(){
        if (List.isEmpty()){
            noDataTv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else {
            noDataTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void initialize(){
        noDataTv=findViewById(R.id.noDataTv);
        floatingInsertBtn=findViewById(R.id.floatingInsertBtn);
        floatingUpdateBtn = findViewById(R.id.floatingUpdatetBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DataBaseHelper(this);
        List = new ArrayList<>();
    }
}