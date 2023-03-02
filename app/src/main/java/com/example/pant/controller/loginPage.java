package com.example.pant.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pant.R;

import org.w3c.dom.Text;

import java.util.jar.Attributes;


public class loginPage extends AppCompatActivity {

    private TextView mName;
    private TextView mRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mName=findViewById(R.id.login_page_name);
        mRole=findViewById(R.id.login_page_role);

        String getLogin = getIntent().getStringExtra("login");
        String getRole = getIntent().getStringExtra("role");

        mName.setText(getLogin);
        mRole.setText(getRole);

    }
}