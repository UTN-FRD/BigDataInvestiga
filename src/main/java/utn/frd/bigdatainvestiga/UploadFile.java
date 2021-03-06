package utn.frd.bigdatainvestiga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import utn.frd.bigdatainvestiga.dao.UserFileDAO;
import utn.frd.bigdatainvestiga.entity.UserFile;
import utn.frd.bigdatainvestiga.parser.ParserFile;
import utn.frd.bigdatainvestiga.util.PropertyUtils;


@WebServlet("/upload")
@MultipartConfig
public class UploadFile extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("files", (new UserFileDAO()).getUserFiles(1l) );  //saveOrUpdate( new UserFile(idUsuario, Long.parseLong(idInvestigacion), fileName) );
        
        request.getRequestDispatcher("upload.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if( request.getSession().getAttribute("userId")==null ){
            response.sendRedirect("login");
        }else{
            StringBuffer sb = new StringBuffer();
            sb.append("<ul class=\"list-group\">");
            String idInvestigacion = request.getParameter("idInvestigacion");
            Long idUsuario = 1l;

            List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">

            //UploadedFile f;

            for (Part filePart : fileParts) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
                InputStream fileContent = filePart.getInputStream();

                File file = new File( PropertyUtils.get("upload.folder") +fileName);
                OutputStream outputStream = new FileOutputStream(file);

                int read = 0;
                byte[] bytes = new byte[1024];
                while ((read = fileContent.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                outputStream.close();

                try{
                    ParserFile.parse(fileName, idInvestigacion, idUsuario );

                    (new UserFileDAO()).saveOrUpdate( new UserFile(idUsuario, Long.parseLong(idInvestigacion), fileName) );
                    sb.append("<li class=\"list-group-item list-group-item-success\"><span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span> Archivo "+fileName+" procesado con exito</li>");
                }catch(Exception e){
                    sb.append("<li class=\"list-group-item list-group-item-danger\"><span class=\"glyphicon glyphicon-ok\" aria-hidden=\"true\"></span> Error con archivo: "+fileName+"</li>");
                }

            }
            sb.append("</ul>");
            
            request.getSession().setAttribute("uploadResult", sb.toString());
            response.sendRedirect("filter#" + idInvestigacion);
        }
    }
}
