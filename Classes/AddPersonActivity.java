import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPersonActivity extends AppCompatActivity {
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        db = new DataBase(this);
        Button button = findViewById(R.id.button_add);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText firstName = findViewById(R.id.first_name);
                EditText lastName = findViewById(R.id.last_name);
                EditText birthDate = findViewById(R.id.birth_date);
                EditText street = findViewById(R.id.street);
                EditText city = findViewById(R.id.city);
                EditText province = findViewById(R.id.province);
                EditText postalCode = findViewById(R.id.postal_code);
                EditText country = findViewById(R.id.country);
                EditText latitude = findViewById(R.id.latitude);
                EditText longitude = findViewById(R.id.longitude);
                EditText isNaughty = findViewById(R.id.is_naughty);

                boolean naughty = false;

                if ((isNaughty.getText().toString().toLowerCase()).equals("true")) {
                   naughty = true;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new Date());

                double lati = 0.0;
                double longi = 0.0;

                if (!((latitude.getText().toString()).equals(""))) {
                    lati = Double.parseDouble(latitude.getText().toString());
                }

                if (!((longitude.getText().toString()).equals(""))) {
                    longi = Double.parseDouble(longitude.getText().toString());
                }


                Person person = new Person(firstName.getText().toString(), lastName.getText().toString(),
                                           birthDate.getText().toString(), street.getText().toString(),
                                           city.getText().toString(), province.getText().toString(),
                                           postalCode.getText().toString(), country.getText().toString(),
                                           lati, longi, naughty, date);

                db.insertPerson(db.getDB(), person);
                Toast.makeText(AddPersonActivity.this, "Person Added", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(AddPersonActivity.this, MainActivity.class);
                finish();
                MainActivity.act.finish();
                startActivity(myIntent);
            }
        });
    }
}
