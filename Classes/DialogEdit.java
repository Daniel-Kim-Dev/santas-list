import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class DialogEdit extends DialogFragment {
    private DataBase db;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        db = new DataBase(getActivity());

        builder.setView(inflater.inflate(R.layout.activity_dialog_edit, null))
                .setPositiveButton(R.string.edit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText iD = (EditText) getDialog().findViewById(R.id.edit_id);
                        String ID = iD.getText().toString();

                        if (db.personExists(db.getDB(), ID)) {
                            Intent i = new Intent(getActivity(), EditPersonActivity.class);
                            i.putExtra("id", ID);
                            startActivity(i);
                        } else {
                            Toast.makeText(getActivity(), "Could Not Find ID", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogEdit.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
