package common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MainHelper extends SQLiteOpenHelper {
    public MainHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Crear los objetos de base de datos
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_BOOKS = "CREATE TABLE Books (" +
                "Code INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Author TEXT, " +
                "Description TEXT, " +
                "Price REAL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_BOOKS);
    }

    // Para actualizar los objetos de datos
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Books");
        onCreate(sqLiteDatabase);
    }
}
