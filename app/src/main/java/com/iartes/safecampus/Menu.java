package com.iartes.safecampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    private ImageView registrarIncidenteBotao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void irParaRegistrarIncidente(View view) {
        registrarIncidenteBotao = (ImageView) findViewById(R.id.imageViewRegistrarIncidente);
        registrarIncidenteBotao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, RegistrarIncidente.class);
                startActivity(intent);
            }
        });
    }
}
