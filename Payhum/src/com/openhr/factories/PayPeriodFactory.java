package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.PayPeriodData;

import com.openhr.factories.common.OpenHRSessionFactory;

public class PayPeriodFactory {
	private static List<PayPeriodData> payprd;
	private static Query query;
	private static Session session;

	public static List<PayPeriodData> findAll() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("PayPeriodData.findAll");
		payprd = query.list();
		session.getTransaction().commit();

		return payprd;
	}

	public static boolean update(PayPeriodData prds) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			PayPeriodData prd = null;

			if (prds.getId() != null) {
				prd = (PayPeriodData) session.get(PayPeriodData.class,
						prds.getId());
				prd.setPeriodName(prds.getPeriodName());
				prd.setPeriodValue(prds.getPeriodValue());

				session.update(prd);
				done = true;
			} else {
				session.save(prds);
				done = true;
			}

			session.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return done;
	}

}
