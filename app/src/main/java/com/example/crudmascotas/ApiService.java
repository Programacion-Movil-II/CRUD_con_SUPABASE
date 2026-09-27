package com.example.crudmascotas;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;
public interface ApiService {
    @GET("mascotas")
    Call<List<Mascota>> getMascotas();
    @Headers("Prefer: return=representation")
    @POST("mascotas")
    Call<List<Mascota>> crearMascota(
            @Body Mascota mascota
    );
    @Headers("Prefer: return=representation")
    @PATCH("mascotas")
    Call<List<Mascota>> actualizarMascota(
            @Query("id") String idFiltro,
            @Body Mascota mascota
    );
    @DELETE("mascotas")
    Call<Void> eliminarMascota(
            @Query("id") String idFiltro
    );
}
