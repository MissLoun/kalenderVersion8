package de.projects.janap.a05_kalender;

public class Termin {
    private String id;
    private String titel, startDatum, endDatum, startZeit, endZeit;
    private Boolean ganztaegig;

    public Termin (String pTitel, String pStartDatum, String pStartZeit, String pEndDatum, String pEndZeit, Boolean pGanztaegig){
        titel = pTitel;
        startDatum = pStartDatum;
        endDatum = pEndDatum;
        startZeit = pStartZeit;
        endZeit = pEndZeit;
        ganztaegig = pGanztaegig;
    }

    public String getTermin(){
        String termin;
        if (ganztaegig){
            termin = titel+ " findet am " +startDatum+ " den ganzen Tag lang statt.";
        }else {
            termin = titel+ " findet zwischen dem " +startDatum+ " um " +startZeit+ " und dem " +endDatum+ " um " +endZeit+ " statt.";
        }
        return termin;
    }

}
