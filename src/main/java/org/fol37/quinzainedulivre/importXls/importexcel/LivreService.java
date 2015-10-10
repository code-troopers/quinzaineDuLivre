package org.fol37.quinzainedulivre.importXls.importexcel;

import org.fol37.quinzainedulivre.domain.Livre;
import org.fol37.quinzainedulivre.repository.LivreRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */


@Service
public class LivreService {

    @Inject
    LivreRepository livreRepository;

    public void addLivre(Livre livre) {
        livreRepository.save(livre);
    }

}
