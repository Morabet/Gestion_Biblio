package com.example.gestion_biblio.Modules;

public class LivreReserver_Model {

    private int id_reservation;
    private String title;

    public LivreReserver_Model(int id_reservation, String title) {
        this.id_reservation = id_reservation;
        this.title = title;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
