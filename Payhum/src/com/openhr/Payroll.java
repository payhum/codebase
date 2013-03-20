package com.openhr;

import com.openhr.data.EmployeePayroll;
import com.openhr.factories.common.OpenHRSessionFactory;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author xmen
 */
public class Payroll {
    private static Session session;
    private static Query query;
    private static List<EmployeePayroll> employeePayroll;
    
    public static List<EmployeePayroll> generatePayroll() { 
        
        session = OpenHRSessionFactory.getInstance().getCurrentSession();
        session.beginTransaction();
        query = session.getNamedQuery("EmployeePayroll.findAll");
        employeePayroll = query.list();
        
        return employeePayroll;
        
    }
}
