/*
 * To change this template, choose Tools | Template
 * and open the template in the editor.
 */

package com.openhr.factories;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

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

    public static List<Users> findByUserName(String usersName) {
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("Users.findByUsername");
        query.setString(0, usersName);
        users = query.list();
        session.getTransaction().commit();
        
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
}
