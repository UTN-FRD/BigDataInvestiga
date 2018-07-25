package utn.frd.bigdatainvestiga.dao;

import utn.frd.bigdatainvestiga.data.manager.HibernateUtil;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CommonDAO<T> {
	
	protected static final Log log = LogFactory.getLog(CommonDAO.class);
	
	private Session currentSession;
	
	private Transaction currentTransaction;
	
	protected long recordsTotal;
	protected long recordsFiltered;
	
	private T t;
	private Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public CommonDAO(){
		entityClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public CommonDAO(Class<T> class1) {
		entityClass = class1;
	}

	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		//return currentSession;
		return HibernateUtil.getSessionFactory().getCurrentSession();
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	public <T> T save(final T o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.save(o);
			if (tx.isActive())
				tx.commit();
		}catch(Exception e){
			log.error("Error guardando "+o+" "+e.getMessage());
			if (tx.isActive())
				tx.rollback();
		}finally {
			if(session.isOpen())
				session.close();
		}
		
		return o;
	}

	public void delete(final Object object) {
		getCurrentSession().delete(object);
	}

	public <T> boolean delete(Long id) {
		Session session = getCurrentSession();
		Transaction tx = null;
		boolean result = true;
		try{
			tx = session.beginTransaction();
			T object = (T) session.get(entityClass, id);
			session.delete(object);
			if (tx.isActive())
				tx.commit();
		}catch(Exception e){
			log.error("Error al borrar "+entityClass.getCanonicalName()+" para ID="+id+" "+e.getMessage());
			result = false;
		}finally {
			if(session.isOpen())
				session.close();
		}
		return result;
	}

	public <T> T get(final Long id) {
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		T o = null;
		try{
			o = (T) session.get(entityClass, id);
		}catch(Exception e){
			log.error("Error al obtener el objeto para ID="+id+" "+e.getMessage());
		}finally {
			if (tx!=null && !tx.isActive())
				tx.commit();
			if(session.isOpen())
				session.close();
		}

		return o;
	}

	public <T> T getByField(String field, String value) {
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		T o = null;
		try{
			Criteria criteria = session.createCriteria(entityClass);
			criteria.add( Restrictions.eq( field, value ) );

			o = (T) criteria.uniqueResult();
		}catch(Exception e){
			log.error("Error al obtener "+entityClass.getCanonicalName()+" para "+field+"="+value+" "+e.getMessage());
		}finally {
			if (tx!=null && !tx.isActive())
				tx.commit();
			if(session.isOpen())
				session.close();
		}

		return o;
	}

	public <T> T getByField(String field, Long value) {
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();
		T o = null;
		try{
			Criteria criteria = session.createCriteria(entityClass);
			criteria.add( Restrictions.eq( field, value ) );

			o = (T) criteria.uniqueResult();
		}catch(Exception e){
			log.error("Error al obtener "+entityClass.getCanonicalName()+" para "+field+"="+value+" "+e.getMessage());
		}finally {
			if (tx!=null && !tx.isActive())
				tx.commit();
			if(session.isOpen())
				session.close();
		}

		return o;
	}

	public <T> T merge(final T o) {
		return (T) getCurrentSession().merge(o);
	}

	public <T> T saveOrUpdate(final T o) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			session.saveOrUpdate(o);
			
			if (tx.isActive())
				tx.commit();
		}catch(Exception e){
			log.error("Error saveOrUpdate "+e.getLocalizedMessage());
		}finally {
			if(session.isOpen())
				session.close();
		}

		return o;
	}

	public <T> List<T> getAll(int pageSize, int pageOffset, String orderBy, String orderType, HashMap<String, String> searchCriteria) {
		List<T> result = new ArrayList<T>();
		Session session = getCurrentSession();
		Transaction tx = session.beginTransaction();

		try{
			Criteria criteria = session.createCriteria(entityClass);
			recordsTotal = criteria.list().size();
			
			for(Entry<String, String> entry : searchCriteria.entrySet()){
				if(StringUtils.isNotEmpty(entry.getValue()))
					criteria.add(Restrictions.like( entry.getKey(), "%"+entry.getKey().replace(".", "_")+"%") );
			}

			recordsFiltered = criteria.list().size();
			
			if(orderBy!=null)
				if(orderType==null || orderType.equalsIgnoreCase("asc"))
					criteria.addOrder(Order.asc(orderBy));
				else
					criteria.addOrder(Order.desc(orderBy));
			
			result = criteria.list().subList(pageOffset, (int)Math.min(pageOffset+pageSize, recordsFiltered) );
			
		}catch(Exception e){
			log.debug(e.getLocalizedMessage());
		}finally{
			if(tx!=null && tx.isActive())
				tx.rollback();
			
			if(session.isOpen())
				session.close();
		}
		return result;
	}

	public <T> List<T> getAll(){
		List<T> result = new ArrayList<T>();
		Session session = getCurrentSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(entityClass);
			result = criteria.list();
		}catch(Exception e){
			log.error(e.getLocalizedMessage());
		}finally {
			if(session!=null && session.isOpen())
				session.close();
		}
		return result;
		
	}

	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
}
