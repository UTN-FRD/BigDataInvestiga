/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser.custom;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import utn.frd.bigdatainvestiga.data.manager.SolrManager;

/**
 *
 * @author Sergio
 */
public class ClaroParser {

    public ClaroParser(Workbook excel, String fileName, String idInvestigacion, Long idUsuario) throws SolrServerException, IOException {
        SolrClient solr = SolrManager.getSolrClient();
        DateFormat solrDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

            //salientes
            for(Row row : excel.getSheetAt(0)){
                if(row.getRowNum()>12){
                    SolrInputDocument document = new SolrInputDocument();
                    document.addField("Nro_Origen", NumberToTextConverter.toText(row.getCell(0).getNumericCellValue()));
                    //document.addField("Destino", row.getCell(1).getStringCellValue());
                    document.addField("Nro_Destino", NumberToTextConverter.toText(row.getCell(2).getNumericCellValue()));
                    document.addField("Fecha_Origen", solrDate.format( HSSFDateUtil.getJavaDate( row.getCell(3).getNumericCellValue() )));
                    document.addField("Duracion", NumberToTextConverter.toText(row.getCell(4).getNumericCellValue()));
                    document.addField("Celda_Num", row.getCell(5).getStringCellValue());
                    //document.addField("Celda", row.getCell(6).getStringCellValue());
                    document.addField("Formato", "Claro");
                    document.addField("Archivo_Origen", fileName);
                    document.addField("ID_Investigacion", idInvestigacion);
            //        document.addField("Id_Usuario", idUsuario);
                    document.addField("Fecha_Proceso", solrDate.format(new Date()) );
                    solr.add(document);
                }
            }

            //entrantes
            for(Row row : excel.getSheetAt(1)){
                if(row.getRowNum()>14){
                    SolrInputDocument document = new SolrInputDocument();
                    document.addField("Nro_Destino", NumberToTextConverter.toText(row.getCell(0).getNumericCellValue()));
                    document.addField("Nro_Origen", NumberToTextConverter.toText(row.getCell(1).getNumericCellValue()));
                    document.addField("Fecha_Origen", solrDate.format( HSSFDateUtil.getJavaDate( row.getCell(2).getNumericCellValue() )));
                    document.addField("Duracion", NumberToTextConverter.toText(row.getCell(3).getNumericCellValue()));
                    document.addField("Celda_Num", row.getCell(4).getStringCellValue());
            //        document.addField("Celda", row.getCell(5).getStringCellValue());
                    document.addField("Formato", "Claro");
                    document.addField("Archivo_Origen", fileName);
                    document.addField("ID_Investigacion", idInvestigacion);
            //        document.addField("Id_Usuario", idUsuario);
                    document.addField("Fecha_Proceso", solrDate.format(new Date()) );
                    solr.add(document);
                }
            }

            solr.commit();
    }
    
}
