package jakub.kask.lab.business.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Audit.class)
public abstract class Audit_ {

	public static volatile SingularAttribute<Audit, Date> modificationDate;
	public static volatile SingularAttribute<Audit, Date> creationDate;
	public static volatile SingularAttribute<Audit, Integer> version;

	public static final String MODIFICATION_DATE = "modificationDate";
	public static final String CREATION_DATE = "creationDate";
	public static final String VERSION = "version";

}

