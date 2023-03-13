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

    private final String[][] tableau = {
            {"p.boyer", "1234", "architect"},
            {"a.dochez", "1234", "agriculyeur"},
            {"n.goubet", "1234", "policier"}
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin=(EditText)findViewById(R.id.Login);
        mMDP=findViewById(R.id.MDP);
        mButton=findViewById(R.id.button);

        mButton.setEnabled(false);

        //création des objet user à partir du tableau
        user[] users=new user[tableau.length];
        for (int i=0; i<tableau.length;i++){
            users[i] = new user(tableau[i][0], tableau[i][1], tableau[i][2]);
        }

        //activation du bouton connexion
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
                        int start=0;
                        for(user user:users){
                            if (mLogin.getText().toString().equals(user.getLogin()) && mMDP.getText().toString().equals(user.getMotDePasse())){
                                Intent loginPageIntent = new Intent(MainActivity.this, loginPage.class);
                                loginPageIntent.putExtra("login", user.getLogin());
                                loginPageIntent.putExtra("role", user.getMetier());
                                startActivity(loginPageIntent);
                                start=1;
                            }
                        }
                        if(start==0) {
                            Toast.makeText(MainActivity.this, "Mauvais nom de compte ou mot de passe", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });



    }
}