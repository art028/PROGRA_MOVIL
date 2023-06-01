package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.Models.Productos;
import com.example.app.adaptador.ListViewProductosAdap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Productos> listProductos= new ArrayList<Productos>();
    ArrayAdapter<Productos> arrayAdapterProductos;
    ListViewProductosAdap listViewProductosAdap;
    LinearLayout linearLayoutEditar;
    ListView listViewProductos;
    EditText inputCodigo,inputNombre,inputCantidad;
    Button btnCancelar;

    Productos productoSeleccionado;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputCodigo = findViewById(R.id.inputCodigo);
        inputNombre = findViewById(R.id.inputNombre);
        inputCantidad = findViewById(R.id.inputCantidad);

        listViewProductos = findViewById(R.id.listViewProductos);
        linearLayoutEditar = findViewById(R.id.linearLayoutEditar);

        inicializarFirebase();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}