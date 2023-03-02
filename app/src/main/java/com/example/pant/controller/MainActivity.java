package com.example.pant.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pant.R;
import com.example.pant.modele.user;

public class MainActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mMDP;
    private Button mButton;

    private Boolean mBoolean1;

    private Boolean mBoolean2;

    String tableau[][] = {
            {"p.boyer", "1234", "architect"},
            {"a.dochez", "1234", "agriculyeur"},
            {"n.goubet", "1234", "policier"}
    };
    user[] users = new user[3];
    int l = tableau.length;
    for (int b=0, b< tableau.length, ++b ){

    }



    /*user[] users = {
            new user("p.boyer", "1234", "architecte"),
            new user("a.dochez", "1234", "agriculteur"),
            new user("n.goubet", "1234", "policier")
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin=(EditText)findViewById(R.id.Login);
        mMDP=findViewById(R.id.MDP);
        mButton=findViewById(R.id.button);

        mBoolean1=false;
        mBoolean2=false;

        mButton.setEnabled(false);

        mLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                mBoolean1=(!s.toString().isEmpty());
                mMDP.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mBoolean2=(!s.toString().isEmpty());
                        mButton.setEnabled(mBoolean1 && mBoolean2);
                    }
                });

                mButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i=0;
                        for(user user:users){
                            if (mLogin.getText().toString().equals(user.getLogin()) && mMDP.getText().toString().equals(user.getMotDePasse())){
                                Intent loginPageIntent = new Intent(MainActivity.this, loginPage.class);
                                loginPageIntent.putExtra("login", user.getLogin());
                                loginPageIntent.putExtra("role", user.getMetier());
                                startActivity(loginPageIntent);
                                i=1;
                            }
                        }
                        if(i==0) {
                            Toast.makeText(MainActivity.this, "Mauvais nom de compte ou mot de passe", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }
}