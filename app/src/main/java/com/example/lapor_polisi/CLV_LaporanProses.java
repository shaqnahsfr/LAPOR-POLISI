package com.example.lapor_polisi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CLV_LaporanProses extends ArrayAdapter<String> {

    private final Activity context;
    private ArrayList<String> vID;
    private ArrayList<String> vJenisLaporan;
    private ArrayList<String> vNamaLengkap;
    private ArrayList<String> vMaps;
    private ArrayList<String> vLtd;
    private ArrayList<String> vLng;
    private ArrayList<String> vLokasiDetail;
    private ArrayList<String> vTanggal;
    private ArrayList<String> vKeterangan;
    private ArrayList<String> vStatusLaporan;
    private ArrayList<String> vFoto;

    public CLV_LaporanProses(Activity context, ArrayList<String> ID, ArrayList<String> JenisLaporan, ArrayList<String> NamaLengkap,
                            ArrayList<String> Maps, ArrayList<String> Ltd, ArrayList<String> Lng, ArrayList<String> LokasiDetail, ArrayList<String> Tanggal,
                            ArrayList<String> Keterangan, ArrayList<String> StatusLaporan, ArrayList<String> Foto)
    {
        super(context, R.layout.list_item_laporan_proses,JenisLaporan);
        this.context        = context;
        this.vID            = ID;
        this.vJenisLaporan  = JenisLaporan;
        this.vNamaLengkap   = NamaLengkap;
        this.vMaps          = Maps;
        this.vLtd           = Ltd;
        this.vLng           = Lng;
        this.vLokasiDetail  = LokasiDetail;
        this.vTanggal       = Tanggal;
        this.vKeterangan    = Keterangan;
        this.vStatusLaporan = StatusLaporan;
        this.vFoto          = Foto;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //Load Custom Layout untuk list
        View rowView= inflater.inflate(R.layout.list_item_laporan_proses, null, true);

        //Declarasi komponen
        TextView jenis_laporan       = rowView.findViewById(R.id.tvKategori);
        TextView nama_lengkap        = rowView.findViewById(R.id.tvNama);
        TextView tgl_kejadian        = rowView.findViewById(R.id.tvDate);
        TextView maps                = rowView.findViewById(R.id.tvLokasi);
        TextView status_laporan      = rowView.findViewById(R.id.TVstatusLaporan);
        ImageView foto               = rowView.findViewById(R.id.fotoLaporan);

        //Set Parameter Value sesuai widget textview
        jenis_laporan.setText(vJenisLaporan.get(position));
        nama_lengkap.setText(vNamaLengkap.get(position));
        tgl_kejadian.setText(vTanggal.get(position));
        maps.setText(vMaps.get(position));
        status_laporan.setText(vStatusLaporan.get(position));

        if (vFoto.get(position).equals(""))
        {
            Picasso.get().load("https://tekajeapunya.com/kelompok_9/image_laporpak/profile.png").into(foto);
        }
        else
        {
            Picasso.get().load("https://tekajeapunya.com/kelompok_9/image_laporpak/"+vFoto.get(position)).into(foto);
        }



        return rowView;
    }

}
