package com.openhr.factories;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.openhr.data.LeaveType;
import com.openhr.factories.common.OpenHRSessionFactory;

public class LeaveTypeFactory {
	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static Session session;
	private static Query query;
	private static List<LeaveType> leaveTypes;

	public LeaveTypeFactory() {
	}

	@SuppressWarnings("unchecked")
	public static List<LeaveType> findAll() {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("LeaveType.findAll");
		leaveTypes = query.list();
		session.getTransaction().commit();

		return leaveTypes;
	}

	public static List<LeaveType> findById(Integer leaveTypeId) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("LeaveType.findById");
		query.setInteger(0, leaveTypeId);
		leaveTypes = query.list();
		session.getTransaction().commit();

		return leaveTypes;
	}

	public static List<LeaveType> findByName(String leaveTypeName) {
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		query = session.getNamedQuery("LeaveType.findByName");
		query.setString(0, leaveTypeName);
		leaveTypes = query.list();
		session.getTransaction().commit();

		return leaveTypes;
	}

	public static boolean delete(LeaveType lt) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			LeaveType leaveType = (LeaveType) session.get(LeaveType.class,
					lt.getId());
			session.delete(leaveType);
			session.flush();
			session.getTransaction().commit();
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	public static boolean insert(LeaveType lt) {
		boolean done = false;

		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		try {
			session.save(lt);
			session.getTransaction().commit();
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}

	public static boolean update(LeaveType lt) {
		boolean done = false;
		session = OpenHRSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();

		try {
			LeaveType leaveType = (LeaveType) session.get(LeaveType.class,
					lt.getId());
			leaveType.setName(lt.getName());
			leaveType.setDayCap(lt.getDayCap());
			session.getTransaction().commit();
			done = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return done;
	}
}
