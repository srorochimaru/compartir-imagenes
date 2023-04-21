package com.example.opengalery;

import android.content.Intent;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_SELECT_IMAGE = 100;

    ImageView imagen, galeria, mensaje, whatsapp;

    private Uri mSelectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = findViewById(R.id.imagen);
        galeria = findViewById(R.id.galeria);
        mensaje = findViewById(R.id.mensaje);

        whatsapp = findViewById(R.id.whatsapp);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImageUri == null){
                    Toast.makeText(MainActivity.this, "Selecciona una imagen", Toast.LENGTH_SHORT).show();
                }
                else {
                    Envioporwasa(mSelectedImageUri);

                }
            }
        });
        mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImageUri == null) {
                    Toast.makeText(MainActivity.this, "Selecciona una imagen", Toast.LENGTH_SHORT).show();
                }
                Enviopormensaje(mSelectedImageUri);

            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            mSelectedImageUri = data.getData();
            imagen.setImageURI(mSelectedImageUri);
            whatsapp.setEnabled(true);
            mensaje.setEnabled(true);
        }
    }

    //manda info a otro lugar, convirtiendo la informacion a un formato legible
    private void Envioporwasa(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(intent, "Compartir imagen"));
    }
    private void Enviopormensaje(Uri imageUri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Compartir imagen"));
    }
}