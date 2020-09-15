package jakub.kask.lab.business.entities;

import jakub.kask.lab.business.AgreementType;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Agreement.class)
public abstract class Agreement_ {

	public static volatile SingularAttribute<Agreement, AgreementType> agreementType;
	public static volatile SingularAttribute<Agreement, Date> endDate;
	public static volatile SingularAttribute<Agreement, Integer> id;
	public static volatile SingularAttribute<Agreement, Date> startDate;

	public static final String AGREEMENT_TYPE = "agreementType";
	public static final String END_DATE = "endDate";
	public static final String ID = "id";
	public static final String START_DATE = "startDate";

}

