package com.example.lapor_polisi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

public class DetailLaporanMasuk extends AppCompatActivity implements OnMapReadyCallback{

    Toolbar toolbar;
    Intent toolbar1;
    TextView idLaporan, namaLengkap, jenisLaporan, tanggal, lokasiMaps, latitude, longitude, lokasiDetail, keterangan;
    ImageView foto_kejadian;
    String id, ltd, lng;

    private GoogleMap mMap;

    com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton verifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan_masuk);

        toolbar = findViewById(R.id.IDtoolbar);
        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(DetailLaporanMasuk.this, LaporanMasuk.class);
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
        verifikasi = findViewById(R.id.fab_btn);

        getDataIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = idLaporan.getText().toString();

                updateStatus();
            }
        });
    }

    void getDataIntent(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
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
        }else{
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

    public void updateStatus(){
        AndroidNetworking.post("https://tekajeapunya.com/kelompok_9/laporpolisi/updateLaporan.php")
                .addBodyParameter("id",""+id)
                .setTag("Update Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("cekUpdate",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(DetailLaporanMasuk.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if (status){
                                new AlertDialog.Builder(DetailLaporanMasuk.this)
                                        .setMessage("Terverifikasi")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent page;
                                                page = new Intent(DetailLaporanMasuk.this,LaporanMasuk.class);
                                                startActivity(page);
                                            }
                                        })
                                        .show();
                            }else {
                                new AlertDialog.Builder(DetailLaporanMasuk.this)
                                        .setMessage("Laporan gagal diverifikasi!")
                                        .setPositiveButton("Kembali", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Intent i = getIntent();
                                                //setResult(RESULT_CANCELED,i);
                                                //add_mahasiswa.this.finish();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


}