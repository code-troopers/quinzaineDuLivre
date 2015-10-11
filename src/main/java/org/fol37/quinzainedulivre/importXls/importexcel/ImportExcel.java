package org.fol37.quinzainedulivre.importXls.importexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fol37.quinzainedulivre.domain.Age;
import org.fol37.quinzainedulivre.domain.Categorie;
import org.fol37.quinzainedulivre.domain.Editeur;
import org.fol37.quinzainedulivre.domain.Livre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Component
public class ImportExcel {

    private final Logger log = LoggerFactory.getLogger(ImportExcel.class);

    @Inject
    DbService dbService;
    @Inject
    LivreService livreService;
    @Inject
    Truncate truncate;

    private final static int COL_ID=0;
    private final static int COL_CODE_BARRE=1;
    private final static int COL_CODE_CATEGORIE=2;
    private final static int COL_COUP_AGE=3;
    private final static int COL_COUP_COEUR=4;
    private final static int COL_COUP_TITRE=5;
    private final static int COL_COUP_SOUS_TITRE=6;
    private final static int COL_COUP_AUTEUR=7;
    private final static int COL_COUP_ILLUSTRATEUR=8;
    private final static int COL_COUP_EDITEUR=9;
    private final static int COL_COUP_RESUME=10;
    private final static int COL_COUP_COMMENTAIRE=11;
    private final static int COL_COUP_HDV=12;
    private final static int COL_COUP_PRIX=13;
    private final static int COL_COUP_URL=14;

    public void doImport(InputStream xlsToImport) {
        log.debug("START  IMPORT XLS ");
        try {
            truncate.truncate();

            Workbook excel = readExcelFile(xlsToImport, "selection.xls");

            Sheet sheet1 = excel.getSheet("selection");

            List<Livre> livreList = new ArrayList<>();

            for (Row row : sheet1) {
                if (row.getRowNum() > 0) {

                    Livre livre = new Livre();

                    //id
                    Double idLivre = row.getCell(COL_ID).getNumericCellValue();
                    livre.setId(idLivre.longValue());

                    //codeBarre
                    String codeBarre;
                    try {
                        codeBarre = row.getCell(COL_CODE_BARRE).getStringCellValue();
                    } catch (IllegalStateException e) {
                        try {
                            codeBarre = new BigDecimal(row.getCell(COL_CODE_BARRE).getNumericCellValue()).toPlainString();
                        } catch (Exception ex) {
                            codeBarre = "";
                        }
                    }
                    livre.setCodeBarre(codeBarre.trim());

                    //categorie
                    String sCategorie = row.getCell(COL_CODE_CATEGORIE).getStringCellValue();
                    Categorie categorie = dbService.getOrAddCategorieByLibelle(sCategorie);
                    livre.setCategorie(categorie);


                    //age
                    String sAge = row.getCell(COL_COUP_AGE).getStringCellValue();
                    Age age = dbService.getOrAddAgeByLibelle(sAge);
                    livre.setAge(age);

                    //Coup de coeur
                    boolean coupDeCoeur;
                    try{
                        coupDeCoeur = ouiToTrue(row.getCell(COL_COUP_COEUR).getStringCellValue());
                    }catch (Exception e){
                        coupDeCoeur = false;
                    }
                    livre.setCoupCoeur(coupDeCoeur);

                    //titre
                    livre.setTitre(row.getCell(COL_COUP_TITRE).getStringCellValue().trim());

                    //Soustitre
                    String sousTitre;
                    try {
                        sousTitre = row.getCell(COL_COUP_SOUS_TITRE).getStringCellValue().trim();
                    }catch (NullPointerException e){
                        sousTitre="";
                    }
                    livre.setSousTitre(sousTitre);

                    //Auteurs
                    String auteurs;
                    try {
                        auteurs = row.getCell(COL_COUP_AUTEUR).getStringCellValue().trim();
                    }catch (NullPointerException e){
                        auteurs = "";
                    }
                    livre.setAuteurs(auteurs);

                    //Illustrateur
                    String illustrateur;
                    try {
                        illustrateur = row.getCell(COL_COUP_ILLUSTRATEUR).getStringCellValue();
                    } catch (NullPointerException e) {
                        illustrateur = "";
                    }
                    livre.setIllustrateur(illustrateur);

                    //Editeur
                    String sEditeur;
                    sEditeur = row.getCell(COL_COUP_EDITEUR).getStringCellValue().trim();
                    sEditeur = firstLetterCapitalize(sEditeur);
                    Editeur editeur = dbService.getOrAddEditeurByLibelle(sEditeur);
                    livre.setEditeur(editeur);

                    //resume
                    String resume;
                    try{
                        resume = row.getCell(COL_COUP_RESUME).getStringCellValue();
                    }catch (NullPointerException e){
                        resume="";
                    }
                    livre.setResume(resume);

                    //Commentaires
                    String commentaires;
                    try{
                        commentaires = row.getCell(COL_COUP_COMMENTAIRE).getStringCellValue();
                    }catch (NullPointerException e){
                        commentaires="";
                    }
                    livre.setCommentaires(commentaires);

                    // reserve HDV
                    String reserveHdv;
                    try {
                        reserveHdv = row.getCell(COL_COUP_HDV).getStringCellValue();
                    } catch (Exception e) {
                        reserveHdv = "";
                    }
                    livre.setReserveHDV(ouiToTrue(reserveHdv));

                    // prix
                    double prix;
                    try {
                        prix = row.getCell(COL_COUP_PRIX).getNumericCellValue();
                    } catch (Exception e) {
                        prix = 0;
                    }
                    livre.setPrix(prix);

                    String url;
                    try{
                        url = row.getCell(COL_COUP_URL).getStringCellValue();
                    }catch (NullPointerException e){
                        url = "";
                    }
                    livre.setUrlImage(url);
                    livreList.add(livre);

                }

            }

            for (Livre livre : livreList) {
                livreService.addLivre(livre);
            }


            System.out.println(livreList.size());

        } catch (Exception e) {

            log.error("Import du fichier excel impossible", e);
        }

    }


    protected Workbook readExcelFile(InputStream in, String excelFileName) throws IOException {
        Workbook workbook;
        try {
            if (excelFileName.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(in);
            } else if (excelFileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(in);
            } else {
                workbook = null;
            }
        } catch (Exception e) {
            workbook = null;
        }
        return workbook;

    }

    boolean ouiToTrue(String value) {
        if (value != null && ("oui".equals(value.toLowerCase()) || "x".equals(value.toLowerCase()))) {
            return true;
        }
        return false;
    }

    String firstLetterCapitalize(String value){
        return value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase();
    }

}
