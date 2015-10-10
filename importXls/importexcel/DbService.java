package net.vibrac.quinzaine.importexcel;

import net.vibrac.quinzaine.db.Age;
import net.vibrac.quinzaine.db.Categorie;
import net.vibrac.quinzaine.db.Editeur;
import net.vibrac.quinzaine.db.Manager.EntityManagerProducer;
import net.vibrac.quinzaine.db.Manager.PersistenceManager;
import net.vibrac.quinzaine.db.Livre;
import net.vibrac.quinzaine.db.Manager.QuinzaineDatabase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

@Transactional
public class DbService {

    @Inject
    @QuinzaineDatabase
    private EntityManager em;

    public void refresh(){
        em.getEntityManagerFactory().getCache().evictAll();
    }


    public Categorie getOrAddCategorieByLibelle(String libelle){

        if (libelle==null){
            return null;
        }

        Query q = em.createQuery("select c from Categorie c WHERE c.libelle=:libelle");
        q.setParameter("libelle", libelle.toLowerCase().trim());

        List<Categorie> list = q.getResultList();
        if (list!=null && list.size()>0){
            return list.get(0);
        }

        Categorie cat = new Categorie();
        cat.setLibelle(libelle);

        em.persist(cat);
        return cat;

    }

    public Age getOrAddAgeByLibelle(String libelle){

        if (libelle==null){
            return null;
        }

        Query q = em.createQuery("select c from Age c WHERE c.libelle=:libelle");
        q.setParameter("libelle", libelle.toLowerCase().trim());

        List<Age> list = q.getResultList();
        if (list!=null && list.size()>0){
            return list.get(0);
        }

        Age age = new Age();
        age.setLibelle(libelle);

        em.persist(age);
        return age;

    }

    public Editeur getOrAddEditeurByLibelle(String libelle){

        if (libelle==null){
            return null;
        }

        Query q = em.createQuery("select c from Editeur c WHERE c.nom=:libelle");
        q.setParameter("libelle", libelle.toLowerCase().trim());

        List<Editeur> list = q.getResultList();
        if (list!=null && list.size()>0){
            return list.get(0);
        }

        Editeur editeur = new Editeur();
        editeur.setNom(libelle);

        em.persist(editeur);
        return editeur;

    }

}
