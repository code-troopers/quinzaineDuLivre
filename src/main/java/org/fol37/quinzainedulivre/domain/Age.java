package org.fol37.quinzainedulivre.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Age.
 */
@Entity
@Table(name = "age")
public class Age implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull        
    @Column(name = "libelle", nullable = false)
    private String libelle;
    
    @Column(name = "a_partir_de")
    private Integer aPartirDe;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Integer getaPartirDe() {
        return aPartirDe;
    }

    public void setaPartirDe(Integer aPartirDe) {
        this.aPartirDe = aPartirDe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Age age = (Age) o;

        if ( ! Objects.equals(id, age.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Age{" +
                "id=" + id +
                ", libelle='" + libelle + "'" +
                ", aPartirDe='" + aPartirDe + "'" +
                '}';
    }
}
