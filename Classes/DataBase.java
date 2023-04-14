import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "SantasList.sqlite";
    private static final int DB_VERSION = 1;
    private Context context;
    private Cursor cursor;

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public SQLiteDatabase getDB() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        updateMyDatabase(sqLiteDatabase, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        updateMyDatabase(sqLiteDatabase, i, i1);
    }

    private String CreateSantasTableSql() {
        String sql = "";

        sql += "CREATE TABLE SANTASLIST (";
        sql += "ID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        sql += "FirstName TEXT, ";
        sql += "LastName TEXT, ";
        sql += "BirthDate TEXT, ";
        sql += "Street TEXT, ";
        sql += "City TEXT, ";
        sql += "Province TEXT, ";
        sql += "PostalCode TEXT, ";
        sql += "Country TEXT, ";
        sql += "Latitude REAL, ";
        sql += "Longitude REAL, ";
        sql += "IsNaughty NUMERIC, ";
        sql += "DateCreated TEXT);";

        return sql;
    }

    public boolean personExists(SQLiteDatabase db, String id) {
        Cursor cursor;

        try {
            db = this.getReadableDatabase();
            cursor = db.query("SANTASLIST",
                    new String[] {"ID"},
                    "ID = ?",
                    new String[] {id},
                    null, null, null);

            if (cursor.moveToFirst()) {
                return true;
            }
        } catch (SQLiteException sqlex) {
            String msg = "[DataBase/personExists] DB unavailable";
            msg += "\n\n" + sqlex.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return false;
    }

    public void insertPerson(SQLiteDatabase db, Person person) {
        ContentValues values = new ContentValues();

        values.put("FirstName", person.getFirstName());
        values.put("LastName", person.getLastName());
        values.put("BirthDate", person.getBirthDate());
        values.put("Street", person.getStreet());
        values.put("City", person.getCity());
        values.put("Province", person.getProvince());
        values.put("PostalCode", person.getPostalCode());
        values.put("Country", person.getCountry());
        values.put("Latitude", person.getLatitude());
        values.put("Longitude", person.getLongitude());
        values.put("IsNaughty", person.getIsNaughty());
        values.put("DateCreated", person.getDateCreated());

        db.insert("SANTASLIST", null, values);
    }

    public void updatePerson(SQLiteDatabase db, String id, String firstname, String lastname, String birthdate, String street, String city,
                             String province, String postalcode, String country, double latitude, double longitude,
                             boolean isnaughty) {
        ContentValues values = new ContentValues();

        values.put("FirstName", firstname);
        values.put("LastName", lastname);
        values.put("BirthDate", birthdate);
        values.put("Street", street);
        values.put("City", city);
        values.put("Province", province);
        values.put("PostalCode", postalcode);
        values.put("Country", country);
        values.put("Latitude", latitude);
        values.put("Longitude", longitude);
        values.put("IsNaughty", isnaughty);

        db.update("SANTASLIST", values, "ID = ?", new String[] {id});
    }

    public int deletePerson(SQLiteDatabase db, String id) {
        int deleted = 0;
        deleted = db.delete("SANTASLIST", "ID = ?", new String[] {id});

        return deleted;
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            if (oldVersion < 1) {
                db.execSQL(CreateSantasTableSql());
                for (Person p : Person.PEOPLE) {
                    insertPerson(db, p);
                }
            }
        } catch (SQLException sqle) {
            String msg = "[DataBase/updateMyDatabase] DB unavailable";
            msg += "\n\n" + sqle.toString();
            Toast t = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            t.show();
        }
    }
}
