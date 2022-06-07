package com.example.gestion_biblio.Modules;

public class Livres_modelClass {

    // this is a model class for the images
    private int image,id_livre;
    private String title;
    private String Discipline;
    private String Description;
    private String auteur;
    private String disponible;
    private int num_exemplaire;

    public Livres_modelClass(int image, int id_livre, String title, String discipline, String description, String auteur,String disponible, int num_exemplaire) {
        this.image = image;
        this.id_livre = id_livre;
        this.title = title;
        this.Discipline = discipline;
        this.Description = description;
        this.auteur = auteur;
        this.disponible = disponible;
        this.num_exemplaire = num_exemplaire;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getId_livre() {
        return id_livre;
    }

    public void setId_livre(int id_livre) {
        this.id_livre = id_livre;
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

    public int getNum_exemplaire() {
        return num_exemplaire;
    }

    public void setNum_exemplaire(int num_exemplaire) {
        this.num_exemplaire = num_exemplaire;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }
}