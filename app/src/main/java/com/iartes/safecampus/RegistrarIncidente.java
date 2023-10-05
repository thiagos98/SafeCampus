package com.iartes.safecampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegistrarIncidente extends AppCompatActivity {
    private Spinner spinner_categoria;
    private TextView latitude;
    private TextView longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_incidente);

        criarDropDown();
        salvarDadosFormulario();

        // Recupera os dados do formulário do Bundle
//        Bundle savedInstanceStateMethod = getIntent().getExtras();
//        preencherFormulario(savedInstanceStateMethod);
    }

    public void criarDropDown() {
        spinner_categoria = findViewById(R.id.spinnerCategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoria.setAdapter(adapter);
    }

    public void salvarDadosFormulario() {
        Bundle outState = new Bundle();

        EditText etData = findViewById(R.id.etData);
        outState.putString("data", etData.getText().toString());

        EditText etHora = findViewById(R.id.etHora);
        outState.putString("hora", etHora.getText().toString());

        EditText etDescricao = findViewById(R.id.etDescricao);
        outState.putString("descricao", etDescricao.getText().toString());

        Spinner sp = findViewById(R.id.spinnerCategoria);
        int categoriaIndex = sp.getSelectedItemPosition();
        outState.putInt("categoria", categoriaIndex);

        super.onSaveInstanceState(outState);
    }

    public void preencherFormulario(Bundle savedInstanceStateMethod) {
        EditText etData = findViewById(R.id.etData);
        etData.setText(savedInstanceStateMethod.getString("data"));

        EditText etHora = findViewById(R.id.etHora);
        etHora.setText(savedInstanceStateMethod.getString("hora"));

        EditText etDescricao = findViewById(R.id.etDescricao);
        etDescricao.setText(savedInstanceStateMethod.getString("descricao"));

        Spinner sp = findViewById(R.id.spinnerCategoria);
        int categoriaIndex = savedInstanceStateMethod.getInt("categoria");
        sp.setSelection(categoriaIndex);
    }

    public void irParaMapa(View view) {

        Intent intent = new Intent(this, RegistrarLocalizacao.class);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica o resultado da nova activity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Faz algo com o resultado
            latitude = findViewById(R.id.tvLatitude);
            longitude = findViewById(R.id.tvLongitude);
            Intent intent = getIntent();
            Log.d("DEBUG", "Latitude " + intent.getStringExtra("latitude"));
            latitude.setText("Latitude: " + intent.getStringExtra("latitude"));
            longitude.setText("Longitude: " + intent.getStringExtra("longitude"));
        }
    }
}
