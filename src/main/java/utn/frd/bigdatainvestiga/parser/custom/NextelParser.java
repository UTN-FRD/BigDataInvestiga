/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser.custom;

import java.io.IOException;
import java.text.ParseException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import utn.frd.bigdatainvestiga.data.manager.SolrManager;
import utn.frd.bigdatainvestiga.parser.ParserUtils;

/**
 *
 * @author Sergio
 */
public class NextelParser {

    public NextelParser(Workbook excel, String fileName, String idInvestigacion, Long idUsuario) throws SolrServerException, IOException, ParseException {
        SolrClient solr = SolrManager.getSolrClient();
        String aux;
        
            if(fileName.contains("radio")){
                for(Row row : excel.getSheetAt(0)){
                    if(row.getRowNum()>7){
                        SolrInputDocument document = new SolrInputDocument();
                        aux = row.getCell(0).getStringCellValue();
                        document.addField("Nro_Origen", aux.substring(0, aux.length()-2));
                        //document.addField("Destino", row.getCell(1).getStringCellValue());
                        aux = row.getCell(3).getStringCellValue();
                        document.addField("Nro_Destino", aux.substring(0, aux.length()-2));
                        aux = row.getCell(7).getStringCellValue();
                        document.addField("Fecha_Origen", ParserUtils.formatNextelDate(aux.substring(0, aux.length()-2)));
                        aux = row.getCell(8).getStringCellValue();
                        document.addField("Duracion", aux.substring(0, aux.length()-2));
                        aux = row.getCell(14).getStringCellValue();
                        document.addField("Celda_Num", aux.substring(0, aux.length()-2));
                        //document.addField("Celda", row.getCell(6).getStringCellValue());
                        document.addField("Formato", "Nextel");
                        document.addField("Archivo_Origen", fileName);
                        document.addField("ID_Investigacion", idInvestigacion);
                //        document.addField("Id_Usuario", idUsuario);
                        document.addField("Fecha_Proceso", ParserUtils.solrDateNow() );
                        solr.add(document);
                    }
                }
                
            }else{
                for(Row row : excel.getSheetAt(0)){
                    if(row.getRowNum()>7){
                        SolrInputDocument document = new SolrInputDocument();
                        aux = row.getCell(0).getStringCellValue();
                        document.addField("Nro_Origen", aux.substring(0, aux.length()-2));
                        //document.addField("Destino", row.getCell(1).getStringCellValue());
                        if(row.getCell(1)==null || row.getCell(1).getCellTypeEnum() == CellType.BLANK){
                            document.addField("Nro_Destino", "SIN DATOS");
                        }else{
                            aux = row.getCell(1).getStringCellValue();
                            document.addField("Nro_Destino", aux.substring(0, aux.length()-2));
                        }
                        aux = row.getCell(3).getStringCellValue();
                        document.addField("Fecha_Origen", ParserUtils.formatNextelDate(aux.substring(0, aux.length()-2)));
                        aux = row.getCell(4).getStringCellValue();
                        document.addField("Duracion", aux.substring(0, aux.length()-2));
                        aux = row.getCell(5).getStringCellValue();
                        document.addField("Celda_Num", aux.substring(0, aux.length()-2));
                        //document.addField("Celda", row.getCell(6).getStringCellValue());
                        document.addField("Formato", "Nextel");
                        document.addField("Archivo_Origen", fileName);
                        document.addField("ID_Investigacion", idInvestigacion);
                //        document.addField("Id_Usuario", idUsuario);
                        document.addField("Fecha_Proceso", ParserUtils.solrDateNow() );
                        solr.add(document);
                    }
                }
                
            }

            solr.commit();
    }
    
}
