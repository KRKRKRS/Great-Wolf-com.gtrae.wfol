package com.gtrae.wfol;

import static com.gtrae.wfol.Parsergtrae.degtraecode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    WebView webView;
    Button gm;
    Button plc;
    Button ext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        webView = findViewById(R.id.policyWeb);
        gm = findViewById(R.id.StartGame);
        plc = findViewById(R.id.privacyBtn);
        ext = findViewById(R.id.exitBtn);
    }

    public void startGame(View view) {
        startActivity(new Intent(this, GActivity.class));
        finish();
    }


    public void goPrivacy(View view) {
        webView.setVisibility(View.VISIBLE);
        gm.setVisibility(View.INVISIBLE);
        plc.setVisibility(View.INVISIBLE);
        ext.setVisibility(View.INVISIBLE);
        webView.loadUrl(degtraecode("aHR0cHM6Ly93d3cucHJpdmFjeXBvbGljeW9ubGluZS5jb20vbGl2ZS5waHA/dG9rZW49d1lLZHBIRjFqNTZDVmo5Qno5d0wya0c5SERHTTc0QjE="));
    }


    public void exitGame(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        webView.setVisibility(View.INVISIBLE);
        gm.setVisibility(View.VISIBLE);
        plc.setVisibility(View.VISIBLE);
        ext.setVisibility(View.VISIBLE);
    }
}