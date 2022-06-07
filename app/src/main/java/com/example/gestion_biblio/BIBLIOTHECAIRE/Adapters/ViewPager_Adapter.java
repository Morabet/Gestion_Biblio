package com.example.gestion_biblio.BIBLIOTHECAIRE.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Ajouter_livre;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Etud_Emprunter;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.Etud_suspendu;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.liste_livre_fragment;
import com.example.gestion_biblio.BIBLIOTHECAIRE.Fragments.reservation;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class ViewPager_Adapter extends FragmentPagerAdapter {



    public ViewPager_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position ){
            case 0: return new liste_livre_fragment();
            case 1: return new reservation();
            case 2: return  new Etud_Emprunter();
            case 3: return new Etud_suspendu();
            case 4: return new Ajouter_livre();
            default : return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position==0) title = "Liste des livres";
        if(position==1) title = "Livres réservés";
        if(position==2) title = "Confirmer retour de livre";
        if(position==3) title = "Etudiants suspendus";

        return title;
    }
}
