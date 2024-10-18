package it.giuseppepiccolo.auth;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {

    private static final String PREFS_NAME = "auth_prefs";
    private static final String TOKEN_KEY = "token";

    private final ApiService apiService;
    private final SharedPreferences sharedPreferences;

    public AuthManager(Context context, String baseUrl) {
        apiService = ApiClient.getClient(baseUrl).create(ApiService.class);
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void login(String email, String password, AuthCallback callback) {
        Call<LoginResponse> call = apiService.login(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getToken();
                    saveToken(token);
                    callback.onSuccess(token);
                } else {
                    callback.onFailure("Login failed");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public interface AuthCallback {
        void onSuccess(String token);
        void onFailure(String message);
    }
}
