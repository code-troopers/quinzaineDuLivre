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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    @PostConstruct
    public void doImport() {
        log.debug("START  IMPORT XLS ");
        boolean addLivre = true;
        try {
            FileInputStream in = new FileInputStream(new File("/home/fchauveau/dev/workspace/hackathon-quinzaineDuLivre/src/main/resources/selection.xls"));
            Workbook excel = readExcelFile(in, "selection.xls");

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
                    livre.setCoupCoeur(ouiToTrue(row.getCell(4).getStringCellValue()));

                    //titre
                    livre.setTitre(row.getCell(6).getStringCellValue());

                    //Soustitre
                    livre.setSousTitre(row.getCell(7).getStringCellValue());

                    //Auteurs
                    livre.setAuteurs(row.getCell(8).getStringCellValue());

                    //Illustrateur
                    String illustrateur;
                    try {
                        illustrateur = row.getCell(9).getStringCellValue();
                    } catch (NullPointerException e) {
                        illustrateur = "";
                    }
                    livre.setIllustrateur(illustrateur);

                    //Editeur
                    String sEditeur = row.getCell(10).getStringCellValue();
                    Editeur editeur = dbService.getOrAddEditeurByLibelle(sEditeur.trim());
                    livre.setEditeur(editeur);

                    //resume
                    livre.setResume(row.getCell(11).getStringCellValue());

                    //Commentaires
                    livre.setCommentaires(row.getCell(12).getStringCellValue());

                    // reserv� HDV
                    String reserveHdv;
                    try {
                        reserveHdv = row.getCell(13).getStringCellValue();
                    } catch (Exception e) {
                        reserveHdv = "";
                    }
                    livre.setReserveHDV(ouiToTrue(reserveHdv));
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

            if (addLivre) {
                for (Livre livre : livreList) {
                    livreService.addLivre(livre);
                }
            }

            System.out.println(livreList.size());

        } catch (Exception e) {

            e.printStackTrace();
        }


        System.out.println("TEST");

    }


    protected Workbook readExcelFile(FileInputStream in, String excelFileName) throws IOException {
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

}
