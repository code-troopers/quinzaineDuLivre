package net.vibrac.quinzaine.db;/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Editeur {


    private int idEditeur;
    private String nom;

    @Id
    @Column(name = "idEditeur")
    public int getIdEditeur() {
        return idEditeur;
    }

    public void setIdEditeur(int idEditeur) {
        this.idEditeur = idEditeur;
    }

    @Basic
    @Column(name = "nom")
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Editeur editeur = (Editeur) o;

        if (idEditeur != editeur.idEditeur) return false;
        if (nom != null ? !nom.equals(editeur.nom) : editeur.nom != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idEditeur;
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        return result;
    }



}
