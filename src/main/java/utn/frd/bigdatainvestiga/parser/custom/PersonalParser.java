/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser.custom;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.extractor.OldExcelExtractor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import utn.frd.bigdatainvestiga.data.manager.SolrManager;
import utn.frd.bigdatainvestiga.parser.ParserUtils;

/**
 *
 * @author Sergio
 */
public class PersonalParser {

    public PersonalParser(Workbook excel) {
        try{
            Sheet hojaResultado = excel.getSheetAt(0);
            
            String desde = hojaResultado.getRow(2).getCell(4).getStringCellValue();
            String hasta = hojaResultado.getRow(3).getCell(4).getStringCellValue();
            
            for(Sheet hojaActual : excel){
                for(Row filaActual : hojaActual){
                    StringBuffer sb = new StringBuffer();
                    for(Cell celda : filaActual){
                        if(celda.getCellTypeEnum() == CellType.STRING)
                            sb.append(celda.getStringCellValue()+";");
                        if(celda.getCellTypeEnum() == CellType.NUMERIC)
                            sb.append(celda.getNumericCellValue()+";");
                    }
                    System.out.println( sb.toString() );
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(PersonalParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public PersonalParser(File archivoExcel, String fileName, String idInvestigacion, Long idUsuario) {
        SolrClient solr = SolrManager.getSolrClient();
        long start = System.currentTimeMillis();
        try{
            OldExcelExtractor old = new OldExcelExtractor(archivoExcel);
            StringBuilder sb = new StringBuilder(old.getText().replaceAll("(?:\\n|\\r)", " ").replaceAll("\\s{2,}", " "));
            
            Pattern pattern = Pattern.compile("(\\d\\.\\d+E\\d)\\s+(\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2})\\s+([E|S])\\s+([\\d|\\*]+)\\s+([\\d|\\.]+)\\s+(([A-Z0-9]+)\\s+(.*?AIRES))*", Pattern.DOTALL+Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sb.toString());
            int i = 1;
            while(matcher.find()){
                SolrInputDocument document = new SolrInputDocument();
                //formateo el numero de linea "a mano"
                String linea = matcher.group(1);
                linea = linea.replace(".", "");
                linea = linea.replace("E9", "");
                while(linea.length()<10){
                    linea = linea+"0";
                }

                if("S".equals(matcher.group(3))){
                    document.addField("Nro_Origen", linea);
                    document.addField("Nro_Destino", matcher.group(4));
                }else{
                    document.addField("Nro_Origen", matcher.group(4));
                    document.addField("Nro_Destino", linea);
                }
                document.addField("Fecha_Origen", ParserUtils.formatNextelDate( matcher.group(2) ));
                document.addField("Duracion", matcher.group(5));
                document.addField("Celda_Num", matcher.group(7));
                //document.addField("Celda", row.getCell(6).getStringCellValue());
                document.addField("Formato", "Personal");
                document.addField("Archivo_Origen", fileName);
                document.addField("ID_Investigacion", idInvestigacion);
        //        document.addField("Id_Usuario", idUsuario);
                document.addField("Fecha_Proceso", ParserUtils.solrDateNow() );
                solr.add(document);
            }
            solr.commit();
        }catch (Exception e){
            Logger.getLogger(PersonalParser.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
}
