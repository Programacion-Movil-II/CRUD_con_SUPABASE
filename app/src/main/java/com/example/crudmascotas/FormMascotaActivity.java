package com.example.crudmascotas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormMascotaActivity
        extends AppCompatActivity {

    private EditText etNombre;
    private EditText etRaza;
    private EditText etEdad;
    private EditText etColor;

    private Button btnGuardar;
    private TextView tvTituloForm;

    private ApiService apiService;

    // null significa que estamos creando
    private Long mascotaId = null;


    @Override
    protected void onCreate(
            Bundle savedInstanceState
    ) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_form_mascota
        );


        etNombre =
                findViewById(
                        R.id.etNombre
                );

        etRaza =
                findViewById(
                        R.id.etRaza
                );

        etEdad =
                findViewById(
                        R.id.etEdad
                );

        etColor =
                findViewById(
                        R.id.etColor
                );

        btnGuardar =
                findViewById(
                        R.id.btnGuardar
                );

        tvTituloForm =
                findViewById(
                        R.id.tvTituloForm
                );


        apiService = RetrofitClient
                .getClient()
                .create(ApiService.class);


        // Revisamos si recibimos un ID
        Intent intent =
                getIntent();


        if (intent.hasExtra("id")) {

            mascotaId =
                    intent.getLongExtra(
                            "id",
                            -1
                    );


            // Cambiar título
            tvTituloForm.setText(
                    "Editar Mascota"
            );


            // Cambiar botón
            btnGuardar.setText(
                    "Actualizar"
            );


            // Cargar datos actuales
            etNombre.setText(
                    intent.getStringExtra(
                            "nombre"
                    )
            );

            etRaza.setText(
                    intent.getStringExtra(
                            "raza"
                    )
            );

            etEdad.setText(
                    String.valueOf(
                            intent.getIntExtra(
                                    "edad",
                                    0
                            )
                    )
            );

            etColor.setText(
                    intent.getStringExtra(
                            "color"
                    )
            );
        }


        btnGuardar.setOnClickListener(v -> {

            if (mascotaId == null) {

                crearMascota();

            } else {

                actualizarMascota();
            }
        });
    }


    // ==============================
    // VALIDAR
    // ==============================
    private boolean validarCampos() {

        if (
                etNombre.getText()
                        .toString()
                        .trim()
                        .isEmpty()

                        ||

                        etRaza.getText()
                                .toString()
                                .trim()
                                .isEmpty()

                        ||

                        etEdad.getText()
                                .toString()
                                .trim()
                                .isEmpty()

                        ||

                        etColor.getText()
                                .toString()
                                .trim()
                                .isEmpty()
        ) {

            Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }


        return true;
    }


    // ==============================
    // CREATE
    // ==============================
    private void crearMascota() {

        if (!validarCampos()) {
            return;
        }


        String nombre =
                etNombre.getText()
                        .toString()
                        .trim();

        String raza =
                etRaza.getText()
                        .toString()
                        .trim();

        int edad =
                Integer.parseInt(
                        etEdad.getText()
                                .toString()
                                .trim()
                );

        String color =
                etColor.getText()
                        .toString()
                        .trim();


        Mascota mascota =
                new Mascota(
                        nombre,
                        raza,
                        edad,
                        color
                );


        apiService.crearMascota(
                        mascota
                )
                .enqueue(
                        new Callback<List<Mascota>>() {

                            @Override
                            public void onResponse(
                                    Call<List<Mascota>> call,
                                    Response<List<Mascota>> response
                            ) {

                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            FormMascotaActivity.this,
                                            "Mascota guardada",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    finish();

                                } else {

                                    Toast.makeText(
                                            FormMascotaActivity.this,
                                            "Error al guardar: "
                                                    + response.code(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<List<Mascota>> call,
                                    Throwable t
                            ) {

                                Toast.makeText(
                                        FormMascotaActivity.this,
                                        "Error de conexión",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );
    }


    // ==============================
    // UPDATE
    // ==============================
    private void actualizarMascota() {

        if (!validarCampos()) {
            return;
        }


        String nombre =
                etNombre.getText()
                        .toString()
                        .trim();

        String raza =
                etRaza.getText()
                        .toString()
                        .trim();

        int edad =
                Integer.parseInt(
                        etEdad.getText()
                                .toString()
                                .trim()
                );

        String color =
                etColor.getText()
                        .toString()
                        .trim();


        // No mandamos el ID en el BODY.
        // El ID se usa en el filtro de Supabase.
        Mascota mascota =
                new Mascota(
                        nombre,
                        raza,
                        edad,
                        color
                );


        String filtro =
                "eq." + mascotaId;


        apiService.actualizarMascota(
                        filtro,
                        mascota
                )
                .enqueue(
                        new Callback<List<Mascota>>() {

                            @Override
                            public void onResponse(
                                    Call<List<Mascota>> call,
                                    Response<List<Mascota>> response
                            ) {

                                if (response.isSuccessful()) {

                                    Toast.makeText(
                                            FormMascotaActivity.this,
                                            "Mascota actualizada",
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    finish();

                                } else {

                                    Toast.makeText(
                                            FormMascotaActivity.this,
                                            "Error al actualizar: "
                                                    + response.code(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                }
                            }


                            @Override
                            public void onFailure(
                                    Call<List<Mascota>> call,
                                    Throwable t
                            ) {

                                Toast.makeText(
                                        FormMascotaActivity.this,
                                        "Error de conexión",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                );
    }
}