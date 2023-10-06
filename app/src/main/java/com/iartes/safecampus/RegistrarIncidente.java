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
import android.widget.Toast;

public class RegistrarIncidente extends AppCompatActivity {
    private Spinner spinner_categoria;
    private String data;
    private String hora;
    private String descricao;
    private int categoriaIndex;

    private static String TAG = "CAOS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_incidente);

        criarDropDown();

        EditText etData = findViewById(R.id.etData);
        EditText etHora = findViewById(R.id.etHora);
        EditText etDescricao = findViewById(R.id.etDescricao);
        Spinner sp = findViewById(R.id.spinnerCategoria);
        TextView latitude = findViewById(R.id.tvLatitude);
        TextView longitude = findViewById(R.id.tvLongitude);

        etData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Atualizar a variável data toda vez que o texto no EditText for alterado
                data = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        etHora.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Este método não é utilizado neste caso
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Atualizar a variável hora toda vez que o texto no EditText for alterado
                hora = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Este método não é utilizado neste caso
            }
        });

        etDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Atualizar a variável descricao toda vez que o texto no EditText for alterado
                descricao = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Atualizar a variável categoriaIndex quando o usuário selecionar uma opção no Spinner
                categoriaIndex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

//        if (savedInstanceState != null) {
//            // Restaurar os dados do formulário do Bundle
//            data = savedInstanceState.getString("data");
//            hora = savedInstanceState.getString("hora");
//            descricao = savedInstanceState.getString("descricao");
//            categoriaIndex = savedInstanceState.getInt("categoria");
//
//            Log.d(TAG, "Primeiro fluxo de execução do if");
//            Log.d(TAG, "Data: " + data);
//            Log.d(TAG, "Hora: " + hora);
//            Log.d(TAG, "Descrição: " + descricao);
//            Log.d(TAG, "categoria: " + categoriaIndex);
//
//            etData.setText(data);
//            etHora.setText(hora);
//            etDescricao.setText(descricao);
//            sp.setSelection(categoriaIndex);
//        } else {
//            Log.d(TAG, "Segundo fluxo de execução do if");
//            Log.d(TAG, "Data: " + data);
//            Log.d(TAG, "Hora: " + hora);
//            Log.d(TAG, "Descrição: " + descricao);
//            Log.d(TAG, "categoria: " + categoriaIndex);
//        }

        Intent intentRecv = getIntent();

        etData.setText(intentRecv.getStringExtra("data"));
        etHora.setText(intentRecv.getStringExtra("hora"));
        etDescricao.setText(intentRecv.getStringExtra("descricao"));


        latitude.setText("Latitude: " + intentRecv.getDoubleExtra("latitudeValue", 0.0));
        longitude.setText("Longitude: " + intentRecv.getDoubleExtra("longitudeValue", 0.0));
    }

    public void criarDropDown() {
        spinner_categoria = findViewById(R.id.spinnerCategoria);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_categoria.setAdapter(adapter);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        // Salvar os dados do formulário no Bundle
//        Log.d(TAG, "Salvando os dados");
//        Log.d(TAG, "Data: " + data);
//        Log.d(TAG, "Hora: " + hora);
//        Log.d(TAG, "Descrição: " + descricao);
//        Log.d(TAG, "categoria: " + categoriaIndex);
//
//        outState.putString("data", data);
//        outState.putString("hora", hora);
//        outState.putString("descricao", descricao);
//        outState.putInt("categoria", categoriaIndex);
//    }

    public void irParaMapa(View view) {
        // Salvar os dados do formulário no Bundle
        Log.d(TAG, "Salvando os dados");
        Log.d(TAG, "Data: " + data);
        Log.d(TAG, "Hora: " + hora);
        Log.d(TAG, "Descrição: " + descricao);
        Log.d(TAG, "categoria: " + categoriaIndex);

        Intent intent = new Intent(this, RegistrarLocalizacao.class);
        intent.putExtra("data", data);
        intent.putExtra("hora", hora);
        intent.putExtra("descricao", descricao);
        intent.putExtra("categoria", categoriaIndex);
        startActivity(intent);
    }
}
