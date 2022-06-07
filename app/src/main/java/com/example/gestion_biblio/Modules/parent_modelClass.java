package com.example.gestion_biblio.Modules;

import java.util.ArrayList;
import java.util.List;

public class parent_modelClass {
    // this a model class for the parent recyclerView that displays the title and my_biblio_btn_remove and a nested recyclerView

    public String title;
    public ArrayList<Livre_Model> childList;

    public parent_modelClass(String title, ArrayList<Livre_Model> childList) {
        this.title = title;
        this.childList = childList;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Livre_Model> getChildList() {
        return childList;
    }
}
