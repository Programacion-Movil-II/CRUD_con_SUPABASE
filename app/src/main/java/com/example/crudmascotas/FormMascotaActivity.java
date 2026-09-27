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

public class FormMascotaActivity extends AppCompatActivity {

    private EditText etNombre, etRaza, etEdad, etColor;
    private Button btnGuardar;
    private TextView tvTituloForm;
    private ApiService apiService;
    private Long mascotaId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mascota);

        etNombre = findViewById(R.id.etNombre);
        etRaza = findViewById(R.id.etRaza);
        etEdad = findViewById(R.id.etEdad);
        etColor = findViewById(R.id.etColor);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvTituloForm = findViewById(R.id.tvTituloForm);

        apiService = RetrofitClient.getClient().create(ApiService.class);

        Intent intent = getIntent();

        if (intent.hasExtra("id")) {
            mascotaId = intent.getLongExtra("id", -1);

            tvTituloForm.setText("Editar Mascota");
            btnGuardar.setText("Actualizar");

            etNombre.setText(intent.getStringExtra("nombre"));
            etRaza.setText(intent.getStringExtra("raza"));
            etEdad.setText(String.valueOf(intent.getIntExtra("edad", 0)));
            etColor.setText(intent.getStringExtra("color"));
        }

        btnGuardar.setOnClickListener(v -> guardarMascota());
    }

    private void guardarMascota() {
        if (!validarCampos()) return;

        Mascota mascota = new Mascota(
                etNombre.getText().toString().trim(),
                etRaza.getText().toString().trim(),
                Integer.parseInt(etEdad.getText().toString().trim()),
                etColor.getText().toString().trim()
        );

        if (mascotaId == null) {
            enviarPeticion(
                    apiService.crearMascota(mascota),
                    "Mascota guardada"
            );
        } else {
            enviarPeticion(
                    apiService.actualizarMascota("eq." + mascotaId, mascota),
                    "Mascota actualizada"
            );
        }
    }

    private boolean validarCampos() {
        if (etNombre.getText().toString().trim().isEmpty()
                || etRaza.getText().toString().trim().isEmpty()
                || etEdad.getText().toString().trim().isEmpty()
                || etColor.getText().toString().trim().isEmpty()) {

            Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        return true;
    }

    private void enviarPeticion(Call<List<Mascota>> peticion, String mensaje) {
        peticion.enqueue(new Callback<List<Mascota>>() {

            @Override
            public void onResponse(
                    Call<List<Mascota>> call,
                    Response<List<Mascota>> response
            ) {
                if (response.isSuccessful()) {
                    Toast.makeText(
                            FormMascotaActivity.this,
                            mensaje,
                            Toast.LENGTH_SHORT
                    ).show();

                    finish();
                } else {
                    Toast.makeText(
                            FormMascotaActivity.this,
                            "Error: " + response.code(),
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
        });
    }
}