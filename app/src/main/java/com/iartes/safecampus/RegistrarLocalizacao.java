package com.iartes.safecampus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class RegistrarLocalizacao extends AppCompatActivity {
    private GeoPoint localizacaoEscolhida;
    private Button salvarLocalizacao;

    // Dados do formulário
    private String data;
    private String hora;
    private String descricao;
    private int categoriaIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_localizacao);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                String[] permissoes = {android.Manifest.permission.INTERNET, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissoes, 1);
            }
        }

        //caso dê erro de agentes
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue("br.edu.ufam.teste_maps");

        //Pega o mapa adicionada no arquivo activity_main.xml
        MapView mapa = (MapView) findViewById(R.id.map);
        mapa.setTileSource(TileSourceFactory.MAPNIK);
        //seta algumas configuracoes ao mapa
        mapa.setBuiltInZoomControls(true);
        mapa.setMultiTouchControls(true);

        //Cria um controller para setar posicoes no mapa
        IMapController mapController = mapa.getController();
        mapController.setZoom(17.5);
        GeoPoint startPoint = new GeoPoint(-3.09016,-59.96455); // ponto inicial
        mapController.setCenter(startPoint);

        //pega minha pos atual
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Verifique se o usuário concedeu permissão para o seu aplicativo acessar a localização
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Peça permissão ao usuário
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Verifique se o provedor GPS está habilitado
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Alerte o usuário e peça para ele habilitar o provedor GPS
            Toast.makeText(this, "O provedor GPS está desabilitado. Habilite-o para obter sua localização atual.", Toast.LENGTH_LONG).show();
            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        GeoPoint minhaLoc = null;
        if (location != null) {
            Log.i("debug", "uma localizacao conhecida encontrada!");
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            minhaLoc = new GeoPoint(latitude, longitude);
        }

        //Cria um marcador no mapa
        if(minhaLoc != null){
            Marker startMarker = new Marker(mapa);
            startMarker.setPosition(minhaLoc);
            startMarker.setTitle("Ponto Inicial");
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mapa.getOverlays().add(startMarker);

            //inicia localizacao
            localizacaoEscolhida = minhaLoc;
        }

        //cria uma variavel pra recebver eventos no mapa
        MapEventsReceiver mReceive = new MapEventsReceiver(){
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                //troca de localizacao com um toque simples
                localizacaoEscolhida = p;
                //como há apenas um marker sempre, apaga o primeiro
                mapa.getOverlays().remove(
                        mapa.getOverlays().size() - 1
                );
                //cria novo marcador
                Marker startMarker = new Marker(mapa);
                startMarker.setPosition(localizacaoEscolhida);
                startMarker.setTitle("Ponto Inicial");
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                mapa.getOverlays().add(startMarker);
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                // faz nada
                return false;
            }
        };

        //adiciona esse callback de eventos ao mapa
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mReceive);
        mapa.getOverlays().add(0, mapEventsOverlay);

        // Capturar intents da tela anterior
        Intent intentRecv = getIntent();
        data = intentRecv.getStringExtra("data");
        hora = intentRecv.getStringExtra("hora");
        descricao = intentRecv.getStringExtra("descricao");
        categoriaIndex = intentRecv.getIntExtra("categoria", 0);
    }

    public void irParaFormulario(View view) {
        salvarLocalizacao = (Button) findViewById(R.id.buttonLocalizacao);
        salvarLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localizacaoEscolhida != null) {
                    double latitude = localizacaoEscolhida.getLatitude();
                    double longitude = localizacaoEscolhida.getLongitude();

                    Intent intent = new Intent(RegistrarLocalizacao.this, RegistrarIncidente.class);
                    intent.putExtra("data", data);
                    intent.putExtra("hora", hora);
                    intent.putExtra("descricao", descricao);
                    intent.putExtra("categoria", categoriaIndex);
                    intent.putExtra("latitudeValue", latitude);
                    intent.putExtra("longitudeValue", longitude);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // Se a solicitação de permissão foi cancelada o array vem vazio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permissão cedida, recria a activity para carregar o mapa, só será executado uma vez
                    this.recreate();
                }
            }
        }
    }
}