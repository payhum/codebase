/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories.common;

import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.data.DeductionType;

import com.openhr.data.Dtest;
import com.openhr.data.EmpBenefitView;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.Etest;
import com.openhr.data.Exemptionstype;
import com.openhr.data.GLEmployee;
import com.openhr.data.Leave;
import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.Position;
import com.openhr.data.Report;
import com.openhr.data.Roles;
import com.openhr.data.Users;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author Mekbib
 */
public class OpenHRSessionFactory {

    private static SessionFactory sessionFactory = null;

    private OpenHRSessionFactory() {
        AnnotationConfiguration config = new AnnotationConfiguration();
        config.configure();
        config.addAnnotatedClass(Benefit.class);
        config.addAnnotatedClass(BenefitType.class);
        config.addAnnotatedClass(Employee.class);
        config.addAnnotatedClass(EmployeePayroll.class); 
        config.addAnnotatedClass(EmpBenefitView.class); 
        config.addAnnotatedClass(Leave.class);
        config.addAnnotatedClass(LeaveType.class);
        config.addAnnotatedClass(LeaveRequest.class);
        config.addAnnotatedClass(LeaveApproval.class);
        config.addAnnotatedClass(Position.class);
        config.addAnnotatedClass(Report.class);
        config.addAnnotatedClass(Roles.class);
        config.addAnnotatedClass(Users.class);
        config.addAnnotatedClass(EmpDependents.class);
        config.addAnnotatedClass(DeductionsDeclared.class);
        config.addAnnotatedClass(DeductionsDone.class);
        config.addAnnotatedClass(ExemptionsDone.class);
<<<<<<< HEAD
        config.addAnnotatedClass(GLEmployee.class);
        config.addAnnotatedClass(Etest.class);
        config.addAnnotatedClass(Dtest.class);
        config.addAnnotatedClass(DeductionType.class);
        
        config.addAnnotatedClass(Exemptionstype.class);
=======

>>>>>>> branch 'master' of https://github.com/payhum/codebase.git
        sessionFactory = config.buildSessionFactory();
    }

    public static SessionFactory getInstance() {
        if (null == sessionFactory) {
            new OpenHRSessionFactory();
            return sessionFactory;
        } else { 
            return sessionFactory;
        }
    }
}
