package jakub.kask.lab.business.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile ListAttribute<User, String> roles;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> login;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String ID = "id";
	public static final String LOGIN = "login";

}

