package org.fol37.quinzainedulivre.web.rest;


import org.fol37.quinzainedulivre.importXls.importexcel.ImportExcel;
import org.fol37.quinzainedulivre.web.rest.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Decoder;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Controller
@RequestMapping("/api")
public class FileUploadController {

    @Inject
    ImportExcel importExcel;

    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestBody FileDTO file){
        String name = "name";
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(file.getContent());
            InputStream input = new ByteArrayInputStream(bytes);
            importExcel.doImport(input);
            return "You successfully uploaded " + name + "!";
        } catch (Exception e) {
            return "You failed to upload " + name + " => " + e.getMessage();
        }
    }

}