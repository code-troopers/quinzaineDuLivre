package net.vibrac.quinzaine.importexcel;

import net.vibrac.quinzaine.db.Age;
import net.vibrac.quinzaine.db.Categorie;
import net.vibrac.quinzaine.db.Editeur;
import net.vibrac.quinzaine.db.Livre;
import net.vibrac.quinzaine.db.Manager.QuinzaineDatabase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */
@Transactional
public class Truncate {

    @Inject
    @QuinzaineDatabase
    private EntityManager em;

    public void truncate(){
        List<Livre> list = em.createQuery("select l from Livre l").getResultList();
        for (Livre v: list){
            em.remove(v);
        }

        List<Categorie> list2 = em.createQuery("select l from Categorie l").getResultList();
        for (Categorie v:list2){
            em.remove(v);
        }

        List<Age> list3 = em.createQuery("select l from Age l").getResultList();
        for (Age v:list3){
            em.remove(v);
        }

        List<Editeur> list4 = em.createQuery("select l from Editeur l").getResultList();
        for (Editeur v:list4){
            em.remove(v);
        }
    }


}
