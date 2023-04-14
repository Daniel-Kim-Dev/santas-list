import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetailsActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    public PersonDetailsActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        String personFirstName = getIntent().getExtras().get("FirstName").toString();
        String personLastName = getIntent().getExtras().get("LastName").toString();

        Person person = getPerson(personFirstName, personLastName);
        int ID = getID(personFirstName, personLastName);

        TextView text1 = (TextView)findViewById(R.id.textView1);
        TextView text2 = (TextView)findViewById(R.id.textView2);
        TextView text3 = (TextView)findViewById(R.id.textView3);
        TextView text4 = (TextView)findViewById(R.id.textView4);
        TextView text5 = (TextView)findViewById(R.id.textView5);
        TextView text6 = (TextView)findViewById(R.id.textView6);
        TextView text7 = (TextView)findViewById(R.id.textView7);
        TextView text8 = (TextView)findViewById(R.id.textView8);
        TextView text9 = (TextView)findViewById(R.id.textView9);
        TextView text10 = (TextView)findViewById(R.id.textView10);
        TextView text11 = (TextView)findViewById(R.id.textView11);
        TextView text12 = (TextView)findViewById(R.id.textView12);
        TextView text13 = (TextView)findViewById(R.id.textView13);

        text1.setText("ID: " + ID);
        text2.setText("First Name: " + person.getFirstName());
        text3.setText("Last Name: " + person.getLastName());
        text4.setText("Birth Date: " + person.getBirthDate());
        text5.setText("Street: " + person.getStreet());
        text6.setText("City: " + person.getCity());
        text7.setText("Province: " + person.getProvince());
        text8.setText("Postal Code: " + person.getPostalCode());
        text9.setText("Country: " + person.getCountry());
        text10.setText("Latitude: " + person.getLatitude());
        text11.setText("Longitude: " + person.getLongitude());
        text12.setText("Is Naughty: " + person.getIsNaughty());
        text13.setText("Date Created: " + person.getDateCreated());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null)
            cursor.close();

        if (db != null)
            db.close();
    }

    public Person getPerson(String personFirstName, String personLastName) {
        Person person = null;
        SQLiteOpenHelper helper = new DataBase(this);

        try {
            db = helper.getReadableDatabase();
            cursor = db.query("SANTASLIST",
                    new String[] {"FirstName", "LastName", "BirthDate", "Street", "City", "Province", "PostalCode", "Country", "Latitude", "Longitude", "IsNaughty", "DateCreated"},
                    "FirstName = ? AND LastName = ?",
                    new String[] {personFirstName, personLastName},
                    null, null, null);

            if (cursor.moveToFirst()) {
                boolean isNaughty = false;

                if (cursor.getInt(10) == 1) {
                    isNaughty = true;
                }

                person = new Person(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9),
                        isNaughty,
                        cursor.getString(11));
            }
        } catch (SQLiteException sqlex) {
            String msg = "[PersonDetailsActivity/getPerson] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return person;
    }

    public int getID(String personFirstName, String personLastName) {
        SQLiteOpenHelper helper = new DataBase(this);
        int ID = -1;

        try {
            db = helper.getReadableDatabase();
            cursor = db.query("SANTASLIST",
                    new String[] {"ID", "FirstName", "LastName"},
                    "FirstName = ? AND LastName = ?",
                    new String[] {personFirstName, personLastName},
                    null, null, null);

            if (cursor.moveToFirst()) {
                ID = cursor.getInt(0);
            }
        } catch (SQLiteException sqlex) {
            String msg = "[PersonDetailsActivity/getID] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return ID;
    }
}
