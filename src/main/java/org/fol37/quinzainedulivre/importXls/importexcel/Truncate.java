package org.fol37.quinzainedulivre.importXls.importexcel;

import org.fol37.quinzainedulivre.repository.AgeRepository;
import org.fol37.quinzainedulivre.repository.CategorieRepository;
import org.fol37.quinzainedulivre.repository.EditeurRepository;
import org.fol37.quinzainedulivre.repository.LivreRepository;

import javax.inject.Inject;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */
public class Truncate {

    @Inject
    LivreRepository livreRepository;
    @Inject
    AgeRepository ageRepository;
    @Inject
    CategorieRepository categorieRepository;
    @Inject
    EditeurRepository editeurRepository;

    public void truncate() {
        livreRepository.deleteAll();
        categorieRepository.deleteAll();
        editeurRepository.deleteAll();
        ageRepository.deleteAll();
    }
}
