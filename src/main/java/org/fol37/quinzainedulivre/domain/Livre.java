package org.fol37.quinzainedulivre.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Livre.
 */
@Entity
@Table(name = "livre")
public class Livre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "titre")
    private String titre;
    
    @Column(name = "sous_titre")
    private String sousTitre;
    
    @Column(name = "auteurs")
    private String auteurs;
    
    @Column(name = "illustrateur")
    private String illustrateur;
    
    @Column(name = "code_barre")
    private String codeBarre;
    
    @Column(name = "resume")
    private String resume;
    
    @Column(name = "commentaires")
    private String commentaires;
    
    @Column(name = "reserve_hdv")
    private Boolean reserveHDV;
    
    @Column(name = "coup_coeur")
    private Boolean coupCoeur;
    
    @Column(name = "prix")
    private Double prix;
    
    @Column(name = "url_image")
    private String urlImage;

    @ManyToOne
    private Age age;

    @ManyToOne
    private Categorie categorie;

    @ManyToOne
    private Editeur editeur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }

    public String getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    public String getIllustrateur() {
        return illustrateur;
    }

    public void setIllustrateur(String illustrateur) {
        this.illustrateur = illustrateur;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(String codeBarre) {
        this.codeBarre = codeBarre;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Boolean getReserveHDV() {
        return reserveHDV;
    }

    public void setReserveHDV(Boolean reserveHDV) {
        this.reserveHDV = reserveHDV;
    }

    public Boolean getCoupCoeur() {
        return coupCoeur;
    }

    public void setCoupCoeur(Boolean coupCoeur) {
        this.coupCoeur = coupCoeur;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Editeur getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeur editeur) {
        this.editeur = editeur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Livre livre = (Livre) o;

        if ( ! Objects.equals(id, livre.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", titre='" + titre + "'" +
                ", sousTitre='" + sousTitre + "'" +
                ", auteurs='" + auteurs + "'" +
                ", illustrateur='" + illustrateur + "'" +
                ", codeBarre='" + codeBarre + "'" +
                ", resume='" + resume + "'" +
                ", commentaires='" + commentaires + "'" +
                ", reserveHDV='" + reserveHDV + "'" +
                ", coupCoeur='" + coupCoeur + "'" +
                ", prix='" + prix + "'" +
                ", urlImage='" + urlImage + "'" +
                '}';
    }
}
