package de.projects.janap.a05_kalender;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class ZeitAuswaehlen extends DialogFragment {
    public static final int KENNZEICHNUNG_START_ZEIT = 0;
    public static final int KENNZEICHNUNG_ENDE_ZEIT = 1;

    private int kennzeichnung = 0;

    public void setKennzeichnung(int pKennzeichnung) {
        this.kennzeichnung = pKennzeichnung;
    }
    public int getKennzeichnung() {
        return kennzeichnung;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar kalender = Calendar.getInstance();
        int stunde = kalender.get(Calendar.HOUR_OF_DAY);
        int minute = kalender.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), stunde, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }
}
