package jakub.kask.lab.business.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Permission.class)
public abstract class Permission_ {

	public static volatile SingularAttribute<Permission, String> role;
	public static volatile SingularAttribute<Permission, String> permission;
	public static volatile SingularAttribute<Permission, Integer> id;
	public static volatile SingularAttribute<Permission, String> operation;

	public static final String ROLE = "role";
	public static final String PERMISSION = "permission";
	public static final String ID = "id";
	public static final String OPERATION = "operation";

}

