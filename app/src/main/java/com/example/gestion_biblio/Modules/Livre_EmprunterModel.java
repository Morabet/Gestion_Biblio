package com.example.gestion_biblio.Modules;

public class Livre_EmprunterModel {

    private int id_emprunt;
    private String title;

    public Livre_EmprunterModel(int id_emprunt, String title) {
        this.id_emprunt = id_emprunt;
        this.title = title;
    }

    public int getId_emprunt() {
        return id_emprunt;
    }

    public void setId_emprunt(int id_emprunt) {
        this.id_emprunt = id_emprunt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}


