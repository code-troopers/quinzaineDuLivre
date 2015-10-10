package net.vibrac.quinzaine.importexcel;

import net.vibrac.quinzaine.db.Livre;
import net.vibrac.quinzaine.db.Manager.QuinzaineDatabase;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */


@Transactional
public class LivreService {

    @Inject
    @QuinzaineDatabase
    private EntityManager em;

    public void addLivre(Livre livre){
        em.persist(livre);
    }

}
