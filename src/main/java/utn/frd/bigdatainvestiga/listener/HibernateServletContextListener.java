package utn.frd.bigdatainvestiga.listener;

import java.net.URL;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import utn.frd.bigdatainvestiga.data.manager.HibernateUtil;

public class HibernateServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		SessionFactory sf = (SessionFactory) sce.getServletContext().getAttribute("SessionFactory");
		sf.close();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
            try{
		URL url = HibernateServletContextListener.class.getResource("/hibernate.cfg.xml");
		Configuration config = new Configuration();
		config.configure(url);
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(config.getProperties()).build();
		SessionFactory sf = config.buildSessionFactory(serviceRegistry);
		HibernateUtil.setSessionFactory(sf);
		sce.getServletContext().setAttribute("SessionFactory", sf);
            }catch(Exception e){
                System.out.println(e);
            }
	}

}
