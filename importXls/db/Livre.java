package net.vibrac.quinzaine.db;/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

import javax.persistence.*;


@Entity
public class Livre {

    private int idLivre;
    private String titre;
    private String sousTitre;
    private String auteurs;
    private String illustrateur;
    private long codeBarre;
    private String resume;
    private String commentaires;
    private Boolean reserveHdv;
    private Boolean coupCoeur;
    private Double prix;
    private String urlImage;
    private Categorie categorie;
    private Age age;
    private Editeur editeur;

    @Id
    @Column(name = "idLivre")
    public int getIdLivre() {
        return idLivre;
    }

    public void setIdLivre(int idLivre) {
        this.idLivre = idLivre;
    }

    @Basic
    @Column(name = "titre")
    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Basic
    @Column(name = "sousTitre")
    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }

    @Basic
    @Column(name = "auteurs")
    public String getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    @Basic
    @Column(name = "illustrateur")
    public String getIllustrateur() {
        return illustrateur;
    }

    public void setIllustrateur(String illustrateur) {
        this.illustrateur = illustrateur;
    }

    @Basic
    @Column(name = "codeBarre")
    public long getCodeBarre() {
        return codeBarre;
    }

    public void setCodeBarre(long codeBarre) {
        this.codeBarre = codeBarre;
    }

    @Basic
    @Column(name = "resume")
    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Basic
    @Column(name = "commentaires")
    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    @Basic
    @Column(name = "reserveHDV")
    public Boolean getReserveHdv() {
        return reserveHdv;
    }

    public void setReserveHdv(Boolean reserveHdv) {
        this.reserveHdv = reserveHdv;
    }

    @Basic
    @Column(name = "coupCoeur")
    public Boolean getCoupCoeur() {
        return coupCoeur;
    }

    public void setCoupCoeur(Boolean coupCoeur) {
        this.coupCoeur = coupCoeur;
    }

    @Basic
    @Column(name = "prix")
    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Basic
    @Column(name = "urlImage")
    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livre livre = (Livre) o;

        if (idLivre != livre.idLivre) return false;
        if (codeBarre != livre.codeBarre) return false;
        if (titre != null ? !titre.equals(livre.titre) : livre.titre != null) return false;
        if (sousTitre != null ? !sousTitre.equals(livre.sousTitre) : livre.sousTitre != null) return false;
        if (auteurs != null ? !auteurs.equals(livre.auteurs) : livre.auteurs != null) return false;
        if (illustrateur != null ? !illustrateur.equals(livre.illustrateur) : livre.illustrateur != null) return false;
        if (resume != null ? !resume.equals(livre.resume) : livre.resume != null) return false;
        if (commentaires != null ? !commentaires.equals(livre.commentaires) : livre.commentaires != null) return false;
        if (reserveHdv != null ? !reserveHdv.equals(livre.reserveHdv) : livre.reserveHdv != null) return false;
        if (coupCoeur != null ? !coupCoeur.equals(livre.coupCoeur) : livre.coupCoeur != null) return false;
        if (prix != null ? !prix.equals(livre.prix) : livre.prix != null) return false;
        if (urlImage != null ? !urlImage.equals(livre.urlImage) : livre.urlImage != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLivre;
        result = 31 * result + (titre != null ? titre.hashCode() : 0);
        result = 31 * result + (sousTitre != null ? sousTitre.hashCode() : 0);
        result = 31 * result + (auteurs != null ? auteurs.hashCode() : 0);
        result = 31 * result + (illustrateur != null ? illustrateur.hashCode() : 0);
        result = 31 * result + (resume != null ? resume.hashCode() : 0);
        result = 31 * result + (commentaires != null ? commentaires.hashCode() : 0);
        result = 31 * result + (reserveHdv != null ? reserveHdv.hashCode() : 0);
        result = 31 * result + (coupCoeur != null ? coupCoeur.hashCode() : 0);
        result = 31 * result + (prix != null ? prix.hashCode() : 0);
        result = 31 * result + (urlImage != null ? urlImage.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "idCategorie", referencedColumnName = "idCategorie", nullable = false)
    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @ManyToOne
    @JoinColumn(name = "idAge", referencedColumnName = "idAge", nullable = false)
    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    @ManyToOne
    @JoinColumn(name = "idEditeur", referencedColumnName = "idEditeur", nullable = false)
    public Editeur getEditeur() {
        return editeur;
    }

    public void setEditeur(Editeur editeur) {
        this.editeur = editeur;
    }
}
