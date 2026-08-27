package presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;

import uta.edu.ec.android_app_003.R;

public class MainActivity extends AppCompatActivity {

    private Button buttonClose;
    private EditText editTextUser;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonClose = findViewById(R.id.buttonClose);
        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void buttonCloseClic(View view) {
        finish();
    }

    public void buttonOkClic(View view) {
        String user = editTextUser.getText().toString();
        String password = editTextPassword.getText().toString();

        if (user.equals("user123") && password.equals("user123")) {
            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("userParameter", user);
            intent.putExtra("passwordParameter", password);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario y/o clave incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    public void buttonBiometricClic(View view) {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG | BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Executor executor = ContextCompat.getMainExecutor(this);
                BiometricPrompt biometricPrompt = new BiometricPrompt(MainActivity.this,
                        executor, new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(getApplicationContext(), "Error: " + errString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        intent.putExtra("userParameter", editTextUser.getText().toString());
                        intent.putExtra("passwordParameter", "Biometric");
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(), "Autenticación fallida", Toast.LENGTH_SHORT).show();
                    }
                });

                BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Inicio sesión biométrico")
                        .setSubtitle("Use su huella digital")
                        .setNegativeButtonText("Cancelar")
                        .build();

                biometricPrompt.authenticate(promptInfo);
                break;
            default:
                Toast.makeText(this, "Biometría no disponible", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
