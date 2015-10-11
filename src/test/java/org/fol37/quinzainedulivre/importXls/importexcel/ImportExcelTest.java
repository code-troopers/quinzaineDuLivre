package org.fol37.quinzainedulivre.importXls.importexcel;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Geoffroy Vibrac on 11/10/2015.
 */
public class ImportExcelTest {

    @Test
    public void testOuiToTrue() throws Exception {
        ImportExcel ie = new ImportExcel();
        assertTrue(ie.ouiToTrue("oui"));
        assertTrue(ie.ouiToTrue("OUI"));
        assertTrue(ie.ouiToTrue("x"));
        assertTrue(ie.ouiToTrue("X"));
        assertFalse(ie.ouiToTrue(""));
        assertFalse(ie.ouiToTrue("NON"));
        assertFalse(ie.ouiToTrue(" "));
    }

    @Test
    public void testFirstLetterCapitalize() throws Exception {
        ImportExcel ie = new ImportExcel();
        assertTrue("Toto".equals(ie.firstLetterCapitalize("TOTO")));
        assertTrue("Toto".equals(ie.firstLetterCapitalize("toto")));
    }
}
