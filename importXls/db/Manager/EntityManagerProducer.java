package net.vibrac.quinzaine.db.Manager;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Geoffroy Vibrac on 01/10/2015.
 */

public class EntityManagerProducer {

    @PersistenceContext(unitName = "quinzaine")
    @Produces
    @QuinzaineDatabase
    private EntityManager entityManager;
}
