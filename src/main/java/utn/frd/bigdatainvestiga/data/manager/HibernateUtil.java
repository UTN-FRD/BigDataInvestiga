package utn.frd.bigdatainvestiga.data.manager;

import org.hibernate.SessionFactory;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	public HibernateUtil() {}

	public static SessionFactory getSessionFactory() {
	   return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sf) {
	   sessionFactory = sf;
	}

}
