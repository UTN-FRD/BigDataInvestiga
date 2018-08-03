package utn.frd.bigdatainvestiga.dao;

import utn.frd.bigdatainvestiga.entity.UserStep;

public class UserStepDAO extends CommonDAO<UserStep> {

    public UserStepDAO() {}
/*
    public UserStep saveOrUpdate(UserStep o) {
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
*/
}
