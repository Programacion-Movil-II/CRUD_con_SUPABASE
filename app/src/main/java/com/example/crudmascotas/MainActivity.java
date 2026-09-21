package com.example.crudmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerMascotas;
    private Button btnAdd;

    private MascotaAdapter mascotaAdapter;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_main
        );


        recyclerMascotas =
                findViewById(
                        R.id.recyclerMascotas
                );

        btnAdd =
                findViewById(
                        R.id.btnAdd
                );


        recyclerMascotas.setLayoutManager(
                new LinearLayoutManager(this)
        );


        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);


        // ADICIONAR MASCOTA
        btnAdd.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    FormMascotaActivity.class
            );

            startActivity(intent);
        });


        obtenerMascotas();
    }


    // ==============================
    // READ
    // ==============================
    private void obtenerMascotas() {

        apiService.getMascotas()
                .enqueue(
                        new Callback<List<Mascota>>() {

                            @Override
                            public void onResponse(
                                    Call<List<Mascota>> call,
                                    Response<List<Mascota>> response
                            ) {

                                if (response.isSuccessful()
                                        && response.body() != null) {

                                    List<Mascota> mascotas =
                                            response.body();


                                    mascotaAdapter =
                                            new MascotaAdapter(
                                                    mascotas,
                                                    new MascotaAdapter
                                                            .OnMascotaClickListener() {

                                                        @Override
                                                        public void onEditarClick(
                                                                Mascota mascota
                                                        ) {

                                                            abrirEditar(
                                                                    mascota
                                                            );
                                                        }


                                                        @Override
                                                        public void onEliminarClick(
                                                                Mascota mascota
                                                        ) {

                                                            eliminarMascota(
                                                                    mascota
                                                            );
                                                        }
                                                    }
                                            );


                                    recyclerMascotas.setAdapter(
                                            mascotaAdapter
                                    );

                                } else {

                                    Log.e(
                                            "ERROR",
                                            "Error GET: "
                                                    + response.code()
                                    );
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<List<Mascota>> call,
                                    Throwable t
                            ) {

                                Log.e(
                                        "ERROR",
                                        "Error conexión: "
                                                + t.getMessage()
                                );
                            }
                        }
                );
    }


    // ==============================
    // ABRIR FORMULARIO PARA EDITAR
    // ==============================
    private void abrirEditar(
            Mascota mascota
    ) {

        Intent intent = new Intent(
                MainActivity.this,
                FormMascotaActivity.class
        );


        intent.putExtra(
                "id",
                mascota.getId()
        );

        intent.putExtra(
                "nombre",
                mascota.getNombre()
        );

        intent.putExtra(
                "raza",
                mascota.getRaza()
        );

        intent.putExtra(
                "edad",
                mascota.getEdad()
        );

        intent.putExtra(
                "color",
                mascota.getColor()
        );


        startActivity(intent);
    }


    // ==============================
    // DELETE
    // ==============================
    private void eliminarMascota(
            Mascota mascota
    ) {

        String filtro =
                "eq." + mascota.getId();


        apiService.eliminarMascota(
                        filtro
                )
                .enqueue(
                        new Callback<Void>() {

                            @Override
                            public void onResponse(
                                    Call<Void> call,
                                    Response<Void> response
                            ) {

                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Mascota eliminada",
                                            Toast.LENGTH_SHORT
                                    ).show();


                                    // Volvemos a consultar PostgreSQL
                                    obtenerMascotas();

                                } else {

                                    Toast.makeText(
                                            MainActivity.this,
                                            "Error al eliminar: "
                                                    + response.code(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<Void> call,
                                    Throwable t
                            ) {

                                Toast.makeText(
                                        MainActivity.this,
                                        "Error de conexión",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );
    }


    @Override
    protected void onResume() {

        super.onResume();

        if (apiService != null) {

            obtenerMascotas();
        }
    }
}