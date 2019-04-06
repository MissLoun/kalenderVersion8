package de.projects.janap.a05_kalender;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Kalender_Steuerung extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    /*-------------------------Darstellung--------------------------------------------------------*/
    private TextView txtMonatAnzeige, txtHeutigerTag, txtMomentanesDatum;
    private ImageView neuerTermin;
    private GridView tabelleAktuellerMonat;
    private Button btnZuvor, btnWeiter;
    private LinearLayout wochentage;
    private ConstraintLayout trennbalken;


    /*-------------------------Andere Variablen---------------------------------------------------*/
    private View altesView;
    private String[] bezeichnungen = new String[11];
    private String[] farben = {"#4D7DB3", "#4DA1B3", "#4DB3A0", "#4DB37C", "#B3944D", "#A68F59", "#A67459", "#A65959", "#B34D71", "#AC4DB3", "#644DB3", "#4D59B3"};
    private ArrayList<Tag> tage = new ArrayList<>();
    private String aktuelleFarbe;
    private TerminErstellung terminErstellung;

    /*-------------------------Kalender-----------------------------------------------------------*/
    private Calendar kalender; //erstellt einen Kalender mit aktuellen Datum Angaben
    public static Calendar heute = Calendar.getInstance(); //Kalender mit heutigem Datum


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden
    /*-------------------------Set Methoden-------------------------------------------------------*/
    public void setTxtMonatAnzeige(String pNeuerMonat) {
        txtMonatAnzeige.setText(pNeuerMonat);
    }
    public void setTxtMomentanesDatum(int pTag) {
        kalender.set(Calendar.DAY_OF_MONTH, pTag);
        txtMomentanesDatum.setText(new SimpleDateFormat("dd.MM.yyyy").format(kalender.getTime()));
    }

    /*-------------------------Get Methoden-------------------------------------------------------*/
    public String getAktuelleFarbe() {
        aktuelleFarbe = farben[kalender.get(Calendar.MONTH)];
        return aktuelleFarbe;
    }
    public Calendar getKalender(){
        return kalender;
    }

    /*-------------------------Andere Methoden----------------------------------------------------*/
    public void oeffneActifityNeuerTermin(){
        Intent intent = new Intent(this, TerminErstellung.class);
        startActivity(intent);
    }

    public void aktualisiereKalender() {
        setztenDesMonatsArray();
        Kalender_Adapter adapterAktuellerMonat = new Kalender_Adapter(this, tage, this); //KalenderAdapter um den Kalender in der Tabelle darzustellen

        setTxtMonatAnzeige(bezeichnungen[kalender.get(Calendar.MONTH)]);  //setzt die neue Monatsbezeichnung fest

        txtMomentanesDatum.setText("" + (kalender.get(Calendar.YEAR)));
        tabelleAktuellerMonat.setAdapter(adapterAktuellerMonat);    //Kalender wird dargestellt

        aendereFarbe(kalender.get(Calendar.MONTH));
    }
    public void aendereFarbe(int pMonat) {
        int farbe = Color.parseColor(farben[pMonat]);

        txtMonatAnzeige.setBackgroundColor(farbe);
        txtMomentanesDatum.setBackgroundColor(farbe);
        btnZuvor.setBackgroundColor(farbe);
        btnWeiter.setBackgroundColor(farbe);
        wochentage.setBackgroundColor(farbe);
        trennbalken.setBackgroundColor(farbe);

    }
    public void initialisieren(){
        tabelleAktuellerMonat = findViewById(R.id.gridView_Kalender_Tabelle_AktuellerMonat);

        txtMonatAnzeige = findViewById(R.id.txt_Kalender_Monat);
        txtHeutigerTag = findViewById(R.id.txt_Kalender_HeutigerTag);
        txtMomentanesDatum = findViewById(R.id.txt_Kalender_Momentanes_Datum);
        btnZuvor = findViewById(R.id.btn_Kalender_Zuvor);
        btnWeiter = findViewById(R.id.btn_Kalender_Weiter);
        wochentage = findViewById(R.id.layout_Kalender_Wochentage);
        trennbalken = findViewById(R.id.trennbalken);
        neuerTermin = findViewById(R.id.image_neuerTermin);
        bezeichnungen = getResources().getStringArray(R.array.monate);
        kalender = Calendar.getInstance();
        kalender.set(heute.get(Calendar.YEAR), heute.get(Calendar.MONTH), 1);

        txtHeutigerTag.setText("" + (kalender.get(Calendar.DAY_OF_MONTH)));
    }
    public void setztenDerOnClickListener(){
        btnZuvor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH, -1);    //der Monat des Kalenders wird um eins reduziert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        btnWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.add(Calendar.MONTH, 1); //der Monat des Kalenders wird um eins addiert
                aktualisiereKalender(); //der Monat wird mit den momentanen Daten des Kalenders dargestellt
            }
        });

        txtHeutigerTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kalender.set(Calendar.YEAR, heute.get(Calendar.YEAR));
                kalender.set(Calendar.MONTH, heute.get(Calendar.MONTH));
                kalender.set(Calendar.DAY_OF_MONTH, heute.get(Calendar.DAY_OF_MONTH));
                aktualisiereKalender();
                txtMomentanesDatum.setText(new SimpleDateFormat("dd.MM.yyyy").format(kalender.getTime()));
            }
        });

        neuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oeffneActifityNeuerTermin();
            }
        });

        tabelleAktuellerMonat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (tage.get(position) != null) {
                    if (altesView == null){
                        altesView = view;
                    }
                    Tag tag = tage.get(position);
                    setTxtMomentanesDatum(tag.getWelcherTag());
                    altesView.setBackgroundColor(Color.parseColor("#ffffff"));
                    view.setBackgroundColor(Color.parseColor("#E7E7E7"));
                    altesView = view;
                }
            }
        });
    }

    public void setztenDesMonatsArray(){
        int ersterTag = kalender.get(Calendar.DAY_OF_WEEK)-2;   //da 1. Position 0 ist und - dem Tag
        tage = new ArrayList<>();
        int datum = 1;
        for (int i = 0; i < 42; i++) {

            /*--------------------Fehlerbehebung--------------------------------------------------*/
            if (i == 0){
                if (kalender.get(Calendar.DAY_OF_WEEK) == 0){
                    datum = 1;  //Da Fehler mit dem ersten des Monats auftrat
                }
                if( ersterTag == -1){
                    ersterTag = 6;  //Da -1 Sonntag ist tritt sonst ein Fehler auf
                }
            }
            /*--------------------Array füllen----------------------------------------------------*/
            if ( (i >= ersterTag) && !(i >= kalender.getActualMaximum(Calendar.DAY_OF_MONTH)+ ersterTag) ){    //wenn die aktuelle Position groeßer ist als der erste Tag des Monats & wenn die position groeßer ist als das Ende des Monats
                Tag tag = new Tag(datum);
                datum++;
                tage.add(tag);

            }else{
                tage.add(null);
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    } //App wird beendet wenn die Rücktaste benutzt wurde

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Erstellung
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender__gui);

        initialisieren();   //Allen Variablen wird ihr Wert zugeordnet

        aktualisiereKalender();     //der Monat wird mit den momentanen Daten des Kalenders dargestellt

        //txtMomentanesDatum.setText(getString(R.string.datum, heute.get(Calendar.DAY_OF_MONTH), heute.get(Calendar.MONTH) + 1, heute.get(Calendar.YEAR) ));
        txtMomentanesDatum.setText(new SimpleDateFormat("dd.MM.yyyy").format(heute.getTime()));


        setztenDerOnClickListener();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}

