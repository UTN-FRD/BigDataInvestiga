package utn.frd.bigdatainvestiga;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.solr.client.solrj.SolrClient;
import utn.frd.bigdatainvestiga.dao.UserFileDAO;
import utn.frd.bigdatainvestiga.data.manager.SolrManager;


@WebServlet("/remove")
public class Remove extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String idInvestigacion = request.getParameter("idInvestigacion");
            (new UserFileDAO()).removeInvestigacion(idInvestigacion);

            SolrClient solr = SolrManager.getSolrClient();
            solr.deleteByQuery("ID_Investigacion:"+idInvestigacion);
            solr.commit();
        }catch(Exception e){
            System.out.println("ERROR al borrar: "+e.getLocalizedMessage());
        }
        
        response.sendRedirect("upload");
    }
}