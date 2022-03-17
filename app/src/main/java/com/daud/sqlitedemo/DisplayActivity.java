package com.daud.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {
    private EditText nameEt,phoneEt,ageEt,idEt;
    private TextView idTv;
    private Button Btn;
    private DataBaseHelper dataBaseHelper;
    private List<ModelClass> List;
    private Context context;
    private String Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initialize();

        Intent intent = getIntent();
        Activity = intent.getStringExtra("Activity");
        if (Activity.equals("insert")){
            Btn.setText("Insert");
            idTv.setVisibility(View.VISIBLE);
        } else if (Activity.equals("update")) {
            Btn.setText("Update");
            idTv.setVisibility(View.VISIBLE);
            int id = Integer.parseInt(intent.getStringExtra("ID"));
            String name = intent.getStringExtra("NAME");
            String phone = intent.getStringExtra("PHONE");
            String age = intent.getStringExtra("AGE");

            idTv.setText("ID: " + id);
            nameEt.setHint("Name: " + name);
            phoneEt.setHint("Phone: " + phone);
            ageEt.setHint("Age: " + age);
        }

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Activity.equals("insert")){
                    String inName = nameEt.getText().toString();
                    String inPhone = phoneEt.getText().toString();
                    String inAge = ageEt.getText().toString();

                    if(inName.isEmpty() || inPhone.isEmpty() || inAge.isEmpty()){
                        Toast.makeText(DisplayActivity.this, "Invalid Value", Toast.LENGTH_SHORT).show();
                    }else{
                        dataBaseHelper.insertData(inName,inPhone,inAge);
                        nameEt.setText("");
                        phoneEt.setText("");
                        ageEt.setText("");
                    }
                }else if (Activity.equals("update")){
                    Intent intent = getIntent();
                    int id = Integer.parseInt(intent.getStringExtra("ID"));
                    String name = intent.getStringExtra("NAME");
                    String phone = intent.getStringExtra("PHONE");
                    String age = intent.getStringExtra("AGE");

                    idTv.setText("ID: "+id);
                    nameEt.setHint("Name: "+name);
                    phoneEt.setHint("Phone: "+phone);
                    ageEt.setHint("Age: "+id);

                    String upName = nameEt.getText().toString();
                    String upPhone = phoneEt.getText().toString();
                    String upAge = ageEt.getText().toString();

                    if (upName.isEmpty() || upPhone.isEmpty() || upAge.isEmpty()){
                        Toast.makeText(DisplayActivity.this, "Invalid Value", Toast.LENGTH_SHORT).show();
                    } else {
                        dataBaseHelper.updateData(id,upName,upPhone,upAge);
                        nameEt.setText("");
                        phoneEt.setText("");
                        ageEt.setText("");

                        idTv.setText("ID: "+id);
                        nameEt.setHint("Name: "+upName);
                        phoneEt.setHint("Phone: "+upPhone);
                        ageEt.setHint("Age: "+upAge);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DisplayActivity.this,MainActivity.class));
        finish();
    }
    private void initialize() {
        idTv = findViewById(R.id.idTv);
        idEt = findViewById(R.id.idEt);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        ageEt = findViewById(R.id.ageEt);
        Btn = findViewById(R.id.Btn);
        dataBaseHelper = new DataBaseHelper(this);
        List = new ArrayList<>();
    }
}