package org.eclipse.core;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	protected void setUp() throws Exception {
		EntityManagerFactory emf = null;
		try {
			boolean flag = true;
			Map<Object, Object> properties = new HashMap<Object, Object>();
			properties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.h2.Driver");
			properties.put(PersistenceUnitProperties.JDBC_URL, "jdbc:h2:./data/database");
			properties.put(PersistenceUnitProperties.JDBC_USER, "om2m");
			properties.put(PersistenceUnitProperties.JDBC_PASSWORD, "om2m");

			if (flag) {
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.CREATE_OR_EXTEND);
			} else {
				properties.put(PersistenceUnitProperties.DDL_GENERATION, PersistenceUnitProperties.DROP_AND_CREATE);
			}

			emf = Persistence.createEntityManagerFactory("om2mdb", properties);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		if (emf != null) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();

			//SclBase sclTB = em.find(SclBase.class, "ken");
			 SclBase sclTB= new SclBase();
			if (sclTB != null) {
				System.out.println("correct");
				sclTB.setUri("ryu");
				sclTB.setCreationTime("20152525");
				sclTB.setLastModifiedTime("333333");
			}else{
				System.out.println("errorrrrrrr");
			}
			// SclBase sclTB= new SclBase();
			// sclTB.setUri("ken");
			// sclTB.setCreationTime("20150406");
//			em.flush();
			em.persist(sclTB);
//			em.refresh(sclTB);
			
			em.getTransaction().commit();
			em.close();
		} else {
			System.out.println("error");
		}
	}
}
