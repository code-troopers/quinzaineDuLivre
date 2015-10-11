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
import javax.validation.constraints.Null;
import java.io.File;
import java.io.FileInputStream;
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

    public void doImport(InputStream xlsToImport) {
        log.debug("START  IMPORT XLS ");
        try {
            Workbook excel = readExcelFile(xlsToImport, "selection.xls");

            Sheet sheet1 = excel.getSheet("selection");

            List<Livre> livreList = new ArrayList<>();

            for (Row row : sheet1) {
                if (row.getRowNum() > 0) {

                    Livre livre = new Livre();

                    //id
                    Double idLivre = row.getCell(0).getNumericCellValue();
                    livre.setId(idLivre.longValue());

                    //codeBarre
                    String codeBarre;
                    try {
                        codeBarre = row.getCell(1).getStringCellValue();
                    } catch (IllegalStateException e) {
                        try {
                            codeBarre = new BigDecimal(row.getCell(1).getNumericCellValue()).toPlainString();
                        } catch (Exception ex) {
                            codeBarre = "";
                        }
                    }
                    livre.setCodeBarre(codeBarre.trim());

                    //categorie
                    String sCategorie = row.getCell(2).getStringCellValue();
                    Categorie categorie = dbService.getOrAddCategorieByLibelle(sCategorie);
                    livre.setCategorie(categorie);


                    //age
                    String sAge = row.getCell(3).getStringCellValue();
                    Age age = dbService.getOrAddAgeByLibelle(sAge);
                    livre.setAge(age);

                    //Coup de coeur
                    boolean coupDeCoeur;
                    try{
                        coupDeCoeur = ouiToTrue(row.getCell(4).getStringCellValue());
                    }catch (Exception e){
                        coupDeCoeur = false;
                    }
                    livre.setCoupCoeur(coupDeCoeur);

                    //titre
                    livre.setTitre(row.getCell(6).getStringCellValue().trim());

                    //Soustitre
                    String sousTitre;
                    try {
                        sousTitre = row.getCell(7).getStringCellValue().trim();
                    }catch (NullPointerException e){
                        sousTitre="";
                    }
                    livre.setSousTitre(sousTitre);

                    //Auteurs
                    String auteurs;
                    try {
                        auteurs = row.getCell(8).getStringCellValue().trim();
                    }catch (NullPointerException e){
                        auteurs = "";
                    }
                    livre.setAuteurs(auteurs);

                    //Illustrateur
                    String illustrateur;
                    try {
                        illustrateur = row.getCell(9).getStringCellValue();
                    } catch (NullPointerException e) {
                        illustrateur = "";
                    }
                    livre.setIllustrateur(illustrateur);

                    //Editeur
                    String sEditeur;
                    sEditeur = row.getCell(10).getStringCellValue().trim();
                    sEditeur = firstLetterCapitalize(sEditeur);
                    Editeur editeur = dbService.getOrAddEditeurByLibelle(sEditeur);
                    livre.setEditeur(editeur);

                    //resume
                    String resume;
                    try{
                        resume = row.getCell(11).getStringCellValue();
                    }catch (NullPointerException e){
                        resume="";
                    }
                    livre.setResume(resume);

                    //Commentaires
                    String commentaires;
                    try{
                        commentaires = row.getCell(12).getStringCellValue();
                    }catch (NullPointerException e){
                        commentaires="";
                    }
                    livre.setCommentaires(commentaires);

                    // reserve HDV
                    String reserveHdv;
                    try {
                        reserveHdv = row.getCell(13).getStringCellValue();
                    } catch (Exception e) {
                        reserveHdv = "";
                    }
                    livre.setReserveHDV(ouiToTrue(reserveHdv));

                    // prix
                    double prix;
                    try {
                        prix = row.getCell(14).getNumericCellValue();
                    } catch (Exception e) {
                        prix = 0;
                    }
                    livre.setPrix(prix);

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
