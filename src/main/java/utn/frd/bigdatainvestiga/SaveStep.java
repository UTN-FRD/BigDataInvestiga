package utn.frd.bigdatainvestiga;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utn.frd.bigdatainvestiga.dao.UserFileDAO;
import utn.frd.bigdatainvestiga.entity.UserStep;


@WebServlet("/ajax/savestep")
public class SaveStep extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idInvestigacion = request.getParameter("idInvestigacion");
        String url2Save = request.getParameter("url");
        String userId = request.getParameter("idUsuario");
        
        (new UserFileDAO()).saveOrUpdate( 
                new UserStep(Long.parseLong(userId), Long.parseLong(idInvestigacion), url2Save)
        );

    }

}