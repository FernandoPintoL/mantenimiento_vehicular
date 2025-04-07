package com.fpl.mantenimientovehicular.vista.vehiculo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fpl.mantenimientovehicular.R;
import com.fpl.mantenimientovehicular.model.vehiculo.Vehiculo;

import java.util.List;

public class VehiculoAdapter extends ArrayAdapter<Vehiculo> {
    public VehiculoAdapter(Context context, List<Vehiculo> vehiculos) {
        super(context, 0, vehiculos);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vehiculo vehiculo = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_vehiculo, parent, false);
        }

        TextView tvMarcaModelo = convertView.findViewById(R.id.tvMarcaModelo);
        TextView tvPlaca = convertView.findViewById(R.id.tvPlaca);
        TextView tvKilometraje = convertView.findViewById(R.id.tvKilometraje);

        assert vehiculo != null;
        tvMarcaModelo.setText(vehiculo.getMarca() + " " + vehiculo.getModelo());
        tvPlaca.setText(vehiculo.getPlaca());
        tvKilometraje.setText("KM: " + vehiculo.getKilometraje());

        return convertView;
    }
}
