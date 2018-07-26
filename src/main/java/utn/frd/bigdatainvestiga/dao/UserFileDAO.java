package utn.frd.bigdatainvestiga.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import static utn.frd.bigdatainvestiga.dao.CommonDAO.log;
import utn.frd.bigdatainvestiga.data.manager.HibernateUtil;
import utn.frd.bigdatainvestiga.entity.UserFile;

public class UserFileDAO extends CommonDAO<UserFile> {

    public UserFileDAO() {}

    public UserFile saveOrUpdate(UserFile o) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();

        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(o);

            if (tx.isActive()) {
                tx.commit();
            }
        } catch (Exception e) {
            log.error("Error saveOrUpdate " + e.getLocalizedMessage());
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        return o;
    }

    public List<UserFile> getUserFiles(long userId){
        List<UserFile> o = new ArrayList<UserFile>();
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try{
            Criteria criteria = session.createCriteria (UserFile.class);
            criteria.add( Restrictions.eq( "idUsuario", userId ) );

            o = (List<UserFile>) criteria.list();
        }catch(Exception e){
            log.error("Error al obtener UserFile para el usuario "+userId+" "+e.getMessage());
        }finally {
            if (tx!=null && !tx.isActive())
                tx.commit();
            if(session.isOpen())
                session.close();
        }

        return o;
    }
    
    public void removeInvestigacion(String idInvestigacion){
        Session session = getCurrentSession();
        Transaction tx = session.beginTransaction();
        try{
            String hql = "delete from UserFile where idInvestigacion= :idInvestigacion"; 
            session.createQuery(hql).setLong("idInvestigacion", new Integer(idInvestigacion)).executeUpdate();            

            if (tx.isActive())
                tx.commit();
        }catch(Exception e){
            log.error("Error al borrar UserFile para el idInvestigacion "+idInvestigacion+" "+e.getMessage());
        }finally {
            if(session.isOpen())
                session.close();
        }

    }
}
