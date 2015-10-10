package net.vibrac.quinzaine.db;/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Categorie {

    private int idCategorie;
    private String libelle;


    @Id
    @Column(name = "idCategorie")
    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    @Basic
    @Column(name = "libelle")
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categorie categorie = (Categorie) o;

        if (idCategorie != categorie.idCategorie) return false;
        if (libelle != null ? !libelle.equals(categorie.libelle) : categorie.libelle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCategorie;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }




}
