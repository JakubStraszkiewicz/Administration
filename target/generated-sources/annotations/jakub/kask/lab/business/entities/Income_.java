package jakub.kask.lab.business.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Income.class)
public abstract class Income_ extends jakub.kask.lab.business.entities.Audit_ {

	public static volatile SingularAttribute<Income, User> owner;
	public static volatile SingularAttribute<Income, Integer> allowance;
	public static volatile SingularAttribute<Income, Integer> id;
	public static volatile SingularAttribute<Income, Date> settlementDate;
	public static volatile SingularAttribute<Income, Integer> salary;
	public static volatile SingularAttribute<Income, Employee> employee;

	public static final String OWNER = "owner";
	public static final String ALLOWANCE = "allowance";
	public static final String ID = "id";
	public static final String SETTLEMENT_DATE = "settlementDate";
	public static final String SALARY = "salary";
	public static final String EMPLOYEE = "employee";

}

