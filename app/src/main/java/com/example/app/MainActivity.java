package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtCodigo,edtNombre,edtCantidad;
    Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCodigo=(EditText)findViewById(R.id.edtCodigo);
        edtNombre=(EditText)findViewById(R.id.edtNombre);
        edtCantidad=(EditText)findViewById(R.id.edtCantidad);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);

        final claseBD claseBD=new claseBD(getApplicationContext());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                claseBD.agregarProd(edtCodigo.getText().toString(),edtNombre.getText().toString(), Integer.valueOf(edtCantidad.getText().toString()));
                Toast.makeText(getApplicationContext(),"Se Agrego Correctamente",Toast.LENGTH_SHORT).show();
            }
        });
    }
}