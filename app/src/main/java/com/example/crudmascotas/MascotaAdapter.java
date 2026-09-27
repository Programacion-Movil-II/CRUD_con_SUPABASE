package com.example.crudmascotas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<Mascota> mascotas;
    private Consumer<Mascota> editar, eliminar;

    public MascotaAdapter(
            List<Mascota> mascotas,
            Consumer<Mascota> editar,
            Consumer<Mascota> eliminar
    ) {
        this.mascotas = mascotas;
        this.editar = editar;
        this.eliminar = eliminar;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mascota, parent, false);

        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = mascotas.get(position);

        holder.tvNombre.setText(mascota.getNombre());
        holder.tvRaza.setText("Raza: " + mascota.getRaza());
        holder.tvEdad.setText("Edad: " + mascota.getEdad());
        holder.tvColor.setText("Color: " + mascota.getColor());

        holder.btnEditar.setOnClickListener(v -> editar.accept(mascota));
        holder.btnEliminar.setOnClickListener(v -> eliminar.accept(mascota));
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvRaza, tvEdad, tvColor;
        Button btnEditar, btnEliminar;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvRaza = itemView.findViewById(R.id.tvRaza);
            tvEdad = itemView.findViewById(R.id.tvEdad);
            tvColor = itemView.findViewById(R.id.tvColor);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}