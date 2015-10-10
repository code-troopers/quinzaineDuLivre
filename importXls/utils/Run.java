package net.vibrac.quinzaine.utils;

import net.vibrac.quinzaine.importexcel.ImportExcel;
import net.vibrac.quinzaine.importexcel.Truncate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;

/**
 * Created by Geoffroy Vibrac on 10/10/2015.
 */

@Path("/runImport")
public class Run {

    @Inject
    ImportExcel importExcel;

    @Inject
    Truncate truncate;

    @GET
    @Produces("text/plain")
    public String runImport() {
        truncate.truncate();
        importExcel.doImport(false);
        importExcel.doImport(true);
        return "running";
    }

}
