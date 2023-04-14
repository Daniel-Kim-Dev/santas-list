import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class DialogDelete extends DialogFragment {
    private DataBase db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        db = new DataBase(getActivity());

        builder.setView(inflater.inflate(R.layout.activity_dialog_delete, null))
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText iD = (EditText) getDialog().findViewById(R.id.delete_id);
                        String ID = iD.getText().toString();

                        if (db.deletePerson(db.getDB(), ID) == 1) {
                            Toast.makeText(getActivity(), "Person Deleted", Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(getActivity(), MainActivity.class);
                            MainActivity.act.finish();
                            startActivity(myIntent);
                        } else {
                            Toast.makeText(getActivity(), "Could Not Find ID", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogDelete.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
