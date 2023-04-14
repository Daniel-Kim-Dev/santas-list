import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static Activity act;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] fullName = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupPeopleListView();
    }

    private String[] getNames() {
        SQLiteOpenHelper helper = new DataBase(this);
        String[] names = null;

        try {
            db = helper.getReadableDatabase();
            cursor = db.rawQuery("SELECT FirstName, LastName FROM SANTASLIST", null);

            int count = cursor.getCount();
            names = new String[count];

            if (cursor.moveToFirst()) {
                int ndx = 0;
                do {
                    String firstName = cursor.getString(0);
                    String lastName = cursor.getString(1);

                    names[ndx++] = firstName + " " + lastName;
                } while (cursor.moveToNext());
            }
        } catch (SQLiteException sqlex) {
            String msg = "[MainActivity / getNames] DB unavailable";
            msg += "\n\n" + sqlex.toString();

            Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            t.show();
        }

        return names;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cursor != null)
            cursor.close();

        if (db != null)
            db.close();
    }

    private void splitFirstAndLastName(String name) {
        String firstName = "";
        String lastName = "";

        int i = 0;

        while (name.charAt(i) != ' ') {
            firstName += name.charAt(i);
            i++;
        }

        i++;

        for (; i < name.length(); i++) {
            lastName += name.charAt(i);
        }

        fullName[0] = firstName;
        fullName[1] = lastName;
    }

    private void setupPeopleListView() {
        ListView list_people = (ListView) findViewById(R.id.list_people);

        String[] people = getNames();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, people
        );

        list_people.setAdapter(arrayAdapter);
        list_people.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = (TextView) view;
                String person = tv.getText().toString();
                splitFirstAndLastName(person);

                if ((getResources().getConfiguration().screenLayout &
                        Configuration.SCREENLAYOUT_SIZE_MASK) ==
                        Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                    Bundle bundle = new Bundle();
                    bundle.putString("FirstName", fullName[0]);
                    bundle.putString("LastName", fullName[1]);

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Fragment details = new PersonDetailsFragment();//the fragment you want to show
                    details.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, details);//R.id.content_frame is the layout you want to replace
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                } else {
                    Intent intent = new Intent(MainActivity.this, PersonDetailsActivity.class);
                    intent.putExtra("FirstName", fullName[0]);
                    intent.putExtra("LastName", fullName[1]);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.add_person:
                i = new Intent(this, AddPersonActivity.class);
                startActivity(i);
                return true;

            case R.id.search_person:
                DialogFragment dialog = new DialogSearch();
                dialog.show(getSupportFragmentManager(), "searchDialogFragment");
                return true;

            case R.id.edit_person:
                DialogFragment dialog2 = new DialogEdit();
                dialog2.show(getSupportFragmentManager(), "editDialogFragment");
                return true;

            case R.id.delete_person:
                DialogFragment dialog3 = new DialogDelete();
                dialog3.show(getSupportFragmentManager(), "deleteDialogFragment");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
