package com.example.crudmascotas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MascotaAdapter
        extends RecyclerView.Adapter<MascotaAdapter.MascotaViewHolder> {

    private List<Mascota> listaMascotas;
    private OnMascotaClickListener listener;

    public interface OnMascotaClickListener {

        void onEditarClick(Mascota mascota);

        void onEliminarClick(Mascota mascota);
    }

    public MascotaAdapter(
            List<Mascota> listaMascotas,
            OnMascotaClickListener listener
    ) {
        this.listaMascotas = listaMascotas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_mascota,
                        parent,
                        false
                );

        return new MascotaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull MascotaViewHolder holder,
            int position
    ) {

        Mascota mascota = listaMascotas.get(position);

        holder.tvNombre.setText(
                mascota.getNombre()
        );

        holder.tvRaza.setText(
                "Raza: " + mascota.getRaza()
        );

        holder.tvEdad.setText(
                "Edad: " + mascota.getEdad() + " años"
        );

        holder.tvColor.setText(
                "Color: " + mascota.getColor()
        );

        holder.btnEditar.setOnClickListener(v -> {
            listener.onEditarClick(mascota);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onEliminarClick(mascota);
        });
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class MascotaViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvNombre;
        TextView tvRaza;
        TextView tvEdad;
        TextView tvColor;

        Button btnEditar;
        Button btnEliminar;

        public MascotaViewHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            tvNombre =
                    itemView.findViewById(
                            R.id.tvNombre
                    );

            tvRaza =
                    itemView.findViewById(
                            R.id.tvRaza
                    );

            tvEdad =
                    itemView.findViewById(
                            R.id.tvEdad
                    );

            tvColor =
                    itemView.findViewById(
                            R.id.tvColor
                    );

            btnEditar =
                    itemView.findViewById(
                            R.id.btnEditar
                    );

            btnEliminar =
                    itemView.findViewById(
                            R.id.btnEliminar
                    );
        }
    }
}