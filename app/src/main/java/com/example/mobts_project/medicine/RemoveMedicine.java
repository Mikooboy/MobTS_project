package com.example.mobts_project.medicine;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mobts_project.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Creates the Dialog that is used for removing medicines.
 * @author Miko Laasanen
 */
public class RemoveMedicine extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.remove_medicine);
        dialog.show();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Medicine> medicines = MedicineLists.getInstance().getMedications();

        for (int i = 0; i < medicines.size(); i++) {
            names.add(medicines.get(i).getName());
        }

        Button remove = dialog.findViewById(R.id.button3);
        Button cancel = dialog.findViewById(R.id.button4);

        if (names.size() > 0) {
            ListView lv = dialog.findViewById(R.id.removeMedicineList);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, names) {
                @Override
                public int getViewTypeCount() {

                    return getCount();
                }

                @Override
                public int getItemViewType(int position) {

                    return position;
                }
            };
            lv.setAdapter(adapter);
            lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Medicine> newMedicineList = new ArrayList<>();

                    int count = lv.getChildCount();
                    for (int i = 0; i < count; i++) {
                        if (!lv.isItemChecked(i)) {
                            //do your task/work
                            newMedicineList.add(medicines.get(i));
                        }
                    }

                    MedicineLists.getInstance().setMedications(newMedicineList);
                    dialog.dismiss();
                    ((MedicineActivity) Objects.requireNonNull(getActivity())).saveMedicines();

                    ((MedicineActivity) Objects.requireNonNull(getActivity())).updateReminders();
                }
            });
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        return dialog;
    }
}