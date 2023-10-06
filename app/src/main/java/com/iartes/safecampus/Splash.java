package com.iartes.safecampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieImageAsset;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // Crie um Handler associado ao Activity atual
        Handler handler = new Handler();

// Poste o Runnable no Handler
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                irParaProximoActivity();
            }
        }, 3000);

    }

    private void irParaProximoActivity()
    {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        startActivity(intent);
    }
}