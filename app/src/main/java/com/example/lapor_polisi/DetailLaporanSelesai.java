package com.example.lapor_polisi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class DetailLaporanSelesai extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    Intent toolbar1;
    TextView idLaporan, namaLengkap, jenisLaporan, tanggal, lokasiMaps, latitude, longitude, lokasiDetail, keterangan;
    ImageView foto_kejadian;
    String ltd, lng;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_selesai);

        toolbar = findViewById(R.id.IDtoolbar);
        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(DetailLaporanSelesai.this, LaporanSelesai.class);
                startActivity(toolbar1);
                finish();
            }
        });

        idLaporan = findViewById(R.id.tvId);
        namaLengkap = findViewById(R.id.tvNama);
        jenisLaporan = findViewById(R.id.tvKategori);
        tanggal = findViewById(R.id.tvDate);
        lokasiMaps = findViewById(R.id.tvLokasi);
        latitude = findViewById(R.id.tvLTD);
        longitude = findViewById(R.id.tvLNG);
        foto_kejadian = findViewById(R.id.imageLaporan);
        lokasiDetail = findViewById(R.id.tvLokasiDetail);
        keterangan = findViewById(R.id.tvKeterangan);

        getDataSelesai();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    void getDataSelesai(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
            idLaporan.setText(bundle.getString("id"));
            jenisLaporan.setText(bundle.getString("jenis_laporan"));
            namaLengkap.setText(bundle.getString("nama_lengkap"));
            tanggal.setText(bundle.getString("tgl_kejadian"));
            lokasiMaps.setText(bundle.getString("maps"));
            latitude.setText(bundle.getString("ltd"));
            longitude.setText(bundle.getString("lng"));
            lokasiDetail.setText(bundle.getString("lokasi_detail"));
            keterangan.setText(bundle.getString("keterangan"));
            Picasso.get().load("https://tekajeapunya.com/kelompok_9/image_laporpak/" + bundle.getString("foto")).into(foto_kejadian);
        }else {
            idLaporan.setText("");
            jenisLaporan.setText("");
            namaLengkap.setText("");
            tanggal.setText("");
            lokasiMaps.setText("");
            latitude.setText("");
            longitude.setText("");
            lokasiDetail.setText("");
            keterangan.setText("");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ltd = latitude.getText().toString();
        lng = longitude.getText().toString();

        // Add a marker in Sydney and move the camera
        LatLng lokasisekarang = new LatLng(Double.valueOf(ltd), Double.valueOf(lng));
        mMap.addMarker(new MarkerOptions().position(lokasisekarang).title("Lokasi pelapor"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasisekarang));
    }
}