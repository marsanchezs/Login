package com.example.mario.login;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import OpenHelper.SQLite_OpenHelper;

public class MainActivity extends AppCompatActivity {

    TextView tvRegistrese;
    Button btnIngresar;

    SQLite_OpenHelper helper = new SQLite_OpenHelper(this, "BD1", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvRegistrese=(TextView)findViewById(R.id.textView3);

        tvRegistrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), registro.class);
                startActivity(i);
            }
        });

        btnIngresar=(Button)findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edtUser = (EditText)findViewById(R.id.edtIngresaMail);
                EditText edtPass = (EditText)findViewById(R.id.edtIngresaPass);

                Cursor micursor2 = helper.validarUsuario(edtUser.getText().toString(), edtPass.getText().toString());

                if (micursor2.getCount()>0){
                    Intent i = new Intent(getApplicationContext(), RegistroProducto.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(),"Usuario y/o Password Incorrectos", Toast.LENGTH_LONG).show();
                }

                edtUser.setText("");
                edtPass.setText("");
                edtUser.findFocus();
            }
        });
    }
}
