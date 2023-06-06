package com.example.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.Models.Productos;
import com.example.app.adaptador.ListViewProductosAdap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

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
        btnCancelar = findViewById(R.id.btnCancelar);


        listViewProductos = findViewById(R.id.listViewProductos);
        linearLayoutEditar = findViewById(R.id.linearLayoutEditar);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                productoSeleccionado = (Productos) parent.getItemAtPosition(position);
                inputCodigo.setText(productoSeleccionado.getCodigo());
                inputNombre.setText(productoSeleccionado.getNombre());
                inputCantidad.setText(productoSeleccionado.getCantidad());
                //hacer visible
                linearLayoutEditar.setVisibility(View.VISIBLE);
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayoutEditar.setVisibility(View.GONE);
                productoSeleccionado = null;
            }
        });

        inicializarFirebase();
        listarProductos();
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarProductos(){
        databaseReference.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listProductos.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Productos p = objSnaptshot.getValue(Productos.class);
                    listProductos.add(p);
                }
                //iniciar adaptador
                listViewProductosAdap = new ListViewProductosAdap(MainActivity.this,listProductos);
                //arrayAdapterProductos = new ArrayAdapter<Productos>(MainActivity.this, android.R.layout.simple_list_item_1, listProductos);
                listViewProductos.setAdapter(listViewProductosAdap);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moto_cafe, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String codigos  = inputCodigo.getText().toString();
        String nombres = inputNombre.getText().toString();
        String cantidades = inputCantidad.getText().toString();
        switch (item.getItemId()){
            case R.id.menu_agregar:
                insertar();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void insertar(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                MainActivity.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertar,null);
        Button btnInsertar = (Button) mView.findViewById(R.id.btnInsertar);
        final EditText mInputCodigo = (EditText) mView.findViewById(R.id.inputCodigo);
        final EditText mInputNombre = (EditText) mView.findViewById(R.id.inputNombre);
        final EditText mInputCantidad = (EditText) mView.findViewById(R.id.inputCantidad);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigos = mInputCodigo.getText().toString();
                String nombres = mInputNombre.getText().toString();
                String cantidades = mInputCantidad.getText().toString();

                if(codigos.isEmpty() || codigos.length()<3){
                    showError(mInputCodigo, "Codigo Invalido (Min. 3 caracteres)");
                }else if (nombres.isEmpty() || nombres.length()<7){
                    showError(mInputNombre, "Nombre Invalido (Min. 7 letras)");
                }else{
                    Productos p = new Productos();
                    p.setIdproducto(UUID.randomUUID().toString());
                    p.setCodigo(codigos);
                    p.setNombre(nombres);
                    p.setCantidad(cantidades);
                    p.setFechareg(getFechaNormal(getFechaMilisegundos()));
                    p.setTimestamp(getFechaMilisegundos() * -1);
                    databaseReference.child("Productos").child(p.getIdproducto()).setValue(p);
                    Toast.makeText(MainActivity.this, "Registrado Correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void showError(EditText input, String s){
        input.requestFocus();
        input.setError(s);
    }

    public long getFechaMilisegundos(){
        Calendar calendar = Calendar.getInstance();
        long tiempounix = calendar.getTimeInMillis();
        return tiempounix;
    }

    public String getFechaNormal(long fechamilisegundos){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));

        String fecha = sdf.format(fechamilisegundos);
        return fecha;
    }

}