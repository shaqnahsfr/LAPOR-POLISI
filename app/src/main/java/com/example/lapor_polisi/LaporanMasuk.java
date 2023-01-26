package com.example.lapor_polisi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LaporanMasuk extends AppCompatActivity {

    SwipeRefreshLayout srl_main;
    ArrayList<String> array_id,array_jenisLaporan,array_namaLengkap,array_tanngal,array_maps,array_ltd,array_lng,array_lokasiDetail,array_statusLaporan,array_keterangan,array_foto;
    //ArrayList<Integer> array_id;
    ListView listProses;
    ProgressDialog progressDialog;
    //TextView status;
    //String status_laporan;
    private Context mContext;
    Toolbar toolbar;
    Intent toolbar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_masuk);

        toolbar = findViewById(R.id.IDtoolbar);
        //status = findViewById(R.id.statusLaporan);
        //set variable sesuai dengan widget yang digunakan
        listProses = findViewById(R.id.LV);
        srl_main = findViewById(R.id.swipe);
        progressDialog = new ProgressDialog(this);

        toolbar.setNavigationIcon(R.drawable.back_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar1 = new Intent(LaporanMasuk.this, MainActivity.class);
                startActivity(toolbar1);
                finish();
            }
        });

        srl_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollRefresh();
                srl_main.setRefreshing(false);
            }
        });
        // Scheme colors for animation
        srl_main.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)

        );

        scrollRefresh();
    }

    public void scrollRefresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getData();
            }
        },2000);
    }

    void initializeArray(){
        array_id              = new ArrayList<String>();
        array_jenisLaporan    = new ArrayList<String>();
        array_namaLengkap     = new ArrayList<String>();
        array_maps            = new ArrayList<String>();
        array_ltd             = new ArrayList<String>();
        array_lng             = new ArrayList<String>();
        array_lokasiDetail    = new ArrayList<String>();
        array_tanngal         = new ArrayList<String>();
        array_keterangan      = new ArrayList<String>();
        array_statusLaporan   = new ArrayList<String>();
        array_foto            = new ArrayList<String>();

        //clear ini untuk memastikan data empty
        array_id.clear();
        array_jenisLaporan.clear();
        array_namaLengkap.clear();
        array_maps.clear();
        array_ltd.clear();
        array_lng.clear();
        array_lokasiDetail.clear();
        array_tanngal.clear();
        array_keterangan.clear();
        array_statusLaporan.clear();
        array_foto.clear();

        //status_laporan = status.getText().toString();
    }

    public void getData(){
        initializeArray();
        //URL API
        AndroidNetworking.get("https://tekajeapunya.com/kelompok_9/laporpolisi/getLaporanMasukPolisi.php")
                //.addBodyParameter("status_laporan",""+status_laporan)
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Boolean status = response.getBoolean("status");
                            if (status){
                                JSONArray ja = response.getJSONArray("result");
                                Log.d("respon",""+ja);
                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    array_id.add(jo.getString("id"));
                                    array_jenisLaporan.add(jo.getString("jenis_laporan"));
                                    array_namaLengkap.add(jo.getString("nama_lengkap"));
                                    array_maps.add(jo.getString("maps"));
                                    array_ltd.add(jo.getString("ltd"));
                                    array_lng.add(jo.getString("lng"));
                                    array_lokasiDetail.add(jo.getString("lokasi_detail"));
                                    array_tanngal.add(jo.getString("tgl_kejadian"));
                                    array_keterangan.add(jo.getString("keterangan"));
                                    array_statusLaporan.add(jo.getString("status_laporan"));
                                    //add this code
                                    array_foto.add(jo.getString("foto"));

                                }

                                //Menampilkan data berbentuk adapter menggunakan class CLVDataUser
                                final CLV_LaporanMasuk adapter = new CLV_LaporanMasuk(LaporanMasuk.this,array_id,array_jenisLaporan,array_namaLengkap,array_maps,array_ltd,array_lng,array_lokasiDetail,array_tanngal,array_keterangan,array_statusLaporan,array_foto);

                                //Set adapter to list
                                listProses.setAdapter(adapter);

                                //detail
                                listProses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Intent i = new Intent(LaporanMasuk.this, DetailLaporanMasuk.class);
                                        i.putExtra("id",array_id.get(position));
                                        i.putExtra("jenis_laporan",array_jenisLaporan.get(position));
                                        i.putExtra("nama_lengkap",array_namaLengkap.get(position));
                                        i.putExtra("tgl_kejadian",array_tanngal.get(position));
                                        i.putExtra("maps",array_maps.get(position));
                                        i.putExtra("ltd",array_ltd.get(position));
                                        i.putExtra("lng",array_lng.get(position));
                                        i.putExtra("lokasi_detail",array_lokasiDetail.get(position));
                                        i.putExtra("keterangan",array_keterangan.get(position));
                                        i.putExtra("foto",array_foto.get(position));
                                        startActivity(i);

                                    }
                                });

                            }else{
                                Toast.makeText(LaporanMasuk.this, "Tidak Ada Laporan!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {

                    }

                });
    }
}