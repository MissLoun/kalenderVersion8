package de.projects.janap.a05_kalender;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import java.util.Calendar;


public class TerminErstellung extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private EditText titel;
    private TextView startDatum, startZeit, enddatum, endzeit, txtEnde, txtStart;
    private Switch ganztaegigJaNein;
    private Button speichern, abbruch;

    private TextView ueberpruefung;

    /*-------------------------Andere Variablen---------------------------------------------------*/
    private String[] farben = {"#FBC765", "#F08563", "#E76062", "#E53C6E", "#DC276B", "#9D286C", "#742964", "#562363", "#292563", "#153D6B", "#2A6C7C", "#40BD9C"};

    private Termin neuerTermin;
    private Calendar kalender;
    private DatumAuswaehlen datumAuswaehlerDialogFragment;
    private ZeitAuswaehlen zeitAuswaehlenFragment;

    private Boolean titelVorhanden = false;
    private Boolean startDatumVorhanden = false;
    private Boolean startZeitVorhanden = false;
    private Boolean enddatumVorhanden = false;
    private Boolean endzeitVorhanden = false;

    private Boolean ganztaegig = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Andere Methoden----------------------------------------------------*/
    private void oeffneActivityKalenderMonatsUebersicht() {
        Intent intent = new Intent(this, Kalender_Steuerung.class);
        startActivity(intent);
        finish();
    }

    private void initialisieren() {
        titel = findViewById(R.id.txt_Terminerstellung_Eingabe_Titel);
        ganztaegigJaNein = findViewById(R.id.switch_Terminerstellung_Eingabe_JaNein);
        startDatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Start);
        startZeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Start);
        enddatum = findViewById(R.id.txt_Terminerstellung_Eingabe_Datum_Ende);
        endzeit = findViewById(R.id.txt_Terminerstellung_Eingabe_Zeit_Ende);
        speichern = findViewById(R.id.btn_Terminerstellung_Speichern);
        abbruch = findViewById(R.id.btn_Terminerstellung_Abbrechen);
        ueberpruefung = findViewById(R.id.txt_Gespeichert);
        txtEnde = findViewById(R.id.txt_Terminerstellung_Beschriftung_Ende);
        txtStart = findViewById(R.id.txt_Terminerstellung_Beschriftung_Start);

        kalender = Calendar.getInstance();

        datumAuswaehlerDialogFragment = new DatumAuswaehlen();
        zeitAuswaehlenFragment = new ZeitAuswaehlen();

        //setzten der beispielhaften Daten
        startDatum.setHint(new SimpleDateFormat("dd.MM.yyyy").format(kalender.getTime()));
        enddatum.setHint(new SimpleDateFormat("dd.MM.yyyy").format(kalender.getTime()));

        startZeit.setHint(new SimpleDateFormat("HH:mm").format(kalender.getTime()));
        kalender.add(Calendar.MINUTE, 1);
        endzeit.setHint(new SimpleDateFormat("HH:mm").format(kalender.getTime()));

    }

    private void faerben(){
        int farbe = Color.parseColor(farben[kalender.get(Calendar.MONTH)]);
        titel.setBackgroundColor(farbe);
        txtStart.setTextColor(farbe);
        txtEnde.setTextColor(farbe);
        speichern.setBackgroundColor(farbe);
        abbruch.setBackgroundColor(farbe);
    }

    private void lasseUnwichtigesVerschwinde() {
        startZeit.setVisibility(View.INVISIBLE);
        endzeit.setVisibility(View.INVISIBLE);
        enddatum.setVisibility(View.INVISIBLE);
        txtEnde.setVisibility(View.INVISIBLE);
        txtStart.setText(R.string.wann);
    }

    private void allesSichtbarmachen() {
        startZeit.setVisibility(View.VISIBLE);
        endzeit.setVisibility(View.VISIBLE);
        enddatum.setVisibility(View.VISIBLE);
        txtEnde.setVisibility(View.VISIBLE);
        txtStart.setText(R.string.start);
    }

    private void setztenDerOnClickListener() {
        abbruch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActivityKalenderMonatsUebersicht();
            }
        });
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean etwasFehlt = terminDatenUeberpruefen();
                if (etwasFehlt == false) {
                    terminDatenSpeichern();
                }
            }
        });
        startDatum.setOnClickListener(this);
        enddatum.setOnClickListener(this);
        startZeit.setOnClickListener(this);
        endzeit.setOnClickListener(this);
        ganztaegigJaNein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ganztaegig == true) {
                    ganztaegig = false;
                    allesSichtbarmachen();
                } else if (ganztaegig == false) {
                    ganztaegig = true;
                    lasseUnwichtigesVerschwinde();
                }

            }
        });

        titel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0){
                    titelVorhanden = true;
                }
            }
        });
    }

    private Boolean terminDatenUeberpruefen() {
        Boolean min1DatenFehlen = false;
        if (titelVorhanden){
            titel.setTextColor(Color.parseColor("#FF359B38"));
        }else {
            titel.setHintTextColor(Color.parseColor("#FFB60003"));
            min1DatenFehlen = true;
        }
        if (startDatumVorhanden){
            startDatum.setTextColor(Color.parseColor("#FF359B38"));
        }else {
            startDatum.setHintTextColor(Color.parseColor("#FFB60003"));
            min1DatenFehlen = true;

        }
        if (!ganztaegig){
            if (startZeitVorhanden){
                startZeit.setTextColor(Color.parseColor("#FF359B38"));
            }else {
                startZeit.setHintTextColor(Color.parseColor("#FFB60003"));
                min1DatenFehlen = true;
            }
            if (enddatumVorhanden){
                enddatum.setTextColor(Color.parseColor("#FF359B38"));
            }else {
                enddatum.setHintTextColor(Color.parseColor("#FFB60003"));
                min1DatenFehlen = true;
            }
            if (endzeitVorhanden){
                endzeit.setTextColor(Color.parseColor("#FF359B38"));
            }else {
                endzeit.setHintTextColor(Color.parseColor("#FFB60003"));
                min1DatenFehlen = true;
            }

            if (startDatum.getText() == enddatum.getText()){

            }
        }
        if (min1DatenFehlen){
            Toast toast = Toast.makeText(this, R.string.toastInfoFehlt, Toast.LENGTH_SHORT);
            toast.show();
        }
        return min1DatenFehlen;
    }

    private void terminDatenSpeichern() {
        if (ganztaegig == true){
            neuerTermin = new Termin(titel.getText().toString(), startDatum.getText().toString(), "", "", "", ganztaegig);
            ueberpruefung.setText(neuerTermin.getTermin());
        }else {
            neuerTermin = new Termin(titel.getText().toString(), startDatum.getText().toString(), startZeit.getText().toString(), enddatum.getText().toString(), endzeit.getText().toString(), ganztaegig);
            ueberpruefung.setText(neuerTermin.getTermin());
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.txt_Terminerstellung_Eingabe_Datum_Start) {
            datumAuswaehlerDialogFragment.setKennzeichnung(datumAuswaehlerDialogFragment.KENNZEICHNUNG_START_DATUM);
            datumAuswaehlerDialogFragment.show(getSupportFragmentManager(), "Datum auswählen");
        } else if (id == R.id.txt_Terminerstellung_Eingabe_Datum_Ende) {
            datumAuswaehlerDialogFragment.setKennzeichnung(datumAuswaehlerDialogFragment.KENNZEICHNUNG_ENDE_DATUM);
            datumAuswaehlerDialogFragment.show(getSupportFragmentManager(), "Datum auswählen");
        } else if (id == R.id.txt_Terminerstellung_Eingabe_Zeit_Start) {
            zeitAuswaehlenFragment.setKennzeichnung(zeitAuswaehlenFragment.KENNZEICHNUNG_START_ZEIT);
            zeitAuswaehlenFragment.show(getSupportFragmentManager(), "Zeit auswählen");
        } else if (id == R.id.txt_Terminerstellung_Eingabe_Zeit_Ende) {
            zeitAuswaehlenFragment.setKennzeichnung(zeitAuswaehlenFragment.KENNZEICHNUNG_ENDE_ZEIT);
            zeitAuswaehlenFragment.show(getSupportFragmentManager(), "Zeit auswählen");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int jahr, int monat, int tag) {
        kalender.set(jahr, monat, tag);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        if (datumAuswaehlerDialogFragment.getKennzeichnung() == DatumAuswaehlen.KENNZEICHNUNG_START_DATUM) {
            startDatum.setText(format.format(kalender.getTime()));
            startDatumVorhanden = true;
        } else if (datumAuswaehlerDialogFragment.getKennzeichnung() == DatumAuswaehlen.KENNZEICHNUNG_ENDE_DATUM) {
            enddatum.setText(format.format(kalender.getTime()));
            enddatumVorhanden = true;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int stunde, int minute) {
        kalender.set(Calendar.HOUR_OF_DAY, stunde);
        kalender.set(Calendar.MINUTE, minute);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        if (zeitAuswaehlenFragment.getKennzeichnung() == ZeitAuswaehlen.KENNZEICHNUNG_START_ZEIT) {
            startZeit.setText(format.format(kalender.getTime()));
            startZeitVorhanden = true;
        } else if (zeitAuswaehlenFragment.getKennzeichnung() == ZeitAuswaehlen.KENNZEICHNUNG_ENDE_ZEIT) {
            endzeit.setText(format.format(kalender.getTime()));
            endzeitVorhanden = true;
        }


    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termin_erstellung);

        initialisieren();
        setztenDerOnClickListener();
        faerben();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse

}
