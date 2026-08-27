package uta.edu.ec.android_app_003;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewTitleActivity;
    private EditText editTextCode, editTextAuthor, editTextDescription, editTextPrice;
    private Button buttonInsert, buttonSearch, buttonModified, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar controles
        textViewTitleActivity = findViewById(R.id.textViewTitleActivity);
        
        //Obtener los parametros enviados
        Bundle parameters = this.getIntent().getExtras();

        if (parameters != null) {
            String user = parameters.getString("userParameter");
            // Se puede mostrar en un log o Toast si ya no hay TextView para esto
        }

        // Inicializar nuevos controles
        editTextCode = findViewById(R.id.editTextCode);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonModified = findViewById(R.id.buttonModified);
        buttonDelete = findViewById(R.id.buttonDelete);
    }
    public void buttonBrouserClic (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent);
    }

    public void buttonMapsClic (View view){
        try {
            // Ajustado a Zoom 13 para ver detalles de parques, estadios y parroquias cercanas
            Uri gmmIntentUri = Uri.parse("geo:-1.2543,-78.6229?z=13&q=-1.2543,-78.6229(Ambato)");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                mapIntent.setPackage(null);
                startActivity(mapIntent);
            }
        } catch (Exception e) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com/maps/search/?api=1&query=-1.2543,-78.6229"));
            startActivity(browserIntent);
        }
    }
}