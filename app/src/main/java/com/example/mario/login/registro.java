package com.example.mario.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import OpenHelper.SQLite_OpenHelper;

public class registro extends AppCompatActivity {

    Button btnRegUsu; //Decalarar controles.
    EditText edtNombre, edtCorreo, edtClave;

    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "BD1", null,1);
    //Instancia de la BBDD.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegUsu = (Button)findViewById(R.id.btnRegistrar); //Enlazar los controles.
        edtNombre = (EditText)findViewById(R.id.edtName);
        edtCorreo = (EditText)findViewById(R.id.edtMail);
        edtClave = (EditText)findViewById(R.id.edtPass);


        btnRegUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.abrirBD();

                if(edtNombre.getText().toString().trim().equalsIgnoreCase("")){
                    edtNombre.setError("Debe ingresar un nombre");
                } else if (edtCorreo.getText().toString().trim().equalsIgnoreCase("")){
                    edtCorreo.setError("Debe ingresar un correo");
                } else if(edtClave.getText().toString().trim().equalsIgnoreCase("")){
                    edtClave.setError("Debe ingresar un password");
                } else {
                    helper.insertarRegistrosUsuarios(String.valueOf(edtNombre.getText()),
                            String.valueOf(edtCorreo.getText()), String.valueOf(edtClave.getText()));

                    helper.cerrarBD();

                    Toast.makeText(getApplicationContext(), "Usuario Guardado con Éxito", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }
}
