import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

public class DialogSearch extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_dialog_search, null))
                .setPositiveButton(R.string.search, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText fName = (EditText) getDialog().findViewById(R.id.first_name);
                        EditText lName = (EditText) getDialog().findViewById(R.id.last_name);

                        String firstName = fName.getText().toString();
                        String lastName = lName.getText().toString();

                        Intent i = new Intent(getActivity(), SearchPersonActivity.class);
                        i.putExtra("firstName", firstName);
                        i.putExtra("lastName", lastName);
                        startActivity(i);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogSearch.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
