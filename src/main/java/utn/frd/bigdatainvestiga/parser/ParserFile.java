/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser;

import utn.frd.bigdatainvestiga.parser.custom.NextelParser;
import utn.frd.bigdatainvestiga.parser.custom.ClaroParser;
import utn.frd.bigdatainvestiga.parser.custom.PersonalParser;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import utn.frd.bigdatainvestiga.util.PropertyUtils;

/**
 *
 * @author Sergio
 */
public class ParserFile {
    
    public static void parse(String fileName, String idInvestigacion, Long idUsuario) throws Exception{
        File archivoExcel = new File( PropertyUtils.get("upload.folder")+fileName );
        
        try {
            Workbook excel = WorkbookFactory.create(new FileInputStream(archivoExcel)); //crear un libro excel

            if("salientes".equalsIgnoreCase(excel.getSheetName(0))){
                //InputProvider.CLARO;
                new ClaroParser(excel, fileName, idInvestigacion, idUsuario);
            }
            
            if(fileName.contains("extel")){
                //InputProvider.NEXTEL;
                new NextelParser(excel, fileName, idInvestigacion, idUsuario);
            }
            
        }catch(OldExcelFormatException oefe){
            new PersonalParser(archivoExcel, fileName, idInvestigacion, idUsuario);
        }

    }
}
