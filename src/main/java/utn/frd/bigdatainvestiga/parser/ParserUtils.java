/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Sergio
 */
public class ParserUtils {

    static DateFormat solrDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static DateFormat nextelDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    static String formatNextelDate(String nd) throws ParseException {
        return solrDate.format( nextelDate.parse(nd) );
    }

    static String solrDateNow() {
        return solrDate.format(new Date());
    }
}
