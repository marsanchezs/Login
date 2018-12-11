package com.example.mario.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import OpenHelper.SQLite_OpenHelper;

public class RegistroProducto extends AppCompatActivity {

    private EditText nombreProducto, descripcion, precio;
    private Button btnRegProd;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private double longitud, latitud;
    private ImageView imagen;
    private Button btnCaptura;
    private byte[] blob;
    private ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1024);

    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "BD1", null,1);
    //Instancia de la BBDD.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_producto);

        nombreProducto = (EditText)findViewById(R.id.edtProducto);
        descripcion = (EditText)findViewById(R.id.edtDescripcion);
        precio = (EditText)findViewById(R.id.edtPrecio);
        btnRegProd = (Button)findViewById(R.id.btnRegistrarProducto);
        imagen = (ImageView) findViewById(R.id.imageView3);
        btnCaptura = (Button) findViewById(R.id.btnCaptura);

        btnCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LLamarIntent();
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion localizacion = new Localizacion();
        localizacion.setMainActivity(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) localizacion);

        btnRegProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.abrirBD();

                if(nombreProducto.getText().toString().trim().equalsIgnoreCase("")){
                    nombreProducto.setError("Debe ingresar un nombre");
                } else if (descripcion.getText().toString().trim().equalsIgnoreCase("")){
                    descripcion.setError("Debe ingresar una descripcion");
                } else if(precio.getText().toString().trim().equalsIgnoreCase("")){
                    precio.setError("Debe ingresar un precio");
                } else {
                    int prec = Integer.parseInt(precio.getText().toString());
                    blob = bytearrayoutputstream.toByteArray();
                    helper.insertarProducto(String.valueOf(nombreProducto.getText()),
                            String.valueOf(descripcion.getText()), prec, longitud, latitud, blob);

                    helper.cerrarBD();

                    Toast.makeText(getApplicationContext(), "Producto Guardado", Toast.LENGTH_LONG).show();


                }

            }
        });

    }

    public class Localizacion implements LocationListener{
        RegistroProducto mainActivity;

        public void setMainActivity(RegistroProducto mainActivity) {
            this.mainActivity = mainActivity;
        }

        public RegistroProducto getMainActivity() {
            return mainActivity;
        }

        @Override
        public void onLocationChanged(Location location) {
            longitud = location.getLongitude();
            latitud = location.getLatitude();


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }



    public void LLamarIntent ()
    {
        //Nos lleva a un intent para tomar la fotografia
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //lo iniciamos con startActivityForResult esperando una respuesta que es la foto
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Intent es la acción para enlazar una nueva Actividad, Servicio o BroadcastReceiver.
            //Bundle es una coleccion de pares clave-valor.

            //captura la imagen y lo devuelve como un bundle
            Bundle extras = null;
            extras = data.getExtras();
            //lo que recibimos como foto lo convertimos en un bitmap, la clave qui es data.
            //bitmap es una imagen, cualquier imagen digital es en realidad un mapa de bit o bitmap.
            Bitmap imageBitmap = null;
            imageBitmap = (Bitmap) extras.get("data");


            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bytearrayoutputstream);

            //Mostramos la imagen en un imageView
            imagen.setImageBitmap(imageBitmap);
        }
    }
}

