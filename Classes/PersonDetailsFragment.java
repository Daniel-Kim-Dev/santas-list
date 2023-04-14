import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PersonDetailsFragment extends Fragment {
    String firstName = "";
    String lastName = "";

    private SQLiteDatabase db;
    private Cursor cursor;

    public PersonDetailsFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firstName = getArguments().getString("FirstName");
        lastName = getArguments().getString("LastName");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_person_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View v = getView();

        if (v != null) {
            Person person = getPerson(firstName, lastName);
            int ID = getID(firstName, lastName);

            TextView text1 = (TextView)v.findViewById(R.id.textView1);
            TextView text2 = (TextView)v.findViewById(R.id.textView2);
            TextView text3 = (TextView)v.findViewById(R.id.textView3);
            TextView text4 = (TextView)v.findViewById(R.id.textView4);
            TextView text5 = (TextView)v.findViewById(R.id.textView5);
            TextView text6 = (TextView)v.findViewById(R.id.textView6);
            TextView text7 = (TextView)v.findViewById(R.id.textView7);
            TextView text8 = (TextView)v.findViewById(R.id.textView8);
            TextView text9 = (TextView)v.findViewById(R.id.textView9);
            TextView text10 = (TextView)v.findViewById(R.id.textView10);
            TextView text11 = (TextView)v.findViewById(R.id.textView11);
            TextView text12 = (TextView)v.findViewById(R.id.textView12);
            TextView text13 = (TextView)v.findViewById(R.id.textView13);

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
    }

    public Person getPerson(String personFirstName, String personLastName) {
        Person person = null;
        SQLiteOpenHelper helper = new DataBase(getActivity());

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

            Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
            t.show();
        }

        return person;
    }

    public int getID(String personFirstName, String personLastName) {
        SQLiteOpenHelper helper = new DataBase(getActivity());
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

            Toast t = Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG);
            t.show();
        }

        return ID;
    }
}
