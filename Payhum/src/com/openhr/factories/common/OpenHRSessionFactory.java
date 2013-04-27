/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.openhr.factories.common;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.openhr.company.Company;
import com.openhr.company.CompanyPayroll;
import com.openhr.company.Licenses;
import com.openhr.data.Benefit;
import com.openhr.data.BenefitType;
import com.openhr.data.BrachData;
import com.openhr.data.DeductionsType;
import com.openhr.data.DepartBrachData;
import com.openhr.data.Dtest;
import com.openhr.data.EmpBankAccount;
import com.openhr.data.EmpBenefitView;
import com.openhr.data.EmpDependents;
import com.openhr.data.Employee;
import com.openhr.data.EmployeeBonus;
import com.openhr.data.EmployeePayroll;
import com.openhr.data.EmployeeSalary;
import com.openhr.data.Etest;
import com.openhr.data.Exemptionstype;
import com.openhr.data.GLEmployee;
import com.openhr.data.Holidays;
import com.openhr.data.LeaveApproval;
import com.openhr.data.LeaveRequest;
import com.openhr.data.LeaveType;
import com.openhr.data.OverTime;
import com.openhr.data.OverTimePayRateData;
import com.openhr.data.PayPeriodData;
import com.openhr.data.Position;
import com.openhr.data.Report;
import com.openhr.data.Roles;
import com.openhr.data.TaxDetailsData;
import com.openhr.data.TaxRatesData;
import com.openhr.data.TypesData;
import com.openhr.data.Users;
import com.openhr.taxengine.DeductionsDeclared;
import com.openhr.taxengine.DeductionsDone;
import com.openhr.taxengine.ExemptionsDone;

/**
 *
 * @author Vijay
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
        config.addAnnotatedClass(GLEmployee.class);
        config.addAnnotatedClass(Etest.class);
        config.addAnnotatedClass(Dtest.class);
        config.addAnnotatedClass(DeductionsType.class);        
        config.addAnnotatedClass(Exemptionstype.class);
        config.addAnnotatedClass(Licenses.class);
        config.addAnnotatedClass(CompanyPayroll.class);
        config.addAnnotatedClass(EmpBankAccount.class);
        config.addAnnotatedClass(Company.class);
        config.addAnnotatedClass(OverTime.class);
        config.addAnnotatedClass(Holidays.class);
        config.addAnnotatedClass(OverTimePayRateData.class);
        config.addAnnotatedClass(TaxRatesData.class);
        config.addAnnotatedClass(PayPeriodData.class);
        config.addAnnotatedClass(TaxDetailsData.class);
        config.addAnnotatedClass(TypesData.class);
        config.addAnnotatedClass(BrachData.class);
        config.addAnnotatedClass(DepartBrachData.class);
        config.addAnnotatedClass(EmployeeBonus.class);
        config.addAnnotatedClass(EmployeeSalary.class);
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
