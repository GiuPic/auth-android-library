package it.giuseppepiccolo.auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    // Definisce l'endpoint per il login, usando una richiesta POST
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
