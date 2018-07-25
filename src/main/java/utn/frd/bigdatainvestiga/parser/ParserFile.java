/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utn.frd.bigdatainvestiga.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.OldExcelFormatException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Sergio
 */
public class ParserFile {
    
    public static void parse(String SERVER_UPLOAD_LOCATION_FOLDER, String fileName, String idInvestigacion, Long idUsuario) throws IOException{
        File archivoExcel = new File( SERVER_UPLOAD_LOCATION_FOLDER+fileName );
        
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
            
/*            
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
*/            
        }catch(OldExcelFormatException oefe){
            new PersonalParser(archivoExcel, fileName, idInvestigacion, idUsuario);

            /*
            FileInputStream excelFile = new FileInputStream(archivoExcel);
            Workbook workbook = new HSSFWorkbook(excelFile);
            new PersonalParser(workbook);
*/

//            readOldExcel(archivoExcel);
//            OldExcelExtractor old = new OldExcelExtractor(archivoExcel);
//            System.out.println(old.getText());
        } catch (Exception ex) {
            Logger.getLogger(ParserFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private static void readOldExcel(File archivoExcel){
        try {
            FileInputStream excelFile = new FileInputStream(archivoExcel);
            Workbook workbook = new HSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "--");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "--");
                    }
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
