package it.giuseppepiccolo.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private MaterialButton loginButton;
    private AuthManager authManager;

    public static void start(Context context, String baseUrl) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("base_url", baseUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ottieni l'URL base dalle extra
        String baseUrl = getIntent().getStringExtra("base_url");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);

        authManager = new AuthManager(this, baseUrl);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                performLogin(email, password);
            }
        });
    }

    private void performLogin(String email, String password) {
        authManager.login(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess(String token) {
                Toast.makeText(LoginActivity.this, "Login Success! Token: " + token, Toast.LENGTH_SHORT).show();
                // Puoi chiudere l'activity o navigare altrove
                finish();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(LoginActivity.this, "Login Failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
