package presentation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import common.MainHelper;
import uta.edu.ec.android_app_003.R;

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
        editTextCode = findViewById(R.id.editTextCode);
        editTextAuthor = findViewById(R.id.editTextAuthor);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPrice = findViewById(R.id.editTextPrice);

        buttonInsert = findViewById(R.id.buttonInsert);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonModified = findViewById(R.id.buttonModified);
        buttonDelete = findViewById(R.id.buttonDelete);
    }

    public void buttonInsertClic(View view) {
        // Conexión a la base de datos
        MainHelper helper = new MainHelper(this, "LibrosDB", null, 1);
        // Abrir la base de datos en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // Obtener datos de los controles
        String code = editTextCode.getText().toString();
        String author = editTextAuthor.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();

        // Validar que no estén vacíos
        if (!code.isEmpty() && !author.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("Code", code);
            values.put("Author", author);
            values.put("Description", description);
            values.put("Price", price);

            // Enviar a la BD
            long count = db.insert("Books", null, values);

            if (count > 0) {
                Toast.makeText(this, "Registro insertado ", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "No se puede insertar el registro", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        }
        // Cerrar la conexión
        db.close();
    }
    private void cleanControls(){
        editTextCode.setText("");
        editTextAuthor.setText("");
        editTextDescription.setText("");
        editTextPrice.setText("");
    }

    public void buttonSearchClic(View view) {
        // Conexión a la base de datos
        MainHelper helper = new MainHelper(this, "LibrosDB", null, 1);
        // Abrir la base de datos en modo lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        // Obtener el código para buscar
        String code = editTextCode.getText().toString();

        if (!code.isEmpty()) {
            // Ejecutar consulta
            Cursor cursor = db.rawQuery("SELECT Author, Description, Price FROM Books WHERE Code=" + code, null);

            if (cursor.moveToFirst()) {
                // Mostrar resultados en los campos
                editTextAuthor.setText(cursor.getString(0));
                editTextDescription.setText(cursor.getString(1));
                editTextPrice.setText(cursor.getString(2));
            } else {
                Toast.makeText(this, "Libro no encontrado", Toast.LENGTH_SHORT).show();
                clearFieldsExceptCode();
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Ingrese un código para buscar", Toast.LENGTH_SHORT).show();
        }
        // Cerrar la conexión
        db.close();
    }

    public void buttonModifiedClic(View view) {
        // Conexión a la base de datos
        MainHelper helper = new MainHelper(this, "LibrosDB", null, 1);
        // Abrir la base de datos en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // Obtener datos de los controles
        String code = editTextCode.getText().toString();
        String author = editTextAuthor.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();

        if (!code.isEmpty() && !author.isEmpty() && !description.isEmpty() && !price.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put("Author", author);
            values.put("Description", description);
            values.put("Price", price);

            // Actualizar registro en la BD
            int count = db.update("Books", values, "Code=" + code, null);
            if (count > 0) {
                Toast.makeText(this, "Registro modificado con éxito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No se encontró el registro para modificar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Complete todos los campos para modificar", Toast.LENGTH_SHORT).show();
        }
        // Cerrar la conexión
        db.close();
    }

    public void buttonDeleteClic(View view) {
        // Conexión a la base de datos
        MainHelper helper = new MainHelper(this, "LibrosDB", null, 1);
        // Abrir la base de datos en modo escritura
        SQLiteDatabase db = helper.getWritableDatabase();

        // Obtener el código para eliminar
        String code = editTextCode.getText().toString();

        if (!code.isEmpty()) {
            // Eliminar registro de la BD
            int count = db.delete("Books", "Code=" + code, null);
            if (count > 0) {
                Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
                clearFields();
            } else {
                Toast.makeText(this, "No se encontró el registro para eliminar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese un código para eliminar", Toast.LENGTH_SHORT).show();
        }
        // Cerrar la conexión
        db.close();
    }

    public void buttonBrouserClic(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent);
    }

    public void buttonMapsClic(View view) {
        try {
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

    private void clearFields() {
        editTextCode.setText("");
        editTextAuthor.setText("");
        editTextDescription.setText("");
        editTextPrice.setText("");
    }

    private void clearFieldsExceptCode() {
        editTextAuthor.setText("");
        editTextDescription.setText("");
        editTextPrice.setText("");
    }
}
