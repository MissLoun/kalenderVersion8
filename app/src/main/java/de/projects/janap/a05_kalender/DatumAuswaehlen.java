package de.projects.janap.a05_kalender;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatumAuswaehlen extends DialogFragment{

    public static final int KENNZEICHNUNG_START_DATUM = 0;
    public static final int KENNZEICHNUNG_ENDE_DATUM = 1;

    private int kennzeichnung = 0;

    public void setKennzeichnung(int pKennzeichnung) {
        this.kennzeichnung = pKennzeichnung;
    }
    public int getKennzeichnung() {
        return kennzeichnung;
    }

    @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            Calendar kalender = Calendar.getInstance();
            int jahr = kalender.get(Calendar.YEAR);
            int monat = kalender.get(Calendar.MONTH);
            int tag = kalender.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), jahr, monat, tag);
        }




}
