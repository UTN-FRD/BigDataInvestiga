package utn.frd.bigdatainvestiga;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utn.frd.bigdatainvestiga.dao.UserStepDAO;


@WebServlet("/ajax/showsteps")
public class ShowSteps extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idInvestigacion = request.getParameter("idInvestigacion");
        String userId = request.getParameter("idUsuario");
        
        request.setAttribute("movimientos", 
                (new UserStepDAO()).getAll());

    }

}