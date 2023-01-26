package com.example.lapor_polisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton polri, laporanMasuk, onProgress, riwayat;
    Intent tP, lM, progress,done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);

        polri= findViewById(R.id.IVpolri);
        laporanMasuk = findViewById(R.id.IBmasuk);
        onProgress = findViewById(R.id.IBprogress);
        riwayat = findViewById(R.id.IBselesai);

        polri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                tP = new Intent(MainActivity.this, TentangPolri.class);
                startActivity(tP);
                finish();
            }
        });

        laporanMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                lM = new Intent(MainActivity.this, LaporanMasuk.class);
                startActivity(lM);
                finish();
            }
        });

        onProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                progress = new Intent(MainActivity.this, LaporanProses.class);
                startActivity(progress);
                finish();
            }
        });

        riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animAlpha);

                done = new Intent(MainActivity.this, LaporanSelesai.class);
                startActivity(done);
                finish();
            }
        });

    }
}