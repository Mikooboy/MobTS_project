package com.example.mobts_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class AddMedicine extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.add_medicine, null);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v)
                // Add action buttons
                .setPositiveButton(R.string.button_addMedicine, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Get medicine name and reminder days
                        EditText name = (EditText) v.findViewById(R.id.medicineName);
                        ArrayList<String> days = new ArrayList<String>();

                        CheckBox ma = (CheckBox) v.findViewById(R.id.ma);
                        CheckBox ti = (CheckBox) v.findViewById(R.id.ti);
                        CheckBox ke = (CheckBox) v.findViewById(R.id.ke);
                        CheckBox to = (CheckBox) v.findViewById(R.id.to);
                        CheckBox pe = (CheckBox) v.findViewById(R.id.pe);
                        CheckBox la = (CheckBox) v.findViewById(R.id.la);
                        CheckBox su = (CheckBox) v.findViewById(R.id.su);

                        if(ma.isChecked())
                            days.add("ma");
                        if(ti.isChecked())
                            days.add("ti");
                        if(ke.isChecked())
                            days.add("ke");
                        if(to.isChecked())
                            days.add("to");
                        if(pe.isChecked())
                            days.add("pe");
                        if(la.isChecked())
                            days.add("la");
                        if(su.isChecked())
                            days.add("su");

                        Medicines instance = Medicines.getInstance();
                        instance.getMedications().add(new Medicine(name.getText().toString(), days));
                        ((MedicineActivity)getActivity()).updateReminders();
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ((MedicineActivity)getActivity()).updateReminders();
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
