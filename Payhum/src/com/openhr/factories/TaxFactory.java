package com.openhr.factories;

import java.io.Serializable;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.TaxDetailsData;
import com.openhr.data.TaxRatesData;
import com.openhr.data.TypesData;
import com.openhr.factories.common.OpenHRSessionFactory;

public class TaxFactory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static Session session;
	private static Query query;
	private static List<TaxRatesData> trd;
	private static List<TaxDetailsData> tdd;

	public static TaxRatesData findFromIncome() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		Query query = session
				.createQuery("from TaxRatesData  order by id DESC");
		query.setMaxResults(1);
		TaxRatesData taxin = (TaxRatesData) query.uniqueResult();
		session.getTransaction().commit();
		return taxin;
	}

	public static List<TaxRatesData> findByIncomeTo(double double1) {
		Session session2 = OpenHRSessionFactory.getInstance().openSession();
		session2.beginTransaction();
		query = session2.getNamedQuery("TaxRatesData.findByIncomeFrom");
		query.setDouble(0, double1);
		List<TaxRatesData> trdto = query.list();
		session2.getTransaction().commit();
		session2.close();
		return trdto;
	}

	public static boolean update(TaxRatesData trds) {
		boolean done = false;
		List<TaxRatesData> trdis = TaxFactory.findByIncomeTo(trds
				.getIncomeFrom());

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			TaxRatesData trd = null;

			if (trds.getId() != null) {
				trd = (TaxRatesData) session.get(TaxRatesData.class,
						trds.getId());
				;
			} else if (trdis.size() > 0) {
				trd = (TaxRatesData) session.get(TaxRatesData.class,
						trdis.get(0).getId());
				;
			}
			trd.setIncomeTo(trds.getIncomeTo());
			//trd.setIncomePercentage(trds.getIncomePercentage());
			//trd.setIncomeFrom(trds.getIncomeFrom());
			trd.setResidentTypeId(trds.getResidentTypeId());
			trd.setIncomePercentage(trds.getIncomePercentage());

			session.update(trd);
			session.getTransaction().commit();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return done;
	}

	public static boolean updatePercent(TaxRatesData trds) {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			TaxRatesData trd = null;
 			if (trds.getId() != null) {
				trd = (TaxRatesData) session.get(TaxRatesData.class,trds.getId());
			}
 			trd.setIncomePercentage(trds.getIncomePercentage());

			session.update(trd);
			session.getTransaction().commit();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}
		return done;
	}

	public static List<TaxRatesData> findAll() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("TaxRatesData.findAll");

		trd = query.list();
		session.getTransaction().commit();
		return trd;
	}

	public static boolean delete(TaxRatesData txrdata) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			TaxRatesData txr = (TaxRatesData) session.get(TaxRatesData.class,
					txrdata.getId());
			session.delete(txr);
			session.flush();
			session.getTransaction().commit();
			done = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return done;
	}

	public static boolean upDateDelete(Double to, Double from) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			List<TaxRatesData> trdis = TaxFactory.findByIncomeTo(to);

			if (trdis.size() != 0) {
				TaxRatesData txr = (TaxRatesData) session.get(
						TaxRatesData.class, trdis.get(0).getId());
				txr.setIncomeFrom(from);

				session.update(txr);
				session.flush();
				session.getTransaction().commit();
				done = true;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return done;
	}
	
	public static List<TaxDetailsData> findAllTaxDetails() {
		Session lsession = OpenHRSessionFactory.getInstance().openSession();
		lsession.beginTransaction();
		query = lsession.getNamedQuery("TaxDetailsData.findAll");
		tdd = query.list();
		lsession.getTransaction().commit();
		lsession.close();
		return tdd;
	}
	
	public static TaxDetailsData findAllTaxDetailsByType(TypesData typesData) {
		if(tdd != null && !tdd.isEmpty()) {
			for(TaxDetailsData td: tdd) {
				if(td.getTypeId().getId().compareTo(typesData.getId()) == 0){
					return td;
				}
			}
		}
		
		Session lsession = OpenHRSessionFactory.getInstance().openSession();
		lsession.beginTransaction();
		query = lsession.getNamedQuery("TaxDetailsData.findByTypeId");
		query.setParameter(0,typesData);
		tdd = query.list();
		
		lsession.getTransaction().commit();
		lsession.close();
		
		if(tdd != null && !tdd.isEmpty()){
			return tdd.get(0);
		}
		
		return null;
	}
	
	public static List<TaxRatesData> findAllTaxByType(int typeId) {
		Session lsession = OpenHRSessionFactory.getInstance().openSession();
		lsession.beginTransaction();
		query = lsession.getNamedQuery("TaxRatesData.findByResidentType");
		query.setInteger(0, typeId);
		trd = query.list();
		lsession.getTransaction().commit();
		lsession.close();
 		return trd;
 	}
}
