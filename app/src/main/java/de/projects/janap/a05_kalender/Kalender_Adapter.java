package de.projects.janap.a05_kalender;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Kalender_Adapter extends ArrayAdapter<Tag> {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Attribute
    private LayoutInflater inflater;
    private Kalender_Steuerung strg;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Konstruktor
    public Kalender_Adapter(Context pContext, ArrayList<Tag> pTage, Kalender_Steuerung pStrg){
        super(pContext, R.layout.zelle_aktueller_monat, pTage);
        inflater = LayoutInflater.from(pContext);
        strg = pStrg;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Methoden

    @Override
    public View getView(int position, View view, ViewGroup parent){
        try{
        Tag tag = getItem(position);

        //wenn covertView = 0 ist, wird eine neue Zelle festgelegt
        if (view == null) {
            view = inflater.inflate(R.layout.zelle_aktueller_monat, parent, false);
        }

            TextView textView = view.findViewById(R.id.textview_tag);

            if ((tag.getWelcherTag() == Kalender_Steuerung.heute.get(Calendar.DAY_OF_MONTH)) && (strg.getKalender().get(Calendar.MONTH) == Kalender_Steuerung.heute.get(Calendar.MONTH)) && (strg.getKalender().get(Calendar.YEAR)== Kalender_Steuerung.heute.get(Calendar.YEAR))){
                String aktuelleFarbedesMonats = strg.getAktuelleFarbe();
                textView.setTextColor(Color.parseColor(aktuelleFarbedesMonats));  //beim heutigen Tag soll es eingefärbt werden
            }

        textView.setText(String.valueOf(tag.getWelcherTag()));



        return view;

    } catch (Exception e) {
        e.printStackTrace();
    }


            return view;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Ende der Klasse
}
