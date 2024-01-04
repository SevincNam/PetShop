package com.example.petshop;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class KayitEkleGuncelle extends AppCompatActivity {

    private ImageView profilResmi;
    private EditText adSoyad,tel,email,dogumTarihi,aciklama;
    private FloatingActionButton kayitBtn;

    private static final int KameraTalebiKodu=100;
    private static final int DepolamaTalebiKodu=101;

    private static final int KameradanResimSecme=102;
    private static final int GaleridenResimSecme=103;


    private String[] kameraIzinleri;
    private String[] galeriIzinleri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekle_guncelle);

        profilResmi=findViewById(R.id.ProfilResmiIMG);
        adSoyad=findViewById(R.id.AdSoyadTxt);
        tel=findViewById(R.id.TelefonTxt);
        email=findViewById(R.id.EmailTxt);
        dogumTarihi=findViewById(R.id.DogumTarihiTxt);
        aciklama=findViewById(R.id.KisaAciklamaTxt);
        kayitBtn=findViewById(R.id.KayitBtn);

        kameraIzinleri=new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        galeriIzinleri=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        profilResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resimSecmeDialog();
            }




        });


        kayitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(KayitEkleGuncelle.this, "Kayıt Buradan Yapılacak...", Toast.LENGTH_LONG).show();
            }
        });



    }

    private void resimSecmeDialog() {
        String[] ogeler={"Kamera","Galeri"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Resim Seç");
        builder.setItems(ogeler, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    if(!kameraIzinKontrolu()){

                        KameraIzinTalebi();
                    }
                    else{
                        Toast.makeText(KayitEkleGuncelle.this, "KAMERADAN RESİM SEÇ !", Toast.LENGTH_SHORT).show();
                    }
                }

                if(i==1){
                    if(!depolamaIzinKontrolu()){

                        depolamaIzinTalabi();
                    }
                    else{
                        galeridenResimSec();
                    }
                }
            }
        });
        builder.create().show();
    }

    //Galerinden Resim Seçme Metodu

    private void galeridenResimSec() {
        //galeriden seçilen resim onActivityResult metodu ile alınacak
        Intent galeriIntent=new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*");
        startActivityForResult(galeriIntent,GaleridenResimSecme);
    }


    private boolean depolamaIzinKontrolu(){
        boolean sonuc= ContextCompat.checkSelfPermission
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return sonuc;
    } //Depolama İznini Kontrol Ediyoruz


    private void depolamaIzinTalabi(){

        ActivityCompat.requestPermissions(this,galeriIzinleri,DepolamaTalebiKodu);

    }//Depolama izni yoksa Talep ediyoruz


    private void KameraIzinTalebi(){

        ActivityCompat.requestPermissions(this,kameraIzinleri,KameraTalebiKodu);

    }//Kamera izni yoksa talep ediyoruz


    private boolean kameraIzinKontrolu(){
        boolean sonuc1=ContextCompat.checkSelfPermission
                (this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        boolean sonuc2=ContextCompat.checkSelfPermission
                (this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);

        return  sonuc1 && sonuc2;
    }//Depolama İznini Kontrol Ediyoruz

}