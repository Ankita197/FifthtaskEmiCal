package com.ankita.emicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ankita.emicalculator.adapter.ItemEmiCalAdapter;

public class ShowEMICalculation extends AppCompatActivity {

    RecyclerView rvCalEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_emicalculation);
        rvCalEntity=findViewById(R.id.rvCalEntity);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rvCalEntity.setLayoutManager(linearLayoutManager);
        ItemEmiCalAdapter itemEmiCalAdapter=new ItemEmiCalAdapter(this,MainActivity.arrayList);
        rvCalEntity.setAdapter(itemEmiCalAdapter);
    }
}
