package com.example.gestion_biblio.Modules;

public class Livre_Model {

    private int id_livre;
    private int id_reservation;
    private String imageCover;
    private String title;
    private String Discipline;
    private String Description;
    private String auteur;
    private String disponible;
    private int num_exemplaire;

    public Livre_Model(int id_reservation, int id_livre, String imageCover, String title, String discipline, String description, String auteur, String disponible, int num_exemplaire) {
        this.id_livre = id_livre;
        this.id_reservation = id_reservation;
        this.imageCover = imageCover;
        this.title = title;
        Discipline = discipline;
        Description = description;
        this.auteur = auteur;
        this.disponible = disponible;
        this.num_exemplaire = num_exemplaire;
    }

    public Livre_Model(int id_livre, String imageCover, String title, String discipline, String description, String auteur, String disponible, int num_exemplaire) {
        this.id_livre = id_livre;
        this.imageCover = imageCover;
        this.title = title;
        Discipline = discipline;
        Description = description;
        this.auteur = auteur;
        this.disponible = disponible;
        this.num_exemplaire = num_exemplaire;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscipline() {
        return Discipline;
    }

    public void setDiscipline(String discipline) {
        Discipline = discipline;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public int getNum_exemplaire() {
        return num_exemplaire;
    }

    public void setNum_exemplaire(int num_exemplaire) {
        this.num_exemplaire = num_exemplaire;
    }
}
