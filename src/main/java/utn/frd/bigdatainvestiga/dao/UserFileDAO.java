package utn.frd.bigdatainvestiga.dao;

import utn.frd.bigdatainvestiga.data.manager.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

}
