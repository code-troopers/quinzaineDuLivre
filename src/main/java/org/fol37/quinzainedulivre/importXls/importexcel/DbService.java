package org.fol37.quinzainedulivre.importXls.importexcel;

import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.domain.Categorie;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.repository.AgeRepository;
import org.fol37.quinzainedulivre.repository.CategorieRepository;
import org.fol37.quinzainedulivre.repository.EditeurRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

@Service
public class DbService {

    @Inject
    AgeRepository ageRepository;
    @Inject
    CategorieRepository categorieRepository;
    @Inject
    EditeurRepository editeurRepository;


    public Categorie getOrAddCategorieByLibelle(String libelle) {

        if (libelle == null) {
            return null;
        }

        List<Categorie> list = categorieRepository.findByLibelle(libelle.toLowerCase().trim());
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        Categorie cat = new Categorie();
        cat.setLibelle(libelle);

        categorieRepository.save(cat);
        return cat;

    }

    public Age getOrAddAgeByLibelle(String libelle) {

        if (libelle == null) {
            return null;
        }

        List<Age> list = ageRepository.findByLibelle(libelle.toLowerCase().trim());
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        Age age = new Age();
        age.setLibelle(libelle);

        ageRepository.save(age);
        return age;

    }

    public Editeur getOrAddEditeurByLibelle(String libelle) {

        if (libelle == null) {
            return null;
        }

        List<Editeur> list = editeurRepository.findByNom(libelle.toLowerCase().trim());
        if (list != null && list.size() > 0) {
            return list.get(0);
        }

        Editeur editeur = new Editeur();
        editeur.setNom(libelle);

        editeurRepository.save(editeur);
        return editeur;

    }

}
