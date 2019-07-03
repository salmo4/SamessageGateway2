package com.apkglobal.regsamlog;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {
    Button btn_trimite, btnLogout;
    EditText sam_mobil, sam_mesaj;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btn_trimite=findViewById(R.id.btn_trimite);
        sam_mesaj=findViewById(R.id.sam_mesaj);
        sam_mobil=findViewById(R.id.sam_mobil);
        btn_trimite.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // copiem aici codul Java din textlocal de trimitere SMS
                // tastam alt+Enter pentru a importa clasa
                try {
                    // Construct data
                    // key este valoare fixa
                    String apiKey = "apikey=" + "i858LD6ZzZA-5ALwtvTOxw5IvugS3wKGJbrBYcG1ug";
                    // mesajul intotdeauna se poate schimba apeland sam_mesaj
                    String message = "&message=" + sam_mesaj.getText().toString();
                    // fiind cont demo nu se poate schimba numele de id a user-ului
                    String sender = "&sender=" + "Jims Autos";
                    // chiar daca e cont demo se poate schimba numarul de mobil al receptorului oricand
                    String numbers = "&numbers=" + sam_mobil.getText().toString();

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        Toast.makeText(HomeActivity.this,
                                line.toString() , Toast.LENGTH_SHORT).show();
                    }
                    rd.close();


                } catch (Exception e) {
                    Toast.makeText(HomeActivity.this,
                            e.toString() , Toast.LENGTH_SHORT).show();

                }



            }
        });
        // codul pt metoda on click
        StrictMode.ThreadPolicy st=new StrictMode.ThreadPolicy.Builder().build();
        StrictMode.setThreadPolicy(st);

        // se poate rula aplicatia pe un telefon mobil
        btnLogout = findViewById(R.id.logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intToMain);
            }
        });
    }
}

