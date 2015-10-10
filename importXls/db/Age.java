package net.vibrac.quinzaine.db;/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Age {


    private int idAge;
    private String libelle;
    private Integer aPartirDe;


    @Id
    @Column(name = "idAge")
    public int getIdAge() {
        return idAge;
    }

    public void setIdAge(int idAge) {
        this.idAge = idAge;
    }

    @Basic
    @Column(name = "libelle")
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Basic
    @Column(name = "aPartirDe")
    public Integer getaPartirDe() {
        return aPartirDe;
    }

    public void setaPartirDe(Integer aPartirDe) {
        this.aPartirDe = aPartirDe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Age age = (Age) o;

        if (idAge != age.idAge) return false;
        if (libelle != null ? !libelle.equals(age.libelle) : age.libelle != null) return false;
        if (aPartirDe != null ? !aPartirDe.equals(age.aPartirDe) : age.aPartirDe != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAge;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (aPartirDe != null ? aPartirDe.hashCode() : 0);
        return result;
    }

}
