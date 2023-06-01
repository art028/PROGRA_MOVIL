package com.example.app.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.app.Models.Productos;
import com.example.app.R;

import java.util.ArrayList;

public class ListViewProductosAdap extends BaseAdapter {

    Context context;
    ArrayList<Productos> productosData;
    LayoutInflater layoutInflater;
    Productos productoModel;

    public ListViewProductosAdap(Context context, ArrayList<Productos> productosData) {
        this.context = context;
        this.productosData = productosData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return productosData.size();
    }

    @Override
    public Object getItem(int position) {
        return productosData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_productos,
                    null,
            true);
        }
        //enlazar vistas
        TextView codigos = rowView.findViewById(R.id.lblcodigo);
        TextView nombre = rowView.findViewById(R.id.lblnombre);
        TextView cantidad = rowView.findViewById(R.id.lblcantidad);

        productoModel = productosData.get(position);
        codigos.setText(productoModel.getCodigo());
        nombre.setText(productoModel.getNombre());
        cantidad.setText(productoModel.getCantidad());

        return rowView;
    }
}
