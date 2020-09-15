package jakub.kask.lab.business.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, String> surname;
	public static volatile SingularAttribute<Employee, String> name;
	public static volatile ListAttribute<Employee, Agreement> agreements;
	public static volatile SingularAttribute<Employee, Integer> id;
	public static volatile SingularAttribute<Employee, String> pesel;

	public static final String SURNAME = "surname";
	public static final String NAME = "name";
	public static final String AGREEMENTS = "agreements";
	public static final String ID = "id";
	public static final String PESEL = "pesel";

}

