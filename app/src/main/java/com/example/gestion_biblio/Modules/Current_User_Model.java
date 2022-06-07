package com.example.gestion_biblio.Modules;

public class Current_User_Model {

    private String apogee,nom,prenom,email,password,tele,statut;
    private int num_reserver,num_emprunter;

    public Current_User_Model(String apogee, String nom, String prenom, String email, String password, String tele, String statut, int num_reserver, int num_emprunter) {
        this.apogee = apogee;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.tele = tele;
        this.statut = statut;
        this.num_reserver = num_reserver;
        this.num_emprunter = num_emprunter;
    }

    public String getApogee() {
        return apogee;
    }

    public void setApogee(String apogee) {
        this.apogee = apogee;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTele() {
        return tele;
    }

    public void setTele(String tele) {
        this.tele = tele;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNum_reserver() {
        return num_reserver;
    }

    public void setNum_reserver(int num_reserver) {
        this.num_reserver = num_reserver;
    }

    public int getNum_emprunter() {
        return num_emprunter;
    }

    public void setNum_emprunter(int num_emprunter) {
        this.num_emprunter = num_emprunter;
    }
}
