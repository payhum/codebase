/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */

package com.openhr.factories;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.openhr.data.Dtest;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.factories.common.OpenHRSessionFactory;

/**
 *
 * @author Mekbib
 */
public class UsersFactory {
	private static Session session;
    private static Query query;
    private static List<Users> users;
   // private static SQLQuery qry;
    private static List<Object[]> lib;

    public UsersFactory() {
    }

    public static List<Users> findAll() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Users.findAll");
        users = query.list();
        session.getTransaction().commit();
        
        return users;
    }

    public static List<Users> findById(Integer usersId) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Users.findById");
        query.setInteger(0, usersId);
        users = query.list();
        session.getTransaction().commit();
        
        return users;
    }

    public static List<Users> findByRoleId(Users rid) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Users.findByRoleId");
        query.setParameter(0, rid.getRoleId());
        users = query.list();
        session.getTransaction().commit();
        
        return users;
    }
    public static List<Users> findByUserName(String usersName) {
        Session lsession = OpenHRSessionFactory.getInstance().openSession();
        lsession.beginTransaction();
        query = lsession.getNamedQuery("Users.findByUsername");
        query.setString(0, usersName);
        users = query.list();
        lsession.getTransaction().commit();
        lsession.close();
        return users;
    }

    public static List<Users> findByPassword(String password) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Users.findByPassword");
        query.setString(0, password);
        users = query.list();
        session.getTransaction().commit();
        
        return users;
    }
    public static List findEmpDepart() {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        SQLQuery qry= session.createSQLQuery("select r.name, count(u.roleId) as total, u.roleId from roles r,users u where u.roleId=r.id group by  roleId");
        
     lib = qry.list();
        session.getTransaction().commit();
        
        return lib;
    }

    public static boolean delete(Users usrs) {
        boolean done = false;
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
            Users usr = (Users) session.get(Users.class, usrs.getId());
            session.delete(usr);
            session.flush();
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
    }

    public static boolean insert(Users usrs) {
        boolean done = false;

        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        try {
        	session.save(usrs);            
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
        
    }

    public static boolean update(Users usrs) {
        boolean done = false;
        List<Users> matchingUsrs = UsersFactory.findByUserName(usrs.getUsername());
        
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();

        try {
        	Users usr = null;
        	
        	if(usrs.getId() != null) {
        		usr = (Users) session.get(Users.class, usrs.getId()); ;
        	}
        	else if (matchingUsrs.size() > 0){
        		usr = (Users) session.get(Users.class, matchingUsrs.get(0).getId()); ;
        	}
        	
            usr.setUsername(usrs.getUsername());
            usr.setPassword(usrs.getPassword());
            
            if(usrs.getEmployeeId() != null)
            	usr.setEmployeeId(usrs.getEmployeeId());
            
            if(usrs.getRoleId() != null)
                usr.setRoleId(usrs.getRoleId());
            
            session.update(usr);
            session.getTransaction().commit();
            done = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	
        }
        return done;
    }

	public static boolean isCredsValid(String userName, String password) {
		List<Users> matchingUsrs = UsersFactory.findByUserName(userName);
        
		if(matchingUsrs != null && matchingUsrs.size() > 0) {
			if(password.equalsIgnoreCase(matchingUsrs.get(0).getPassword())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	
	 public static List<Dtest>  findBy() {
	       // session = OpenHRSessionFactory.getInstance().getCurrentSession();
	        Session session2 = OpenHRSessionFactory.getInstance().openSession();
	        session2.beginTransaction();
	        query = session2.getNamedQuery("Dtest.findAll");
	        
	        List<Dtest>   users1 = query.list();
	        session2.getTransaction().commit();
	        session2.close();
	        return users1;
	    }
	
	
}
