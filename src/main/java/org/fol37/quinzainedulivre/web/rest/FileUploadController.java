package org.fol37.quinzainedulivre.web.rest;


import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;
import org.fol37.quinzainedulivre.importXls.importexcel.ImportExcel;
import org.fol37.quinzainedulivre.web.rest.dto.FileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.inject.Inject;
import java.io.*;


@Controller
@RequestMapping("/api")
public class FileUploadController {



    private final Logger log = LoggerFactory.getLogger(FileUploadController.class);
    @Inject
    ImportExcel importExcel;


    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> handleFileUpload(@RequestBody FileDTO file){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(file.getContent());
            InputStream input = new ByteArrayInputStream(bytes);
            importExcel.doImport(input);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value="/upload/mainLogo", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> uploadMainImage(@RequestBody FileDTO file){
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(file.getContent());
            InputStream input = new ByteArrayInputStream(bytes);

            final File tmpFile = File.createTempFile("/tmp/mainLogo", ".tmp");

            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(tmpFile);
                IOUtils.copy(input, fileOutputStream);

            } catch (IOException e) {
                log.error("Unable to read uploaded file to a temporary one");
            } finally {
                IOUtils.closeQuietly(fileOutputStream);
            }

            String currentDir = System.getProperty("user.dir");
            File finalFile = new File(currentDir + "/src/main/webapp/assets/images/couverture.png");
            Files.move(tmpFile, finalFile);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
